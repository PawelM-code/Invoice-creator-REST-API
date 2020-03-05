package com.app.invoicecreator.mapper;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.item.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final ProductMapper productMapper;
    private final InvoiceMapper invoiceMapper;

    public Item mapToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                productMapper.mapToProduct(itemDto.getProductDto()),
                invoiceMapper.mapToInvoice(itemDto.getInvoiceDto()),
                itemDto.getNetPrice(),
                itemDto.getQuantity());
    }

    public ItemDto mapToItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                productMapper.mapToProductDto(item.getProduct()),
                invoiceMapper.mapToInvoiceDto(item.getInvoice()),
                item.getNetPrice(),
                item.getGrossPrice(),
                item.getQuantity(),
                item.getValue());
    }

    public List<Item> mapToItemList(List<ItemDto> itemDtoList) {
        return itemDtoList.stream()
                .map(i -> new Item(
                        i.getId(),
                        productMapper.mapToProduct(i.getProductDto()),
                        invoiceMapper.mapToInvoice(i.getInvoiceDto()),
                        i.getNetPrice(),
                        i.getQuantity()))
                .collect(Collectors.toList());
    }

    public List<ItemDto> mapToItemDtoList(List<Item> itemList) {
        return itemList.stream()
                .map(i -> new ItemDto(
                        i.getId(),
                        productMapper.mapToProductDto(i.getProduct()),
                        invoiceMapper.mapToInvoiceDto(i.getInvoice()),
                        i.getNetPrice(),
                        i.getGrossPrice(),
                        i.getQuantity(),
                        i.getValue()))
                .collect(Collectors.toList());
    }
}
