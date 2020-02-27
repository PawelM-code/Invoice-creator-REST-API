package com.app.invoicecreator.scheduler;

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

//    @Scheduled(cron = "0 0 13 * * *")
    @Scheduled(fixedDelay = 10000)
    public void saveCurrencies() {
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        currencyService.saveCurrencyRateByCode(
                InvoiceCurrency.EUR.toString(),
                localDate);
        currencyService.saveCurrencyRateByCode(
                InvoiceCurrency.USD.toString(),
                localDate);
        currencyService.saveCurrencyRateByCode(
                InvoiceCurrency.CHF.toString(),
                localDate);
    }
}
