package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<TaxpayerDto> mapToTaxpayerDtoList(List<Taxpayer> taxpayers) {
        return taxpayers.stream()
                .map(taxpayer -> new TaxpayerDto(
                        taxpayer.getId(),
                        taxpayer.getName(),
                        taxpayer.getNip(),
                        taxpayer.getRegon(),
                        taxpayer.getWorkingAddress()))
                .collect(Collectors.toList());
    }
}
