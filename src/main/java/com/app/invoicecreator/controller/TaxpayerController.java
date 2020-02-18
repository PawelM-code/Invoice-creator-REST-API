package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.taxpayer.TaxpayerDto;
import com.app.invoicecreator.service.TaxpayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TaxpayerController {
    private final TaxpayerService taxpayerService;

    @GetMapping(value = "/taxpayers/{nip}&{date}")
    public TaxpayerDto getTaxpayer(@PathVariable Long nip, @PathVariable String date) {
        return taxpayerService.getTaxpayerByNip(nip, date);
    }
}
