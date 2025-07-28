package com.currency_converter.util.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateDTOResponse {
    private String baseCode;
    private Map<String, Double> conversionRates;
}
