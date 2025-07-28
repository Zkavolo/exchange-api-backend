package com.currency_converter.service.impl;

import com.currency_converter.model.Currency;
import com.currency_converter.repository.CurrencyRepository;
import com.currency_converter.service.CurrencyService;
import com.currency_converter.util.dto.ExchangeRateDTOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Override
    public Page<Currency> getCurrencies(String name, Pageable pageable) {
        return currencyRepository.findByName(name, pageable);
    }

    @Override
    public void syncCurrencies() {
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        ResponseEntity<ExchangeRateDTOResponse> response = restTemplate.getForEntity(url, ExchangeRateDTOResponse.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            ExchangeRateDTOResponse data = response.getBody();
            List<Currency> currencies = data.getConversionRates().entrySet().stream()
                    .map(entry -> new Currency(entry.getKey(), entry.getValue(), data.getBaseCode()))
                    .toList();

            currencyRepository.saveAll(currencies);
        }
    }
}
