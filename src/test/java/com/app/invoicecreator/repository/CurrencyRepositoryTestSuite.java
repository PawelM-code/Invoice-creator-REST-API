package com.app.invoicecreator.repository;


import com.app.invoicecreator.domain.currency.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyRepositoryTestSuite {
    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    public void testCurrencyRepository() {
        //Given
        Currency currency = new Currency("euro", "EUR", "2020-02-17", new BigDecimal(4.26));

        //When
        currencyRepository.save(currency);
        Long id = currency.getId();

        //Then
        assertEquals(currency, currencyRepository.getOne(id));
        assertEquals("euro", currencyRepository.getOne(id).getCurrency());
        assertEquals("EUR", currencyRepository.getOne(id).getCode());
        assertEquals("2020-02-17", currencyRepository.getOne(id).getDate());
        assertEquals(new BigDecimal(4.26), currencyRepository.getOne(id).getMidRate());
    }
}
