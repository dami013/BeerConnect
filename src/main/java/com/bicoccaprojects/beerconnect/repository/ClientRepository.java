package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, java.lang.Long> {
    @Override
    Optional<Client> findById(java.lang.Long id);
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.following WHERE c.idClient = :id")
    Optional<Client> findByIdWithFollowing(@Param("id") Long id);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.followers WHERE c.idClient = :id")
    Optional<Client> findByIdWithFollowers(@Param("id") Long id);

}
