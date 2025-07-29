package com.currency_converter.service;

import com.currency_converter.model.Currency;
import com.currency_converter.util.dto.ConvertExchangeDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyService {
    Page<Currency> getCurrencies(String code, Pageable pageable);
    void syncCurrencies();
    ConvertExchangeDTOResponse convCurrencies(String baseCode, String target, Double value);
}