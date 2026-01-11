package com.orderkaro.service.impl;

import com.orderkaro.dao.ShopProductRepository;
import com.orderkaro.entity.Shop;
import com.orderkaro.entity.ShopProduct;
import com.orderkaro.service.SearchService;
import com.orderkaro.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ShopService shopService;
    private final ShopProductRepository shopProductRepository;

    @Override
    public List<Shop> searchShops(Double lat, Double lng, Double radiusInKm) {
        return shopService.getNearbyShops(lat, lng, radiusInKm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShopProduct> searchProducts(Double lat, Double lng, String query, Double radiusInKm) {
        List<Shop> nearbyShops = shopService.getNearbyShops(lat, lng, radiusInKm);
        
        if (nearbyShops.isEmpty()) {
            return Collections.emptyList();
        }

        List<UUID> shopIds = nearbyShops.stream()
                .map(Shop::getId)
                .collect(Collectors.toList());

        return shopProductRepository.searchProductsInShops(shopIds, query);
    }
}
