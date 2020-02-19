package com.app.invoicecreator.domain.taxpayer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxpayerDto {
    //    @JsonIgnore
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("nip")
    private Long nip;
    @JsonProperty("regon")
    private Long regon;
    @JsonProperty("workingAddress")
    private String workingAddress;
}
