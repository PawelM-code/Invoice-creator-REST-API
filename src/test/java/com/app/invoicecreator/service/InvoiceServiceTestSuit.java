package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.owner.Owner;
import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.exception.InvoiceNotFoundException;
import com.app.invoicecreator.repository.ItemRepository;
import com.app.invoicecreator.repository.OwnerRepository;
import com.app.invoicecreator.repository.ProductRepository;
import com.app.invoicecreator.repository.TaxpayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.invoicecreator.domain.invoice.InvoiceCurrency.PLN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InvoiceServiceTestSuit {
    @Autowired
    InvoiceService invoiceService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    TaxpayerRepository taxpayerRepository;

    private Invoice invoice;
    private Owner owner;
    private Taxpayer taxpayer;

    @Before
    public void initSetup() {
        //Given
        owner = new Owner(
                "MyCompany",
                (long) 666,
                (long) 555,
                "Address",
                "12-12",
                "test@test.pl");

        taxpayer = new Taxpayer("Company test", 111L, 222L, "Address");

        Product product = new Product("TV", 23);
        Item item = new Item(product, new BigDecimal(1000), 1);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        invoice = Invoice.builder()
                .number("FV/01")
                .issueDate("2020-03-12")
                .dateOfPayment("2020-03-22")
                .owner(owner)
                .taxpayer(taxpayer)
                .comments("test")
                .invoiceCurrency(PLN)
                .items(itemList)
                .build();

        ownerRepository.save(owner);
        taxpayerRepository.save(taxpayer);
        productRepository.save(product);
        itemRepository.save(item);
    }

    @Test
    public void testSaveInvoice() {
        //When
        invoiceService.saveInvoice(invoice);
        Optional<Invoice> savedInvoice = invoiceService.getInvoice(invoice.getId());

        //Then
        assertTrue(savedInvoice.isPresent());
        assertEquals("FV/01", savedInvoice.get().getNumber());
        assertEquals("2020-03-12", savedInvoice.get().getIssueDate());
        assertEquals("2020-03-22", savedInvoice.get().getDateOfPayment());
        assertEquals(owner, savedInvoice.get().getOwner());
        assertEquals(taxpayer, savedInvoice.get().getTaxpayer());
        assertEquals("test", savedInvoice.get().getComments());
        assertEquals(PLN, savedInvoice.get().getInvoiceCurrency());
        assertEquals(1, savedInvoice.get().getItems().size());
        assertEquals(new BigDecimal(230).setScale(2, RoundingMode.HALF_UP), savedInvoice.get().getVatTotal());
        assertEquals(new BigDecimal(1000).setScale(0, RoundingMode.HALF_UP), savedInvoice.get().getNetTotal());
        assertEquals(new BigDecimal(1230).setScale(2, RoundingMode.HALF_UP), savedInvoice.get().getGrossTotal());
        assertEquals(new BigDecimal(1230).setScale(2, RoundingMode.HALF_UP), savedInvoice.get().getCurrencyGrossTotal());
    }

    @Test
    public void testGetInvoices() {
        //When
        invoiceService.saveInvoice(invoice);
        List<Invoice> invoiceList = invoiceService.getInvoices();

        //Then
        assertEquals(1, invoiceList.size());
    }

    @Test
    public void testGetInvoiceId() throws InvoiceNotFoundException {
        //When
        invoiceService.saveInvoice(invoice);
        Long id = invoiceService.getInvoiceId("FV/01");

        //Then
        assertEquals(invoice.getId(), id);
    }

    @Test
    public void testDeleteInvoice() {
        //When
        invoiceService.saveInvoice(invoice);
        invoiceService.deleteInvoice(invoice.getId());
        List<Invoice> invoiceList = invoiceService.getInvoices();

        //Then
        assertEquals(0, invoiceList.size());
    }
}
