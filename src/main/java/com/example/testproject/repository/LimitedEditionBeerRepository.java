package com.example.testproject.repository;

import com.example.testproject.entity.LimitedEditionBeer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitedEditionBeerRepository extends JpaRepository<LimitedEditionBeer, Long> {
}
