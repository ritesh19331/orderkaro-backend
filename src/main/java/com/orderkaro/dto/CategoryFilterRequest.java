package com.orderkaro.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class CategoryFilterRequest {
    private String name;
    private Boolean active;
    private UUID parentId;
}
