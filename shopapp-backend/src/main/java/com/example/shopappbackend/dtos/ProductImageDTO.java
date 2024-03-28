package com.example.shopappbackend.dtos;

import com.example.shopappbackend.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    @Min(value = 1)
    private int productId;
    @Size(min = 5, max = 200)
    @JsonProperty("image_url")
    private String imageUrl;
}
