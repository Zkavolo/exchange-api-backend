package com.currency_converter.controller;

import com.currency_converter.model.Currency;
import com.currency_converter.service.CurrencyService;
import com.currency_converter.service.impl.CurrencyServiceImpl;
import com.currency_converter.util.dto.ConvertExchangeDTOResponse;
import com.currency_converter.util.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CurrencyServiceImpl currencyServiceImpl; // For additional methods

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Currency>>> getCurrencies(
            @RequestParam(defaultValue = "") String code,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Currency> result = currencyService.getCurrencies(code, pageable);
        ApiResponse<Page<Currency>> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Fetched successfully");
        response.setData(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getCurrencyCount() {
        long count = currencyServiceImpl.getCurrencyCount();
        ApiResponse<Long> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage(count > 0 ? "Currency count retrieved" : "Currency count is Empty");
        response.setData(count);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<ApiResponse<String>> getStatus() {
        long count = currencyServiceImpl.getCurrencyCount();
        ApiResponse<String> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Status retrieved");
        response.setData(count > 0 ? "Database contains " + count + " currencies" : "Database is empty");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/convert")
    public ResponseEntity<ApiResponse<ConvertExchangeDTOResponse>> getConvertion(
            @RequestParam(name = "baseCurrency", required = true) String baseCurrency,
            @RequestParam(name = "target", required = true) String target,
            @RequestParam(name = "value", required = true) Double value
    ) {
        ApiResponse<ConvertExchangeDTOResponse> response = new ApiResponse<>();
        try {
            ConvertExchangeDTOResponse result = currencyService.convCurrencies(baseCurrency, target, value);

            response.setSuccess(true);
            response.setMessage("Currency Converted Successfully");
            response.setData(result);
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}