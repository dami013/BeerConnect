package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.service.LimitedEditionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LimitedTest {

    @Autowired
    private LimitedEditionService limitedEditionService;

    @BeforeEach
    public void setUp() {
        limitedEditionService.deleteLEBeers();
    }

    @Test
    public void testAddAndGetLimitedEditionBeer() {
        LimitedEdition limitedEdition = new LimitedEdition("Limited Beer", "Limited Type", "Limited Aroma", 7.0, "Limited Color", "Limited Country", "Limited Ingredients", 20.0f, 50, "Original Beer", 2023);
        limitedEditionService.addLEBeer(limitedEdition);

        Optional<LimitedEdition> retrievedLimitedEdition = limitedEditionService.getLEBeer(1L);
        assertTrue(retrievedLimitedEdition.isPresent());
        assertEquals("Limited Beer", retrievedLimitedEdition.get().getNameBeer());
    }

    @Test
    public void testUpdateLimitedEditionBeer() {
        LimitedEdition limitedEdition = new LimitedEdition("Limited Beer", "Limited Type", "Limited Aroma", 7.0, "Limited Color", "Limited Country", "Limited Ingredients", 20.0f, 50, "Original Beer", 2023);
        limitedEditionService.addLEBeer(limitedEdition);

        Optional<LimitedEdition> retrievedLimitedEdition = limitedEditionService.getLEBeer(1L);
        assertTrue(retrievedLimitedEdition.isPresent());

        LimitedEdition updatedLimitedEdition = retrievedLimitedEdition.get();
        updatedLimitedEdition.setPrice(25.0f);
        limitedEditionService.updateLEBeer(updatedLimitedEdition);

        Optional<LimitedEdition> updatedLimitedEditionOptional = limitedEditionService.getLEBeer(1L);
        assertTrue(updatedLimitedEditionOptional.isPresent());
        assertEquals(25.0f, updatedLimitedEditionOptional.get().getPrice(), 0.01);
    }

    @Test
    public void testDeleteLimitedEditionBeer() {
        LimitedEdition limitedEdition = new LimitedEdition("Limited Beer", "Limited Type", "Limited Aroma", 7.0, "Limited Color", "Limited Country", "Limited Ingredients", 20.0f, 50, "Original Beer", 2023);
        limitedEditionService.addLEBeer(limitedEdition);

        Optional<LimitedEdition> retrievedLimitedEdition = limitedEditionService.getLEBeer(1L);
        assertTrue(retrievedLimitedEdition.isPresent());

        limitedEditionService.deleteLEBeer(1L);

        Optional<LimitedEdition> deletedLimitedEdition = limitedEditionService.getLEBeer(1L);
        assertFalse(deletedLimitedEdition.isPresent());
    }
}
