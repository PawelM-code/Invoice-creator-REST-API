package com.app.invoicecreator.service;

import com.app.invoicecreator.client.currencies.CurrencyClient;
import com.app.invoicecreator.domain.currency.CurrencyDto;
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

    public CurrencyDto getCurrencyRateByCode(String code, String date) {
        CurrencyDto currencyDto = currencyClient.getCurrencyRateByCode(code, date);
        currencyRepository.save(currencyMapper.mapToCurrency(currencyDto));
        return currencyDto;
    }
}
