package com.orderkaro.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orderkaro.dto.ProductFilterRequest;
import com.orderkaro.dto.ProductSearchRequest;
import com.orderkaro.dto.ProductUpsertRequest;
import com.orderkaro.dto.ShopProductUpsertRequest;
import com.orderkaro.entity.Product;
import com.orderkaro.entity.ShopProduct;
import com.orderkaro.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated
@Tag(name = "Product Management", description = "Master Product & Shop Product APIs")
public class ProductController {

    private final ProductService productService;

    // =========================================
    // MASTER PRODUCT (GLOBAL CATALOG)
    // =========================================

    @Operation(summary = "Create a new master product")
    @PostMapping("/master")
    public ResponseEntity<Product> createMaster(@RequestBody ProductUpsertRequest request) {
        Product created = productService.createMasterProduct(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing master product")
    @PutMapping("/master/{productId}")
    public ResponseEntity<Product> updateMaster(@PathVariable UUID productId,
                                                @RequestBody ProductUpsertRequest request) {
        Product updated = productService.updateMasterProduct(productId, request);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Get master product by ID")
    @GetMapping("/master/{productId}")
    public ResponseEntity<Product> getMaster(@PathVariable UUID productId) {
        Product product = productService.getMasterProduct(productId);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Delete a master product")
    @DeleteMapping("/master/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMaster(@PathVariable UUID productId) {
        productService.deleteMasterProduct(productId);
    }

//    @Operation(summary = "Search master products with filters, pagination, and sorting")
//    @PostMapping("/master/search")
//    public ResponseEntity<Page<Product>> searchMaster(@RequestBody ProductSearchRequest request) {
//
//        // Build Sort safely
//        Sort sort = Sort.by(
//            request.getSort() != null
//                ? request.getSort().stream()
//                    .map(s -> {
//                        String[] parts = s.split(",");
//                        if (parts.length != 2) parts = new String[]{parts[0], "asc"}; // default to asc
//                        return new Sort.Order(Sort.Direction.fromString(parts[1].trim()), parts[0].trim());
//                    })
//                    .collect(Collectors.toList())
//                : List.of()
//        );
//
//        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
//
//        Page<Product> products = productService.searchMasterProducts(request.getFilters(), pageable);
//
//        return ResponseEntity.ok(products);
//    }
    
    @Operation(summary = "Search master products with filters, pagination, and sorting")
    @PostMapping("/master/search")
    public ResponseEntity<Page<Product>> searchMaster(@RequestBody ProductSearchRequest request) {

        // Build Sort
        Sort sort = Sort.by(
            request.getSort() != null
                ? request.getSort().stream()
                    .map(s -> {
                        String[] parts = s.split(",");
                        String property = parts[0].trim();
                        String direction = parts.length > 1 ? parts[1].trim() : "asc";
                        return new Sort.Order(Sort.Direction.fromString(direction), property);
                    })
                    .collect(Collectors.toList())
                : List.of()
        );

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        ProductFilterRequest filters = request.getFilters() != null ? request.getFilters() : new ProductFilterRequest();

        Page<Product> products = productService.searchMasterProducts(filters, pageable);

        return ResponseEntity.ok(products);
    }

    // =========================================
    // SHOP PRODUCT (INVENTORY + PRICING)
    // =========================================

    @Operation(summary = "Add a product to shop inventory")
    @PostMapping("/{shopId}/inventory/{productId}")
    public ResponseEntity<ShopProduct> addToShop(@PathVariable UUID shopId,
                                                 @PathVariable UUID productId,
                                                 @RequestBody ShopProductUpsertRequest request) {
        ShopProduct sp = productService.addProductToShop(shopId, productId, request);
        return new ResponseEntity<>(sp, HttpStatus.CREATED);
    }

    @Operation(summary = "Update shop product details")
    @PutMapping("/{shopId}/inventory/{productId}")
    public ResponseEntity<ShopProduct> updateShopProduct(@PathVariable UUID shopId,
                                                         @PathVariable UUID productId,
                                                         @RequestBody ShopProductUpsertRequest request) {
        ShopProduct sp = productService.updateShopProduct(shopId, productId, request);
        return ResponseEntity.ok(sp);
    }

    @Operation(summary = "Update stock quantity of shop product")
    @PatchMapping("/{shopId}/inventory/{productId}/stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStock(@PathVariable UUID shopId,
                            @PathVariable UUID productId,
                            @RequestParam Integer stock) {
        productService.updateShopProductStock(shopId, productId, stock);
    }

    @Operation(summary = "Get a shop product by ID")
    @GetMapping("/{shopId}/inventory/{productId}")
    public ResponseEntity<ShopProduct> getShopProduct(@PathVariable UUID shopId,
                                                      @PathVariable UUID productId) {
        ShopProduct sp = productService.getShopProduct(shopId, productId);
        return ResponseEntity.ok(sp);
    }

    @Operation(summary = "Delete a shop product")
    @DeleteMapping("/{shopId}/inventory/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShopProduct(@PathVariable UUID shopId,
                                  @PathVariable UUID productId) {
        productService.deleteShopProduct(shopId, productId);
    }
}
