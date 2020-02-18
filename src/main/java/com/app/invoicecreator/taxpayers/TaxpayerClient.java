package com.app.invoicecreator.taxpayers;

import com.app.invoicecreator.config.FinanceMinistryConfig;
import com.app.invoicecreator.domain.jsonTaxpayer.TaxpayerResultDto;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TaxpayerClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaxpayerClient.class);
    private final FinanceMinistryConfig financeMinistryConfig;
    private final RestTemplate restTemplate;

    public TaxpayerResultDto getTaxpayerDataByNip(Long nip, String date) {
        URI url = getTaxpayerDataNipUri(nip, date);

        try {
           String response = restTemplate.getForObject(url, String.class);
           Gson gson = new Gson();

           return gson.fromJson(response, TaxpayerResultDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new TaxpayerResultDto();
        }
    }

    private URI getTaxpayerDataNipUri(Long nip, String date) {
        return UriComponentsBuilder.fromHttpUrl(financeMinistryConfig.getFinanceMinistryApiEndpoint()
                + "/search/nip/"
                + nip
                + "?date="
                + date).build().encode().toUri();
    }
}
