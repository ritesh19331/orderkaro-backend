package com.orderkaro.controller;

import com.orderkaro.entity.Shop;
import com.orderkaro.entity.ShopProduct;
import com.orderkaro.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/shops")
    public ResponseEntity<List<Shop>> searchShops(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam(value = "radius", defaultValue = "5.0") Double radius) {
        return ResponseEntity.ok(searchService.searchShops(lat, lng, radius));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ShopProduct>> searchProducts(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("q") String query,
            @RequestParam(value = "radius", defaultValue = "5.0") Double radius) {
        return ResponseEntity.ok(searchService.searchProducts(lat, lng, query, radius));
    }
}
