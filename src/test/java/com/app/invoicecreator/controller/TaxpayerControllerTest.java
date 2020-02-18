package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.TaxpayerDto;
import com.app.invoicecreator.service.TaxpayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaxpayerController.class)
public class TaxpayerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxpayerService taxpayerService;

    @Test
    public void testGetEmptyTaxpayer() throws Exception {
        //Given
        long nip = 1111L;
        String date = "2020-02-17";
        TaxpayerDto taxpayerDto = new TaxpayerDto(1L, "Company", nip, 55555L, "Address");
        when(taxpayerService.getTaxpayerByNip(nip, date)).thenReturn(taxpayerDto);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1//taxpayers/{nip}&{data}", nip, date)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Company"))
                .andExpect(jsonPath("$.nip").value(1111L))
                .andExpect(jsonPath("$.workingAddress").value("Address"));
    }
}
