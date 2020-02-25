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

    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> getItemsByInvoiceId(Long id) {
        return itemRepository.findAllByInvoiceId(id);
    }

    public Optional<Item> getItem(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
