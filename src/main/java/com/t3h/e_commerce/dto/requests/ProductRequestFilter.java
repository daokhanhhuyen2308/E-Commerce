package com.t3h.e_commerce.dto.requests;


import java.math.BigDecimal;
import java.time.LocalDate;



public class ProductRequestFilter {
    private String name;
    private String category;
    private String brand;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private LocalDate fromDateQuery;
    private LocalDate toDateQuery;

    public ProductRequestFilter() {
    }

    public ProductRequestFilter(String name, String category, String brand, BigDecimal minPrice, BigDecimal maxPrice,
                                LocalDate fromDateQuery, LocalDate toDateQuery) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.fromDateQuery = fromDateQuery;
        this.toDateQuery = toDateQuery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public LocalDate getFromDateQuery() {
        return fromDateQuery;
    }

    public void setFromDateQuery(LocalDate fromDateQuery) {
        this.fromDateQuery = fromDateQuery;
    }

    public LocalDate getToDateQuery() {
        return toDateQuery;
    }

    public void setToDateQuery(LocalDate toDateQuery) {
        this.toDateQuery = toDateQuery;
    }
}
