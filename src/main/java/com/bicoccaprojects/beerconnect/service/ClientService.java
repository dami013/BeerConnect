package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

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
    public void followedByClient(Client client, Client followed) {
        // Carica le liste all'interno della stessa transazione
        client = clientRepository.findById(client.getIdClient()).orElse(null);
        followed = clientRepository.findById(followed.getIdClient()).orElse(null);

        if (client != null && followed != null) {
            // Aggiungi la relazione di follow
            client.getFollowing().add(followed);
            followed.getFollowers().add(client);

            // Salva le modifiche nel repository
            clientRepository.save(client);
            clientRepository.save(followed);
        } else {
            System.out.println("errore");
        }
    }

    @Transactional
    public void unfollowClient(Client client, Client followed) {
        client.getFollowing().remove(followed);
        followed.getFollowers().remove(client);
        clientRepository.save(client);
        clientRepository.save(followed);
    }
    @Transactional
    public void printClientFollowed(Client client) {
        // Carica il client all'interno della stessa transazione
        client = clientRepository.findById(client.getIdClient()).orElse(null);

        if (client != null) {
            Set<Client> followers = client.getFollowers();
            System.out.println("Followers for Client " + client.getIdClient() + ":");

            for (Client follower : followers) {
                System.out.println("  - " + follower.getNameClient());  // Assuming there is a 'getName()' method in the Client class
            }
        } else {
            System.out.println("Client not found");
        }
    }




}


