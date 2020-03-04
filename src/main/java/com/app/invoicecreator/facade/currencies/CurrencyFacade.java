package com.app.invoicecreator.facade.currencies;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.mapper.CurrencyMapper;
import com.app.invoicecreator.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CurrencyFacade {
    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    public CurrencyDto fetchCurrencyRate(String code, String date) {
        return currencyMapper.mapToCurrencyDto(currencyService.getCurrencyRate(code, date));
    }

    public List<CurrencyDto> getCurrencies(){
        return currencyMapper.mapToCurrencyDtoList(currencyService.getCurrencies());
    }

    public void saveCurrency(CurrencyDto currencyDto){
        currencyService.saveCurrencyRate(currencyMapper.mapToCurrency(currencyDto));
    }
}
