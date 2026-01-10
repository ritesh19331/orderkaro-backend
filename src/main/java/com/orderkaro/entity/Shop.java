package com.orderkaro.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.orderkaro.enums.ShopStatus;
import com.orderkaro.exception.InvalidShopStateException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "shops",
    indexes = {
        @Index(name = "idx_shop_owner", columnList = "owner_id"),
        @Index(name = "idx_shop_city", columnList = "city"),
        @Index(name = "idx_shop_active", columnList = "is_active")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Shop extends BaseEntity {

    /* ===============================
       Business Identity
       =============================== */

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(
        name = "owner_id",
        nullable = false,
        columnDefinition = "uuid"
    )
    private UUID ownerId; // store only ID, no mapping

    /* ===============================
       Location
       =============================== */

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "area", nullable = false, length = 100)
    private String area;

    @Column(name = "address_line", nullable = false, length = 255)
    private String addressLine;

    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private BigDecimal longitude;

    @Column(name = "delivery_radius_meters", nullable = false)
    private Integer deliveryRadiusMeters;

    /* ===============================
       Operational Status
       =============================== */

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private ShopStatus status;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // admin toggle

    @Column(name = "is_open", nullable = false)
    private boolean isOpen = true; // real-time toggle

    /* ===============================
       Business Rules
       =============================== */

    @Column(name = "min_order_amount", nullable = false)
    private Integer minOrderAmount;

    @Column(name = "avg_preparation_time_minutes", nullable = false)
    private Integer avgPreparationTimeMinutes;

    /* ===============================
       Ratings & Metrics
       =============================== */

    @Column(name = "rating", nullable = false)
    private Double rating = 0.0;

    @Column(name = "rating_count", nullable = false)
    private Integer ratingCount = 0;

    /* ===============================
       State Rules
       =============================== */

    public void open() {
        if (!this.isActive) {
            throw new InvalidShopStateException("Inactive shop cannot be opened");
        }

        if (this.status != ShopStatus.APPROVED) {
            throw new InvalidShopStateException("Only approved shops can be opened");
        }

        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }

    public void deactivate() {
        this.isActive = false;
        this.isOpen = false;
    }

    public void activate() {
        this.isActive = true;
    }
}
