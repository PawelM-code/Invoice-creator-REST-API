package com.app.invoicecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private String number;
    private Date issueDate;
    private CompanyData dealerData;
    private CompanyData buyerData;
    private List<Item> products;
    private String comments;
}
