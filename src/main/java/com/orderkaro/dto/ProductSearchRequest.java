package com.orderkaro.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductSearchRequest {
    private ProductFilterRequest filters;
    private int page = 0;
    private int size = 20;
    private List<String> sort; // format: ["name,asc", "price,desc"]
}
