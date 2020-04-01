package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceCurrency;
import com.app.invoicecreator.domain.product.Product;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InvoiceRepositoryTestSuite {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    TaxpayerRepository taxpayerRepository;
    @Autowired
    ProductRepository productRepository;

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

}
