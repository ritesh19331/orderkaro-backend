package com.orderkaro.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orderkaro.dto.CategoryFilterRequest;
import com.orderkaro.dto.CategoryUpsertRequest;
import com.orderkaro.entity.Category;

public interface CategoryService {

    // Create or update
    Category createCategory(CategoryUpsertRequest request);
    Category updateCategory(UUID categoryId, CategoryUpsertRequest request);

    // Read
    Category getCategory(UUID categoryId);
    Page<Category> searchCategories(CategoryFilterRequest filters, Pageable pageable);

    // Delete (soft delete)
    void deleteCategory(UUID categoryId);
}
