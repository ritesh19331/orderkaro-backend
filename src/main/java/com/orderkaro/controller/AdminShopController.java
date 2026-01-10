package com.orderkaro.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.orderkaro.service.ShopAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/shops")
@RequiredArgsConstructor
public class AdminShopController {

    private final ShopAdminService shopAdminService;

    @PostMapping("/{shopId}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID shopId) {
        shopAdminService.approveShop(shopId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{shopId}/reject")
    public ResponseEntity<Void> reject(@PathVariable UUID shopId) {
        shopAdminService.rejectShop(shopId);
        return ResponseEntity.noContent().build();
    }
}
