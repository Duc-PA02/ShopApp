package com.example.shopappbackend.services;


import com.example.shopappbackend.dtos.ProductDTO;
import com.example.shopappbackend.dtos.ProductImageDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.models.Product;
import com.example.shopappbackend.models.ProductImage;
import com.example.shopappbackend.responses.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
    Product updateProduct(int id, ProductDTO productDTO) throws Exception;
    void deleteProduct(int id);
    Product getProductById(int id) throws Exception;
    Page<ProductResponse> getAllProduct(String keyword, int categoryId, PageRequest pageRequest);
    boolean existByName(String name);
    public ProductImage createProductImage(int productId, ProductImageDTO productImageDTO) throws Exception;
    List<Product> findProductsByIds(List<Integer> productIds);
}
