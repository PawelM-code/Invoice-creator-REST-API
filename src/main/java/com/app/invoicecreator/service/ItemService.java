package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item saveItem(Item item){
        return itemRepository.save(item);
    }

    public Optional<Item> getItem(Long id){
        return itemRepository.findById(id);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }
}
