package com.orderkaro.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderkaro.dao.ShopRepository;
import com.orderkaro.entity.Shop;
import com.orderkaro.enums.ShopStatus;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopAdminService {

    private final ShopRepository shopRepository;

    @Transactional
    public void approveShop(UUID shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        shop.setStatus(ShopStatus.APPROVED);
        shop.setActive(true);
    }

    @Transactional
    public void rejectShop(UUID shopId) {
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        shop.setStatus(ShopStatus.REJECTED);
        shop.setActive(false);
    }
}
