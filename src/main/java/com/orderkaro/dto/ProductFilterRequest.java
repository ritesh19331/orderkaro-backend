package com.orderkaro.dto;

import java.util.UUID;

import lombok.Data;

/**
 * Filter DTO for master product search
 */
@Data
public class ProductFilterRequest {

    private String search;        // search by name/brand/barcode
    private String categoryId;      // filter by category
    private Boolean active;       // filter active/inactive
}
