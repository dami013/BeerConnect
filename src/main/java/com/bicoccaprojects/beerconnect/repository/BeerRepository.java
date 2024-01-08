package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * L'interfaccia BeerRepository estende JpaRepository per gestire le operazioni CRUD sull'entità Beer.
 * Introduce due query personalizzate utilizzando l'annotazione @Query. Il metodo searchBeerByType consente
 * di cercare birre per tipo, restituendo una lista di stringhe contenenti id, nome e prezzo delle birre
 * corrispondenti al tipo specificato. Il metodo searchBeerByName consente di cercare birre per nome,
 * restituendo una lista di stringhe contenenti tutte le colonne della tabella beer corrispondenti al nome
 * specificato.
 */

public interface BeerRepository extends JpaRepository<Beer, Long> {
    @Query(value = "SELECT id_beer, name_beer, price " +
            "FROM beer WHERE type =:type", nativeQuery = true)
    List<String> searchBeerByType(@Param("type") String type);

    @Query(value = "SELECT * FROM beer WHERE name_beer =:name", nativeQuery = true)
    List<String> searchBeerByName(@Param("name") String name);
}
