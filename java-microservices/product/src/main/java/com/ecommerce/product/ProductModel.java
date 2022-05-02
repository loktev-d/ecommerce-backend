package com.ecommerce.product;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductModel {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    private String image;

    @NotBlank
    private String description;

    private float price;
}
