package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.lang.Long;
import java.util.List;
import java.util.Optional;

/**
 * L'interfaccia ClientRepository estende JpaRepository per gestire le operazioni CRUD sull'entità Client.
 * Implementa metodi personalizzati come findByIdWithFollowing e findByIdWithFollowers per recuperare un
 * cliente inclusivo delle relazioni di seguaci e seguaci. Inoltre, fornisce query native per ottenere le
 * preferenze dei follower e dei seguiti di un cliente specificato dal suo ID. Il metodo findPreferencesOfFollowersOfId
 * restituisce una lista di preferenze dei clienti che seguono il cliente con ID specificato, mentre
 * findPreferencesOfFollowedById restituisce una lista di preferenze dei clienti seguiti dal cliente con ID
 * specificato.
 */

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    Optional<Client> findById(Long id);
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.followedByClient WHERE c.idClient = :id")
    Optional<Client> findByIdWithFollowing(@Param("id") Long id);

    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.clientFollowers WHERE c.idClient = :id")
    Optional<Client> findByIdWithFollowers(@Param("id") Long id);

    // return a List of preferences of client who follow client with ID = id
    @Query(value = "SELECT c.preferences " +
            "FROM client_to_client cc " +
            "JOIN client c ON c.id_client = cc.client_id "+
            "WHERE cc.id_client_followed = :id", nativeQuery = true)
    List<String> findPreferencesOfFollowersOfId(@Param("id") Long id);

    // return a List of preferences of Client followed by the client with ID = id
    @Query(value = "SELECT c.preferences " +
            "FROM client_to_client cc " +
            "JOIN client c ON c.id_client = cc.id_client_followed "+
            "WHERE cc.client_id = :id", nativeQuery = true)
    List<String> findPreferencesOfFollowedById(@Param("id") Long id);

}
