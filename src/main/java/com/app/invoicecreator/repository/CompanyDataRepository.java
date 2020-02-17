package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.CompanyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDataRepository extends JpaRepository<CompanyData, Long> {
    @Override
    <S extends CompanyData> S save(S entity);
}
