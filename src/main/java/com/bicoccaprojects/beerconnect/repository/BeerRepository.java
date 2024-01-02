package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    @Query(value = "SELECT id_beer, name_beer, price " +
            "FROM beer WHERE type =:type", nativeQuery = true)
    List<String> searchBeerByType(@Param("type") String type);

    @Query(value = "SELECT * FROM beer WHERE name_beer =:name", nativeQuery = true)
    List<String> searchBeerByName(@Param("name") String name);
}
