package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.currency.CurrencyDto;
import com.app.invoicecreator.domain.currency.CurrencyRatesDto;
import com.app.invoicecreator.facade.currencies.CurrencyFacade;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyFacade currencyFacade;

    @Mock
    private CurrencyDto currencyDto;

    @Before
    public void initSetup() {
        CurrencyRatesDto[] currencyRatesDto = new CurrencyRatesDto[1];
        currencyRatesDto[0] = new CurrencyRatesDto("", new BigDecimal(4.26), "2020-01-09");
        currencyDto = new CurrencyDto(1L, "euro", "EUR", currencyRatesDto);
    }

    @Test
    public void testGetCurrency() throws Exception {
        //Given
        when(currencyFacade.fetchCurrencyRate("EUR", "2020-01-09")).thenReturn(currencyDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/currency/A/{code}/{date}", "EUR", "2020-01-09")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("EUR"))
                .andExpect(jsonPath("$.rates[0].mid").value(new BigDecimal(4.26)));
    }

    @Test
    public void testSaveCurrency() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(currencyDto);

        //When & Then
        mockMvc.perform(post("/v1/currency/A/")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(currencyFacade, times(1)).saveCurrency(any());
    }

    @Test
    public void testGetCurrencies() throws Exception {
        //Given
        List<CurrencyDto> currencyDtoList = new ArrayList<>();
        currencyDtoList.add(currencyDto);

        when(currencyFacade.getCurrencies()).thenReturn(currencyDtoList);

        //When & Then
        mockMvc.perform(get("/v1/currency")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].currency", is("euro")))
                .andExpect(jsonPath("$[0].code", is("EUR")))
                .andExpect(jsonPath("$[0].rates[0].mid", is(new BigDecimal(4.26))))
                .andDo(print());
    }
}
