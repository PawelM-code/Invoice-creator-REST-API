package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.product.Product;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemRepositoryTestSuite {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void testItemSave() {
        //Given
        Product product = new Product("Samsung Z flip", 23);
        productRepository.save(product);
        Item item = new Item(product, new BigDecimal(100), 10);

        //When
        itemRepository.save(item);
        Long id = item.getId();

        //Then
        assertTrue(itemRepository.findById(id).isPresent());
        assertEquals(10, itemRepository.findById(id).get().getQuantity());
        assertEquals(new BigDecimal(100), itemRepository.findById(id).get().getNetPrice());
        assertThat(new BigDecimal(1230.00), Matchers.comparesEqualTo(itemRepository.getOne(id).getValue()));
        assertThat(new BigDecimal(123), Matchers.comparesEqualTo(itemRepository.findById(id).get().getGrossPrice()));
    }
}
