package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.domain.currency.CurrencyRatesDto;
import com.app.invoicecreator.service.CurrencyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    public void testGetCurrency() throws Exception {
        //Given
        String code = "EUR";
        String date = "2020-01-09";
        CurrencyRatesDto[] currencyRatesDto = new CurrencyRatesDto[1];
        currencyRatesDto[0] = new CurrencyRatesDto("",new BigDecimal(4.26),date);
        CurrencyDto currencyDto = new CurrencyDto(1L,"euro",code,currencyRatesDto);

        when(currencyService.getCurrencyRateByCode(code,date)).thenReturn(currencyDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1//currency/A/{code}/{date}", code, date)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("EUR"))
                .andExpect(jsonPath("$.rates[0].mid").value(new BigDecimal(4.26)));
    }
}
