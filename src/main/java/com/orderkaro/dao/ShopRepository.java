package com.orderkaro.dao;

import java.util.List;
import java.util.UUID;

import com.orderkaro.enums.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orderkaro.entity.Shop;
public interface ShopRepository extends JpaRepository<Shop, UUID> {

    List<Shop> findByOwnerId(UUID ownerId);

    List<Shop> findByStatus(ShopStatus status);
}
