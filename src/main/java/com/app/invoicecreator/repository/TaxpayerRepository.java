package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxpayerRepository extends JpaRepository<Taxpayer, Long> {
    @Override
    <S extends Taxpayer> S save(S entity);

    @Override
    Taxpayer getOne(Long buyerId);
}
