package com.t3h.e_commerce.dto.responses;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Integer id;
    String name;
    String imageUrl;
    Double price;
    String description;
    BrandResponse brand;
    CategoryResponse category;
    Integer quantity;

}
