package com.t3h.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "recipient")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipientEntity extends BaseEntity{
    String recipientName;
    String phoneNumber;
    String recipientEmail;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    @ManyToMany
    @JoinTable(name = "recipient_addresses",
            joinColumns = @JoinColumn(name = "recipient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    List<AddressEntity> addresses;

    @OneToMany(mappedBy = "payee", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PaymentEntity> payments;

}
