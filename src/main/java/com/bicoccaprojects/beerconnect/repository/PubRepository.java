package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * L'interfaccia PubRepository estende JpaRepository per interagire con il database e fornire operazioni di
 * accesso ai dati dell'entità Pub.
 */
public interface PubRepository extends JpaRepository<Pub, Long> {

    /**
     * La query findPubByCountryAndBeerType permette di cercare i pub
     * in base al paese, al tipo di birra e alla valutazione delle recensioni delle birre servite in quei pub.
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
