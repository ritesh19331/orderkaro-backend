package com.orderkaro.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderkaro.dto.CreateShopRequest;
import com.orderkaro.dto.ShopResponse;
import com.orderkaro.entity.Shop;
import com.orderkaro.service.ShopService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @PostMapping
    public ResponseEntity<ShopResponse> createShop(
            @Valid @RequestBody CreateShopRequest request,
            @RequestHeader("X-OWNER-ID") UUID ownerId
    ) {
        Shop shop = shopService.createShop(request, ownerId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(shop));
    }

    @PostMapping("/{shopId}/open")
    public ResponseEntity<Void> openShop(
            @PathVariable UUID shopId,
            @RequestHeader("X-OWNER-ID") UUID ownerId
    ) {
        shopService.openShop(shopId, ownerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{shopId}/close")
    public ResponseEntity<Void> closeShop(
            @PathVariable UUID shopId,
            @RequestHeader("X-OWNER-ID") UUID ownerId
    ) {
        shopService.closeShop(shopId, ownerId);
        return ResponseEntity.noContent().build();
    }

    private ShopResponse toResponse(Shop shop) {
        return ShopResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .city(shop.getCity())
                .area(shop.getArea())
                .status(shop.getStatus())
                .open(shop.isOpen())
                .active(shop.isActive())
                .build();
    }
}
