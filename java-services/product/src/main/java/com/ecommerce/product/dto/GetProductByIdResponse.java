package com.ecommerce.product.dto;

import com.ecommerce.product.ProductModel;

public record GetProductByIdResponse(
        ProductModel product) {
}
