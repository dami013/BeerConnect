package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRespository;

    public Iterable<Client> getClients() {
        return  clientRespository.findAll();
    }

    public Optional<Client> getClient(java.lang.Long id) {
        return clientRespository.findById(id);
    }
    public void deleteClient(java.lang.Long id) {
        clientRespository.deleteById(id);
    }
    public void deleteClients() {
        clientRespository.deleteAll();
    }
    public void addClient(Client client) {
        clientRespository.save(client);
    }
    public void updateClient(Client client) {
        clientRespository.save(client);
    }
}
