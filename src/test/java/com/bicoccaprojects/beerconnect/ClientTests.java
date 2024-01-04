package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Client;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientTests {

    @Autowired
    private ClientService clientService;
    @BeforeEach
    @Sql("/data.sql")
    void setUp() {
    }
    @AfterEach
    void tearDown() {
        clientService.deleteClients();
    }
    @Test
    public void testCreateClient() {
        // Create
        Client newClient = new Client(13L, "John Mcc", "john.mcc@example.com", LocalDate.of(2000, 1, 1),
                "123 Second St", "Beer Lover");
        clientService.addClient(newClient);
         Long clientId = newClient.getIdClient();
         assertNotNull(clientId, "Client ID should not be null after creation.");

        // Read
        Client retrievedClient = clientService.getClient(clientId).orElse(null);
        assertNotNull(retrievedClient, "Retrieved client should not be null.");
        assertEquals("John Mcc", retrievedClient.getNameClient(), "Client name should match.");
        System.out.println(newClient);
    }

    @Test
    public void testReadClient() {
        Long existingClientId = 1L;
        Client retrievedClient = clientService.getClient(existingClientId).orElse(null);
        assertNotNull(retrievedClient, "Retrieved client should not be null.");
        assertEquals("Damian Ficher", retrievedClient.getNameClient(), "Client name should match.");
    }


    @Test
    @Transactional
    public void testUpdateClient() {
        // Update
        Client retrievedClient = clientService.getClient(1L).orElse(null);
        assertNotNull(retrievedClient, "Retrieved client should not be null.");
        retrievedClient.setNameClient("Damiano Ficara");
        clientService.updateClient(retrievedClient);
        Client updatedClient = clientService.getClient(1L).orElse(null);
        assertNotNull(updatedClient, "Updated client should not be null.");
        assertEquals("Damiano Ficara", updatedClient.getNameClient(), "Client name should be updated.");
    }

    @Test
    @Transactional
    public void testReadAllClients() {
        // Assuming you have exactly 12 clients in the database (as per your sample data)

        // Read all clients
        Iterable<Client> allClients = clientService.getAllClients();

        // Perform assertions
        assertNotNull(allClients, "Retrieved client list should not be null.");

        // Verify the exact number of clients in the database
        List<Client> clientList = (List<Client>) allClients;
        assertEquals(12, clientList.size(), "The number of clients should match the expected count.");

        // Iterate through clients and perform individual assertions
        for (Client client : clientList) {
            assertNotNull(client.getIdClient(), "Client ID should not be null.");
            assertNotNull(client.getNameClient(), "Client name should not be null.");
            // Add more assertions based on your data model
        }
    }
}
