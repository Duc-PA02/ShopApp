package com.example.shopappbackend.services.product;

import com.example.shopappbackend.responses.product.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductRedisService {
    void clear();//clear cache
    List<ProductResponse> getAllProducts(
            String keyword,
            Integer categoryId, PageRequest pageRequest) throws JsonProcessingException;
    void saveAllProducts(List<ProductResponse> productResponses,
                         String keyword,
                         Integer categoryId,
                         PageRequest pageRequest) throws JsonProcessingException;
}
