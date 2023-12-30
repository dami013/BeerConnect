package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, java.lang.Long> {
    @Override
    Optional<Client> findById(java.lang.Long id);
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.followedByClient WHERE c.idClient = :id")
    Optional<Client> findByIdWithFollowing(@Param("id") Long id);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.clientFollowers WHERE c.idClient = :id")
    Optional<Client> findByIdWithFollowers(@Param("id") Long id);

    // return a List of client who follow client with ID = id
    @Query(value = "SELECT c.id_client, c.name_client, c.preferences " +
            "FROM client_to_client cc " +
            "JOIN client c ON c.id_client = cc.id_client_followed "+
            "WHERE cc.id_client_followed = :id", nativeQuery = true)
    List<String> findPreferencesOfFollowersOfId(@Param("id") Long id);

    // return a List of Client followed by the client with ID = id
    @Query(value = "SELECT c.id_client, c.name_client, c.preferences " +
            "FROM client_to_client cc " +
            "JOIN client c ON c.id_client = cc.client_id "+
            "WHERE cc.client_id = :id", nativeQuery = true)
    List<String> findPreferencesOfFollowedById(@Param("id") Long id);

}
