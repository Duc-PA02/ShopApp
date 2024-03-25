package com.example.shopappbackend.repositories;

import com.example.shopappbackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
