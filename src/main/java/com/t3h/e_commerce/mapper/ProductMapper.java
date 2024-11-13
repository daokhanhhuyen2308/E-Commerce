package com.t3h.e_commerce.mapper;

import com.t3h.e_commerce.dto.requests.ProductCreationRequest;
import com.t3h.e_commerce.dto.responses.BrandResponse;
import com.t3h.e_commerce.dto.responses.CategoryResponse;
import com.t3h.e_commerce.dto.responses.ProductResponse;
import com.t3h.e_commerce.entity.ProductEntity;


public class ProductMapper {
    public static ProductResponse toProductResponse(ProductEntity product){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDescription(product.getDescription());
        productResponse.setImageUrl(product.getImage());
        productResponse.setAvailable(product.isAvailable());
        productResponse.setDeleted(false);
        productResponse.setSoldOut(product.isSoldOut());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setProductStatus(String.valueOf(product.getStatus()));

        if(product.getBrand() != null){
            BrandResponse brandResponse = new BrandResponse();
            brandResponse.setId(product.getBrand().getId());
            brandResponse.setName(product.getBrand().getCode());
            brandResponse.setDescription(product.getBrand().getDescription());
            productResponse.setBrand(brandResponse);
        }

        if (product.getCategory() != null){
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setId(product.getCategory().getId());
            categoryResponse.setName(product.getCategory().getCode());
            categoryResponse.setDescription(product.getCategory().getDescription());
            productResponse.setCategory(categoryResponse);
        }

        productResponse.setCreatedDate(product.getCreatedDate());
        productResponse.setCreatedBy(product.getCreatedBy());
        productResponse.setLastModifiedDate(product.getLastModifiedDate());
        productResponse.setLastModifiedBy(product.getLastModifiedBy());
        productResponse.setSlug(product.getSlug());
        return productResponse;
    }

    public static ProductEntity toProductEntity(ProductCreationRequest request){
        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setImage(request.getImage());
        product.setQuantity(request.getQuantity());
        return product;
    }

}
