package com.currency_converter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency",
        indexes = {
                @Index(name = "idx_currency_code", columnList = "code"),
                @Index(name = "idx_base_currency", columnList = "baseCurrency"),
                @Index(name = "idx_last_updated", columnList = "lastUpdated")
        },
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code", "base_currency"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String baseCurrency;

    @Column(nullable = false)
    private Double rate;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    public Currency(String code, Double rate, String baseCurrency) {
        this.code = code;
        this.rate = rate;
        this.baseCurrency = baseCurrency;
        this.lastUpdated = LocalDateTime.now();
    }

    @PrePersist
    @PreUpdate
    private void updateTimestamp() {
        this.lastUpdated = LocalDateTime.now();
    }
}