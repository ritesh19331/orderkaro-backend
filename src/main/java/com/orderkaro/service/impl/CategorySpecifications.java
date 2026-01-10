package com.orderkaro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.orderkaro.dto.CategoryFilterRequest;
import com.orderkaro.entity.Category;

import jakarta.persistence.criteria.Predicate;

public class CategorySpecifications {

    /**
     * Build a Specification<Category> based on filters
     * Supports:
     * - name contains
     * - active status
     * - parentId
     */
    public static Specification<Category> withFilters(CategoryFilterRequest filters) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Filter by name (case-insensitive, contains)
            if (StringUtils.hasText(filters.getName())) {
                predicates.add(
                        cb.like(cb.lower(root.get("name")), "%" + filters.getName().toLowerCase() + "%")
                );
            }

            // Filter by active
            if (filters.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filters.getActive()));
            }

            // Filter by parentId
            if (filters.getParentId() != null) {
                predicates.add(cb.equal(root.get("parentId"), filters.getParentId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
