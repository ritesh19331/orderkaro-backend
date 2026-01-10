package com.orderkaro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "products",
    indexes = {
        @Index(name = "idx_product_normalized_name", columnList = "normalized_name"),
        @Index(name = "idx_product_barcode", columnList = "barcode"),
        @Index(name = "idx_product_category", columnList = "category_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_product_barcode", columnNames = "barcode")
    }
)
@Getter
@Setter
public class Product extends BaseEntity {

    /* ---------- Core Identity ---------- */

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 200, name = "normalized_name")
    private String normalizedName;

    @Column(length = 100)
    private String brand;

    /* ---------- Classification (ID ONLY) ---------- */

    @Column(nullable = false, name = "category_id")
    private String categoryId;   // directly store category id

    /* ---------- SKU / Packaging ---------- */

    @Column(length = 30)
    private String unit;        // kg, g, ltr, pcs

    @Column(length = 50, name = "pack_size")
    private String packSize;    // 1kg, 500g, 1L

    @Column(length = 50, unique = true)
    private String barcode;     // UPC / EAN

    /* ---------- Media (NO ELEMENT COLLECTION) ---------- */

    @Column(columnDefinition = "TEXT", name = "image_urls")
    private String imageUrls;   // JSON or CSV string

    /* ---------- Description ---------- */

    @Column(columnDefinition = "TEXT")
    private String description;

    /* ---------- Status ---------- */

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "search_vector", columnDefinition = "tsvector")
    private String searchVector;

}
