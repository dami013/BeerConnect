package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The LimitedEditionRepository interface extends JpaRepository to handle CRUD operations on the LimitedEdition entity.
 * It introduces two custom queries using the @Query annotation.
 */



public interface LimitedEditionRepository extends JpaRepository<LimitedEdition, Long> {

    /**
     * The findLEByBeer method allows searching for limited editions based on the name of the original beer,
     * returning a list of strings containing all columns of the beer table corresponding to the specified name.
     */

    @Query(value = "SELECT * "+
            "FROM beer "+
            "WHERE original_beer = :original", nativeQuery = true)
    List<String> findLEByBeer(@Param("original") String original);

    /**
     * The searchBeerByName method allows searching for limited edition beers by name,
     * returning a list of strings containing all columns of the beer table
     * corresponding to the specified name.
     */

    @Query(value = "SELECT * FROM beer WHERE name_beer =:name", nativeQuery = true)
    List<String> searchBeerByName(@Param("name") String name);
}
