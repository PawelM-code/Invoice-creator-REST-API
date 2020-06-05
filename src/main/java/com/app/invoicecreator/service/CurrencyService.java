package com.app.invoicecreator.service;

import com.app.invoicecreator.client.currencies.CurrencyClient;
import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.mapper.CurrencyMapper;
import com.app.invoicecreator.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {
    private CurrencyClient currencyClient;
    private CurrencyMapper currencyMapper;
    private CurrencyRepository currencyRepository;

    public CurrencyService() {
    }

    @Autowired
    public CurrencyService(CurrencyClient currencyClient, CurrencyMapper currencyMapper, CurrencyRepository currencyRepository) {
        this.currencyClient = currencyClient;
        this.currencyMapper = currencyMapper;
        this.currencyRepository = currencyRepository;
    }

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
