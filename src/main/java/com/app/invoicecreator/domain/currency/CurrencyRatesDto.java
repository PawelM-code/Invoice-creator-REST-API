package com.app.invoicecreator.domain.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CurrencyRatesDto {
    private String no;
    private BigDecimal mid;
    private String effectiveDate;
}
