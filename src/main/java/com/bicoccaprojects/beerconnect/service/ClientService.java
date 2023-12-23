package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<Client> getClients() {
        return  clientRepository.findAll();
    }

    public Optional<Client> getClient(java.lang.Long id) {
        return clientRepository.findById(id);
    }
    public void deleteClient(java.lang.Long id) {
        clientRepository.deleteById(id);
    }
    public void deleteClients() {
        clientRepository.deleteAll();
    }
    public void addClient(Client client) {
        clientRepository.save(client);
    }
    public void updateClient(Client client) {
        clientRepository.save(client);
    }

    public void followClient(Client follower, Client followed) {
        follower.getFollowing().add(followed);
        followed.getFollowers().add(follower);
        clientRepository.save(follower);
        clientRepository.save(followed);
    }

    public void unfollowClient(Client follower, Client followed) {
        follower.getFollowing().remove(followed);
        followed.getFollowers().remove(follower);
        clientRepository.save(follower);
        clientRepository.save(followed);
    }

}
