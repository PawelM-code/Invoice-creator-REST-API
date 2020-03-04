package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.owner.OwnerDto;
import com.app.invoicecreator.exception.OwnerNotFoundException;
import com.app.invoicecreator.mapper.OwnerMapper;
import com.app.invoicecreator.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    @PostMapping(value = "/owner")
    public void saveOwner(@RequestBody OwnerDto ownerDto) {
        ownerService.save(ownerMapper.mapToOwner(ownerDto));
    }

    @GetMapping(value = "/owner/{id}")
    public OwnerDto getOwner(@PathVariable Long id) throws OwnerNotFoundException {
        return ownerMapper.mapToOwnerDto(ownerService.findById(id).orElseThrow(OwnerNotFoundException::new));
    }

    @GetMapping(value = "/owner")
    public List<OwnerDto> getOwners() {
        return ownerMapper.mapToOwnerDtoList(ownerService.getOwners());
    }

    @PutMapping(value = "/owner")
    public OwnerDto updateOwner(@RequestBody OwnerDto ownerDto) {
        return ownerMapper.mapToOwnerDto(ownerService.save(ownerMapper.mapToOwner(ownerDto)));
    }
}
