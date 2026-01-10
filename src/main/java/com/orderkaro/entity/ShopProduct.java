package com.orderkaro.entity;

import java.util.UUID;

import com.orderkaro.enums.ShopProductStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "shop_products",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_shop_product",
            columnNames = {"shop_id", "product_id"}
        )
    },
    indexes = {
        @Index(name = "idx_shop_product_shop", columnList = "shop_id"),
        @Index(name = "idx_shop_product_product", columnList = "product_id"),
        @Index(name = "idx_shop_product_status", columnList = "status"),
        @Index(name = "idx_shop_product_available", columnList = "is_available")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ShopProduct extends BaseEntity {

    @Column(name = "shop_id", nullable = false, columnDefinition = "uuid")
    private UUID shopId;

    @Column(name = "product_id", nullable = false, columnDefinition = "uuid")
    private UUID productId;

    /* ---------- Pricing ---------- */

    @Column(nullable = false)
    private Integer price; // paise
    
    @Column(name = "discounted_price")
    private Integer discountedPrice;


    /* ---------- Inventory ---------- */

    @Column(nullable = false)
    private Integer stock;


    /* ---------- Status ---------- */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ShopProductStatus status = ShopProductStatus.ACTIVE;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable = true;

    /* ---------- Shop specific ---------- */

    @Column(name = "display_name", length = 200)
    private String displayName;

    @Column(name = "shop_description", columnDefinition = "TEXT")
    private String shopDescription;

    @Column(nullable = false)
    private Integer maxOrderQuantity;
}
