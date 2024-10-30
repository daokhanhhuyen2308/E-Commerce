package com.t3h.e_commerce.service.impl;

import com.t3h.e_commerce.dto.requests.OrderCreationRequest;
import com.t3h.e_commerce.dto.responses.OrderResponse;
import com.t3h.e_commerce.entity.*;
import com.t3h.e_commerce.enums.OrderStatusType;
import com.t3h.e_commerce.enums.PaymentType;
import com.t3h.e_commerce.exception.CustomExceptionHandler;
import com.t3h.e_commerce.repository.CartItemRepository;
import com.t3h.e_commerce.repository.OrderRepository;
import com.t3h.e_commerce.service.IOrderService;
import com.t3h.e_commerce.service.IUserService;
import com.t3h.e_commerce.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final IUserService iUserService;


    @Override
    public OrderResponse orderProduct(OrderCreationRequest request) {

        UserEntity userLoggedIn = iUserService.getUserLoggedIn();

        if (userLoggedIn == null) {
            throw CustomExceptionHandler.unauthorizedException("User must be logged in before order any products");
        }

        List<CartItemEntity> cartItemEntities = userLoggedIn.getCart().getCartItems();

        // Lấy cart items từ repository dựa trên itemIds
        List<OrderItemEntity> orderItems = request.getItemIds().stream()
                .map(cartItemRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(cartItem -> {
                    if (!cartItemEntities.contains(cartItem)) {
                        throw CustomExceptionHandler.badRequestException("One or more cart items do not belong to the user");
                    }
                    return true;
                })
                .map(this::mapToOrderItemEntity)
                .collect(Collectors.toList());

        if (orderItems.isEmpty()) {
            throw CustomExceptionHandler.badRequestException("Bạn vẫn chưa chọn sản phẩm nào để mua.");
        }

        // Tính toán tổng giá tiền và phí vận chuyển
        BigDecimal totalPrice = orderItems.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //mặc định tiền ship se la 20
        BigDecimal shippingCost = BigDecimal.valueOf(20.0);

        // Giả định có một discount cố định (bạn có thể thay đổi theo logic cụ thể của bạn)
        BigDecimal discount = BigDecimal.valueOf(10.0); // Ví dụ: giảm giá 10.0

        // Tạo và lưu OrderEntity
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderItems(orderItems);
        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setShippingCost(shippingCost);
        orderEntity.setDiscount(discount); // Thiết lập discount
        orderEntity.setFinalPrice(totalPrice.add(shippingCost).subtract(discount)); // Tính final price
        orderEntity.setOrderStatus(OrderStatusType.Pending);
        orderEntity.setCreatedDate(LocalDateTime.now());
        orderEntity.setUser(userLoggedIn);
        //mặc định phương thức thanh toán là COD
        orderEntity.getPayment().setPaymentMethod(PaymentType.COD); // Thiết lập paymentMethod
        //mặc định trang thái giao hàng là pending
        orderEntity.getPayment().setPaymentStatus(PaymentStatus.PENDING);


        // Thêm tracking ID và ngày giao hàng dự kiến
        orderEntity.setTrackingId("TRACK123456");
        orderEntity.setExpectedDeliveryDate(Date.from(LocalDateTime.now().plusDays(5).atZone(ZoneId.systemDefault()).toInstant())); // Thiết lập ngày giao hàng dự kiến // Giao hàng trong 5 ngày

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // Trả về OrderResponse
        return mapToOrderResponse(savedOrder);
    }

    private OrderResponse mapToOrderResponse(OrderEntity orderEntity) {
        return OrderResponse.builder()
                .orderId(Long.valueOf(orderEntity.getId()))
                .totalQuantity(orderEntity.getOrderItems().size())
                .totalPrice(orderEntity.getTotalPrice())
                .shippingCost(orderEntity.getShippingCost())
                .discount(orderEntity.getDiscount().doubleValue()) // Thêm discount vào response
                .finalPrice(orderEntity.getFinalPrice().doubleValue()) // Thêm final price vào response
                .trackingId(orderEntity.getTrackingId()) // Thêm tracking ID vào response
                .expectedDeliveryDate(convertDateToLocalDateTime(orderEntity.getExpectedDeliveryDate())) // Chuyển đổi từ Date sang LocalDateTime // Thêm ngày giao hàng vào response
                .orderStatus(orderEntity.getOrderStatus().name())
                .orderPlacedBy(orderEntity.getUser().getLastName())
                .paymentStatus(orderEntity.getPayment().getPaymentStatus().name())
                .paymentMethod(orderEntity.getPayment().getPaymentMethod().name())
                .createdDate(orderEntity.getCreatedDate())
                .build();
    }
    private LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant() // Chuyển đổi Date thành Instant
                .atZone(ZoneId.systemDefault()) // Chọn múi giờ hiện tại
                .toLocalDateTime(); // Chuyển đổi thành LocalDateTime
    }

    private OrderItemEntity mapToOrderItemEntity(CartItemEntity cartItem) {
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getPrice());
        return orderItem;
    }

}
