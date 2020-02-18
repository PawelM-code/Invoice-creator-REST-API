package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.item.ItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item mapToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getProduct(),
                itemDto.getPrice(),
                itemDto.getQuantity());
    }

    public List<Item> mapToItemList(List<ItemDto> itemDtoList) {
        return itemDtoList.stream()
                .map(i -> new Item(
                        i.getProduct(),
                        i.getPrice(),
                        i.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<ItemDto> mapToItemDtoList(List<Item> itemList) {
        return itemList.stream()
                .map(i -> new ItemDto(
                        i.getId(),
                        i.getProduct(),
                        i.getInvoice(),
                        i.getPrice(),
                        i.getQuantity(),
                        i.getValue()))
                .collect(Collectors.toList());
    }
}
