package com.app.invoicecreator.service;

import com.app.invoicecreator.client.currencies.CurrencyClient;
import com.app.invoicecreator.domain.currency.Currency;
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

    public void saveCurrencyRate(Currency currency) {
        if (currencyRepository.findByCodeAndDate(currency.getCode(), currency.getDate()).orElse(null) == null) {
            currencyRepository.save(currency);
        }
    }

    public Currency getCurrencyRate(String code, String date) {
        return currencyMapper.mapToCurrency(currencyClient.getCurrencyRateByCode(code, date));
    }

    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }
}
