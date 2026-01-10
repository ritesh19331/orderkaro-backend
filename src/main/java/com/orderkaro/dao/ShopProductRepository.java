package com.orderkaro.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.orderkaro.entity.ShopProduct;

public interface ShopProductRepository extends JpaRepository<ShopProduct, Long>, JpaSpecificationExecutor<ShopProduct> {
    Optional<ShopProduct> findByShopIdAndProductId(UUID shopId, Long productId);
    Optional<ShopProduct> findByShopIdAndProductId(UUID shopId, UUID productId);
//    Page<ShopProduct> searchShopProducts(UUID shopId, ProductFilterRequest filters, Pageable pageable);

}
