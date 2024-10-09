package com.t3h.e_commerce.entity;

import com.t3h.e_commerce.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenEntity extends BaseEntity{

    String token;

    boolean isRevoked;

    boolean isExpired;

    @Enumerated(EnumType.STRING)
    TokenType tokenType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

}
