package com.app.invoicecreator.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDataDto {
    private Long id;
    private String name;
    private Long nip;
    private Long regon;
    private String workingAddress;
}
