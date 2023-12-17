package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitedEditionRepository extends JpaRepository<LimitedEdition, Long> {
}
