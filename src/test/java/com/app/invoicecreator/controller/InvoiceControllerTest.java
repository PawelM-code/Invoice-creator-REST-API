package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.domain.invoice.InvoiceDto;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.mapper.InvoiceMapper;
import com.app.invoicecreator.service.InvoiceService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
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

    @Test
    public void testCreateInvoice() throws Exception {
        //Given
        Taxpayer taxpayer = new Taxpayer(1L, "Firma", 10101010L, 55555L, "Address");
        TaxpayerDto taxpayerDto = new TaxpayerDto(1L, "Firma", 10101010L, 55555L, "Address");
        Invoice invoice = new Invoice(1L, "FV/01", "2002-01-03", taxpayer, "comments", InvoiceCurrency.PLN);
        InvoiceDto invoiceDto = new InvoiceDto(1L, "FV/01", "2002-01-03", taxpayerDto, "comments",BigDecimal.ZERO, InvoiceCurrency.PLN);

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
        Taxpayer taxpayer = new Taxpayer(1L, "Firma", 10101010L, 55555L, "Address");
        TaxpayerDto taxpayerDto = new TaxpayerDto(1L, "Firma", 10101010L, 55555L, "Address");
        Invoice invoice = new Invoice(1L, "FV/01", "2002-01-03", taxpayer, "comments", InvoiceCurrency.PLN);
        InvoiceDto invoiceDto = new InvoiceDto(1L, "FV/02", "2002-01-03", taxpayerDto, "comments", BigDecimal.ZERO, InvoiceCurrency.PLN);
        when(invoiceService.saveInvoice(ArgumentMatchers.any(Invoice.class))).thenReturn(invoice);
        when(invoiceMapper.mapToInvoiceDto(any())).thenReturn(invoiceDto);

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
        Taxpayer taxpayer = new Taxpayer(1L, "Firma", 10101010L, 55555L, "Address");
        TaxpayerDto taxpayerDto = new TaxpayerDto(1L, "Firma", 10101010L, 55555L, "Address");
        Invoice invoice = new Invoice(1L, "FV/01", "2002-01-03", taxpayer, "comments", InvoiceCurrency.PLN);
        InvoiceDto invoiceDto = new InvoiceDto(1L, "FV/01", "2002-01-03", taxpayerDto, "comments", BigDecimal.ZERO, InvoiceCurrency.PLN);

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
    public void testGetTasksList() throws Exception {
        //Given
        Taxpayer taxpayer = new Taxpayer(1L, "Firma", 10101010L, 55555L, "Address");
        TaxpayerDto taxpayerDto = new TaxpayerDto(1L, "Firma", 10101010L, 55555L, "Address");
        Invoice invoice = new Invoice(1L, "FV/01", "2002-01-03", taxpayer, "comments", InvoiceCurrency.PLN);
        InvoiceDto invoiceDto = new InvoiceDto(1L, "FV/01", "2002-01-03", taxpayerDto, "comments", BigDecimal.ZERO, InvoiceCurrency.PLN);

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
