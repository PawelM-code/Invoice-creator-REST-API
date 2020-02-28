package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.mapper.CurrencyMapper;
import com.app.invoicecreator.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;

    @GetMapping(value = "/currency/A/{code}/{date}")
    public CurrencyDto getCurrencyRate(@PathVariable String code, @PathVariable String date) {
        return currencyMapper.mapToCurrencyDto(currencyService.saveCurrencyRateByCode(code, date));
    }

    @GetMapping(value = "/currency")
    public List<CurrencyDto> getCurrencies() {
       return currencyMapper.mapToCurrencyDtoList(currencyService.getCurrencies());
    }
}
