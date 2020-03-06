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

    public Invoice mapToInvoice(InvoiceDto invoiceDto) {
        return Invoice.builder()
                .id(invoiceDto.getId())
                .number(invoiceDto.getNumber())
                .issueDate(invoiceDto.getIssueDate())
                .taxpayer(taxpayerMapper.mapToTaxpayer(invoiceDto.getTaxpayerDto()))
                .comments(invoiceDto.getComments())
                .invoiceCurrency(invoiceDto.getInvoiceCurrency())
                .dateOfPayment(invoiceDto.getDateOfPayment())
                .netTotal(invoiceDto.getNetTotal())
                .vatTotal(invoiceDto.getVatTotal())
                .grossTotal(invoiceDto.getGrossTotal())
                .build();
    }

    public InvoiceDto mapToInvoiceDto(Invoice invoice) {
        return InvoiceDto.builder()
                .id(invoice.getId())
                .number(invoice.getNumber())
                .issueDate(invoice.getIssueDate())
                .taxpayerDto(taxpayerMapper.mapToTaxpayerDto(invoice.getTaxpayer()))
                .comments(invoice.getComments())
                .netTotal(invoice.getNetTotal())
                .vatTotal(invoice.getVatTotal())
                .grossTotal(invoice.getGrossTotal())
                .currencyGrossTotal(invoice.getCurrencyGrossTotal())
                .invoiceCurrency(invoice.getInvoiceCurrency())
                .build();
    }

    public List<InvoiceDto> mapToInvoiceDtoList(List<Invoice> invoiceList) {
        return invoiceList.stream()
                .map(i -> InvoiceDto.builder()
                        .id(i.getId())
                        .number(i.getNumber())
                        .issueDate(i.getIssueDate())
                        .taxpayerDto(taxpayerMapper.mapToTaxpayerDto(i.getTaxpayer()))
                        .comments(i.getComments())
                        .netTotal(i.getNetTotal())
                        .vatTotal(i.getVatTotal())
                        .grossTotal(i.getGrossTotal())
                        .currencyGrossTotal(i.getCurrencyGrossTotal())
                        .invoiceCurrency(i.getInvoiceCurrency())
                        .build())
                .collect(Collectors.toList());
    }
}
