package com.orderkaro.service.impl;

import org.springframework.data.jpa.domain.Specification;

import com.orderkaro.dto.ProductFilterRequest;
import com.orderkaro.entity.Product;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecifications {

    public static Specification<Product> withFilters(ProductFilterRequest filters) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // 🔍 Full-text search
            if (filters.getSearch() != null && !filters.getSearch().isBlank()) {
                String like = "%" + filters.getSearch().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("brand")), like),
                        cb.like(cb.lower(root.get("barcode")), like)
                ));
            }

            // 📂 Category filter (string)
            if (filters.getCategoryId() != null && !filters.getCategoryId().isBlank()) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("categoryId"), filters.getCategoryId()));
            }

            // 🟢 Active filter
            if (filters.getActive() != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("active"), filters.getActive()));
            }

            return predicate;
        };
    }
}
