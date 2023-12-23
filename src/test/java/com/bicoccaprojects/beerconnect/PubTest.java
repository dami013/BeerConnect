package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PubTest {

    @Autowired
    private PubService pubService;

    @BeforeEach
    public void setUp() {
        pubService.deletePubs();
    }

    @Test
    public void testAddAndGetPub() {
        Pub pub = new Pub("Test Pub", "Test Country", 2000);
        pubService.addPub(pub);

        Optional<Pub> retrievedPub = pubService.getPub(1L);
        assertTrue(retrievedPub.isPresent());
        assertEquals("Test Pub", retrievedPub.get().getNamePub());
    }

    @Test
    public void testUpdatePub() {
        Pub pub = new Pub("Test Pub", "Test Country", 2000);
        pubService.addPub(pub);

        Optional<Pub> retrievedPub = pubService.getPub(1L);
        assertTrue(retrievedPub.isPresent());

        Pub updatedPub = retrievedPub.get();
        updatedPub.setYearOfFoundation(2020);
        pubService.updatePub(updatedPub);

        Optional<Pub> updatedPubOptional = pubService.getPub(1L);
        assertTrue(updatedPubOptional.isPresent());
        assertEquals(2020, updatedPubOptional.get().getYearOfFoundation());
    }

    @Test
    public void testDeletePub() {
        Pub pub = new Pub("Test Pub", "Test Country", 2000);
        pubService.addPub(pub);

        Optional<Pub> retrievedPub = pubService.getPub(1L);
        assertTrue(retrievedPub.isPresent());

        pubService.deletePub(1L);

        Optional<Pub> deletedPub = pubService.getPub(1L);
        assertFalse(deletedPub.isPresent());
    }

    @Test
    public void testGetPubs() {
        // Add some test pubs
        pubService.addPub(new Pub("Pub1", "Country1", 1990));
        pubService.addPub(new Pub("Pub2", "Country2", 2005));

        Iterable<Pub> pubs = pubService.getPubs();
        assertNotNull(pubs);

        // Convert Iterable to List for easier assertions
        var pubList = (List<Pub>) pubs;

        assertEquals(2, pubList.size());
        assertEquals("Pub1", pubList.get(0).getNamePub());
        assertEquals("Pub2", pubList.get(1).getNamePub());
    }

    @Test
    public void testDeletePubs() {
        // Add some test pubs
        pubService.addPub(new Pub("Pub1", "Country1", 1990));
        pubService.addPub(new Pub("Pub2", "Country2", 2005));

        pubService.deletePubs();

        Iterable<Pub> pubs = pubService.getPubs();
        assertFalse(pubs.iterator().hasNext()); // No pubs should be present after deletion
    }
}
