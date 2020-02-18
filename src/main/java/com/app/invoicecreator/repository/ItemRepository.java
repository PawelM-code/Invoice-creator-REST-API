package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Override
    <S extends Item> S save(S entity);

    @Override
    Item getOne(Long itemId);
}
