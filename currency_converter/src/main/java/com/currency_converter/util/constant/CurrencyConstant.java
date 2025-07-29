package com.currency_converter.util.constant;

import java.math.BigDecimal;
import java.util.*;

public class CurrencyConstant {
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

    // ========================== DATABASE CONFIGURATION ==========================

    /**
     * Number of currencies to process in each batch during database operations
     * Helps prevent database overload during bulk operations
     */
    public static final int BATCH_SIZE = 50;

    /**
     * Maximum number of currencies to save in a single transaction
     */
    public static final int MAX_TRANSACTION_SIZE = 100;

    // ========================== SCHEDULING CONFIGURATION ==========================

    /**
     * Maximum age of currency data before triggering automatic sync (in hours)
     */
    public static final int MAX_DATA_AGE_HOURS = 24;

    /**
     * Minimum number of currencies expected in database
     */
    public static final int MIN_CURRENCIES_THRESHOLD = 50;

    /**
     * Delay between API calls to respect rate limits (in milliseconds)
     */
    public static final long API_CALL_DELAY_MS = 100;

    /**
     * Delay between database batch operations (in milliseconds)
     */
    public static final long BATCH_PROCESSING_DELAY_MS = 50;

    // ========================== API ENDPOINTS ==========================

    /**
     * ExchangeRate-API base URL
     */
    public static final String EXCHANGE_RATE_API_BASE_URL = "https://v6.exchangerate-api.com/v6/";

    /**
     * Endpoint for getting latest exchange rates
     */
    public static final String LATEST_RATES_ENDPOINT = "/latest/";

    /**
     * Endpoint for getting supported currency codes
     */
    public static final String SUPPORTED_CODES_ENDPOINT = "/codes";

    /**
     * Endpoint for getting API quota information
     */
    public static final String QUOTA_ENDPOINT = "/quota";

    // ========================== VALIDATION CONSTANTS ==========================

    /**
     * Maximum acceptable exchange rate (to detect API errors)
     */
    public static final double MAX_EXCHANGE_RATE = 1000000.0;

    /**
     * Minimum acceptable exchange rate (to detect API errors)
     */
    public static final double MIN_EXCHANGE_RATE = 0.000001;

    /**
     * Maximum retries for failed API calls
     */
    public static final int MAX_API_RETRIES = 3;

    /**
     * Timeout for API calls (in milliseconds)
     */
    public static final int API_TIMEOUT_MS = 10000;

    // ========================== UTILITY METHODS ==========================

    /**
     * Get API URL for latest rates with base currency
     */
    public static String getLatestRatesUrl(String apiKey, String baseCurrency) {
        return EXCHANGE_RATE_API_BASE_URL + apiKey + LATEST_RATES_ENDPOINT + baseCurrency;
    }

    /**
     * Get API URL for supported codes
     */
    public static String getSupportedCodesUrl(String apiKey) {
        return EXCHANGE_RATE_API_BASE_URL + apiKey + SUPPORTED_CODES_ENDPOINT;
    }

    /**
     * Get API URL for quota information
     */
    public static String getQuotaUrl(String apiKey) {
        return EXCHANGE_RATE_API_BASE_URL + apiKey + QUOTA_ENDPOINT;
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
