package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.invoice.InvoiceDto;
import com.app.invoicecreator.exception.InvoiceNotFoundException;
import com.app.invoicecreator.mapper.InvoiceMapper;
import com.app.invoicecreator.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @PostMapping(value = "/invoices")
    public void createInvoice(@RequestBody InvoiceDto invoiceDto) {
        invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto));
    }

    @PutMapping(value = "/invoices")
    public InvoiceDto updateInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceMapper.mapToInvoiceDto(invoiceService.saveInvoice(invoiceMapper.mapToInvoice(invoiceDto)));
    }

    @GetMapping(value = "/invoices/{id}")
    public InvoiceDto getInvoice(@PathVariable Long id) throws InvoiceNotFoundException {
        return invoiceMapper.mapToInvoiceDto(invoiceService.getInvoice(id).orElseThrow(InvoiceNotFoundException::new));
    }

    @GetMapping(value = "/invoices")
    public List<InvoiceDto> getInvoices() {
        return invoiceMapper.mapToInvoiceDtoList(invoiceService.getInvoices());
    }

    @DeleteMapping(value = "/invoices/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}
