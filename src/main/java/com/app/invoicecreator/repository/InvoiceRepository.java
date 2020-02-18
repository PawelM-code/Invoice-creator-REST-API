package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Override
    <S extends Invoice> S save(S entity);

    @Override
    Invoice getOne(Long id);
}
