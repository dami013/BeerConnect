package com.bicoccaprojects.beerconnect;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ClientTest {

    @Autowired
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        clientService.deleteClients();
    }

    @Test
    public void testAddAndGetClient() {
        Client client = new Client("Test Client", "test@example.com", 19900101, "Test Address", "Test Preferences");
        clientService.addClient(client);

        Optional<Client> retrievedClient = clientService.getClient(1L);
        assertTrue(retrievedClient.isPresent());
        assertEquals("Test Client", retrievedClient.get().getNameClient());
    }

    @Test
    public void testUpdateClient() {
        Client client = new Client("Test Client", "test@example.com", 19900101, "Test Address", "Test Preferences");
        clientService.addClient(client);

        Optional<Client> retrievedClient = clientService.getClient(1L);
        assertTrue(retrievedClient.isPresent());

        Client updatedClient = retrievedClient.get();
        updatedClient.setPreferences("Updated Preferences");
        clientService.updateClient(updatedClient);

        Optional<Client> updatedClientOptional = clientService.getClient(1L);
        assertTrue(updatedClientOptional.isPresent());
        assertEquals("Updated Preferences", updatedClientOptional.get().getPreferences());
    }

    @Test
    public void testDeleteClient() {
        Client client = new Client("Test Client", "test@example.com", 19900101, "Test Address", "Test Preferences");
        clientService.addClient(client);

        Optional<Client> retrievedClient = clientService.getClient(1L);
        assertTrue(retrievedClient.isPresent());

        clientService.deleteClient(1L);

        Optional<Client> deletedClient = clientService.getClient(1L);
        assertFalse(deletedClient.isPresent());
    }

    @Test
    public void testGetClients() {
        // Add some test clients
        clientService.addClient(new Client("Client1", "client1@example.com", 19900101, "Address1", "Preferences1"));
        clientService.addClient(new Client("Client2", "client2@example.com", 19851203, "Address2", "Preferences2"));

        Iterable<Client> clients = clientService.getClients();
        assertNotNull(clients);

        // Convert Iterable to List for easier assertions
        List<Client> clientList = (List<Client>) clients;

        assertEquals(2, clientList.size());
        assertEquals("Client1", clientList.get(0).getNameClient());
        assertEquals("Client2", clientList.get(1).getNameClient());
    }

    @Test
    public void testDeleteClients() {
        // Add some test clients
        clientService.addClient(new Client("Client1", "client1@example.com", 19900101, "Address1", "Preferences1"));
        clientService.addClient(new Client("Client2", "client2@example.com", 19851203, "Address2", "Preferences2"));

        clientService.deleteClients();

        Iterable<Client> clients = clientService.getClients();
        assertFalse(clients.iterator().hasNext()); // No clients should be present after deletion
    }
}
