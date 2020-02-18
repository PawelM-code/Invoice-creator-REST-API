package com.app.invoicecreator.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NbpConfig {
    @Value("${nbp.api.prod}")
    private String NbpApiEndpoint;
}
