package com.bicoccaprojects.beerconnect.repository;

import com.bicoccaprojects.beerconnect.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.lang.Long;
import java.util.List;
import java.util.Optional;

/**
 * The ClientRepository interface extends JpaRepository to handle CRUD operations on the Client entity.
 * It implements custom methods such as findByIdWithFollowing and findByIdWithFollowers to retrieve a
 * client inclusive of follower and following relationships. Additionally, it provides native queries to
 * obtain preferences of followers and those followed by a specific client identified by their ID.
 */

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Override
    Optional<Client> findById(Long id);

    /**
     * The findPreferencesOfFollowersOfId method returns a list of preferences of clients who follow
     * the client with the specified ID.
     */

    @Query(value = "SELECT c.preferences " +
            "FROM client_to_client cc " +
            "JOIN client c ON c.id_client = cc.client_id "+
            "WHERE cc.id_client_followed = :id", nativeQuery = true)
    List<String> findPreferencesOfFollowersOfId(@Param("id") Long id);

    /**
     * The findPreferencesOfFollowedById method returns a list of preferences of clients followed by the client
     * with the specified ID.
     */

    @Query(value = "SELECT c.preferences " +
            "FROM client_to_client cc " +
            "JOIN client c ON c.id_client = cc.id_client_followed "+
            "WHERE cc.client_id = :id", nativeQuery = true)
    List<String> findPreferencesOfFollowedById(@Param("id") Long id);

}
