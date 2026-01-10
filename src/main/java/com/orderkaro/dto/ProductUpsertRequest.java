package com.orderkaro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Request DTO for creating or updating master products (catalog)
 */
@Data
public class ProductUpsertRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @Size(max = 200)
    private String brand;

    @NotNull
    private String categoryId;

    @Size(max = 30)
    private String unit; // kg, pcs, ltr

    @Size(max = 50)
    private String packSize; // 500g, 1kg, 1L

    @Size(max = 50)
    private String barcode; // unique UPC / EAN

    private String imageUrls; // JSON or CSV string

    private String description;
    
    private String normalizedName;

    private boolean active;
}
