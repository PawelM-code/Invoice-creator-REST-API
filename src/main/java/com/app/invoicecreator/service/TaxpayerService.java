package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.domain.taxpayer.TaxpayerResultDto;
import com.app.invoicecreator.mapper.TaxpayerMapper;
import com.app.invoicecreator.repository.TaxpayerRepository;
import com.app.invoicecreator.client.taxpayers.TaxpayerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxpayerService {
    private final TaxpayerClient taxpayerClient;
    private final TaxpayerMapper taxpayerMapper;
    private final TaxpayerRepository taxpayerRepository;

    public TaxpayerDto getTaxpayerByNip(Long nip, String date){
        TaxpayerResultDto taxpayerResultDto = taxpayerClient.getTaxpayerDataByNip(nip, date);
        taxpayerRepository.save(taxpayerMapper.mapToTaxpayer(taxpayerResultDto.getResult().getSubject()));
        return taxpayerResultDto.getResult().getSubject();
    }
}
