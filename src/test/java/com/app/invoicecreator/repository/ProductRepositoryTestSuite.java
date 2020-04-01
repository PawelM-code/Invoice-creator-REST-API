package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTestSuite {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testProductSave() {
        //Given
        Product product = new Product("Samsung Z flip", 23);

        //When
        productRepository.save(product);
        Long id = product.getId();

        //Then
        assertEquals(product, productRepository.getOne(id));
        assertEquals("Samsung Z flip", productRepository.getOne(id).getDescription());
    }

    @Test
    public void testGetProducts() {
        //Given
        Product product1 = new Product("Samsung Z flip", 23);
        Product product2 = new Product("Samsung Z2 flip", 23);
        productRepository.save(product1);
        productRepository.save(product2);

        //When
        List<Product> productList = productRepository.findAll();

        //Then
        assertEquals(2, productList.size());
    }

    @Test
    public void testDeleteProduct() {
        //Given
        Product product1 = new Product("Samsung Z flip", 23);
        Product product2 = new Product("Samsung Z2 flip", 23);
        productRepository.save(product1);
        productRepository.save(product2);
        Long id = product1.getId();

        //When
        productRepository.deleteById(id);
        List<Product> productList = productRepository.findAll();

        //Then
        assertEquals(1, productList.size());
    }

}
