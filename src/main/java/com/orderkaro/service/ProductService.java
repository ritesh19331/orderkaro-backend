package com.orderkaro.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.orderkaro.dto.ProductFilterRequest;
import com.orderkaro.dto.ProductUpsertRequest;
import com.orderkaro.dto.ShopProductUpsertRequest;
import com.orderkaro.entity.Product;
import com.orderkaro.entity.ShopProduct;

public interface ProductService {

	// Master catalog
	Product createMasterProduct(ProductUpsertRequest request);
	Product updateMasterProduct(UUID productId, ProductUpsertRequest request);
	void deleteMasterProduct(UUID productId);
	Page<Product> searchMasterProducts(ProductFilterRequest filters, Pageable pageable);
	Product getMasterProduct(UUID productId);

	// Shop-level
	ShopProduct addProductToShop(UUID shopId, UUID productId, ShopProductUpsertRequest request);
	ShopProduct updateShopProduct(UUID shopId, UUID productId, ShopProductUpsertRequest request);
	void deleteShopProduct(UUID shopId, UUID productId);

    // Reverted to Entity (populating transient field)
    Page<ShopProduct> listShopProducts(UUID shopId, ProductFilterRequest filters, Pageable pageable);

	ShopProduct getShopProduct(UUID shopId, UUID productId);
	void updateShopProductStock(UUID shopId, UUID productId, Integer stock);
}
