package com.currency_converter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "currency")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Currency {
    @Id
    private String code;

    private Double rate;

    private String baseCurrency;
}
