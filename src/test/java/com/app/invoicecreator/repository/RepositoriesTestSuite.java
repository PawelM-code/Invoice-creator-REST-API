package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

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
    @Autowired
    CurrencyRepository currencyRepository;

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
    public void testGetProducts() {
        //Given
        Product product1 = new Product("Samsung Z flip");
        Product product2 = new Product("Samsung Z2 flip");
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
        Product product1 = new Product("Samsung Z flip");
        Product product2 = new Product("Samsung Z2 flip");
        productRepository.save(product1);
        productRepository.save(product2);
        Long id = product1.getId();

        //When
        productRepository.deleteById(id);
        List<Product> productList = productRepository.findAll();

        //Then
        assertEquals(1, productList.size());
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
                .getOne(id)
                .getValue()));
    }

    @Test
    public void testTaxpayerRepository() {
        //Given
        Taxpayer taxpayer = new Taxpayer("Firma", 666666666L, 555555L, "Powstańców 33");

        //When
        taxpayerRepository.save(taxpayer);
        Long id = taxpayer.getId();

        //Then
        assertTrue(taxpayerRepository.findById(id).isPresent());
        assertEquals(taxpayer.getName(), taxpayerRepository.findById(id).get().getName());
        assertEquals(taxpayer.getNip(), taxpayerRepository.findById(id).get().getNip());
        assertEquals(taxpayer.getRegon(), taxpayerRepository.findById(id).get().getRegon());
        assertEquals(taxpayer.getWorkingAddress(), taxpayerRepository.findById(id).get().getWorkingAddress());
    }

    @Test
    public void testInvoiceRepository() {
        //Given
        Product product = new Product("Samsung Z flip");
        Taxpayer taxpayer = new Taxpayer("Firma", 666666666L, 555555L, "Powstańców 33");
        Invoice invoice = new Invoice("FV/01/2020", "2020-02-17", taxpayer, "comments", InvoiceCurrency.PLN);
        Invoice invoice2 = new Invoice("FV/02/2020", "2020-02-18", taxpayer, "comments2", InvoiceCurrency.EUR);

        productRepository.save(product);
        taxpayerRepository.save(taxpayer);

        //When
        invoiceRepository.save(invoice);
        invoiceRepository.save(invoice2);
        Long id = invoice.getId();
        Long id2 = invoice2.getId();

        //Then
        assertTrue(invoiceRepository.findById(id).isPresent());
        assertTrue(invoiceRepository.findById(id2).isPresent());
        assertEquals("comments", invoiceRepository.findById(id).get().getComments());
        assertEquals("FV/01/2020", invoiceRepository.findById(id).get().getNumber());
        assertEquals("FV/02/2020", invoiceRepository.findById(id2).get().getNumber());
        assertEquals("2020-02-17", invoiceRepository.findById(id).get().getIssueDate());
        assertEquals("Firma", invoiceRepository.findById(id).get().getTaxpayer().getName());
    }

    @Test
    public void testCurrencyRepository() {
        //Given
        Currency currency = new Currency(1L, "euro", "EUR", "2020-02-17", new BigDecimal(4.26));

        //When
        currencyRepository.save(currency);
        Long id = currency.getId();

        //Then
        assertEquals(currency, currencyRepository.getOne(id));
    }
}
