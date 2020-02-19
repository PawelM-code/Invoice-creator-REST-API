package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Override
    <S extends Invoice> S save(S entity);

    @Override
    Optional<Invoice> findById(Long id);

    @Override
    void deleteById(Long id);

    @Override
    List<Invoice> findAll();
}
