package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.exception.pub.PubAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.pub.PubNotFoundException;
import com.bicoccaprojects.beerconnect.exception.pub.NoPubsFoundException;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
public class PubTests {

    @Autowired
    private PubService pubService;

    private static final Long VALID_PUB_ID = 1L;
    private static final Long NON_EXISTENT_PUB_ID = 999L;
    private static final Long PUB_ID_TO_DELETE = 2L;
    private static final Long PUB_ID_TO_UPDATE = 3L;

    @BeforeEach
    @Sql("/data.sql")
    void setUp() {
        System.out.println("Pub data added");
        System.out.println(pubService.getAllPubs());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Pub data deleted");
        pubService.deletePubs();
    }

    @Test
    void getAllPubs() {
        Iterable<Pub> pubs = pubService.getAllPubs();
        assertNotNull(pubs);
    }

    @Test
    void getPubById() {
        Pub pub = pubService.getPub(VALID_PUB_ID);
        assertNotNull(pub);
        assertEquals(VALID_PUB_ID, pub.getIdPub());
    }

    @Test
    void getPubByIdNotFound() {
        assertThrows(PubNotFoundException.class, () -> pubService.getPub(NON_EXISTENT_PUB_ID));
    }

    @Test
    void deletePubById() {
        assertTrue(pubService.deletePub(PUB_ID_TO_DELETE));
    }

    @Test
    void addDuplicatePub() {
        Pub existingPub = pubService.getPub(PUB_ID_TO_UPDATE);
        assertThrows(PubAlreadyExistsException.class, () -> pubService.addPub(existingPub));
    }

    @Test
    void addPub() {
        // Given
        Pub testPub = new Pub(10L,"Test Pub","Germany",2002);

        // When
        pubService.addPub(testPub);

        // Then
        assertNotNull(testPub.getIdPub(), "Pub ID should not be null after addition");

        // Retrieve the added pub from the service or repository
        Pub addedPub = pubService.getPub(testPub.getIdPub());

        assertNotNull(addedPub, "Added pub should not be null");
        assertEquals("Test Pub", addedPub.getNamePub(), "Name should match");
        assertEquals("Germany", addedPub.getCountry(), "Location should match");
        // Add more assertions for other properties
    }

    @Test
    void updatePub() {
        // Given
        Pub existingPub = pubService.getPub(PUB_ID_TO_UPDATE);
        assertNotNull(existingPub, "Existing pub should not be null");

        // When
        existingPub.setNamePub("Updated Pub Name");
        existingPub.setCountry("Portugal");
        // Add other necessary updates

        pubService.updatePub(existingPub);

        // Then
        Pub updatedPub = pubService.getPub(PUB_ID_TO_UPDATE);
        assertNotNull(updatedPub, "Updated pub should not be null");
        assertEquals("Updated Pub Name", updatedPub.getNamePub(), "Name should be updated");
        assertEquals("Portugal", updatedPub.getCountry(), "Location should be updated");
        // Add other assertions for updated properties
    }

}
