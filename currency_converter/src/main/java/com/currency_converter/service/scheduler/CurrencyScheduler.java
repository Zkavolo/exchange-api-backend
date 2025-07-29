package com.currency_converter.service.scheduler;

import com.currency_converter.service.CurrencyService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.currency_converter.util.constant.CurrencyConstant.SCHEDULE_CRON_TIME_FORMAT;

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

    @Scheduled(cron = "${app.schedule.cron}")
    public void scheduledSync() {
        currencyService.syncCurrencies();
    }
}

