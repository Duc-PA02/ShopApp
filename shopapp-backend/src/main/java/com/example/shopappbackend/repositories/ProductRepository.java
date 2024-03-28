package com.example.shopappbackend.repositories;

import com.example.shopappbackend.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);
    Page<Product> findAll(Pageable pageable);
}
