package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping(value = "/currency/A/{code}/{date}")
    public void getCurrencyRate(@PathVariable String code, @PathVariable String date) {
        currencyService.saveCurrencyRateByCode(code, date);
    }
}
