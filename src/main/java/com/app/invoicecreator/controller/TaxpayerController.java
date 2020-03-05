package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.exception.TaxpayerNotFoundException;
import com.app.invoicecreator.facade.taxpayers.TaxpayerFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TaxpayerController {
    private final TaxpayerFacade taxpayerFacade;

    @GetMapping(value = "/taxpayers/{nip}&{date}")
    public TaxpayerDto getTaxpayer(@PathVariable Long nip, @PathVariable String date) {
        return taxpayerFacade.getTaxpayer(nip, date);
    }

    @GetMapping(value = "/taxpayers/id/{nip}")
    public Long getTaxpayerId(@PathVariable Long nip) throws TaxpayerNotFoundException {
        return taxpayerFacade.getTaxpayerId(nip);
    }

    @GetMapping(value = "/taxpayers")
    public List<TaxpayerDto> getTaxpayers() {
        return taxpayerFacade.getTaxpayers();
    }

    @PostMapping(value = "/taxpayers")
    public void saveTaxpayer(@RequestBody TaxpayerDto taxpayerDto) {
        taxpayerFacade.saveTaxpayer(taxpayerDto);
    }
}
