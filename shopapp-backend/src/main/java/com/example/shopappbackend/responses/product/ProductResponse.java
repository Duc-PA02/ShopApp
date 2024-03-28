package com.example.shopappbackend.responses.product;

import com.example.shopappbackend.models.Product;
import com.example.shopappbackend.responses.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private int categoryId;
    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse =  ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;
    }
}
