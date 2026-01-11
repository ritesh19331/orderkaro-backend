package com.orderkaro.service;

import java.util.List;
import java.util.UUID;

import com.orderkaro.dto.CreateShopRequest;
import com.orderkaro.entity.Shop;

public interface ShopService {

    Shop createShop(CreateShopRequest request, UUID ownerId);

    void openShop(UUID shopId, UUID ownerId);

    void closeShop(UUID shopId, UUID ownerId);

    List<Shop> getAllActiveShops();

    // New Geolocation Method
    Shop findNearestShop(Double lat, Double lon);
}
