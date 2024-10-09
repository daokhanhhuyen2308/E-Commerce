package com.t3h.e_commerce.dto.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    String name;
    String image;
    String description;
    Double price;
    Integer quantity;
    Integer brandId;
    Integer categoryId;
}
