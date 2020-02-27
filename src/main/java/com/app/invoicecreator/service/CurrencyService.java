package com.app.invoicecreator.service;

import com.app.invoicecreator.client.currencies.CurrencyClient;
import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.domain.currency.CurrencyRatesDto;
import com.app.invoicecreator.mapper.CurrencyMapper;
import com.app.invoicecreator.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyClient currencyClient;
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    public CurrencyDto saveCurrencyRateByCode(String code, String date) {
        Currency currency = currencyRepository.findByCodeAndDate(code, date).orElse(null);
        CurrencyDto currencyDto;

        if (currency == null) {
            currencyDto = currencyClient.getCurrencyRateByCode(code, date);
            currency = currencyMapper.mapToCurrency(currencyDto);
            currencyRepository.save(currency);
        } else {
            CurrencyRatesDto[] currencyRatesDto = new CurrencyRatesDto[1];
            currencyRatesDto[0] = new CurrencyRatesDto("", currency.getMidRate(), date);
            currencyDto = new CurrencyDto(currency.getId(), currency.getCurrency(), code, currencyRatesDto);
        }
        return currencyDto;
    }
}
