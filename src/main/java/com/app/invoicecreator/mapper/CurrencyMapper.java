package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.domain.currency.CurrencyRatesDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public CurrencyDto mapToCurrencyDto(Currency currency){
        return new CurrencyDto(
                currency.getId(),
                currency.getCurrency(),
                currency.getCode(),
                mapToCurrencyRatesDtoArray(currency));
    }

    private CurrencyRatesDto[] mapToCurrencyRatesDtoArray(Currency currency){
        CurrencyRatesDto[] currencyRatesDto = new CurrencyRatesDto[1];
        currencyRatesDto[0] = new CurrencyRatesDto();
        currencyRatesDto[0].setMid(currency.getMidRate());
        currencyRatesDto[0].setEffectiveDate(currency.getDate());
        return currencyRatesDto;
    }

    public List<CurrencyDto> mapToCurrencyDtoList(List<Currency> currencies){
        return currencies.stream()
                .map(currency -> new CurrencyDto(
                            currency.getId(),
                            currency.getCurrency(),
                            currency.getCode(),
                            mapToCurrencyRatesDtoArray(currency))
                ).collect(Collectors.toList());
    }
}
