package com.orderkaro.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class CategoryUpsertRequest {
    private String name;
    private String slug; // optional
    private UUID parentId; // optional for root categories
    private boolean active = true;
}

