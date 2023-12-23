package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.service.BeerService;
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
public class BeerTest {

    @Autowired
    private BeerService beerService;


    @BeforeEach
    public void setUp() {
        beerService.deleteBeers();
    }

    @Test
    public void testAddAndGetBeer() {
        Beer beer = new Beer("Test Beer", "Test Type", "Test Aroma", 5.0, "Test Color", "Test Country", "Test Ingredients", 10.0f, 100);
        beerService.addBeer(beer);
        Optional<Beer> retrievedBeer = beerService.getBeer(1L);
        assertTrue(retrievedBeer.isPresent());
        assertEquals("Test Beer", retrievedBeer.get().getNameBeer());
    }

    @Test
    public void testUpdateBeer() {
        Beer beer = new Beer("Test Beer", "Test Type", "Test Aroma", 5.0, "Test Color", "Test Country", "Test Ingredients", 10.0f, 100);
        beerService.addBeer(beer);

        Optional<Beer> retrievedBeer = beerService.getBeer(1L);
        assertTrue(retrievedBeer.isPresent());

        Beer updatedBeer = retrievedBeer.get();
        updatedBeer.setPrice(15.0f);
        beerService.updateBeer(updatedBeer);

        Optional<Beer> updatedBeerOptional = beerService.getBeer(1L);
        assertTrue(updatedBeerOptional.isPresent());
        assertEquals(15.0f, updatedBeerOptional.get().getPrice(), 0.01);
    }

    @Test
    public void testDeleteBeer() {
        Beer beer = new Beer("Test Beer", "Test Type", "Test Aroma", 5.0, "Test Color", "Test Country", "Test Ingredients", 10.0f, 100);
        beerService.addBeer(beer);

        Optional<Beer> retrievedBeer = beerService.getBeer(1L);
        assertTrue(retrievedBeer.isPresent());

        beerService.deleteBeer(1L);

        Optional<Beer> deletedBeer = beerService.getBeer(1L);
        assertFalse(deletedBeer.isPresent());
    }

    @Test
    public void testGetBeers() {
        // Add some test beers
        beerService.addBeer(new Beer("Beer1", "Type1", "Aroma1", 5.0, "Color1", "Country1", "Ingredients1", 10.0f, 100));
        beerService.addBeer(new Beer("Beer2", "Type2", "Aroma2", 6.0, "Color2", "Country2", "Ingredients2", 12.0f, 120));

        Iterable<Beer> beers = beerService.getBeers();
        assertNotNull(beers);

        // Convert Iterable to List for easier assertions
        List<Beer> beerList = (List<Beer>) beers;

        assertEquals(2, beerList.size());
        assertEquals("Beer1", beerList.get(0).getNameBeer());
        assertEquals("Beer2", beerList.get(1).getNameBeer());
    }

    @Test
    public void testDeleteBeers() {
        // Add some test beers
        beerService.addBeer(new Beer("Beer1", "Type1", "Aroma1", 5.0, "Color1", "Country1", "Ingredients1", 10.0f, 100));
        beerService.addBeer(new Beer("Beer2", "Type2", "Aroma2", 6.0, "Color2", "Country2", "Ingredients2", 12.0f, 120));

        beerService.deleteBeers();

        Iterable<Beer> beers = beerService.getBeers();
        assertFalse(beers.iterator().hasNext()); // No beers should be present after deletion
    }
}
