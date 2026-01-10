package com.orderkaro.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orderkaro.dto.ProductFilterRequest;
import com.orderkaro.dto.ProductUpsertRequest;
import com.orderkaro.dto.ShopProductUpsertRequest;
import com.orderkaro.entity.Product;
import com.orderkaro.entity.ShopProduct;

/**
 * Enterprise-grade service for Product & ShopProduct management. Supports: -
 * Shop-level inventory & pricing - Soft delete - Pagination & filtering -
 * Multi-shop isolation - Optimistic locking for stock updates
 */
public interface ProductService {

	// -------------------------
	// Master catalog (global product)
	// -------------------------

	/**
	 * Create a new master product (catalog-level)
	 */
	Product createMasterProduct(ProductUpsertRequest request);

	/**
	 * Update master product
	 */
	Product updateMasterProduct(UUID productId, ProductUpsertRequest request);

	/**
	 * Soft delete master product
	 */
	void deleteMasterProduct(UUID productId);

	/**
	 * Search master products with filters & pagination
	 */
	Page<Product> searchMasterProducts(ProductFilterRequest filters, Pageable pageable);

	/**
	 * Get master product by ID
	 */
	Product getMasterProduct(UUID productId);

	// -------------------------
	// Shop-level inventory & listing
	// -------------------------

	/**
	 * Add product to shop listing with price, stock, visibility
	 */
	ShopProduct addProductToShop(UUID shopId, UUID productId, ShopProductUpsertRequest request);

	/**
	 * Update shop product (price, stock, visibility)
	 */
	ShopProduct updateShopProduct(UUID shopId, UUID productId, ShopProductUpsertRequest request);

	/**
	 * Soft delete shop product
	 */
	void deleteShopProduct(UUID shopId, UUID productId);

	/**
	 * List products for shop with filters and pagination
	 */
//    Page<ShopProduct> listShopProducts(UUID shopId, ProductFilterRequest filters, Pageable pageable);

	/**
	 * Get shop product by ID
	 */
	ShopProduct getShopProduct(UUID shopId, UUID productId);

	/**
	 * Update stock quantity in shop product (atomic / optimistic lock)
	 */
	void updateShopProductStock(UUID shopId, UUID productId, Integer stock);
}
