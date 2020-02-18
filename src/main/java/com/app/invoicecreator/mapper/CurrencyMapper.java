package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.currency.CurrencyDto;
import org.springframework.stereotype.Component;

@Component
public class CurrencyMapper {
    public Currency mapToCurrency(final CurrencyDto currencyDto) {
        return new Currency(
                currencyDto.getId(),
                currencyDto.getCurrency(),
                currencyDto.getCode(),
                currencyDto.getRates()[0].getEffectiveDate(),
                currencyDto.getRates()[0].getMid());
    }
}
