package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.exception.clientreviews.ClientReviewNotFoundException;
import com.bicoccaprojects.beerconnect.service.BeerService;
import com.bicoccaprojects.beerconnect.service.ClientService;
import com.bicoccaprojects.beerconnect.service.relational_service.ClientReviewService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.StreamSupport;

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
    void setUp() {}

    @AfterEach
    void tearDown() {
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
        Iterable<ClientReview> allReview = clientReviewService.getAllReviews(); // get all the Review in the DB

        // Check that REVIEW_ID_TO_DELETE is in allReview collection
        assertTrue(StreamSupport.stream(allReview.spliterator(), false).anyMatch(
                review -> review.getIdReview().equals(REVIEW_ID_TO_DELETE)
        ), "Initial check failed: REVIEW_ID_TO_DELETE should be in the collection");

        // delete Review with id = REVIEW_ID_TO_DELETE
        assertDoesNotThrow(() -> clientReviewService.deleteReview(REVIEW_ID_TO_DELETE), "Deletion failed for BEER_ID_TO_DELETE");
        allReview = clientReviewService.getAllReviews();

        // Check that REVIEW_ID_TO_DELETE isn't in allReview collection
        assertTrue(StreamSupport.stream(allReview.spliterator(), false).noneMatch(
                review -> review.getIdReview().equals(REVIEW_ID_TO_DELETE)
        ), "Deletion check failed: REVIEW_ID_TO_DELETE should not be in the collection after deletion");
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
        assertEquals(testReview.getIdClient(), addedReview.getIdClient(), "ID Client should match");
        assertEquals(testReview.getIdBeer(), addedReview.getIdBeer(), "ID Beer should match");
        assertEquals(testReview.getReview(), addedReview.getReview(), "Review comment should match");
        assertEquals(testReview.getRating(), addedReview.getRating(), "Reviewer name should match");
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
        assertEquals(existingReview.getReview(), updatedReview.getReview(), "Review comment should be updated");
        assertEquals(existingReview.getRating(), updatedReview.getRating(), "Rating should be updated");
    }

    @Test
    void findReviewsByBeerCountryAndRating() {
        List<String> reviewList = clientReviewService.findReviewsByBeerCountryAndRating("Japan", 4);
        assertFalse(reviewList.isEmpty());
        assertEquals("Fantastic Saison with a delightful herbal touch.", reviewList.get(0));
    }
}