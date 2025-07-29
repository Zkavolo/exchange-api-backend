package com.currency_converter.service.impl;

import com.currency_converter.util.constant.CurrencyConstant;
import com.currency_converter.model.Currency;
import com.currency_converter.repository.CurrencyRepository;
import com.currency_converter.service.CurrencyService;
import com.currency_converter.util.dto.ConvertExchangeDTOResponse;
import com.currency_converter.util.dto.ConvertExchangeDTOResponse.CurrencyValue;
import com.currency_converter.util.dto.ExchangeRateDTOResponse;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final RestTemplate restTemplate;

    @Value("${exchangerate.api.key}")
    private String apiKey;

    @Override
    public Page<Currency> getCurrencies(String code, Pageable pageable) {
        if (code == null || code.trim().isEmpty()) {
            return currencyRepository.findAll(pageable);
        }
        return currencyRepository.findByCodeContainingIgnoreCase(code.trim(), pageable);
    }

    @Override
    @Transactional
    public void syncCurrencies() {
        try {
            System.out.println("Starting comprehensive currency sync at: " + LocalDateTime.now());

            Map<String, Currency> allCurrencies = new HashMap<>();
            List<Currency> existing = currencyRepository.findAll();
            Map<String, Currency> existingMap = existing.stream()
                    .collect(Collectors.toMap(
                            c -> c.getCode() + "_" + c.getBaseCurrency(),
                            c -> c
                    ));

            for (String baseCurrency : CurrencyConstant.BASE_CURRENCIES) {
                Map<String, Currency> currencies = syncWithBaseCurrency(baseCurrency);
                for (Currency newCurrency : currencies.values()) {
                    String key = newCurrency.getCode() + "_" + newCurrency.getBaseCurrency();
                    if (existingMap.containsKey(key)) {
                        newCurrency.setId(existingMap.get(key).getId());
                    }
                    allCurrencies.put(key, newCurrency);
                }
            }

            currencyRepository.saveAll(allCurrencies.values());
            System.out.println("Successfully synced " + allCurrencies.size() + " currencies");
        } catch (Exception e) {
            System.err.println("Error during comprehensive currency sync: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to sync currencies: " + e.getMessage(), e);
        }
    }


    private Map<String, Currency> syncWithBaseCurrency(String baseCurrency) {
        Map<String, Currency> currencies = new HashMap<>();

        try {
            String url = CurrencyConstant.getLatestRatesUrl(apiKey, baseCurrency);
            ResponseEntity<ExchangeRateDTOResponse> response = restTemplate.getForEntity(url, ExchangeRateDTOResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                ExchangeRateDTOResponse data = response.getBody();

                if (data.getConversionRates() != null) {
                    currencies.put(baseCurrency, new Currency(baseCurrency, 1.0, baseCurrency));
                    data.getConversionRates().entrySet().forEach(entry -> {
                        String code = entry.getKey();
                        Double rate = entry.getValue();

                        if (CurrencyConstant.isValidExchangeRate(rate) && CurrencyConstant.isValidCurrencyCode(code)) {
                            Currency currency = currencyRepository
                                    .findByBaseCurrencyAndCode(baseCurrency, code)
                                    .orElse(new Currency(code, rate, baseCurrency));
                            currency.setRate(rate);
                            currency.setLastUpdated(LocalDateTime.now());

                            currencies.put(code + "_" + baseCurrency, currency);
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to sync with base currency " + baseCurrency + ": " + e.getMessage());
        }
        System.out.println("From sync WithBaseCurrency : "+currencies);
        return currencies;
    }

    @Override
    public ConvertExchangeDTOResponse convCurrencies(String baseCode, String target, Double value) {
        Currency currency = currencyRepository.findByBaseCurrencyAndCode(baseCode, target)
                .orElseThrow(()-> new RuntimeException("baseCode or code not found"));

        List<CurrencyValue> currencyList = new ArrayList<>();
        currencyList.add(new CurrencyValue(currency.getCode(), value, value));

        Double rate = currency.getRate();
        Double converted = value * rate;
        currencyList.add(new CurrencyValue(currency.getBaseCurrency(), currency.getRate(), converted));
        ConvertExchangeDTOResponse result = new ConvertExchangeDTOResponse(
                currency.getLastUpdated(),
                currencyList
                );
        return result;
    }

    public long getCurrencyCount() {
        return currencyRepository.count();
    }
}