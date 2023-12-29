package com.bicoccaprojects.beerconnect.repository.relational_repository;

import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientReviewRepository extends JpaRepository<ClientReview, Long> {
    @Query(value = "SELECT c.name_client, cr.review, b.price " +
            "FROM client_review cr " +
            "JOIN beer b ON b.id_beer = cr.id_beer " +
            "JOIN client c ON c.id_client = cr.id_client " +
            "WHERE b.country = :country AND cr.rating >= :rating", nativeQuery = true)
    List<String> findClientByReview(@Param("country") String country, @Param("rating") int rating);
}
