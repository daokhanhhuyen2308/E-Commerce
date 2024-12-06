package com.t3h.e_commerce.dto.requests;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToWishlistRequest {
    private Integer userId;
    private Integer productId;
    private Integer colorId; // Thêm colorId
    private Integer sizeId;  // Thêm sizeId
}
