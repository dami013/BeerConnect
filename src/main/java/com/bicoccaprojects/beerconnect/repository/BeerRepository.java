package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {
}
