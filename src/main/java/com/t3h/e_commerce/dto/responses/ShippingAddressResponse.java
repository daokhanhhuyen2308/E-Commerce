package com.t3h.e_commerce.dto.responses;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingAddressResponse {
    private String name;
    private String address;
    private String city;
    private String phoneNumber;
}