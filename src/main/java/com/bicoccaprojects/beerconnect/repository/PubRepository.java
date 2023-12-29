package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Pub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PubRepository extends JpaRepository<Pub, Long> {
    @Query(value = "SELECT b.name_beer, b.quantity_in_stock, b.production_year, b.price "+
            "FROM pub p "+
            "JOIN beer b ON b.id_pub = p.id_pub "+
            "WHERE b.id_pub = :idPub ", nativeQuery = true)
    List<String> findBeerByPub(@Param("idPub") Long idPub);
}
