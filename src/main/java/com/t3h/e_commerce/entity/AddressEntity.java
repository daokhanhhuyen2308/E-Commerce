package com.t3h.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressEntity extends BaseEntity{
    String street;
    String city;
    String state;
    String zipCode;

    @ManyToMany(mappedBy = "addresses", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE})
    List<RecipientEntity> recipients;
}
