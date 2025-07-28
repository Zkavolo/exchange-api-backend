package com.currency_converter.service;

import com.currency_converter.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyService {
    Page<Currency> getCurrencies(String name, Pageable pageable);
    void syncCurrencies();
}
