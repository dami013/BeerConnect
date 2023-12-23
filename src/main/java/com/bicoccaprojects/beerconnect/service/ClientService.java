package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public Iterable<Client> getClients() {
        return  clientRepository.findAll();
    }

    @Transactional
    public Optional<Client> getClient(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public void deleteClients() {
        clientRepository.deleteAll();
    }

    @Transactional
    public void addClient(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public void followClient(Client client, Client followed) {
        client.getFollowing().add(followed);
        followed.getFollowers().add(client);
        clientRepository.save(client);
        clientRepository.save(followed);
    }

    @Transactional
    public void unfollowClient(Client client, Client followed) {
        client.getFollowing().remove(followed);
        followed.getFollowers().remove(client);
        clientRepository.save(client);
        clientRepository.save(followed);
    }

}
