package com.ecommerce.product.dto;

import javax.validation.constraints.NotBlank;

public record RegisterProductRequest(
        @NotBlank(message = "Name is required")
        String name,
        String image,
        @NotBlank(message = "Description is required")
        String description,
        float price) {
}
