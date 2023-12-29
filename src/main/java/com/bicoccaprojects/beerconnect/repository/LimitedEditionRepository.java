package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LimitedEditionRepository extends JpaRepository<LimitedEdition, Long> {
    @Query(value = "SELECT * "+
            "FROM beer "+
            "WHERE original_beer = :original", nativeQuery = true)
    List<String> findLEByBeer(@Param("original") String original);
}
