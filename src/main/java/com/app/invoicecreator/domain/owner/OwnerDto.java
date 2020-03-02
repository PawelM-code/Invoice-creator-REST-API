package com.app.invoicecreator.domain.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {
    private Long id;
    private String name;
    private Long nip;
    private Long regon;
    private String workingAddress;
    private String bankAccount;
    private String email;
}
