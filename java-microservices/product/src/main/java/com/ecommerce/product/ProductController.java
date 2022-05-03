package com.ecommerce.product;

import com.ecommerce.product.dto.GetAllProductsResponse;
import com.ecommerce.product.dto.GetProductByIdResponse;
import com.ecommerce.product.dto.RegisterProductRequest;
import com.ecommerce.product.dto.RegisterProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
public record ProductController(ProductService productService) {

    @PostMapping
    public RegisterProductResponse registerProduct(@Valid @RequestBody RegisterProductRequest request) {
        log.info("Registrating new product {}", request);

        var product = productService.registerProduct(request);

        log.info("Registered new product with id = {}", product.getId());

        return new RegisterProductResponse(product.getId());
    }

    @GetMapping
    public GetAllProductsResponse getAllProducts() {
        log.info("Retrieving all products");

        var products = productService.getAllProducts();

        log.info("Retrieved {} products", products.size());

        return new GetAllProductsResponse(products.toArray(new ProductModel[0]));
    }

    @GetMapping("/{productId}")
    public GetProductByIdResponse getProductById(@PathVariable UUID productId) {
        log.info("Retrieving product by id = {}", productId);

        var product = productService.getProductById(productId);

        if (product.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with given Id not found");

        log.info("Retrieved product {} by id = {}", product, productId);

        return new GetProductByIdResponse(product.get());
    }
}
