package com.app.invoicecreator.facade.taxpayers;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.exception.TaxpayerNotFoundException;
import com.app.invoicecreator.mapper.TaxpayerMapper;
import com.app.invoicecreator.service.TaxpayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaxpayerFacade {
    private final TaxpayerService taxpayerService;
    private final TaxpayerMapper taxpayerMapper;

    public List<TaxpayerDto> getTaxpayers() {
        return taxpayerMapper.mapToTaxpayerDtoList(taxpayerService.getTaxpayers());
    }

    public TaxpayerDto getTaxpayer(Long nip, String date) {
        return taxpayerMapper.mapToTaxpayerDto(taxpayerService.getTaxpayerByNip(nip, date));
    }

    public void saveTaxpayer(TaxpayerDto taxpayerDto) {
        taxpayerService.saveTaxpayer(taxpayerMapper.mapToTaxpayer(taxpayerDto));
    }

    public Long getTaxpayerId(Long nip) throws TaxpayerNotFoundException {
        return taxpayerService.getTaxpayerId(nip);
    }
}
