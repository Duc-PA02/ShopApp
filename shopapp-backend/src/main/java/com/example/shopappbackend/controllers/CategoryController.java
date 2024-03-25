package com.example.shopappbackend.controllers;

import com.example.shopappbackend.dtos.CategoryDTO;
import com.example.shopappbackend.models.Category;
import com.example.shopappbackend.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            categoryService.createCategory(categoryDTO);
            return ResponseEntity.ok("Create category successfully" + categoryDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategories(@RequestParam int page, @RequestParam int limit) {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    //update category by id
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id,@Valid @RequestBody CategoryDTO categoryDTO) {

        Category updatedCategory = categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok("category is updated: " + updatedCategory);
    }

    //delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("delete: " + id);
    }
}
