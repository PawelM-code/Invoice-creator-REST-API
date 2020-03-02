package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.owner.Owner;
import com.app.invoicecreator.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    public List<Owner> getOwners(){
        return ownerRepository.findAll();
    }

    public Optional<Owner> findById(Long id){
        return ownerRepository.findById(id);
    }
}
