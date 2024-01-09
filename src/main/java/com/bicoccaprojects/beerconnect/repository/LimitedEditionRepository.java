package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * L'interfaccia LimitedEditionRepository estende JpaRepository per gestire le operazioni CRUD sull'entità LimitedEdition.
 * Introduce due query personalizzate utilizzando l'annotazione @Query.
 */


public interface LimitedEditionRepository extends JpaRepository<LimitedEdition, Long> {

    /**
     * Il metodo findLEByBeer consente di cercare edizioni limitate
     * basate sul nome della birra originale, restituendo una lista di stringhe contenenti tutte le
     * colonne della tabella beer corrispondenti al nome specificato.
     */
    @Query(value = "SELECT * "+
            "FROM beer "+
            "WHERE original_beer = :original", nativeQuery = true)
    List<String> findLEByBeer(@Param("original") String original);

    /**
     * Il metodo searchBeerByName consente di cercare birre limitate per nome,
     * restituendo una lista di stringhe contenenti tutte le colonne della tabella beer
     * corrispondenti al nome specificato.
     */
    @Query(value = "SELECT * FROM beer WHERE name_beer =:name", nativeQuery = true)
    List<String> searchBeerByName(@Param("name") String name);
}
