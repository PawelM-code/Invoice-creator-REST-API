package com.app.invoicecreator.domain.invoice;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class InvoiceDto {
    private Long id;
    private String number;
    private String issueDate;
    private TaxpayerDto taxpayerDto;
    private String comments;
    private BigDecimal baseTotal;
    private BigDecimal plnTotal;
    private InvoiceCurrency invoiceCurrency;
    private String dateOfPayment;
}
