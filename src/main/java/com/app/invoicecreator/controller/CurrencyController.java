package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.facade.currencies.CurrencyFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyFacade currencyFacade;

    @GetMapping(value = "/currency/A/{code}/{date}")
    public CurrencyDto fetchCurrencyRate(@PathVariable String code, @PathVariable String date) {
        return currencyFacade.fetchCurrencyRate(code, date);
    }

    @PostMapping(value = "/currency/A/")
    public void saveCurrencyRate(@RequestBody CurrencyDto currencyDto) {
        currencyFacade.saveCurrency(currencyDto);
    }

    @GetMapping(value = "/currency")
    public List<CurrencyDto> getCurrencies() {
        return currencyFacade.getCurrencies();
    }
}
