package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The BeerRepository interface extends JpaRepository to handle CRUD operations on the Beer entity.
 * It introduces two custom queries using the @Query annotation.
 */


public interface BeerRepository extends JpaRepository<Beer, Long> {
    /**
     * The searchBeerByType method allows searching for beers by type,
     * returning a list of strings containing the id, name, and price of beers
     * corresponding to the specified type.
     */

    @Query(value = "SELECT id_beer, name_beer, price " +
            "FROM beer WHERE type =:type", nativeQuery = true)
    List<String> searchBeerByType(@Param("type") String type);

    /**
     * The searchBeerByName method allows searching for beers by name,
     * returning a list of strings containing all columns of the beer table
     * corresponding to the specified name.
     */

    @Query(value = "SELECT * FROM beer WHERE name_beer =:name", nativeQuery = true)
    List<String> searchBeerByName(@Param("name") String name);
}
