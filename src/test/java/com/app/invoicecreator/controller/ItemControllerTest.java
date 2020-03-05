package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.domain.invoice.InvoiceDto;
import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.item.ItemDto;
import com.app.invoicecreator.domain.owner.Owner;
import com.app.invoicecreator.domain.owner.OwnerDto;
import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.domain.product.ProductDto;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.mapper.ItemMapper;
import com.app.invoicecreator.service.ItemService;
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
@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemMapper itemMapper;
    @Mock
    private Taxpayer taxpayer;
    @Mock
    private TaxpayerDto taxpayerDto;
    @Mock
    private Product product;
    @Mock
    private ProductDto productDto;
    @Mock
    private Invoice invoice;
    @Mock
    private InvoiceDto invoiceDto;
    @Mock
    private Item item;
    @Mock
    private ItemDto itemDto;

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
                .baseTotal(BigDecimal.ZERO)
                .plnTotal(BigDecimal.ZERO)
                .invoiceCurrency(InvoiceCurrency.PLN)
                .build();
        product = new Product(1L, "TV", 23);
        productDto = new ProductDto(1L, "TV", 23);
        item = new Item(1L,product,invoice,new BigDecimal(100),10);
        itemDto = new ItemDto(1L,productDto,invoiceDto,new BigDecimal(100),new BigDecimal(123),10,new BigDecimal(1000));
    }

    @Test
    public void testSaveItem() throws Exception {
        //Given

        when(itemMapper.mapToItem(any())).thenReturn(item);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(invoiceDto);

        //When & Then
        mockMvc.perform(post("/v1/items")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(itemService, times(1)).saveItem(item);
    }

    @Test
    public void testDeleteItem() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/v1/items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetItem() throws Exception {
        //Given
        when(itemMapper.mapToItemDto(any())).thenReturn(itemDto);
        when(itemService.getItem(anyLong())).thenReturn(Optional.of(item));

        //When & Then
        mockMvc.perform(get("/v1/items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.quantity", is(10)))
                .andExpect(jsonPath("$.productDto.description", is("TV")))
                .andExpect(jsonPath("$.invoiceDto.number", is("FV/01")))
                .andExpect(jsonPath("$.invoiceDto.taxpayerDto.name", is("Firma")))
                .andExpect(jsonPath("$.grossPrice", is(123)))
                .andDo(print());
    }

    @Test
    public void testGetItems() throws Exception {
        //Given
        List<ItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(itemDto);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        when(itemService.getItems()).thenReturn(itemList);
        when(itemMapper.mapToItemDtoList(any())).thenReturn(itemDtoList);

        //When & Then
        mockMvc.perform(get("/v1/items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(jsonPath("$[0].productDto.description", is("TV")))
                .andExpect(jsonPath("$[0].invoiceDto.number", is("FV/01")))
                .andExpect(jsonPath("$[0].invoiceDto.taxpayerDto.name", is("Firma")))
                .andExpect(jsonPath("$[0].grossPrice", is(123)));
    }

    @Test
    public void testGetItemsByInvoiceId() throws Exception {
        //Given
        List<ItemDto> itemDtoList = new ArrayList<>();
        itemDtoList.add(itemDto);

        when(itemMapper.mapToItemDtoList(any())).thenReturn(itemDtoList);

        //When & Then
        mockMvc.perform(get("/v1/items/invoice/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(jsonPath("$[0].productDto.description", is("TV")))
                .andExpect(jsonPath("$[0].invoiceDto.id", is(1)))
                .andExpect(jsonPath("$[0].invoiceDto.number", is("FV/01")))
                .andExpect(jsonPath("$[0].invoiceDto.taxpayerDto.name", is("Firma")))
                .andExpect(jsonPath("$[0].grossPrice", is(123)));
    }
}
