package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTestSuit {
    @Autowired
    ProductService productService;

    private Product product;

    @Before
    public void initSetup() {
        //Given
        product = new Product("TV", 23);
    }

    @Test
    public void testSaveAndGetProduct() {
        //When
        productService.saveProduct(product);
        Long id = product.getId();
        Optional<Product> tv = productService.getProduct(id);

        //Then
        assertTrue(tv.isPresent());
        assertEquals("TV", tv.get().getDescription());
        assertEquals(23, tv.get().getVat());
    }

    @Test
    public void testGetProducts() {
        //Given
        productService.saveProduct(product);

        //When
        List<Product> products = productService.getProducts();

        //Then
        assertEquals(1, products.size());
    }

    @Test
    public void testDeleteProduct() {
        //Given
        productService.saveProduct(product);
        Long id = product.getId();

        //When
        productService.deleteProduct(id);
        List<Product> products = productService.getProducts();

        //Then
        assertEquals(0, products.size());
    }
}
