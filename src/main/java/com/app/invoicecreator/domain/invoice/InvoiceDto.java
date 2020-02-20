package com.app.invoicecreator.domain.invoice;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto {
    private Long id;
    private String number;
    private String issueDate;
    private TaxpayerDto taxpayerDto;
    private String comments;
    private BigDecimal total;
    private InvoiceCurrency invoiceCurrency;
}
