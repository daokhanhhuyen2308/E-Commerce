package com.t3h.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends BaseEntity{
    String name;
    String image;
    BigDecimal price;
    String description;
    Integer quantity;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    CategoryEntity category;

    @Column(name = "is_sold_out", nullable = false)
    boolean isSoldOut;

    @Column(name = "is_available", nullable = false)
    boolean isAvailable = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderItemEntity> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<CartItemEntity> cartItems;
}
