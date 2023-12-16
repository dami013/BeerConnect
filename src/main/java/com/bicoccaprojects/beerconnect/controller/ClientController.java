package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    public ResponseEntity<String> addClient(@RequestBody Client client) {
        clientService.addClient(client);
        return new ResponseEntity<>("Client added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/clients/{id_client}")
    public ResponseEntity<Client> getClientById(@PathVariable("id_client") long id) {
        Optional<Client> clientData = clientService.getClient(id);

        return clientData.map(client -> new ResponseEntity<>(client, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/clients")
    public Iterable<Client> getAllClients() {
        return clientService.getClients();
    }

    @PutMapping("/updateclient")
    public ResponseEntity<String> updateClientById(@RequestBody Client inClient) {
        Optional<Client> client = clientService.getClient(inClient.getIdClient());

        if (client.isPresent()) {
            Client updateClient = client.get();
            updateClient.setNameClient(inClient.getNameClient());
            clientService.updateClient(updateClient);
            return new ResponseEntity<>("Client updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteclient/{id_client}")
    public ResponseEntity<String> deleteClientById(@PathVariable(value = "id_client") Long id) {
        if (clientService.getClient(id).isPresent()) {
            clientService.deleteClient(id);
            return new ResponseEntity<>("Client deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
    }
}
