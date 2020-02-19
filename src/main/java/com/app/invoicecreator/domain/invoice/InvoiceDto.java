package com.app.invoicecreator.domain.invoice;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private String number;
    private String issueDate;
    private TaxpayerDto taxpayerDto;
    private String comments;
}
