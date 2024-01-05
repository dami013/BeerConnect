package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.exception.client.ClientAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.client.ClientNotFoundException;
import com.bicoccaprojects.beerconnect.service.ClientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientTests {

    @Autowired
    private ClientService clientService;

    private static final Long VALID_CLIENT_ID = 1L;
    private static final Long NON_EXISTENT_CLIENT_ID = 999L;
    private static final Long CLIENT_ID_TO_DELETE = 2L;
    private static final Long CLIENT_ID_TO_UPDATE = 3L;

    private static final Long CLIENT_ID_TO_FOLLOW = 10L;

    private static final Long CLIENT_ID_FOLLOWED = 12L;

    private static final Long CLIENT_ID_FOLLOWING = 11L;

    @BeforeEach
    @Sql("/data.sql")
    void setUp() {
        System.out.println("Dati client aggiunti");
    }

    @AfterEach
    void tearDown() {
        clientService.deleteClients();
        System.out.println("Dati client eliminati");
    }

    @Test
    void getAllClients() {
        Iterable<Client> clients = clientService.getAllClients();
        assertNotNull(clients);
    }

    @Test
    void getClientById() {
        Client client = clientService.getClient(VALID_CLIENT_ID);
        assertNotNull(client);
        assertEquals(VALID_CLIENT_ID, client.getIdClient());
    }

    @Test
    void getClientByIdNotFound() {
        assertThrows(ClientNotFoundException.class, () -> clientService.getClient(NON_EXISTENT_CLIENT_ID));
    }

    @Test
    void deleteClientById() {
        System.out.println(clientService.getClient(CLIENT_ID_TO_DELETE));
        assertTrue(clientService.deleteClient(CLIENT_ID_TO_DELETE));
    }

    @Test
    void addDuplicateClient() {
        Client existingClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertThrows(ClientAlreadyExistsException.class, () -> clientService.addClient(existingClient));
    }

    @Test
    void addClient() {
        // Given
        Client testClient = new Client(17L, "Claudio Doe", "claudio.doe@example.com", LocalDate.of(2001, 1, 1), "123 Problem St", "Beer Enthusiast");

        // When
        clientService.addClient(testClient);

        // Then
        assertNotNull(testClient.getIdClient(), "Client ID should not be null after addition");

        // Retrieve the added client from the service
        Client addedClient = clientService.getClient(testClient.getIdClient());
        assertNotNull(addedClient, "Added client should not be null");
        assertEquals("Claudio Doe", addedClient.getNameClient(), "Name should match");
        assertEquals("claudio.doe@example.com", addedClient.getEmail(), "Email should match");
        // Add more assertions for other properties
    }

    @Test
    void updateClient() {
        // Given
        Client existingClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertNotNull(existingClient, "Existing client should not be null");

        // When
        existingClient.setNameClient("Updated Name");
        existingClient.setEmail("updated.email@example.com");
        // Add other necessary modifications

        clientService.updateClient(existingClient);

        // Then
        Client updatedClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertNotNull(updatedClient, "Updated client should not be null");
        assertEquals("Updated Name", updatedClient.getNameClient(), "Name should be updated");
        assertEquals("updated.email@example.com", updatedClient.getEmail(), "Email should be updated");
        // Add other assertions for updated properties
    }
    @Test
    void followedByClient() {
        Client subject = clientService.getClient(1L);
        Client followed = clientService.getClient(2L);

        assertDoesNotThrow(() -> clientService.followedByClient(subject, followed));

        Set<Client> followers = subject.getFollowedByClient();
        System.out.println(followers);
        assertTrue(followers.contains(followed));

        System.out.println("----------------------");

        Set<Client> followedClients = followed.getClientFollowers();
        assertTrue(followedClients.contains(subject));
        System.out.println(followedClients);
    }



    @Test
    void unfollowClient() {
        Client subject = clientService.getClient(1L);
        Client followed = clientService.getClient(2L);
        Set<Client> followers = subject.getFollowedByClient();
        System.out.println(followers);
        assertDoesNotThrow(() -> clientService.unfollowClient(subject, followed));

        Set<Client> de = subject.getFollowedByClient();
        System.out.println(de);
        assertFalse(followers.contains(followed));

        System.out.println("----------------------");

        Set<Client> followedClients = followed.getClientFollowers();
        assertFalse(followedClients.contains(subject));
        System.out.println(followedClients);
    }

    @Test
    void getFollowersPreferences() {
        Long clientId = CLIENT_ID_TO_FOLLOW;

        assertDoesNotThrow(() -> {
            List<String> preferencesFollower = clientService.getFollowersPreferences(clientId);
            System.out.println(preferencesFollower);

            Client clientFollower = clientService.getClient(CLIENT_ID_FOLLOWING);
            String preferences = clientFollower.getPreferences();
            System.out.println("Preferenze del follower di "+clientId+" è: "+preferences);
            assertEquals(preferences, preferencesFollower.get(0));

            assertNotNull(preferencesFollower);
            assertFalse(preferencesFollower.isEmpty());
        });
    }

    @Test
    void getFollowedPreferences() {
        Long clientId = CLIENT_ID_TO_FOLLOW;

        assertDoesNotThrow(() -> {
            List<String> preferencesFollowed = clientService.getFollowedPreferences(clientId);
            System.out.println(preferencesFollowed);

            Client clientFollowed = clientService.getClient(CLIENT_ID_FOLLOWED);
            String preferences = clientFollowed.getPreferences();
            System.out.println("Preferenze di quello seguito da "+clientId+" è "+preferences);
            assertEquals(preferences, preferencesFollowed.get(0));

            assertNotNull(preferencesFollowed);
            assertFalse(preferencesFollowed.isEmpty());
        });
    }
}
