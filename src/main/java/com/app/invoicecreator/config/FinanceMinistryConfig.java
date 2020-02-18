package com.app.invoicecreator.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FinanceMinistryConfig {
    @Value("${mf.api.prod}")
    private String financeMinistryApiEndpoint;
}
