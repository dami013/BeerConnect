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
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientTests {

    @Autowired
    private ClientService clientService;

    private static final Long VALID_CLIENT_ID = 1L;
    private static final Long NON_EXISTENT_CLIENT_ID = 999L;
    private static final Long CLIENT_ID_TO_DELETE = 12L;
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
        Client clientToBeRemoved = clientService.getClient(CLIENT_ID_TO_DELETE);

        // unfollow all Client followed by clientToBeRemoved
        for (Client client : clientToBeRemoved.getFollowedByClient()) {
            clientService.unfollowClient(clientToBeRemoved, client);
            assertFalse(clientToBeRemoved.getFollowedByClient().contains(client));
        }

        // unfollow clientToBeRemoved from all client followed who follows him
        for (Client client : clientToBeRemoved.getFollowedByClient()) {
            clientService.unfollowClient(client, clientToBeRemoved);
            assertFalse(clientToBeRemoved.getClientFollowers().contains(client));
        }

        clientToBeRemoved.getClientFollowers().clear();

        Iterable<Client> allClient = clientService.getAllClients();
        System.out.println(allClient);

        assertTrue(StreamSupport.stream(allClient.spliterator(), false).anyMatch(
                client -> client.getIdClient().equals(CLIENT_ID_TO_DELETE)
        ));

        assertDoesNotThrow(() -> clientService.deleteClient(CLIENT_ID_TO_DELETE));

        allClient = clientService.getAllClients();
        System.out.println(allClient);

        assertTrue(StreamSupport.stream(allClient.spliterator(), false).noneMatch(
                client -> client.getIdClient().equals(CLIENT_ID_TO_DELETE)
        ));
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
        assertDoesNotThrow(() -> clientService.addClient(testClient));

        // Then
        assertNotNull(testClient.getIdClient(), "Client ID should not be null after addition");

        // Retrieve the added client from the service
        Client addedClient = clientService.getClient(testClient.getIdClient());
        assertNotNull(addedClient, "Added client should not be null");
        assertEquals(testClient.getNameClient(), addedClient.getNameClient(), "Name should match");
        assertEquals(testClient.getEmail(), addedClient.getEmail(), "Email should match");
    }

    @Test
    void updateClient() {
        // Given
        Client existingClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertNotNull(existingClient, "Existing client should not be null");

        // When
        existingClient.setNameClient("Updated Name");
        existingClient.setEmail("updated.email@example.com");

        assertDoesNotThrow(() -> clientService.updateClient(existingClient));

        // Then
        Client updatedClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertNotNull(updatedClient, "Updated client should not be null");
        assertEquals(existingClient.getNameClient(), updatedClient.getNameClient(), "Name should be updated");
        assertEquals(existingClient.getEmail(), updatedClient.getEmail(), "Email should be updated");
        // Add other assertions for updated properties
    }
    @Test
    void followedByClient() {
        Client subject = clientService.getClient(1L); // Client who follow
        Client followed = clientService.getClient(2L); // Client who get followed

        Set<Client> clientsFollowedBySubject = subject.getFollowedByClient(); // List of Client followed by Client 1
        // System.out.println("Client 1 follows: "+clientsFollowedBySubject);

        Set<Client> followerOfFollowed = followed.getClientFollowers(); // List of Client who follow Client 2
        // System.out.println("Client 2 is followed by: "+followerOfFollowed);

        assertFalse(clientsFollowedBySubject.contains(followed));
        assertFalse(followerOfFollowed.contains(subject));

        // follow operation
        assertDoesNotThrow(() -> clientService.followedByClient(subject, followed));

        clientsFollowedBySubject = subject.getFollowedByClient();
        // System.out.println("Client 1 now follows: "+clientsFollowedBySubject);

        followerOfFollowed = followed.getClientFollowers();
        // System.out.println("Client 2 now is followed by: "+followerOfFollowed);

        assertTrue(clientsFollowedBySubject.contains(followed));
        assertTrue(followerOfFollowed.contains(subject));
    }



    @Test
    void unfollowClient() {
        Client subject = clientService.getClient(2L); // Client who follow
        Client followed = clientService.getClient(1L); // Client who get followed
        Set<Client> listFollowedSubject = subject.getFollowedByClient(); // followed by Client 1
        // System.out.println("Client followed by Client 2: "+listFollowedSubject);

        Set<Client> listFollowerOtherClient = followed.getClientFollowers(); // follower Client 2, contains Client 1
        // System.out.println("Client 1 is followed by: "+listFollowerOtherClient);

        assertTrue(listFollowerOtherClient.contains(subject)); // list of follower of Client 2 must contain Client 1
        assertTrue(listFollowedSubject.contains(followed)); // list of client followed by Client 1 must contain Client 2

        assertDoesNotThrow(() -> clientService.unfollowClient(subject, followed)); // unfollow operation

        assertFalse(listFollowerOtherClient.contains(subject)); // now list of follower of Client 2 must not contain Client 1
        assertFalse(listFollowedSubject.contains(followed)); // now list of followed by Client 1 must not contain Client 2

        // Set<Client> de = subject.getFollowedByClient();
        // System.out.println("Client followed by Client 2: "+de);
        // System.out.println("Client 1 is followed by: "+listFollowerOtherClient);
    }

    @Test
    void getFollowersPreferences() {
        Long clientId = CLIENT_ID_TO_FOLLOW;

        List<String> preferencesFollower = clientService.getFollowersPreferences(clientId);
        // System.out.println(preferencesFollower);

        Client clientFollower = clientService.getClient(CLIENT_ID_FOLLOWING);
        String preferences = clientFollower.getPreferences();
        // System.out.println("Preferenze del follower di "+clientId+" è: "+preferences);
        assertFalse(preferencesFollower.isEmpty());
        assertEquals(preferences, preferencesFollower.get(0));
    }

    @Test
    void getFollowedPreferences() {
        Long clientId = CLIENT_ID_TO_FOLLOW;

        List<String> preferencesFollowed = clientService.getFollowedPreferences(clientId);
        //System.out.println(preferencesFollowed);

        Client clientFollowed = clientService.getClient(CLIENT_ID_FOLLOWED);
        String preferences = clientFollowed.getPreferences();
        //System.out.println("Preferenze di quello seguito da "+clientId+" è "+preferences);
        assertFalse(preferencesFollowed.isEmpty());
        assertEquals(preferences, preferencesFollowed.get(0));
    }
}
