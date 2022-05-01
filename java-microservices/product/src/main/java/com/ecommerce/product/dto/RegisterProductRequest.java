package com.ecommerce.product.dto;

public record RegisterProductRequest(
        int id,
        String name,
        String image,
        String description,
        float price) {
}
