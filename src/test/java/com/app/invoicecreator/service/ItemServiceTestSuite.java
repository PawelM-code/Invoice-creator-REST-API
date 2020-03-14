package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.item.Item;
import com.app.invoicecreator.domain.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTestSuite {
    @Autowired
    ProductService productService;
    @Autowired
    ItemService itemService;

    private Item item;

    @Before
    public void initSetup() {
        //Given
        Product product = new Product("TV", 23);
        item = new Item(product, new BigDecimal(1000), 2);

        productService.saveProduct(product);
    }

    @Test
    public void testSaveItem() {
        //When
        itemService.saveItem(item);
        Optional<Item> findItem = itemService.getItem(item.getId());

        //Then
        assertTrue(findItem.isPresent());
        assertEquals("TV", findItem.get().getProduct().getDescription());
        assertEquals(new BigDecimal(1000).setScale(0, RoundingMode.HALF_UP), findItem.get().getNetPrice());
        assertEquals(new BigDecimal(230).setScale(2, RoundingMode.HALF_UP), findItem.get().getVat());
        assertEquals(new BigDecimal(1230).setScale(2, RoundingMode.HALF_UP), findItem.get().getGrossPrice());
        assertEquals(new BigDecimal(2460).setScale(2, RoundingMode.HALF_UP), findItem.get().getValue());
    }

    @Test
    public void testGetItems() {
        //When
        itemService.saveItem(item);
        List<Item> itemList = itemService.getItems();

        //Then
        assertEquals(1, itemList.size());
    }

    @Test
    public void testDeleteItem() {
        //When
        itemService.saveItem(item);
        itemService.deleteItem(item.getId());
        List<Item> itemList = itemService.getItems();

        //Then
        assertEquals(0, itemList.size());
    }
}
