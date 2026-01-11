package com.orderkaro.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.orderkaro.entity.ShopProduct;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ShopProductRepository extends JpaRepository<ShopProduct, UUID>, JpaSpecificationExecutor<ShopProduct> {
    Optional<ShopProduct> findByShopIdAndProductId(UUID shopId, UUID productId);

    @Query(value = """
            SELECT sp.* FROM shop_products sp
            JOIN products p ON sp.product_id = p.id
            WHERE 
                sp.shop_id IN :shopIds 
                AND sp.status = 'ACTIVE' 
                AND sp.is_available = true 
                AND (
                    :query IS NULL 
                    OR (
                        p.search_vector @@ websearch_to_tsquery(:query)
                        OR p.search_vector @@ to_tsquery(regexp_replace(:query, '\\s+', ' & ', 'g') || ':*')
                        OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))
                        OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :query, '%'))
                        OR p.barcode = :query
                        OR similarity(p.name, :query) >= 0.3
                        OR similarity(p.brand, :query) >= 0.3
                    )
                )
            """, 
           nativeQuery = true)
    List<ShopProduct> searchProductsInShops(@Param("shopIds") List<UUID> shopIds, @Param("query") String query);
}
