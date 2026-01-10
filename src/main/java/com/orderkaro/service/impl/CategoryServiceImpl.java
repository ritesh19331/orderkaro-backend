package com.orderkaro.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderkaro.dao.CategoryRepository;
import com.orderkaro.dto.CategoryFilterRequest;
import com.orderkaro.dto.CategoryUpsertRequest;
import com.orderkaro.entity.Category;
import com.orderkaro.exception.ResourceNotFoundException;
import com.orderkaro.exception.ValidationException;
import com.orderkaro.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	// ========================================
	// CREATE
	// ========================================
	@Override
	public Category createCategory(CategoryUpsertRequest request) {
		// Validate unique name under same parent
		Optional<Category> existing = categoryRepository.findByNameAndParentId(request.getName(),
				request.getParentId());
		if (existing.isPresent()) {
			throw new ValidationException("Category with this name already exists under the same parent");
		}

		Category category = new Category();
		category.setName(request.getName());
		category.setSlug(generateSlug(request.getSlug(), request.getName()));
		category.setParentId(request.getParentId());
		category.setLevel(calculateLevel(request.getParentId()));
		category.setActive(request.isActive());

		return categoryRepository.save(category);
	}

	// ========================================
	// UPDATE
	// ========================================
	@Override
	public Category updateCategory(UUID categoryId, CategoryUpsertRequest request) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		// Optional: prevent moving category under itself
		if (request.getParentId() != null && request.getParentId().equals(categoryId)) {
			throw new ValidationException("Cannot set category as its own parent");
		}

		category.setName(request.getName());
		category.setSlug(generateSlug(request.getSlug(), request.getName()));
		category.setParentId(request.getParentId());
		category.setLevel(calculateLevel(request.getParentId()));
		category.setActive(request.isActive());

		return categoryRepository.save(category);
	}

	// ========================================
	// DELETE (soft delete)
	// ========================================
	@Override
	public void deleteCategory(UUID categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));

		// Soft delete: mark inactive
		category.setDeleted(true);
		categoryRepository.save(category);
	}

	// ========================================
	// READ
	// ========================================
	@Override
	@Transactional(readOnly = true)
	public Category getCategory(UUID categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Category> searchCategories(CategoryFilterRequest filters, Pageable pageable) {
		Specification<Category> spec = CategorySpecifications.withFilters(filters);
		return categoryRepository.findAll(spec, pageable);
	}

	// ========================================
	// HELPERS
	// ========================================

	private String generateSlug(String slug, String name) {
		if (slug != null && !slug.isBlank())
			return slug.toLowerCase().replace(" ", "-");
		return name.toLowerCase().replace(" ", "-");
	}

	private int calculateLevel(UUID parentId) {
		if (parentId == null)
			return 1;
		return categoryRepository.findById(parentId).map(parent -> parent.getLevel() + 1).orElse(1);
	}
}
