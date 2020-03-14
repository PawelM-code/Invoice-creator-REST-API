package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.repository.CurrencyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CurrencyServiceTestSuite {
    @Autowired
    CurrencyService currencyService;

    @Autowired
    CurrencyRepository currencyRepository;

    private Currency currency;

    @Before
    public void initSetup() {
        //Given
        currency = new Currency("euro", "EUR", "2020-03-12", new BigDecimal(4.26));
    }

    @Test
    public void testSaveCurrencyRate() {
        //When
        currencyService.saveCurrencyRate(currency);
        Long id = currency.getId();

        //Then
        assertEquals("euro", currencyRepository.getOne(id).getCurrency());
        assertEquals("EUR", currencyRepository.getOne(id).getCode());
        assertEquals("2020-03-12", currencyRepository.getOne(id).getDate());
        assertEquals(new BigDecimal(4.26), currencyRepository.getOne(id).getMidRate());
    }

    @Test
    public void testGetCurrencies() {
        //Given
        currencyService.saveCurrencyRate(currency);

        //When
        List<Currency> currencyList = currencyService.getCurrencies();

        //Then
        assertEquals(1, currencyList.size());
    }

    @Test
    public void testGetCurrencyRate() {
        //When
        Currency currencyNbp = currencyService.getCurrencyRate("EUR", "2020-03-13");

        //Then
        assertEquals("EUR", currencyNbp.getCode());
        assertEquals("euro", currencyNbp.getCurrency());
        assertEquals("2020-03-13", currencyNbp.getDate());
        assertEquals(new BigDecimal(4.3618, new MathContext(5)), currencyNbp.getMidRate());
    }
}
