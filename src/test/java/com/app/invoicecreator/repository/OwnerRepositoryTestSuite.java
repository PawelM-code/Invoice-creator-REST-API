package com.app.invoicecreator.repository;

import com.app.invoicecreator.domain.owner.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OwnerRepositoryTestSuite {
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    public void testOwnerRepository() {
        //Given
        Owner owner = new Owner(
                "MyCompany",
                111L,
                22222L,
                "ul. Calineczki",
                "12-1212-1212-1212",
                "test@test.test");

        //When
        ownerRepository.save(owner);
        Optional<Owner> findedOwner = ownerRepository.findById(owner.getId());

        //Then
        assertTrue(findedOwner.isPresent());
        assertEquals("MyCompany", findedOwner.get().getName());
        assertEquals(111L, (long) findedOwner.get().getNip());
        assertEquals(22222L, (long) findedOwner.get().getRegon());
        assertEquals("ul. Calineczki", findedOwner.get().getWorkingAddress());
        assertEquals("12-1212-1212-1212", findedOwner.get().getBankAccount());
        assertEquals("test@test.test", findedOwner.get().getEmail());
        assertEquals(1, ownerRepository.findAll().size());
    }
}
