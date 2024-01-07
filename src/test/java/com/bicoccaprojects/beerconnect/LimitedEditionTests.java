package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.exception.beer.BeerNotFoundException;
import com.bicoccaprojects.beerconnect.exception.beer.NoBeersFoundException;
import com.bicoccaprojects.beerconnect.service.LimitedEditionService;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.IterableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LimitedEditionTests {

    @Autowired
    private LimitedEditionService limitedEditionService;

    @Autowired
    private PubService pubService;

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
        Iterable<LimitedEdition> allLE = limitedEditionService.getAllLEBeers(); // get all Limited Edition in the DB

        // Check that LE_BEER_ID_TO_DELETE is in allLE collection
        assertTrue(StreamSupport.stream(allLE.spliterator(), false).anyMatch(
                limited -> limited.getIdBeer().equals(LE_BEER_ID_TO_DELETE)
        ), "Initial check failed: LE_BEER_ID_TO_DELETE should be in the collection");

        // delete Limited Edition Beer with id = LE_BEER_ID_TO_DELETE
        assertTrue(limitedEditionService.deleteBeer(LE_BEER_ID_TO_DELETE), "Deletion failed for LE_BEER_ID_TO_DELETE");
        allLE = limitedEditionService.getAllLEBeers();

        // Check that LE_BEER_ID_TO_DELETE isn't in allLE collection
        assertTrue(StreamSupport.stream(allLE.spliterator(), false).noneMatch(
                limited -> limited.getIdBeer().equals(LE_BEER_ID_TO_DELETE)
        ), "Deletion check failed: LE_BEER_ID_TO_DELETE should not be in the collection after deletion");
    }

    @Test
    void addDuplicateLEBeer() {
        LimitedEdition existingLEBeer = limitedEditionService.getLEBeer(LE_BEER_ID_TO_UPDATE);
        assertThrows(Exception.class, () -> limitedEditionService.addLEBeer(existingLEBeer));
    }

    @Test
    void addLEBeer()  {

        Pub pub = pubService.getPub(1L);

        LimitedEdition testLE = new LimitedEdition(16L, "limitedEditionTest", "Weiss", "Sweet", 5.0d, "Yellow", "Germany", "Hops", 3.0f, 100, pub, "Slalom", 2024);

        // When
        assertTrue(limitedEditionService.addLEBeer(testLE), "Addition failed");

        // Then
        assertNotNull(testLE.getIdBeer(), "LE Beer ID should not be null after addition");

        // Retrieve the added LE beer from the service or repository
        LimitedEdition addedLEBeer = limitedEditionService.getLEBeer(testLE.getIdBeer());

        assertNotNull(addedLEBeer, "Added beer should not be null");
        assertEquals("limitedEditionTest", addedLEBeer.getNameBeer(), "Name should match");
        assertEquals("Weiss", addedLEBeer.getType(), "Type should match");
        assertEquals(pub.getIdPub(), addedLEBeer.getPub().getIdPub(), "ID Pub should match");
        assertEquals(testLE.getOriginalBeer(), addedLEBeer.getOriginalBeer(), "Original beer name should match");
    }

    @Test
    void updateLEBeer() {
        // Given
        LimitedEdition existingLEBeer = limitedEditionService.getLEBeer(LE_BEER_ID_TO_UPDATE);
        assertNotNull(existingLEBeer, "Existing LE beer should not be null");

        Pub pub = pubService.getPub(2L);

        // When
        existingLEBeer.setNameBeer("Updated Limited Edition Name");
        existingLEBeer.setType("Updated Limited Edition Type");
        existingLEBeer.setPub(pub);

        limitedEditionService.updateLEBeer(existingLEBeer);

        // Then
        LimitedEdition updatedLEBeer = limitedEditionService.getLEBeer(LE_BEER_ID_TO_UPDATE);

        assertNotNull(updatedLEBeer, "Updated LE beer should not be null");
        assertEquals(existingLEBeer.getNameBeer(), updatedLEBeer.getNameBeer(), "Name should be updated");
        assertEquals(existingLEBeer.getType(), updatedLEBeer.getType(), "Type should be updated");
        assertEquals(existingLEBeer.getPub(), updatedLEBeer.getPub(), "Pub should be updated match");
    }

    @Test
    void findLEByOriginalBeer() {
        List<String> leBeerList = limitedEditionService.findLEByOriginalBeer("Slalom");
        assertFalse(leBeerList.isEmpty());
    }

    @Test
    void searchLEByName() {
        List<String> leBeerList = limitedEditionService.searchLEByName("Xmas Slalom");
        assertFalse(leBeerList.isEmpty());
    }
}
