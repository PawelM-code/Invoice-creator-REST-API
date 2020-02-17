package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    <S extends Product> S save(S entity);

    @Override
    Product getOne(Long productId);
}
