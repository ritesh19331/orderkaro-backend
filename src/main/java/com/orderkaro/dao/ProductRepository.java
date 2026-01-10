package com.orderkaro.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.orderkaro.entity.Product;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    boolean existsByBarcode(String barcode);
    Optional<Product> findByBarcode(String barcode);
    
//    @Query(value = """
//    	    SELECT *
//    	    FROM products p
//    	    WHERE
//    	        (
//    	            :search IS NULL
//    	            OR (
//    	                -- full text search with multi-word parsing
//    	                p.search_vector @@ websearch_to_tsquery(:search)
//
//    	                -- prefix matching (patanjali & ata:*)
//    	                OR p.search_vector @@ to_tsquery(
//    	                    regexp_replace(:search, '\\s+', ' & ', 'g') || ':*'
//    	                )
//
//    	                -- fallback substring matches
//    	                OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
//    	                OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))
//    	                OR p.barcode = :search
//    	            )
//    	        )
//    	        AND (:categoryId IS NULL OR p.category_id = :categoryId)
//    	        AND (:active IS NULL OR p.active = :active)
//    	    """,
//    	    countQuery = """
//    	    SELECT COUNT(*)
//    	    FROM products p
//    	    WHERE
//    	        (
//    	            :search IS NULL
//    	            OR (
//    	                p.search_vector @@ websearch_to_tsquery(:search)
//    	                OR p.search_vector @@ to_tsquery(
//    	                    regexp_replace(:search, '\\s+', ' & ', 'g') || ':*'
//    	                )
//    	                OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
//    	                OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))
//    	                OR p.barcode = :search
//    	            )
//    	        )
//    	        AND (:categoryId IS NULL OR p.category_id = :categoryId)
//    	        AND (:active IS NULL OR p.active = :active)
//    	    """,
//    	    nativeQuery = true)
//    	Page<Product> searchProducts(String search, String categoryId, Boolean active, Pageable pageable);
    
    @Query(value = """
    	    SELECT *
    	    FROM products p
    	    WHERE
    	        (
    	            :search IS NULL
    	            OR (
    	                -- full text fuzzy search with google-like syntax
    	                p.search_vector @@ websearch_to_tsquery(:search)

    	                -- prefix word matching
    	                OR p.search_vector @@ to_tsquery(
    	                    regexp_replace(:search, '\\s+', ' & ', 'g') || ':*'
    	                )

    	                -- substring fallback
    	                OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
    	                OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))

    	                -- exact barcode
    	                OR p.barcode = :search

    	                -- fuzzy/typo tolerant matching (pg_trgm)
    	                OR similarity(p.name, :search) >= 0.3
    	                OR similarity(p.brand, :search) >= 0.3
    	            )
    	        )
    	        AND (:categoryId IS NULL OR p.category_id = :categoryId)
    	        AND (:active IS NULL OR p.active = :active)
    	    """,
    	    countQuery = """
    	    SELECT COUNT(*)
    	    FROM products p
    	    WHERE
    	        (
    	            :search IS NULL
    	            OR (
    	                p.search_vector @@ websearch_to_tsquery(:search)
    	                OR p.search_vector @@ to_tsquery(
    	                    regexp_replace(:search, '\\s+', ' & ', 'g') || ':*'
    	                )
    	                OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%'))
    	                OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :search, '%'))
    	                OR p.barcode = :search
    	                OR similarity(p.name, :search) >= 0.3
    	                OR similarity(p.brand, :search) >= 0.3
    	            )
    	        )
    	        AND (:categoryId IS NULL OR p.category_id = :categoryId)
    	        AND (:active IS NULL OR p.active = :active)
    	    """,
    	    nativeQuery = true)
    	Page<Product> searchProducts(String search, String categoryId, Boolean active, Pageable pageable);

}
