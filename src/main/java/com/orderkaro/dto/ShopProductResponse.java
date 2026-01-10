package com.orderkaro.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShopProductResponse {

    private UUID id;          // shopProduct id
    private Long productId;   // master product id
    private String productName;
    private String brand;
    private String packSize;
    private Integer price;
    private Integer discountedPrice;
    private Integer stockQuantity;
    private Boolean visible;
}
