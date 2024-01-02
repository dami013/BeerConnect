import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BeerTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BeerRepository beerRepository; // Assuming you have a repository for Beer entities

    @Test
    public void testCRUDOperations() {
        // Create
        Beer beer = new Beer("SampleBeer", null, "Type", "Aroma", 5.0, "Color", "Country", "Ingredients", 10.0f, 100);
        beerRepository.save(beer);
        assertNotNull(beer.getIdBeer());

        // Read
        Beer retrievedBeer = beerRepository.findById(beer.getIdBeer()).orElse(null);
        assertNotNull(retrievedBeer);
        assertEquals("SampleBeer", retrievedBeer.getNameBeer());

        // Update
        retrievedBeer.setPrice(12.0f);
        beerRepository.save(retrievedBeer);
        Beer updatedBeer = beerRepository.findById(beer.getIdBeer()).orElse(null);
        assertEquals(12.0f, updatedBeer.getPrice(), 0.001);

        // Delete
        beerRepository.deleteById(beer.getIdBeer());
        Beer deletedBeer = beerRepository.findById(beer.getIdBeer()).orElse(null);
        assertNull(deletedBeer);
    }

    @Test
    public void testClientReviewsAssociation() {
        Beer beer = new Beer("AnotherBeer", null, "Type", "Aroma", 5.0, "Color", "Country", "Ingredients", 10.0f, 100);
        ClientReview review = new ClientReview("Good beer!", beer);
        beerRepository.save(beer);
        entityManager.persist(review);

        Beer retrievedBeer = beerRepository.findById(beer.getIdBeer()).orElse(null);
        assertNotNull(retrievedBeer);

        List<ClientReview> reviews = retrievedBeer.getClientReviews();
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Good beer!", reviews.get(0).getReviewText());
    }

    @Test
    public void testSearchOperation() {
        Beer beer1 = new Beer("SearchBeer1", null, "Type1", "Aroma", 5.0, "Color", "Country", "Ingredients", 10.0f, 100);
        Beer beer2 = new Beer("SearchBeer2", null, "Type2", "Aroma", 6.0, "Color", "Country", "Ingredients", 12.0f, 150);
        beerRepository.save(beer1);
        beerRepository.save(beer2);

        // Search beers by type
        TypedQuery<Beer> query = entityManager.createQuery("SELECT b FROM Beer b WHERE b.type = :beerType", Beer.class);
        query.setParameter("beerType", "Type1");
        List<Beer> searchResults = query.getResultList();

        assertEquals(1, searchResults.size());
        assertEquals("SearchBeer1", searchResults.get(0).getNameBeer());
    }
}
