package com.currency_converter.repository;

import com.currency_converter.model.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("SELECT c FROM Currency c WHERE c.code LIKE %:code%")
    Page<Currency> findByCodeContainingIgnoreCase(@Param("code") String code, Pageable pageable);

    Optional<Currency> findByBaseCurrencyAndCode(String baseCurrency, String code);
}