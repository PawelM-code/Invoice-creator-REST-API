package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.domain.product.ProductDto;
import com.app.invoicecreator.mapper.ProductMapper;
import com.app.invoicecreator.service.ProductService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Test
    public void testCreateProduct() throws Exception {
        //Given
        Product product = new Product(1L, "tv");
        ProductDto productDto = new ProductDto(1L, "tv");
        when(productMapper.mapToProduct(any())).thenReturn(product);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDto);

        //When & Then
        mockMvc.perform(post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(productService, times(1)).saveProduct(product);
    }

    @Test
    public void testProductNotFound() throws Exception {
        mockMvc.perform(get("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        //Given
        Product product = new Product(1L, "tv");
        ProductDto productDto = new ProductDto(1L, "tv2");
        when(productService.saveProduct(ArgumentMatchers.any(Product.class))).thenReturn(product);
        when(productMapper.mapToProductDto(any())).thenReturn(productDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDto);

        //When & Then
        mockMvc.perform(put("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("tv2")));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        //Given & When & Then
        mockMvc.perform(delete("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProduct() throws Exception {
        //Given
        Product product = new Product(1L, "tv");
        ProductDto productDto = new ProductDto(1L, "tv");
        when(productMapper.mapToProductDto(any())).thenReturn(productDto);
        when(productService.getProduct(anyLong())).thenReturn(Optional.of(product));

        //When & Then
        mockMvc.perform(get("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("tv")));
    }

    @Test
    public void testGetProductsList() throws Exception {
        //Given
        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(new ProductDto(1L, "tv"));
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(1L, "tv"));

        when(productService.getProducts()).thenReturn(productList);
        when(productMapper.mapToProductDtoList(any())).thenReturn(productDtoList);

        //When & Then
        mockMvc.perform(get("/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("tv")));
    }

}
