package com.app.invoicecreator.domain.item;

import com.app.invoicecreator.domain.invoice.InvoiceDto;
import com.app.invoicecreator.domain.product.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private ProductDto productDto;
    private InvoiceDto invoiceDto;
    private BigDecimal netPrice;
    private BigDecimal grossPrice;
    private int quantity;
    private BigDecimal value;
}
