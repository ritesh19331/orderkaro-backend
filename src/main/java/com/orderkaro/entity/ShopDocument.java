package com.orderkaro.entity;

import java.util.UUID;

import com.orderkaro.enums.DocumentType;
import com.orderkaro.enums.VerificationStatus;

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
    name = "shop_documents",
    indexes = {
        @Index(name = "idx_shop_document_shop", columnList = "shop_id"),
        @Index(name = "idx_shop_document_status", columnList = "status")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ShopDocument extends BaseEntity {

    @Column(name = "shop_id", nullable = false, columnDefinition = "uuid")
    private UUID shopId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DocumentType type;

    @Column(name = "document_url", nullable = false, length = 500)
    private String documentUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private VerificationStatus status;
}
