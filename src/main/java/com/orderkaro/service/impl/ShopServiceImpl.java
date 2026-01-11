package com.orderkaro.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderkaro.dao.ShopRepository;
import com.orderkaro.dto.CreateShopRequest;
import com.orderkaro.entity.Shop;
import com.orderkaro.enums.ShopStatus;
import com.orderkaro.exception.BusinessException;
import com.orderkaro.exception.ResourceNotFoundException;
import com.orderkaro.service.ShopService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public Shop createShop(CreateShopRequest request, UUID ownerId) {

        Shop shop = new Shop();
        shop.setName(request.getName());
        shop.setDescription(request.getDescription());
        shop.setOwnerId(ownerId);

        shop.setCity(request.getCity());
        shop.setArea(request.getArea());
        shop.setAddressLine(request.getAddressLine());
        shop.setLatitude(request.getLatitude());
        shop.setLongitude(request.getLongitude());
        shop.setDeliveryRadiusMeters(request.getDeliveryRadiusMeters());

        shop.setMinOrderAmount(request.getMinOrderAmount());
        shop.setAvgPreparationTimeMinutes(request.getAvgPreparationTimeMinutes());

        shop.setStatus(ShopStatus.ONBOARDING);
        shop.setActive(true);
        shop.setOpen(false);

        return shopRepository.save(shop);
    }

    @Override
    public void openShop(UUID shopId, UUID ownerId) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found"));

        if (!shop.getOwnerId().equals(ownerId)) {
            throw new BusinessException("Unauthorized shop access");
        }

        shop.open();
    }

    @Override
    public void closeShop(UUID shopId, UUID ownerId) {

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found"));

        if (!shop.getOwnerId().equals(ownerId)) {
            throw new BusinessException("Unauthorized shop access");
        }

        shop.close();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shop> getAllActiveShops() {
        return shopRepository.findByStatus(ShopStatus.APPROVED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Shop> getNearbyShops(Double lat, Double lng, Double radiusInKm) {
        return shopRepository.findShopsNear(lat, lng, radiusInKm);
    }
}
