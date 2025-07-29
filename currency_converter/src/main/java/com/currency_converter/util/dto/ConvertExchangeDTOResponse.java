package com.currency_converter.util.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvertExchangeDTOResponse {
    private LocalDateTime lastUpdated;
    private List<CurrencyValue> values;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CurrencyValue {
        private String name;
        private Double baseValue;
        private Double convertedValue;
    }
}

