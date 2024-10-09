package com.t3h.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String phone;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<TokenEntity> tokens;

    @OneToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    CartEntity cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<RecipientEntity> recipients;

    @OneToMany(mappedBy = "payer", cascade = CascadeType.ALL)
    Set<PaymentEntity> payments;
}
