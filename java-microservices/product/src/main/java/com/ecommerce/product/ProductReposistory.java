package com.ecommerce.product;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReposistory extends JpaRepository<ProductModel, UUID> {

}
