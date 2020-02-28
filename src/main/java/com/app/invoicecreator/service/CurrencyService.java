package com.app.invoicecreator.service;

import com.app.invoicecreator.client.currencies.CurrencyClient;
import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.domain.currency.CurrencyRatesDto;
import com.app.invoicecreator.mapper.CurrencyMapper;
import com.app.invoicecreator.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyClient currencyClient;
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    public Currency saveCurrencyRateByCode(String code, String date) {
        Currency currency = currencyRepository.findByCodeAndDate(code, date).orElse(null);

        if (currency == null) {
            currency = currencyMapper.mapToCurrency(currencyClient.getCurrencyRateByCode(code, date));
            currencyRepository.save(currency);
        }
        return currency;
    }

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
}
