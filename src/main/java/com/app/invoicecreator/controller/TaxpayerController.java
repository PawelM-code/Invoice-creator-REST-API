package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.exception.TaxpayerNotFoundException;
import com.app.invoicecreator.mapper.TaxpayerMapper;
import com.app.invoicecreator.service.TaxpayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TaxpayerController {
    private final TaxpayerService taxpayerService;
    private final TaxpayerMapper taxpayerMapper;

    @GetMapping(value = "/taxpayers/{nip}&{date}")
    public TaxpayerDto getTaxpayer(@PathVariable Long nip, @PathVariable String date) {
        return taxpayerMapper.mapToTaxpayerDto(taxpayerService.getTaxpayerByNip(nip, date));
    }

    @GetMapping(value = "/taxpayers/id/{nip}")
    public Long getTaxpayerId(@PathVariable Long nip) throws TaxpayerNotFoundException {
        return taxpayerService.getTaxpayerId(nip);
    }

    @GetMapping(value = "/taxpayers")
    public List<TaxpayerDto> getTaxpayer() {
        return taxpayerMapper.mapToTaxpayerDtoList(taxpayerService.getTaxpayers());
    }

    @PostMapping(value = "/taxpayers")
    public void saveTaxpayer(@RequestBody TaxpayerDto taxpayerDto) {
        taxpayerService.saveTaxpayer(taxpayerMapper.mapToTaxpayer(taxpayerDto));
    }
}
