package com.orderkaro.dao;

import java.util.List;
import java.util.UUID;

import com.orderkaro.enums.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.orderkaro.entity.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShopRepository extends JpaRepository<Shop, UUID> {

    List<Shop> findByOwnerId(UUID ownerId);

    List<Shop> findByStatus(ShopStatus status);

    @Query(value = "SELECT *, " +
           "(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(latitude)))) AS distance " +
           "FROM shops " +
           "WHERE is_active = true AND is_open = true AND status = 'APPROVED' " +
           "HAVING distance < :radius " +
           "ORDER BY distance ASC", 
           nativeQuery = true)
    List<Shop> findShopsNear(@Param("lat") Double lat, 
                             @Param("lng") Double lng, 
                             @Param("radius") Double radiusInKm);
}
