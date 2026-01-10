package com.orderkaro.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String brand;
    private Long categoryId;
    private String unit;
    private String packSize;
    private String barcode;
    private String imageUrls;
    private String description;
    private boolean active;
}
