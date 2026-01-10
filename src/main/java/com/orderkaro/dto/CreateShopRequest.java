package com.orderkaro.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateShopRequest {

    @NotBlank
    @Size(min = 3, max = 150)
    private String name;

    @Size(max = 255)
    private String description;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    private String area;

    @NotBlank
    @Size(max = 255)
    private String addressLine;

    @NotNull
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    private BigDecimal latitude;

    @NotNull
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
    private BigDecimal longitude;

    @NotNull
    @Min(100) // at least 100 meters
    private Integer deliveryRadiusMeters;

    @NotNull
    @Min(0)
    private Integer minOrderAmount;


    @NotNull
    @Min(1)
    @Max(240)
    private Integer avgPreparationTimeMinutes;
}