package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.exception.beer.BeerAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.beer.BeerNotFoundException;
import com.bicoccaprojects.beerconnect.service.BeerService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BeerTests {

    @Autowired
    private BeerService beerService;

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
        assertTrue(beerService.deleteBeer(BEER_ID_TO_DELETE));
    }

    @Test
    void addDuplicateBeer() {
        Beer existingBeer = beerService.getBeer(BEER_ID_TO_UPDATE);
        assertThrows(BeerAlreadyExistsException.class, () -> beerService.addBeer(existingBeer));
    }

    @Test
    void addBeer() {
        Beer testBeer = new Beer(17L, "ciao", "green", "black", 5.0d, "green", "italy", "milk", 2.99f, 100, null);

        beerService.addBeer(testBeer);

        assertNotNull(testBeer.getIdBeer(), "Beer ID should not be null after addition");

        Beer addedBeer = beerService.getBeer(testBeer.getIdBeer());

        assertNotNull(addedBeer, "Added beer should not be null");
        assertEquals("ciao", addedBeer.getNameBeer(), "Name should match");
        assertEquals("green", addedBeer.getType(), "Type should match");
        // Add more assertions for other properties
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
        assertNotNull(beerList);
        assertFalse(beerList.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = { "Slalom", "Cherry Blossom Saison", "Porter" })
    void searchBeerByName(String beerName) {
        List<String> beerList = beerService.searchBeerByName(beerName);
        assertNotNull(beerList);
        assertFalse(beerList.isEmpty());
    }
}
