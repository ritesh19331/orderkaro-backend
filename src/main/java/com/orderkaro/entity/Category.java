package com.orderkaro.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "categories",
    indexes = {
        @Index(name = "idx_category_name", columnList = "name"),
        @Index(name = "idx_category_parent", columnList = "parent_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Category extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50, unique = true)
    private String slug; // optional for URL / search

    @Column(name = "parent_id", columnDefinition = "uuid")
    private UUID parentId; // hierarchy

    @Column(nullable = false)
    private int level; // root = 1

    @Column(name = "is_active", nullable = false)
    private boolean active = true;
}
