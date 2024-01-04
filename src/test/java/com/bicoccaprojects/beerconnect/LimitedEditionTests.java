package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.exception.beer.BeerNotFoundException;
import com.bicoccaprojects.beerconnect.exception.beer.NoBeersFoundException;
import com.bicoccaprojects.beerconnect.service.LimitedEditionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LimitedEditionTests {

    @Autowired
    private LimitedEditionService limitedEditionService;

    private static final Long VALID_LE_BEER_ID = 11L;
    private static final Long NON_EXISTENT_LE_BEER_ID = 999L;
    private static final Long LE_BEER_ID_TO_DELETE = 12L;
    private static final Long LE_BEER_ID_TO_UPDATE =11L;

    @BeforeEach
    @Sql("/data.sql")
    void setUp() {
        System.out.println("Data added");
        System.out.println(limitedEditionService.getAllLEBeers());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Data deleted");
        limitedEditionService.deleteLEBeers();
    }

    @Test
    void getAllLEBeers() {
        Iterable<LimitedEdition> leBeers = limitedEditionService.getAllLEBeers();
        assertNotNull(leBeers);
    }

    @Test
    void getLEBeerById() {
        LimitedEdition leBeer = limitedEditionService.getLEBeer(VALID_LE_BEER_ID);
        assertNotNull(leBeer);
        assertEquals(VALID_LE_BEER_ID, leBeer.getIdBeer());
    }

    @Test
    void getLEBeerByIdNotFound() {
        assertThrows(BeerNotFoundException.class, () -> limitedEditionService.getLEBeer(NON_EXISTENT_LE_BEER_ID));
    }

    @Test
    void deleteLEBeerById() {
        assertTrue(limitedEditionService.deleteLEBeer(LE_BEER_ID_TO_DELETE));
    }

    @Test
    void addDuplicateLEBeer() {
        LimitedEdition existingLEBeer = limitedEditionService.getLEBeer(LE_BEER_ID_TO_UPDATE);
        assertThrows(Exception.class, () -> limitedEditionService.addLEBeer(existingLEBeer));
    }

    @Test
    void addLEBeer()  {
        // Given
        // Creating an object with all values provided in the constructor
        LimitedEdition testLEBeer = new LimitedEdition(
16L,
                "Limited Edition Beer",
                "Special Type",
                "aroma",  // Replace with an actual Pub object
                3d,
                "pink",
                "green",         // Replace with the actual alcohol content
                "Example Color",
                10f,
                200,
                null,      // Replace with the actual price// Replace with the actual quantity
                "Original Example Beer",
                2022         // Replace with the actual production year
        );


        // When
        limitedEditionService.addLEBeer(testLEBeer);

        // Then
        assertNotNull(testLEBeer.getIdBeer(), "LE Beer ID should not be null after addition");

        // Retrieve the added LE beer from the service or repository
        LimitedEdition addedLEBeer = limitedEditionService.getLEBeer(testLEBeer.getIdBeer());

        assertNotNull(addedLEBeer, "Added LE beer should not be null");
        assertEquals("Limited Edition Beer", addedLEBeer.getNameBeer(), "Name should match");
        assertEquals("Special Type", addedLEBeer.getType(), "Type should match");
        // Add more assertions for other properties
    }

    @Test
    void updateLEBeer() {
        // Given
        LimitedEdition existingLEBeer = limitedEditionService.getLEBeer(LE_BEER_ID_TO_UPDATE);
        assertNotNull(existingLEBeer, "Existing LE beer should not be null");

        // When
        existingLEBeer.setNameBeer("Updated Limited Edition Name");
        existingLEBeer.setType("Updated Limited Edition Type");

        limitedEditionService.updateLEBeer(existingLEBeer);

        // Then
        LimitedEdition updatedLEBeer = limitedEditionService.getLEBeer(LE_BEER_ID_TO_UPDATE);
        assertNotNull(updatedLEBeer, "Updated LE beer should not be null");
        assertEquals("Updated Limited Edition Name", updatedLEBeer.getNameBeer(), "Name should be updated");
        assertEquals("Updated Limited Edition Type", updatedLEBeer.getType(), "Type should be updated");
        // Add more assertions for other properties
    }

    @Test
    void findLEByOriginalBeer() {
        List<String> leBeerList = limitedEditionService.findLEByOriginalBeer("Slalom");
        assertNotNull(leBeerList);
        assertFalse(leBeerList.isEmpty());
    }

    @Test
    void searchLEByName() {
        List<String> leBeerList = limitedEditionService.searchLEByName("Xmas Slalom");
        assertNotNull(leBeerList);
        assertFalse(leBeerList.isEmpty());
    }
}
