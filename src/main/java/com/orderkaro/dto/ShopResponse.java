package com.orderkaro.dto;

import java.util.UUID;

import com.orderkaro.enums.ShopStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopResponse {

    private UUID id;
    private String name;
    private String city;
    private String area;
    private ShopStatus status;
    private boolean open;
    private boolean active;
}
