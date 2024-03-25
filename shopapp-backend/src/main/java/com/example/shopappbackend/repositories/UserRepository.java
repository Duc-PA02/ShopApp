package com.example.shopappbackend.repositories;

import com.example.shopappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
