package com.t3h.e_commerce.mapper;

import com.t3h.e_commerce.dto.responses.UserResponse;
import com.t3h.e_commerce.entity.RoleEntity;
import com.t3h.e_commerce.entity.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toUserResponse(UserEntity user){
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()))
                .build();
    }
 }
