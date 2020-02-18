package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    <S extends Product> S save(S entity);

    @Override
    Optional<Product> findById(Long id);

    @Override
    List<Product> findAll();

    @Override
    void deleteById(Long id);
}
