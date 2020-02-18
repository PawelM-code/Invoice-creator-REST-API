package com.app.invoicecreator.client.currencies;

import com.app.invoicecreator.config.NbpConfig;
import com.app.invoicecreator.domain.currency.CurrencyDto;
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
public class CurrencyClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyClient.class);
    private final NbpConfig nbpConfig;
    private final RestTemplate restTemplate;

    public CurrencyDto getCurrencyRateByCode(String code, String date) {
        URI url = getCurrencyRateUri(code, date);

        try {
            String response = restTemplate.getForObject(url, String.class);
            Gson gson = new Gson();

            return gson.fromJson(response, CurrencyDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new CurrencyDto();
        }
    }

    private URI getCurrencyRateUri(String code, String date) {
        return UriComponentsBuilder.fromHttpUrl(nbpConfig.getNbpApiEndpoint()
                + "/"
                + code
                + "/"
                + date).build().encode().toUri();
    }
}
