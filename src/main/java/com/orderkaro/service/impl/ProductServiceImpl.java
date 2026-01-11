package com.orderkaro.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.orderkaro.dao.ProductRepository;
import com.orderkaro.dao.ShopProductRepository;
import com.orderkaro.dto.ProductFilterRequest;
import com.orderkaro.dto.ProductUpsertRequest;
import com.orderkaro.dto.ShopProductUpsertRequest;
import com.orderkaro.entity.Product;
import com.orderkaro.entity.ShopProduct;
import com.orderkaro.exception.ResourceNotFoundException;
import com.orderkaro.exception.ValidationException;
import com.orderkaro.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ShopProductRepository shopProductRepository;


    // =========================================================================
    // MASTER PRODUCT (GLOBAL CATALOG)
    // =========================================================================

    @Override
    public Product createMasterProduct(ProductUpsertRequest request) {

        // Validate uniqueness, brand rules, etc.
        productRepository.findByBarcode(request.getBarcode()).ifPresent(p -> {
            throw new ValidationException("Product with given barcode already exists");
        });

        Product product = new Product();
        product.setName(request.getName());
        product.setNormalizedName(request.getNormalizedName());
        product.setBrand(request.getBrand());
        product.setCategoryId(request.getCategoryId());
        product.setUnit(request.getUnit());
        product.setPackSize(request.getPackSize());
        product.setBarcode(request.getBarcode());
        product.setImageUrls(request.getImageUrls());
        product.setDescription(request.getDescription());
        product.setActive(request.isActive());

        return productRepository.save(product);
    }

    @Override
    public Product updateMasterProduct(UUID productId, ProductUpsertRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Optional: check soft-delete
        if (product.isDeleted()) {
            throw new ValidationException("Product already deleted");
        }

        product.setName(request.getName());
        product.setNormalizedName(request.getNormalizedName());
        product.setBrand(request.getBrand());
        product.setCategoryId(request.getCategoryId());
        product.setUnit(request.getUnit());
        product.setPackSize(request.getPackSize());
        product.setBarcode(request.getBarcode());
        product.setImageUrls(request.getImageUrls());
        product.setDescription(request.getDescription());

        return productRepository.save(product);
    }

    @Override
    public void deleteMasterProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
        // Will trigger @SQLDelete soft delete
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchMasterProducts(ProductFilterRequest filters, Pageable pageable) {
        String search = (filters.getSearch() != null && !filters.getSearch().isBlank()) ? filters.getSearch() : null;
        String categoryId = filters.getCategoryId();
        Boolean active = filters.getActive();

        return productRepository.searchProducts(search, categoryId, active, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getMasterProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


    // =========================================================================
    // SHOP PRODUCT (INVENTORY + PRICING)
    // =========================================================================

    @Override
    public ShopProduct addProductToShop(UUID shopId, UUID productId, ShopProductUpsertRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        shopProductRepository.findByShopIdAndProductId(shopId, productId)
                .ifPresent(p -> {
                    throw new ValidationException("Product already added to shop");
                });

        ShopProduct sp = new ShopProduct();
        sp.setShopId(shopId);
        sp.setProductId(product.getId());
        sp.setPrice(request.getPrice());
        sp.setStock(request.getStockQuantity());
        sp.setAvailable(request.getVisible()); // mapping visible -> isAvailable
//
//        // Optional fields if you add support
//        if (request.getDiscountedPrice() != null) {
//            sp.setDiscountedPrice(request.getDiscountedPrice());
//        }

        // Set default max order quantity if not provided
        sp.setMaxOrderQuantity(Math.max(1, request.getStockQuantity()));
        sp.setProduct(product); // Set for response

        return shopProductRepository.save(sp);
    }

    
    @Override
    public ShopProduct updateShopProduct(UUID shopId, UUID productId, ShopProductUpsertRequest request) {

        ShopProduct sp = shopProductRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop product not found"));

        sp.setPrice(request.getPrice());
        sp.setStock(request.getStockQuantity());
        sp.setAvailable(request.getVisible()); // mapping visible -> isAvailable

        return shopProductRepository.save(sp);
    }



    @Override
    public void deleteShopProduct(UUID shopId, UUID productId) {
        ShopProduct sp = shopProductRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop product not found"));

        shopProductRepository.delete(sp);
        // Will trigger @SQLDelete soft delete
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShopProduct> listShopProducts(UUID shopId, ProductFilterRequest filters, Pageable pageable) {
        
        Specification<ShopProduct> spec = (root, query, cb) -> {
            return cb.equal(root.get("shopId"), shopId);
        };
        
        Page<ShopProduct> page = shopProductRepository.findAll(spec, pageable);
        
        // Manual Fetch & Merge Logic
        Set<UUID> productIds = page.getContent().stream()
                .map(ShopProduct::getProductId)
                .collect(Collectors.toSet());
        
        if (!productIds.isEmpty()) {
            Map<UUID, Product> productMap = productRepository.findAllById(productIds).stream()
                    .collect(Collectors.toMap(Product::getId, Function.identity()));
            
            // Populate @Transient field
            page.getContent().forEach(sp -> {
                sp.setProduct(productMap.get(sp.getProductId()));
            });
        }

        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public ShopProduct getShopProduct(UUID shopId, UUID productId) {
        return shopProductRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop product not found"));
    }

    @Override
	public void updateShopProductStock(UUID shopId, UUID productId, Integer stock) {
        ShopProduct sp = shopProductRepository.findByShopIdAndProductId(shopId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop product not found"));

        if (stock < 0) {
            throw new ValidationException("Stock cannot be negative");
        }

        sp.setStock(stock);
        shopProductRepository.save(sp);
        // Optimistic lock handled via @Version in entity
    }
}
