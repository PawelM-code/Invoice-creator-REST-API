package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Override
    <S extends Owner> S save(S entity);

    @Override
    Optional<Owner> findById(Long aLong);

    @Override
    List<Owner> findAll();
}