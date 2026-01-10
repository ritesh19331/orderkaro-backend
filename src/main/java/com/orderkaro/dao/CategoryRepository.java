package com.orderkaro.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orderkaro.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>,
        JpaSpecificationExecutor<Category> {

    /**
     * Find category by name and parentId (for uniqueness validation)
     */
    Optional<Category> findByNameAndParentId(String name, UUID parentId);

    /**
     * Optional: find category by slug
     */
    Optional<Category> findBySlug(String slug);
}
