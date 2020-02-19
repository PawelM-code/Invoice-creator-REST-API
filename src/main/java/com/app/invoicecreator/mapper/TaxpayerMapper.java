package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import org.springframework.stereotype.Component;

@Component
public class TaxpayerMapper {
    public Taxpayer mapToTaxpayer(TaxpayerDto taxpayerDto) {
        return new Taxpayer(
                taxpayerDto.getId(),
                taxpayerDto.getName(),
                taxpayerDto.getNip(),
                taxpayerDto.getRegon(),
                taxpayerDto.getWorkingAddress());
    }

    public TaxpayerDto mapToTaxpayerDto(Taxpayer taxpayer) {
        return new TaxpayerDto(
                taxpayer.getId(),
                taxpayer.getName(),
                taxpayer.getNip(),
                taxpayer.getRegon(),
                taxpayer.getWorkingAddress()
        );
    }
}
