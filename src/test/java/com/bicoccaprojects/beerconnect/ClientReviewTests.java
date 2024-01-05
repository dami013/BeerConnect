package com.bicoccaprojects.beerconnect.service.relational_service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.exception.clientreviews.ClientReviewNotFoundException;
import com.bicoccaprojects.beerconnect.exception.clientreviews.NoClientReviewsFoundException;
import com.bicoccaprojects.beerconnect.repository.relational_repository.ClientReviewRepository;
import com.bicoccaprojects.beerconnect.service.BeerService;
import com.bicoccaprojects.beerconnect.service.ClientService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientReviewTests {

    @Autowired
    private ClientReviewService clientReviewService;
    @Autowired
    private ClientService clientService;

    @Autowired
    private BeerService beerService;

    private static final Long VALID_REVIEW_ID = 1L;
    private static final Long NON_EXISTENT_REVIEW_ID = 999L;
    private static final Long REVIEW_ID_TO_DELETE = 2L;
    private static final Long REVIEW_ID_TO_UPDATE = 3L;

    @BeforeEach
    @Sql("/data.sql")
    void setUp() {
        System.out.println("Client reviews added");
        System.out.println(clientReviewService.getAllReviews());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Client reviews deleted");
        clientReviewService.deleteReviews();
    }

    @Test
    void getAllReviews() {
        Iterable<ClientReview> reviews = clientReviewService.getAllReviews();
        assertNotNull(reviews);
    }

    @Test
    void getReviewById() {
        ClientReview review = clientReviewService.getReview(VALID_REVIEW_ID);
        assertNotNull(review);
        assertEquals(VALID_REVIEW_ID, review.getIdReview());
    }

    @Test
    void getReviewByIdNotFound() {
        assertThrows(ClientReviewNotFoundException.class, () -> clientReviewService.getReview(NON_EXISTENT_REVIEW_ID));
    }

    @Test
    void deleteReviewById() {
        assertTrue(clientReviewService.deleteReview(REVIEW_ID_TO_DELETE));
    }

    @Test
    void addDuplicateReview() {
        ClientReview existingReview = clientReviewService.getReview(REVIEW_ID_TO_UPDATE);
        assertThrows(Exception.class, () -> clientReviewService.addReview(existingReview));
    }

    @Test
    void addReview() {
        Client client = clientService.getClient(1L);
        Beer beer = beerService.getBeer(1L);
        ClientReview testReview = new ClientReview(17L, client, beer, 5, "WOW");

        assertDoesNotThrow(() -> clientReviewService.addReview(testReview));

        assertNotNull(testReview.getIdReview(), "Review ID should not be null after addition");

        ClientReview addedReview = clientReviewService.getReview(testReview.getIdReview());

        assertNotNull(addedReview, "Added review should not be null");
        assertEquals("WOW", addedReview.getReview(), "Review comment should match");
        assertEquals(5, addedReview.getRating(), "Reviewer name should match");
        // Add more assertions for other properties
    }

    @Test
    void updateReview() {
        ClientReview existingReview = clientReviewService.getReview(REVIEW_ID_TO_UPDATE);
        assertNotNull(existingReview, "Existing review should not be null");

        existingReview.setReview("Updated Comment");
        existingReview.setRating(4);

        assertDoesNotThrow(() -> clientReviewService.updateReview(existingReview));

        ClientReview updatedReview = clientReviewService.getReview(REVIEW_ID_TO_UPDATE);
        assertNotNull(updatedReview, "Updated review should not be null");
        assertEquals("Updated Comment", updatedReview.getReview(), "Review comment should be updated");
        assertEquals(4, updatedReview.getRating(), "Rating should be updated");
    }

    @Test
    void findReviewsByBeerCountryAndRating() {
        List<String> reviewList = clientReviewService.findReviewsByBeerCountryAndRating("Italy", 4);
        assertNotNull(reviewList);
        assertFalse(reviewList.isEmpty());
    }
}
