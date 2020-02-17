package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.CompanyData;
import com.app.invoicecreator.domain.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoriesTestSuite {
    @Autowired
    CompanyDataRepository companyDataRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testProductSave(){
        //Given
        Product product = new Product("Samsung Z flip");

        //When
        productRepository.save(product);
        Product result = productRepository.getOne(product.getId());

        //Then
        Assert.assertEquals("Samsung Z flip", result.getDescription());
    }
}
