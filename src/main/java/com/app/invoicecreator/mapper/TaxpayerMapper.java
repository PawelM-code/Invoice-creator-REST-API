package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.Taxpayer;
import com.app.invoicecreator.domain.TaxpayerDto;
import org.springframework.stereotype.Component;

@Component
public class TaxpayerMapper {
    public Taxpayer mapToTaxpayer(final TaxpayerDto taxpayerDto) {
        return new Taxpayer(
                taxpayerDto.getName(),
                taxpayerDto.getNip(),
                taxpayerDto.getRegon(),
                taxpayerDto.getWorkingAddress());
    }
}
