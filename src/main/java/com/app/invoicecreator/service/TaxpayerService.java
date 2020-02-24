package com.app.invoicecreator.service;

import com.app.invoicecreator.client.taxpayers.TaxpayerClient;
import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.exception.TaxpayerNotFoundException;
import com.app.invoicecreator.mapper.TaxpayerMapper;
import com.app.invoicecreator.repository.TaxpayerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxpayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaxpayerService.class);
    private final TaxpayerClient taxpayerClient;
    private final TaxpayerMapper taxpayerMapper;
    private final TaxpayerRepository taxpayerRepository;

    public Taxpayer getTaxpayerByNip(Long nip, String date) {
        return taxpayerMapper.mapToTaxpayer(taxpayerClient
                .getTaxpayerDataByNip(nip, date)
                .getResult()
                .getSubject());
    }

    public Long getTaxpayerId(Long nip) throws TaxpayerNotFoundException {
        Taxpayer taxpayer = taxpayerRepository.findByNip(nip).orElseThrow(TaxpayerNotFoundException::new);
        return taxpayer.getId();
    }

    public void saveTaxpayer(Taxpayer taxpayer) {
        Taxpayer taxpayerDB = taxpayerRepository.findByNip(taxpayer.getNip()).orElse(null);
        if (!taxpayer.equals(taxpayerDB)) {
            taxpayerRepository.save(taxpayer);
        } else {
            LOGGER.info("Not saved, the data is already in the database!");
        }
    }

    public List<Taxpayer> getTaxpayers() {
        return taxpayerRepository.findAll();
    }
}
