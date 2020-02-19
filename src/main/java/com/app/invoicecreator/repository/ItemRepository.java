package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Override
    <S extends Item> S save(S entity);

    @Override
    Optional<Item> findById(Long id);

    @Override
    List<Item> findAll();

    @Override
    void deleteById(Long id);
}
