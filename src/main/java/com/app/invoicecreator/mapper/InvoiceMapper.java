package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.invoice.Invoice;
import com.app.invoicecreator.domain.invoice.InvoiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InvoiceMapper {
    private final TaxpayerMapper taxpayerMapper;

    public Invoice mapToInvoice(InvoiceDto invoiceDto){
        return new Invoice(
                invoiceDto.getId(),
                invoiceDto.getNumber(),
                invoiceDto.getIssueDate(),
                taxpayerMapper.mapToTaxpayer(invoiceDto.getTaxpayerDto()),
                invoiceDto.getComments());
    }

    public InvoiceDto mapToInvoiceDto(Invoice invoice){
        return new InvoiceDto(
                invoice.getId(),
                invoice.getNumber(),
                invoice.getIssueDate(),
                taxpayerMapper.mapToTaxpayerDto(invoice.getTaxpayer()),
                invoice.getComments());
    }

    public List<InvoiceDto> mapToInvoiceDtoList(List<Invoice> invoiceList){
        return invoiceList.stream()
                .map(i-> new InvoiceDto(
                        i.getId(),
                        i.getNumber(),
                        i.getIssueDate(),
                        taxpayerMapper.mapToTaxpayerDto(i.getTaxpayer()),
                        i.getComments()))
                .collect(Collectors.toList());
    }
}
