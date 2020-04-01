package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaxpayerRepositoryTestSuite {
    @Autowired
    TaxpayerRepository taxpayerRepository;

    @Test
    public void testTaxpayerRepository() {
        //Given
        Taxpayer taxpayer = new Taxpayer("Firma", 666666666L, 555555L, "Powstańców 33");

        //When
        taxpayerRepository.save(taxpayer);
        Long id = taxpayer.getId();

        //Then
        assertTrue(taxpayerRepository.findById(id).isPresent());
        assertEquals(taxpayer.getName(), taxpayerRepository.findById(id).get().getName());
        assertEquals(taxpayer.getNip(), taxpayerRepository.findById(id).get().getNip());
        assertEquals(taxpayer.getRegon(), taxpayerRepository.findById(id).get().getRegon());
        assertEquals(taxpayer.getWorkingAddress(), taxpayerRepository.findById(id).get().getWorkingAddress());
    }
}
