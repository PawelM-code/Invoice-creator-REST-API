package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.Taxpayer;
import com.app.invoicecreator.domain.Invoice;
import com.app.invoicecreator.domain.Item;
import com.app.invoicecreator.domain.Product;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RepositoriesTestSuite {
    @Autowired
    TaxpayerRepository taxpayerRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testProductSave() {
        //Given
        Product product = new Product("Samsung Z flip");

        //When
        productRepository.save(product);
        Long id = product.getId();

        //Then
        assertEquals(product, productRepository.getOne(id));
    }

    @Test
    public void testItemSave() {
        //Given
        Product product = new Product("Samsung Z flip");
        Item item = new Item(product, new BigDecimal(100.50), 10);
        productRepository.save(product);

        //When
        itemRepository.save(item);
        Long id = item.getId();

        //Then
        assertEquals(item, itemRepository.getOne(id));

        assertThat(new BigDecimal(1005), Matchers.comparesEqualTo(itemRepository
                .getOne(item.getId())
                .getValue()));
    }

    @Test
    public void testBuyerRepository() {
        //Given
        Taxpayer taxpayer = new Taxpayer("Firma", 666666666L, 555555L, "Powstańców 33");

        //When
        taxpayerRepository.save(taxpayer);
        Long id = taxpayer.getId();

        //Then
        assertEquals(taxpayer, taxpayerRepository.getOne(id));
    }

    @Test
    public void testInvoiceRepository() {
        //Given
        Product product = new Product("Samsung Z flip");
        Item item = new Item(product, new BigDecimal(100.50), 10);
        List<Item> items = new ArrayList<>();
        items.add(item);
        Taxpayer taxpayer = new Taxpayer("Firma", 666666666L, 555555L, "Powstańców 33");
        Invoice invoice = new Invoice("FV/01/2020", new Date(2020 - 2 - 17), taxpayer, items, "comments");

        productRepository.save(product);
        itemRepository.save(item);
        taxpayerRepository.save(taxpayer);

        //When
        invoiceRepository.save(invoice);
        Long id = invoice.getId();

        //Then
        assertEquals(invoice, invoiceRepository.getOne(id));
        assertEquals(1, invoiceRepository.getOne(id).getItems().size());
    }
}
