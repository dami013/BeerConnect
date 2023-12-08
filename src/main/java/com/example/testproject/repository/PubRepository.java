package com.example.testproject.repository;

import com.example.testproject.entity.Pub;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PubRepository extends JpaRepository<Pub, Long> { // Long perchè ID di Pub è Long

}
