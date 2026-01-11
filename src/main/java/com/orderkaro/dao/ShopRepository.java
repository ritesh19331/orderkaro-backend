package com.orderkaro.dao;

import java.util.List;
import java.util.UUID;

import com.orderkaro.enums.ShopStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.orderkaro.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, UUID> {

    List<Shop> findByOwnerId(UUID ownerId);

    List<Shop> findByStatus(ShopStatus status);

    // Haversine Formula for Nearest Shop
    // 6371 = Earth radius in km
    @Query(value = "SELECT *, " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:lon)) + sin(radians(:lat)) * sin(radians(latitude)))) AS distance " +
            "FROM shop " +
            "WHERE status = 'ACTIVE' AND is_open = true " +
            "ORDER BY distance ASC " +
            "LIMIT 1", nativeQuery = true)
    Shop findNearestShop(@Param("lat") Double lat, @Param("lon") Double lon);
}
