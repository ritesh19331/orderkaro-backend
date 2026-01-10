package com.orderkaro.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.orderkaro.dto.CategoryFilterRequest;
import com.orderkaro.dto.CategoryUpsertRequest;
import com.orderkaro.entity.Category;
import com.orderkaro.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Category Management", description = "Master Category APIs with hierarchy & filtering")
public class CategoryController {

    private final CategoryService categoryService;

    // ========================================
    // CREATE CATEGORY
    // ========================================
    @Operation(summary = "Create a new category")
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryUpsertRequest request) {
        Category created = categoryService.createCategory(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // ========================================
    // UPDATE CATEGORY
    // ========================================
    @Operation(summary = "Update an existing category")
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID categoryId,
                                                   @RequestBody CategoryUpsertRequest request) {
        Category updated = categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(updated);
    }

    // ========================================
    // GET CATEGORY
    // ========================================
    @Operation(summary = "Get category by ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable UUID categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    // ====================a====================
    // DELETE CATEGORY (soft delete)
    // ========================================
    @Operation(summary = "Soft-delete a category")
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }

    // ========================================
    // SEARCH & PAGINATION
    // ========================================
    @Operation(summary = "Search categories with filters & pagination")
    @PostMapping("/search")
    public ResponseEntity<Page<Category>> searchCategories(@RequestBody CategoryFilterRequest filters,
                                                           Pageable pageable) {
        Page<Category> page = categoryService.searchCategories(filters, pageable);
        return ResponseEntity.ok(page);
    }

    // ========================================
    // GET CHILDREN OF CATEGORY
    // ========================================
    @Operation(summary = "Get child categories of a parent category")
    @GetMapping("/{parentId}/children")
    public ResponseEntity<Page<Category>> getChildren(@PathVariable UUID parentId, Pageable pageable) {
        CategoryFilterRequest filter = new CategoryFilterRequest();
        filter.setParentId(parentId);
        filter.setActive(true);
        Page<Category> children = categoryService.searchCategories(filter, pageable);
        return ResponseEntity.ok(children);
    }
}
