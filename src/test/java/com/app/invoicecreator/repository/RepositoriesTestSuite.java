package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.currency.Currency;
import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.owner.Owner;
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
import java.util.Optional;

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
    @Autowired
    OwnerRepository ownerRepository;

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

    @Test
    public void testItemSave() {
        //Given
        Product product = new Product("Samsung Z flip", 23);
        productRepository.save(product);
        Item item = new Item(product, new BigDecimal(100), 10);

        //When
        itemRepository.save(item);
        Long id = item.getId();

        //Then
        assertTrue(itemRepository.findById(id).isPresent());
        assertEquals(10, itemRepository.findById(id).get().getQuantity());
        assertEquals(new BigDecimal(100), itemRepository.findById(id).get().getNetPrice());
        assertThat(new BigDecimal(1230.00), Matchers.comparesEqualTo(itemRepository.getOne(id).getValue()));
        assertThat(new BigDecimal(123), Matchers.comparesEqualTo(itemRepository.findById(id).get().getGrossPrice()));
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
        Product product = new Product("Samsung Z flip", 23);
        Taxpayer taxpayer = new Taxpayer("Firma", 666666666L, 555555L, "Powstańców 33");
        Invoice invoice = Invoice.builder()
                .number("FV/01/2020")
                .issueDate("2020-02-17")
                .taxpayer(taxpayer)
                .comments("comments")
                .invoiceCurrency(InvoiceCurrency.PLN)
                .dateOfPayment("2020-02-27")
                .build();
        Invoice invoice2 = Invoice.builder()
                .number("FV/02/2020")
                .issueDate("2020-02-18")
                .taxpayer(taxpayer)
                .comments("comments2")
                .invoiceCurrency(InvoiceCurrency.EUR)
                .dateOfPayment("2020-02-28")
                .build();

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
        Currency currency = new Currency("euro", "EUR", "2020-02-17", new BigDecimal(4.26));

        //When
        currencyRepository.save(currency);
        Long id = currency.getId();

        //Then
        assertEquals(currency, currencyRepository.getOne(id));
        assertEquals("euro", currencyRepository.getOne(id).getCurrency());
        assertEquals("EUR", currencyRepository.getOne(id).getCode());
        assertEquals("2020-02-17", currencyRepository.getOne(id).getDate());
        assertEquals(new BigDecimal(4.26), currencyRepository.getOne(id).getMidRate());
    }

    @Test
    public void testOwnerRepository() {
        //Given
        Owner owner = new Owner(
                "MyCompany",
                111L,
                22222L,
                "ul. Calineczki",
                "12-1212-1212-1212",
                "test@test.test");

        //When
        ownerRepository.save(owner);
        Optional<Owner> findedOwner = ownerRepository.findById(owner.getId());

        //Then
        assertTrue(findedOwner.isPresent());
        assertEquals("MyCompany", findedOwner.get().getName());
        assertEquals(111L, (long) findedOwner.get().getNip());
        assertEquals(22222L, (long) findedOwner.get().getRegon());
        assertEquals("ul. Calineczki", findedOwner.get().getWorkingAddress());
        assertEquals("12-1212-1212-1212", findedOwner.get().getBankAccount());
        assertEquals("test@test.test", findedOwner.get().getEmail());
        assertEquals(1, ownerRepository.findAll().size());
    }
}
