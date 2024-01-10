package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The PubRepository interface extends JpaRepository to interact with the database and provide data access operations
 * for the Pub entity.
 */

public interface PubRepository extends JpaRepository<Pub, Long> {

    /**
     * The findPubByCountryAndBeerType query allows searching for pubs
     * based on country, beer type, and the rating of reviews for the beers served in those pubs.
     */

    @Query(value = "SELECT p.name_pub "+
            "FROM pub p "+
            "JOIN beer b ON p.id_pub = b.id_pub "+
            "JOIN client_review cr ON cr.id_beer = b.id_beer "+
            "WHERE p.country = :country AND b.type = :type AND cr.rating >= :rating", nativeQuery = true)
    List<String> findPubByCountryAndBeerType(@Param("country") String country,
                                             @Param("type") String type,
                                             @Param("rating") Integer rating);
}
