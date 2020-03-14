package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.taxpayer.Taxpayer;
import com.app.invoicecreator.exception.TaxpayerNotFoundException;
import com.app.invoicecreator.repository.TaxpayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaxpayerServiceTestSuite {
    @Autowired
    TaxpayerService taxpayerService;

    @Autowired
    TaxpayerRepository taxpayerRepository;

    private Taxpayer taxpayer;

    @Before
    public void initSetup() {
        //Given
        taxpayer = new Taxpayer("Company test", 111L, 222L, "Address");
    }

    @Test
    public void testSaveTaxpayer() {
        //When
        taxpayerService.saveTaxpayer(taxpayer);
        Long id = taxpayer.getId();
        Taxpayer findTaxpayer = taxpayerRepository.getOne(id);

        //Then
        assertEquals("Company test", findTaxpayer.getName());
        assertEquals(111L, (long) findTaxpayer.getNip());
        assertEquals(222L, (long) findTaxpayer.getRegon());
        assertEquals("Address", findTaxpayer.getWorkingAddress());
    }

    @Test
    public void testGetTaxpayerId() throws TaxpayerNotFoundException {
        //Given
        taxpayerService.saveTaxpayer(taxpayer);
        Long id = taxpayer.getId();

        //When
        Long findTaxpayerId = taxpayerService.getTaxpayerId(111L);

        //Then
        assertEquals(id, findTaxpayerId);
    }

    @Test
    public void testGetTaxpayers() {
        //Given
        taxpayerService.saveTaxpayer(taxpayer);
        taxpayerService.saveTaxpayer(new Taxpayer());

        //When
        List<Taxpayer> taxpayerList = taxpayerService.getTaxpayers();

        //Then
        assertEquals(2, taxpayerList.size());
    }
}
