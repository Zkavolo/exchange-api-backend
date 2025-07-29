package com.currency_converter.util.constant;

import java.math.BigDecimal;
import java.util.*;

public class CurrencyConstant {

    // ========================== CURRENCY API CONFIGURATION ==========================

    /**
     *
     * You can change the base currencies as what you prefer
     */
    public static final List<String> BASE_CURRENCIES = Arrays.asList(
            "USD", // US Dollar
            "EUR", // Euro
            "GBP", // British Pound Sterling
            "JPY", // Japanese Yen
            "CHF", // Swiss Franc
            "CAD", // Canadian Dollar
            "AUD", // Australian Dollar
            "CNY",  // Chinese Yuan
            "IDR" // Indonesian Rupiah
    );

    // ========================== SCHEDULING CONFIGURATION ==========================

    /**
     * Default value is midnight at 00.00 value
     */
    public static final String SCHEDULE_CRON_TIME_FORMAT = "0 0 0 * * *";

    // ========================== API ENDPOINTS ==========================

    /**
     * ExchangeRate-API base URL
     */
    public static final String EXCHANGE_RATE_API_BASE_URL = "https://v6.exchangerate-api.com/v6/";

    /**
     * Endpoint for getting latest exchange rates
     */
    public static final String LATEST_RATES_ENDPOINT = "/latest/";

    // ========================== VALIDATION CONSTANTS ==========================

    /**
     * Maximum acceptable exchange rate (to detect API errors)
     */
    public static final double MAX_EXCHANGE_RATE = 1000000.0;

    /**
     * Minimum acceptable exchange rate (to detect API errors)
     */
    public static final double MIN_EXCHANGE_RATE = 0.000001;

    // ========================== UTILITY METHODS ==========================

    /**
     * Get API URL for latest rates with base currency
     */
    public static String getLatestRatesUrl(String apiKey, String baseCurrency) {
        return EXCHANGE_RATE_API_BASE_URL + apiKey + LATEST_RATES_ENDPOINT + baseCurrency;
    }

    /**
     * Check if exchange rate is valid
     */
    public static boolean isValidExchangeRate(Double rate) {
        return rate != null &&
                rate > MIN_EXCHANGE_RATE &&
                rate < MAX_EXCHANGE_RATE;
    }

    /**
     * Check if currency code is valid
     */
    public static boolean isValidCurrencyCode(String code) {
        return code != null && BASE_CURRENCIES.contains(code);
    }
}
