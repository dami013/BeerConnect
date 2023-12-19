package com.bicoccaprojects.beerconnect.repository.relational_repository;

import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientReviewRepository extends JpaRepository<ClientReview, Long> {
}
