package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.CategoryDTO;
import com.example.shopappbackend.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category updateCategory(int id, CategoryDTO categoryDTO);
    void deleteCategory(int id);
    Category getCategoryById(int id);
    List<Category> getAllCategories();
}
