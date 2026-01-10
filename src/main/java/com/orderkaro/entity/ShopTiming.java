package com.orderkaro.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "shop_timings",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_shop_day",
            columnNames = {"shop_id", "day_of_week"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class ShopTiming extends BaseEntity {

    @Column(
        name = "shop_id",
        nullable = false,
        columnDefinition = "uuid"
    )
    private UUID shopId; // Only ID stored, no mapping

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 15)
    private DayOfWeek dayOfWeek;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed = false;
}
