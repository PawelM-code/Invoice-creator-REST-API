package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.domain.invoice.InvoiceDto;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.mapper.InvoiceMapper;
import com.app.invoicecreator.service.InvoiceService;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InvoiceService invoiceService;
    @MockBean
    private InvoiceMapper invoiceMapper;
    @Mock
    private Taxpayer taxpayer;
    @Mock
    private TaxpayerDto taxpayerDto;
    @Mock
    private Invoice invoice;
    @Mock
    private InvoiceDto invoiceDto;
    @Mock
    private InvoiceDto invoiceDto2;

    @Before
    public void initSetup() {
        //Given
        taxpayer = new Taxpayer(1L, "Firma", 10101010L, 55555L, "Address");
        taxpayerDto = new TaxpayerDto(1L, "Firma", 10101010L, 55555L, "Address");
        invoice = Invoice.builder()
                .id(1L)
                .number("FV/01")
                .issueDate("2002-01-03")
                .taxpayer(taxpayer)
                .comments("comments")
                .invoiceCurrency(InvoiceCurrency.PLN)
                .build();
        invoiceDto = InvoiceDto.builder()
                .id(1L)
                .number("FV/01")
                .issueDate("2002-01-03")
                .taxpayerDto(taxpayerDto)
                .comments("comments")
                .netTotal(BigDecimal.ZERO)
                .vatTotal(BigDecimal.ZERO)
                .grossTotal(BigDecimal.ZERO)
                .invoiceCurrency(InvoiceCurrency.PLN)
                .dateOfPayment("2020-02-02")
                .build();
        invoiceDto2 = InvoiceDto.builder()
                .id(1L)
                .number("FV/02")
                .issueDate("2002-01-03")
                .taxpayerDto(taxpayerDto)
                .comments("comments")
                .netTotal(BigDecimal.ZERO)
                .vatTotal(BigDecimal.ZERO)
                .grossTotal(BigDecimal.ZERO)
                .invoiceCurrency(InvoiceCurrency.PLN)
                .dateOfPayment("2020-02-02")
                .build();
    }

    @Test
    public void testCreateInvoice() throws Exception {
        //Given
        when(invoiceMapper.mapToInvoice(any())).thenReturn(invoice);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(invoiceDto);

        //When & Then
        mockMvc.perform(post("/v1/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(invoiceService, times(1)).saveInvoice(invoice);
    }

    @Test
    public void testInvoiceNotFound() throws Exception {
        mockMvc.perform(get("/v1/invoices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testUpdateInvoice() throws Exception {
        //Given
        when(invoiceService.saveInvoice(ArgumentMatchers.any(Invoice.class))).thenReturn(invoice);
        when(invoiceMapper.mapToInvoiceDto(any())).thenReturn(invoiceDto2);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(invoiceDto);

        //When & Then
        mockMvc.perform(put("/v1/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.number", is("FV/02")));
    }

    @Test
    public void testDeleteInvoice() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/v1/invoices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInvoice() throws Exception {
        //Given
        when(invoiceMapper.mapToInvoiceDto(any())).thenReturn(invoiceDto);
        when(invoiceService.getInvoice(anyLong())).thenReturn(Optional.of(invoice));

        //When & Then
        mockMvc.perform(get("/v1/invoices/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.number", is("FV/01")));
    }

    @Test
    public void testGetInvoicesList() throws Exception {
        //Given
        List<InvoiceDto> invoiceDtoList = new ArrayList<>();
        invoiceDtoList.add(invoiceDto);
        List<Invoice> invoiceList = new ArrayList<>();
        invoiceList.add(invoice);

        when(invoiceService.getInvoices()).thenReturn(invoiceList);
        when(invoiceMapper.mapToInvoiceDtoList(any())).thenReturn(invoiceDtoList);

        //When & Then
        mockMvc.perform(get("/v1/invoices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].number", is("FV/01")));
    }

}
