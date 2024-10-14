package com.t3h.e_commerce.service.impl;

import com.t3h.e_commerce.dto.requests.ProductCreationRequest;
import com.t3h.e_commerce.dto.responses.ProductResponse;
import com.t3h.e_commerce.entity.BrandEntity;
import com.t3h.e_commerce.entity.CategoryEntity;
import com.t3h.e_commerce.entity.ProductEntity;
import com.t3h.e_commerce.entity.ProductStatusEntity;
import com.t3h.e_commerce.exception.CustomExceptionHandler;
import com.t3h.e_commerce.mapper.ProductMapper;
import com.t3h.e_commerce.repository.BrandRepository;
import com.t3h.e_commerce.repository.CategoryRepository;
import com.t3h.e_commerce.repository.ProductRepository;
import com.t3h.e_commerce.repository.ProductStatusRepository;
import com.t3h.e_commerce.service.IProductService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ProductStatusRepository productStatusRepository;

    @Override
    public ProductResponse createProduct(ProductCreationRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        if (StringUtils.isEmpty(request.getName())){
            throw CustomExceptionHandler.badRequestException("Product name is required");
        }

        if (request.getProductStatusCodeId() == null){
            throw CustomExceptionHandler.badRequestException("Product status is required");
        }

        if (request.getPrice() == null){
            throw CustomExceptionHandler.badRequestException("Product price is required");
        }

        if (request.getBrandId() == null){
            throw CustomExceptionHandler.badRequestException("Brand id is required");
        }

        if (request.getCategoryId() == null){
            throw CustomExceptionHandler.badRequestException("Category id is required");
        }

        if (request.getQuantity() == null){
            throw CustomExceptionHandler.badRequestException("Product quantity is required");
        }

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->  CustomExceptionHandler.notFoundException("Category not found"));

        BrandEntity brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> CustomExceptionHandler.notFoundException("Brand not found"));

        ProductStatusEntity status = productStatusRepository.findById(request.getProductStatusCodeId())
                .orElseThrow(() -> CustomExceptionHandler.notFoundException(""));


        ProductEntity product = ProductMapper.toProductEntity(request);
        product.setCategory(category);
        product.setBrand(brand);

        if (request.getQuantity() <= 0){
            product.setAvailable(false);
            product.setSoldOut(false);
        }

        product.setStatus(status);
        product.setCreatedDate(LocalDateTime.now());
        product.setCreatedBy(username);
        product.setLastModifiedDate(LocalDateTime.now());
        product.setLastModifiedBy(username);

        product = productRepository.save(product);

        return modelMapper.map(product, ProductResponse.class);
    }
}
