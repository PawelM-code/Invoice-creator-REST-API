package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
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

    public Taxpayer getTaxpayerByNip(Long nip, String date){
        return taxpayerMapper.mapToTaxpayer(taxpayerClient
                .getTaxpayerDataByNip(nip, date)
                .getResult()
                .getSubject());
    }

    public void saveTaxpayer(Taxpayer taxpayer) {
        taxpayerRepository.save(taxpayer);
    }
}
