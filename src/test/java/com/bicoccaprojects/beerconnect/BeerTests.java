package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.exception.beer.BeerAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.beer.BeerNotFoundException;
import com.bicoccaprojects.beerconnect.repository.PubRepository;
import com.bicoccaprojects.beerconnect.service.BeerService;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BeerTests {

    @Autowired
    private BeerService beerService;

    @Autowired
    private PubService pubService;

    private static final Long VALID_BEER_ID = 1L;
    private static final Long NON_EXISTENT_BEER_ID = 999L;
    private static final Long BEER_ID_TO_DELETE = 2L;
    private static final Long BEER_ID_TO_UPDATE = 3L;

    @BeforeEach
    @Sql("/data.sql")
    void setUp() {
        System.out.println("Dati aggiunti");
        System.out.println(beerService.getAllBeers());
    }

    @AfterEach
    void tearDown() {
        System.out.println("dati eliminati");
        beerService.deleteBeers();
    }

    @Test
    void getAllBeers() {
        Iterable<Beer> beers = beerService.getAllBeers();
        assertNotNull(beers);
    }

    @Test
    void getBeerById() {
        Beer beer = beerService.getBeer(VALID_BEER_ID);
        assertNotNull(beer);
        assertEquals(VALID_BEER_ID, beer.getIdBeer());
    }

    @Test
    void getBeerByIdNotFound() {
        assertThrows(BeerNotFoundException.class, () -> beerService.getBeer(NON_EXISTENT_BEER_ID));
    }

    @Test
    void deleteBeerById() {
        Iterable<Beer> allBeer = beerService.getAllBeers(); // get all the Beer in the DB

        // Check that BEER_ID_TO_DELETE is in allBeer collection
        assertTrue(StreamSupport.stream(allBeer.spliterator(), false).anyMatch(
                beer -> beer.getIdBeer().equals(BEER_ID_TO_DELETE)
        ), "Initial check failed: BEER_ID_TO_DELETE should be in the collection");

        // delete Beer with id = BEER_ID_TO_DELETE
        assertTrue(beerService.deleteBeer(BEER_ID_TO_DELETE), "Deletion failed for BEER_ID_TO_DELETE");
        allBeer = beerService.getAllBeers();

        // Check that BEER_ID_TO_DELETE isn't in allBeer collection
        assertTrue(StreamSupport.stream(allBeer.spliterator(), false).noneMatch(
                beer -> beer.getIdBeer().equals(BEER_ID_TO_DELETE)
        ), "Deletion check failed: BEER_ID_TO_DELETE should not be in the collection after deletion");
    }

    @Test
    void addDuplicateBeer() {
        Beer existingBeer = beerService.getBeer(BEER_ID_TO_UPDATE);
        assertThrows(BeerAlreadyExistsException.class, () -> beerService.addBeer(existingBeer));
    }

    @Test
    void addBeer() {
        Pub pub = pubService.getPub(1L);

        Beer testBeer = new Beer(17L, "beerTest", "Weiss", "Sweet", 5.0d, "Yellow", "Germany", "Hops", 2.99f, 100, pub);

        assertTrue(beerService.addBeer(testBeer), "Addition failed");

        assertNotNull(testBeer.getIdBeer(), "Beer ID should not be null after addition");

        Beer addedBeer = beerService.getBeer(testBeer.getIdBeer());

        assertNotNull(addedBeer, "Added beer should not be null");
        assertEquals("beerTest", addedBeer.getNameBeer(), "Name should match");
        assertEquals("Weiss", addedBeer.getType(), "Type should match");
        assertEquals(pub.getIdPub(), addedBeer.getPub().getIdPub(), "ID Pub should match");
        assertEquals(2.99f, addedBeer.getPrice(), "Price should match");
    }

    @Test
    void updateBeer() {
        Beer existingBeer = beerService.getBeer(BEER_ID_TO_UPDATE);
        assertNotNull(existingBeer, "Existing beer should not be null");

        existingBeer.setNameBeer("Updated Name");
        existingBeer.setType("Updated Type");

        beerService.updateBeer(existingBeer);

        Beer updatedBeer = beerService.getBeer(BEER_ID_TO_UPDATE);
        assertNotNull(updatedBeer, "Updated beer should not be null");
        assertEquals("Updated Name", updatedBeer.getNameBeer(), "Name should be updated");
        assertEquals("Updated Type", updatedBeer.getType(), "Type should be updated");
        // Add more assertions for other properties
    }

    @ParameterizedTest
    @ValueSource(strings = { "IPA", "Stout" })
    void searchBeerByType(String beerType) {
        List<String> beerList = beerService.searchBeerByType(beerType);
        assertFalse(beerList.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Slalom", "Cherry Blossom Saison", "Porter" })
    void searchBeerByName(String beerName) {
        List<String> beerList = beerService.searchBeerByName(beerName);
        assertFalse(beerList.isEmpty());
    }
}
