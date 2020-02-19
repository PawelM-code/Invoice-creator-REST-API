package com.app.invoicecreator.controller;

import com.app.invoicecreator.domain.item.ItemDto;
import com.app.invoicecreator.exception.ItemNotFoundException;
import com.app.invoicecreator.mapper.ItemMapper;
import com.app.invoicecreator.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @PostMapping(value = "/items")
    public void createItem(@RequestBody ItemDto itemDto){
        itemService.saveItem(itemMapper.mapToItem(itemDto));
    }

    @DeleteMapping(value = "/items/{id}")
    public void deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
    }

    @GetMapping(value = "/items/{id}")
    public ItemDto getItem(@PathVariable Long id) throws ItemNotFoundException{
        return itemMapper.mapToItemDto(itemService.getItem(id).orElseThrow(ItemNotFoundException::new));
    }

    @GetMapping(value = "/items")
    public List<ItemDto> getItems() {
        return itemMapper.mapToItemDtoList(itemService.getItems());
    }
}
