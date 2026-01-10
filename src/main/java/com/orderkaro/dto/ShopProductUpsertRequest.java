package com.orderkaro.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Request DTO for adding/updating shop inventory
 */
@Data
public class ShopProductUpsertRequest {

    @NotNull
    @Min(0)
    private Integer stockQuantity;

    @NotNull
    @Min(1)
    private Integer price;  // in smallest currency unit (e.g., paise / cents)

    private Integer discountedPrice;

    private Boolean visible = true;
}
