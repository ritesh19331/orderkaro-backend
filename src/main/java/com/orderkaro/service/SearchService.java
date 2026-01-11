package com.orderkaro.service;

import com.orderkaro.entity.Shop;
import com.orderkaro.entity.ShopProduct;
import java.util.List;

public interface SearchService {
    List<Shop> searchShops(Double lat, Double lng, Double radiusInKm);
    List<ShopProduct> searchProducts(Double lat, Double lng, String query, Double radiusInKm);
}
