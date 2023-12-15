package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Pub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubRepository extends JpaRepository<Pub, Long> { // Long perchè ID di Pub è Long

}
