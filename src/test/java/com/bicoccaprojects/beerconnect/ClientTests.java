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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientTests {

    @Autowired
    private ClientService clientService;

    private static final Long VALID_CLIENT_ID = 1L;
    private static final Long NON_EXISTENT_CLIENT_ID = 999L;
    private static final Long CLIENT_ID_TO_DELETE = 6L;
    private static final Long CLIENT_ID_TO_UPDATE = 3L;

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
}
