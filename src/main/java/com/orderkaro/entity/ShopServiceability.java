package com.orderkaro.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shop_serviceability")
@Getter
@Setter
@NoArgsConstructor
public class ShopServiceability extends BaseEntity {

    @Column(
        name = "shop_id",
        nullable = false,
        columnDefinition = "uuid"
    )
    private UUID shopId; // Only store ID, no mapping

    @Column(name = "max_delivery_distance_meters", nullable = false)
    private Integer maxDeliveryDistanceMeters;

    @Column(name = "max_order_weight_kg", nullable = false)
    private Integer maxOrderWeightKg;
}
