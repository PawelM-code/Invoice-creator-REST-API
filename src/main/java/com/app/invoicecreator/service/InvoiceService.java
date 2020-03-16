package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.exception.InvoiceNotFoundException;
import com.app.invoicecreator.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        String currencyDate = getCurrencyDate(invoiceDate);

        if (!invoiceCurrency.equals("PLN")) {
            BigDecimal rate = currencyService.getCurrencyRate(
                    invoiceCurrency,
                    currencyDate)
                    .getMidRate();
            invoice.setCurrencyGrossTotal(invoice.getGrossTotal().divide(rate.setScale(2, RoundingMode.HALF_UP), RoundingMode.HALF_UP));
        } else {
            invoice.setCurrencyGrossTotal(invoice.getGrossTotal());
        }
        return invoiceRepository.save(invoice);
    }

    private String getCurrencyDate(String invoiceDate) {
        LocalDate invoiceLocalDate = LocalDate.parse(invoiceDate);
        LocalDate currencyLocalDate;

        if (invoiceLocalDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
            currencyLocalDate = invoiceLocalDate.minusDays(2);
        } else if (invoiceLocalDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            currencyLocalDate = invoiceLocalDate.minusDays(3);
        } else if (invoiceLocalDate.getDayOfWeek() == DayOfWeek.MONDAY) {
            currencyLocalDate = invoiceLocalDate.minusDays(4);
        } else {
            currencyLocalDate = invoiceLocalDate.minusDays(1);
        }

        return currencyLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
