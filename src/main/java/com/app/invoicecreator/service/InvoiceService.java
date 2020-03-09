package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.exception.InvoiceNotFoundException;
import com.app.invoicecreator.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    public final InvoiceRepository invoiceRepository;
    public final TaxpayerService taxpayerService;
    public final ItemService itemService;
    public final CurrencyService currencyService;

    public Invoice saveInvoice(Invoice invoice) {
        invoice.setNetTotal(getInvoiceNetTotal(invoice.getId()));
        invoice.setVatTotal(getInvoiceVatTotal(invoice.getId()));
        invoice.setGrossTotal(getInvoiceGrossTotal(invoice.getId()));

        String invoiceCurrency = invoice.getInvoiceCurrency().toString();
        String invoiceDate = invoice.getIssueDate();
        if (!invoiceCurrency.equals("PLN")) {
            BigDecimal rate = currencyService.getCurrencyRate(
                    invoiceCurrency,
                    invoiceDate)
                    .getMidRate();
            invoice.setCurrencyGrossTotal(invoice.getGrossTotal().divide(rate, new MathContext(4)));
        }else {
            invoice.setCurrencyGrossTotal(invoice.getGrossTotal());
        }
        return invoiceRepository.save(invoice);
    }

    private BigDecimal getInvoiceGrossTotal(Long id) {
        List<Item> itemList = itemService.getItemsByInvoiceId(id);

        BigDecimal totalResult = BigDecimal.ZERO;
        for (Item item : itemList) {
            totalResult = totalResult.add(item.getValue());
        }
        return totalResult;
    }


    private BigDecimal getInvoiceNetTotal(Long id) {
        List<Item> itemList = itemService.getItemsByInvoiceId(id);

        BigDecimal totalResult = BigDecimal.ZERO;
        for (Item item : itemList) {
            totalResult = totalResult.add(item.getNetPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalResult;
    }


    private BigDecimal getInvoiceVatTotal(Long id) {
        List<Item> itemList = itemService.getItemsByInvoiceId(id);

        BigDecimal totalResult = BigDecimal.ZERO;
        for (Item item : itemList) {
            totalResult = totalResult.add(item.getVat().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return totalResult;
    }

    public Optional<Invoice> getInvoice(Long id) {
        return invoiceRepository.findById(id);
    }

    public Long getInvoiceId(String number) throws InvoiceNotFoundException {
        Invoice invoice = invoiceRepository.findByNumber(number).orElseThrow(InvoiceNotFoundException::new);
        return invoice.getId();
    }

    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
