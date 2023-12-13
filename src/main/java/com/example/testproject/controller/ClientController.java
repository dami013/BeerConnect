package com.example.testproject.controller;

import com.example.testproject.entity.Client;
import com.example.testproject.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api") // definisce un prefisso per tutti i metodi all'interno del controller
public class ClientController {

    @Autowired
    ClientRepository clientRepository;


    @PostMapping("/client")
    public Client addClient(@RequestBody Client c) {
        return clientRepository.save(c);
    }
    @GetMapping("/beers/{id_client}")
    public ResponseEntity<Client> getBeerById(@PathVariable("id_client") long id) {
        Optional<Client> clientData = clientRepository.findById(id);

        if (clientData.isPresent()) {
            return new ResponseEntity<>(clientData.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Retrive all the beers available
    @GetMapping("/client")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    // Update a beer with an id sent with body
    @PutMapping("/update")
    public Client updateEmployeeById(@RequestBody Client inClient){
        Optional<Client> client = clientRepository.findById(inClient.getId_client());
        Client updateClient = client.get();
        updateClient.setName_client(client.get().getName_client());
        return clientRepository.save(updateClient);
    }

    // Delete a beer with given id
    @DeleteMapping("/delete/{id_client}")
    public void deleteEmployeeById(@PathVariable(value = "id_client") Long id){
        clientRepository.deleteById(id);
    }


}
