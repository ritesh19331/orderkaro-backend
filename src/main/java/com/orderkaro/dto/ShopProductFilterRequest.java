package com.orderkaro.dto;

import lombok.Data;

/**
 * Filter DTO for shop products
 */
@Data
public class ShopProductFilterRequest {

    private String search;          // product name, brand, barcode
    private Long categoryId;        // master category filter
    private Boolean visible;        // only visible products
    private Integer minPrice;       // filter min price
    private Integer maxPrice;       // filter max price
    private String sortBy;          // optional sort field
    private String sortDirection;   // ASC / DESC
}
