package com.app.invoicecreator.service;

import com.app.invoicecreator.domain.owner.Owner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OwnerServiceTestSuite {
    @Autowired
    OwnerService ownerService;

    private Owner owner;

    @Before
    public void initSetup() {
        //Given
        owner = new Owner(
                "MyCompany",
                (long) 666,
                (long) 555,
                "Address",
                "12-12",
                "test@test.pl");
    }

    @Test
    public void testSaveOwner() {
        //When
        ownerService.save(owner);
        Optional<Owner> ownerOne = ownerService.findById(owner.getId());

        //Then
        assertTrue(ownerOne.isPresent());
        assertEquals("MyCompany", ownerOne.get().getName());
        assertEquals(Long.valueOf(666), ownerOne.get().getNip());
        assertEquals(Long.valueOf(555), ownerOne.get().getRegon());
        assertEquals("Address", ownerOne.get().getWorkingAddress());
        assertEquals("12-12", ownerOne.get().getBankAccount());
        assertEquals("test@test.pl", ownerOne.get().getEmail());
    }

    @Test
    public void testGetOwners() {
        //Given
        ownerService.save(owner);

        //When
        List<Owner> ownerList = ownerService.getOwners();

        //Then
        assertEquals(1, ownerList.size());
    }

    @Test
    public void testFindOwnerById() {
        //Given
        ownerService.save(owner);
        ownerService.save(new Owner());
        List<Owner> ownerList = ownerService.getOwners();

        //When
        Optional<Owner> ownerId = ownerService.findById(owner.getId());

        //Then
        assertEquals(2, ownerList.size());
        assertTrue(ownerId.isPresent());
        assertEquals("12-12", ownerId.get().getBankAccount());
    }

}
