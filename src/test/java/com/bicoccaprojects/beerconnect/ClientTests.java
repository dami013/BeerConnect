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
    void setUp() {}

    @AfterEach
    void tearDown() {
        clientService.deleteClients();
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

        assertTrue(StreamSupport.stream(allClient.spliterator(), false).anyMatch(
                client -> client.getIdClient().equals(CLIENT_ID_TO_DELETE)
        ));

        assertDoesNotThrow(() -> clientService.deleteClient(CLIENT_ID_TO_DELETE));

        allClient = clientService.getAllClients();

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
        Client testClient = new Client(17L, "Test Client", "test.cliemt@example.com", LocalDate.of(2001, 1, 1), "123 Beer St", "Beer Enthusiast");
        assertDoesNotThrow(() -> clientService.addClient(testClient));

        assertNotNull(testClient.getIdClient(), "Client ID should not be null after addition");

        Client addedClient = clientService.getClient(testClient.getIdClient());
        assertNotNull(addedClient, "Added client should not be null");
        assertEquals(testClient.getNameClient(), addedClient.getNameClient(), "Name should match");
        assertEquals(testClient.getEmail(), addedClient.getEmail(), "Email should match");
    }

    @Test
    void updateClient() {
        Client existingClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertNotNull(existingClient, "Existing client should not be null");

        existingClient.setNameClient("Updated Name");
        existingClient.setEmail("updated.email@example.com");

        assertDoesNotThrow(() -> clientService.updateClient(existingClient));

        Client updatedClient = clientService.getClient(CLIENT_ID_TO_UPDATE);
        assertNotNull(updatedClient, "Updated client should not be null");
        assertEquals(existingClient.getNameClient(), updatedClient.getNameClient(), "Name should be updated");
        assertEquals(existingClient.getEmail(), updatedClient.getEmail(), "Email should be updated");
    }
    @Test
    void followedByClient() {
        Client subject = clientService.getClient(1L); // Client who follow
        Client followed = clientService.getClient(2L); // Client who get followed

        Set<Client> clientsFollowedBySubject = subject.getFollowedByClient(); // List of Client followed by Client 1

        Set<Client> followerOfFollowed = followed.getClientFollowers(); // List of Client who follow Client 2

        assertFalse(clientsFollowedBySubject.contains(followed));
        assertFalse(followerOfFollowed.contains(subject));

        assertDoesNotThrow(() -> clientService.followedByClient(subject, followed));

        clientsFollowedBySubject = subject.getFollowedByClient();

        followerOfFollowed = followed.getClientFollowers();

        assertTrue(clientsFollowedBySubject.contains(followed));
        assertTrue(followerOfFollowed.contains(subject));
    }



    @Test
    void unfollowClient() {
        Client subject = clientService.getClient(2L); // Client who follow
        Client followed = clientService.getClient(1L); // Client who get followed
        Set<Client> listFollowedSubject = subject.getFollowedByClient(); // followed by Client 1

        Set<Client> listFollowerOtherClient = followed.getClientFollowers(); // follower Client 2, contains Client 1

        assertTrue(listFollowerOtherClient.contains(subject)); // list of follower of Client 2 must contain Client 1
        assertTrue(listFollowedSubject.contains(followed)); // list of client followed by Client 1 must contain Client 2

        assertDoesNotThrow(() -> clientService.unfollowClient(subject, followed)); // unfollow operation

        assertFalse(listFollowerOtherClient.contains(subject)); // now list of follower of Client 2 must not contain Client 1
        assertFalse(listFollowedSubject.contains(followed)); // now list of followed by Client 1 must not contain Client 2
    }

    @Test
    void getFollowersPreferences() {

        List<String> preferencesFollower = clientService.getFollowersPreferences(CLIENT_ID_TO_FOLLOW);

        Client clientFollower = clientService.getClient(CLIENT_ID_FOLLOWING);
        String preferences = clientFollower.getPreferences();
        assertFalse(preferencesFollower.isEmpty());
        assertEquals(preferences, preferencesFollower.get(0));
    }

    @Test
    void getFollowedPreferences() {
        List<String> preferencesFollowed = clientService.getFollowedPreferences(CLIENT_ID_TO_FOLLOW);
        Client clientFollowed = clientService.getClient(CLIENT_ID_FOLLOWED);
        String preferences = clientFollowed.getPreferences();
        assertFalse(preferencesFollowed.isEmpty());
        assertEquals(preferences, preferencesFollowed.get(0));
    }
}
