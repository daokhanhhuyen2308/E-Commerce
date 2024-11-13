package com.t3h.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity extends BaseEntity{
    String name;
    String image;
    BigDecimal price;
    String description;
    Integer quantity;
    String slug;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    BrandEntity brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    CategoryEntity category;

    @Column(name = "is_sold_out", nullable = false)
    boolean isSoldOut;

    @Column(name = "is_available", nullable = false)
    boolean isAvailable = true;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<OrderItemEntity> orderItems;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<CartItemEntity> cartItems;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    ProductStatusEntity status;

    public ProductEntity() {

    }

    public ProductEntity(Integer id, LocalDateTime createdDate,
                         String createdBy, LocalDateTime lastModifiedDate,
                         String lastModifiedBy, Boolean deleted,
                         String name, String image, BigDecimal price,
                         String description, Integer quantity,
                         BrandEntity brand, CategoryEntity category,
                         boolean isSoldOut, boolean isAvailable,
                         List<OrderItemEntity> orderItems, List<CartItemEntity> cartItems,
                         ProductStatusEntity status,
                         String slug) {
        super(id, createdDate, createdBy, lastModifiedDate, lastModifiedBy, deleted);
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.brand = brand;
        this.category = category;
        this.isSoldOut = isSoldOut;
        this.isAvailable = isAvailable;
        this.orderItems = orderItems;
        this.cartItems = cartItems;
        this.status = status;
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BrandEntity getBrand() {
        return brand;
    }

    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public boolean isSoldOut() {
        return isSoldOut;
    }

    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public List<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public ProductStatusEntity getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEntity status) {
        this.status = status;
    }
}
