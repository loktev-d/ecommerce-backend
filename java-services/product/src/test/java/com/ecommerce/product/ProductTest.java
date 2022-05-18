package com.ecommerce.product;

import com.ecommerce.product.dto.GetAllProductsResponse;
import com.ecommerce.product.dto.GetProductByIdResponse;
import com.ecommerce.product.dto.RegisterProductRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final String url = "/products";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductReposistory productReposistory;

    @Test
    void shouldRegisterProduct() throws Exception {
        var content = new RegisterProductRequest(
                "product name",
                "product image",
                "product description",
                1005.50f
        );

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(content)))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.id").isNotEmpty()
                );

        var result = productReposistory.findAll().stream().findFirst().get();

        assertThat(result.getName()).isEqualTo(content.name());
        assertThat(result.getImage()).isEqualTo(content.image());
        assertThat(result.getDescription()).isEqualTo(content.description());
        assertThat(result.getPrice()).isEqualTo(content.price());
    }

    @Test
    void shouldReturnErrorWhenRegisteringProduct() throws Exception {
        var content = new RegisterProductRequest(
                null,
                "product image",
                "product description",
                1005.50f
        );

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(content)))
                .andExpectAll(
                        status().isBadRequest()
                );
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        var products = productReposistory.saveAll(getMockValues());
        var response = new GetAllProductsResponse(products.toArray(new ProductModel[0]));

        mockMvc.perform(get(url))
                .andExpectAll(
                        status().isOk(),
                        content().json(mapper.writeValueAsString(response))
                );
    }

    @Test
    void shouldGetProductById() throws Exception {
        var product = ProductModel.builder()
                .name("name 1")
                .image("image 1")
                .description("desc 1")
                .price(100f)
                .build();

        var savedProduct = productReposistory.save(product);
        var response = new GetProductByIdResponse(savedProduct);

        mockMvc.perform(get(url + "/" + savedProduct.getId()))
                .andExpectAll(
                        status().isOk(),
                        content().json(mapper.writeValueAsString(response))
                );
    }

    @Test
    void shouldReturnErrorWhenGettingProductById() throws Exception {
        mockMvc.perform(get(url + "/" + UUID.randomUUID()))
                .andExpectAll(
                        status().isNotFound()
                );
    }

    private List<ProductModel> getMockValues() {
        var products = new ArrayList<ProductModel>();

        products.add(ProductModel.builder()
                .name("name 1")
                .image("image 1")
                .description("desc 1")
                .price(100f)
                .build());

        products.add(ProductModel.builder()
                .name("name 2")
                .image("image 2")
                .description("desc 2")
                .price(200f)
                .build());

        products.add(ProductModel.builder()
                .name("name 3")
                .image("image 3")
                .description("desc 3")
                .price(300f)
                .build());

        return products;
    }
}