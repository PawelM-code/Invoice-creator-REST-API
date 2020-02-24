package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxpayerRepository extends JpaRepository<Taxpayer, Long> {
    @Override
    <S extends Taxpayer> S save(S entity);

    @Override
    Optional<Taxpayer> findById(Long id);

    @Override
    List<Taxpayer> findAll();

    Optional<Taxpayer> findByNip(Long nip);

}
