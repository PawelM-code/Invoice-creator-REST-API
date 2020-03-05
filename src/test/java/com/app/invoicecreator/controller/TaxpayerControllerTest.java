package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.item.ItemDto;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.facade.taxpayers.TaxpayerFacade;
import com.app.invoicecreator.mapper.TaxpayerMapper;
import com.app.invoicecreator.service.TaxpayerService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
@WebMvcTest(TaxpayerController.class)
public class TaxpayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxpayerFacade taxpayerFacade;

    @MockBean
    private TaxpayerMapper taxpayerMapper;

    @MockBean
    private TaxpayerService taxpayerService;

    @Mock
    private Taxpayer taxpayer;

    @Mock
    private TaxpayerDto taxpayerDto;



    @Before
    public void initSetup() {
        //Given
        taxpayer = new Taxpayer(1L, "Firma", 1111L, 55555L, "Address");
        taxpayerDto = new TaxpayerDto(1L, "Firma", 1111L, 55555L, "Address");
    }

    @Test
    public void testGetTaxpayer() throws Exception {
        //Given
        long nip = 1111L;
        String date = "2020-02-17";
        when(taxpayerFacade.getTaxpayer(nip, date)).thenReturn(taxpayerDto);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1//taxpayers/{nip}&{data}", nip, date)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Firma"))
                .andExpect(jsonPath("$.nip").value(1111L))
                .andExpect(jsonPath("$.regon").value(55555L))
                .andExpect(jsonPath("$.workingAddress").value("Address"));
    }

    @Test
    public void testSaveTaxpayer() throws Exception {
        //Given
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taxpayerDto);

        //When & Then
        mockMvc.perform(post("/v1/taxpayers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(taxpayerFacade, times(1)).saveTaxpayer(any());
    }

    @Test
    public void testGetTaxpayers() throws Exception {
        //Given
        List<TaxpayerDto> taxpayerDtoList = new ArrayList<>();
        taxpayerDtoList.add(taxpayerDto);

        when(taxpayerFacade.getTaxpayers()).thenReturn(taxpayerDtoList);

        //When & Then
        mockMvc.perform(get("/v1/taxpayers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Firma")))
                .andExpect(jsonPath("$[0].nip", is(1111)))
                .andExpect(jsonPath("$[0].regon", is(55555)))
                .andExpect(jsonPath("$[0].workingAddress", is("Address")))
                .andDo(print());
    }
}
