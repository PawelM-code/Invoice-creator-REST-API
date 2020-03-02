package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.owner.Owner;
import com.app.invoicecreator.domain.owner.OwnerDto;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
    public OwnerDto mapToOwnerDto(Owner owner){
        return new OwnerDto(
                owner.getId(),
                owner.getName(),
                owner.getNip(),
                owner.getRegon(),
                owner.getWorkingAddress(),
                owner.getBankAccount(),
                owner.getEmail()
        );
    }

    public Owner mapToOwner(OwnerDto ownerDto){
        return new Owner(
                ownerDto.getId(),
                ownerDto.getName(),
                ownerDto.getNip(),
                ownerDto.getRegon(),
                ownerDto.getWorkingAddress(),
                ownerDto.getBankAccount(),
                ownerDto.getEmail()
        );
    }
}
