package com.app.invoicecreator.scheduler;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler {
    private final CurrencyService currencyService;

    @Scheduled(cron = "0 0 15 * * *", zone = "Europe/Warsaw")
    public void saveCurrencies() {
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Currency currencyEUR = currencyService.getCurrencyRate(
                InvoiceCurrency.EUR.toString(),
                localDate);
        Currency currencyUSD = currencyService.getCurrencyRate(
                InvoiceCurrency.USD.toString(),
                localDate);
        Currency currencyCHF = currencyService.getCurrencyRate(
                InvoiceCurrency.CHF.toString(),
                localDate);

        currencyService.saveCurrencyRate(currencyEUR);
        currencyService.saveCurrencyRate(currencyUSD);
        currencyService.saveCurrencyRate(currencyCHF);
    }
}
