package com.ecommerce.product;

import com.ecommerce.product.dto.RegisterProductRequest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record ProductService(ProductReposistory productRepository) {

    public ProductModel registerProduct(RegisterProductRequest request) {
        var product = ProductModel.builder()
                .name(request.name())
                .image(request.image())
                .description(request.description())
                .price(request.price())
                .build();

        return productRepository.save(product);
    }

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductModel getProductById(UUID productId) {
        return productRepository.findById(productId).orElse(null);
    }
}
