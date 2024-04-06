package com.example.shopappbackend.repositories;

import com.example.shopappbackend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%) " +
            "AND (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) ")
    Page<Product> searchProducts
            (@Param("keyword") String keyword,
             @Param("categoryId") int categoryId,
             Pageable pageable);
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
    Optional<Product> getDetailProduct(@Param("productId") int productId);
}
