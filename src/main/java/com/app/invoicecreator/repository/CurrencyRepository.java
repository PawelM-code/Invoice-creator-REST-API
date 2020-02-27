package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    @Override
    <S extends Currency> S save(S entity);

    @Override
    Currency getOne(Long id);

    Optional<Currency> findByCodeAndDate(String code, String date);
}
