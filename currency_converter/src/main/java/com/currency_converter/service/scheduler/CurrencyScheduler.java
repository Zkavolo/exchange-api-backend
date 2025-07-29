package com.currency_converter.service.scheduler;

import com.currency_converter.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CurrencyScheduler {

    private final CurrencyService currencyService;

    public CurrencyScheduler(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostConstruct
    public void init() {
        // Run once immediately on startup
        currencyService.syncCurrencies();
    }

    @Scheduled(cron = "0 0 0 * * *") // every day at midnight (00:00)
    public void scheduledSync() {
        currencyService.syncCurrencies();
    }
}

