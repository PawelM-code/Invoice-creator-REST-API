package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Override
    <S extends Buyer> S save(S entity);

    @Override
    Buyer getOne(Long buyerId);
}
