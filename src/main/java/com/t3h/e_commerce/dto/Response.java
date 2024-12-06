package com.t3h.e_commerce.dto;

import lombok.Data;

@Data
public class Response <T> {
     private int code;
     private String message;
     private T data;
}