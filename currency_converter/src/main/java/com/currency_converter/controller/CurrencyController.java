package com.currency_converter.controller;

import com.currency_converter.model.Currency;
import com.currency_converter.service.CurrencyService;
import com.currency_converter.util.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/currencies")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
//    @GetMapping
//    public ResponseEntity<Page<Currency>> getCurrencies(
//            @RequestParam(defaultValue = "") String name,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Currency> result = currencyService.getCurrencies(name, pageable);
//        ApiResponse<Page<Currency>> response = new ApiResponse<>(true, "Fetched successfully", result);
//        return ResponseEntity.ok(result);
//    }
}
