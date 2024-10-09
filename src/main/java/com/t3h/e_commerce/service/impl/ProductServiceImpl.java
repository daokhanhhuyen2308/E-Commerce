package com.t3h.e_commerce.service.impl;

import com.t3h.e_commerce.dto.requests.ProductCreationRequest;
import com.t3h.e_commerce.dto.responses.ProductResponse;
import com.t3h.e_commerce.repository.BrandRepository;
import com.t3h.e_commerce.repository.CategoryRepository;
import com.t3h.e_commerce.repository.ProductRepository;
import com.t3h.e_commerce.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductResponse createProduct(ProductCreationRequest request) {
        return null;
    }
}
