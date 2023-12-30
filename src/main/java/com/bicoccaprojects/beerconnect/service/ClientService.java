package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void followedByClient(Client soggetto, Client followed) {
        // Carica le liste all'interno della stessa transazione
        soggetto = clientRepository.findById(soggetto.getIdClient()).orElse(null);
        followed = clientRepository.findById(followed.getIdClient()).orElse(null);

        if (soggetto != null && followed != null) {
            // Aggiungi la relazione di follow
            soggetto.getFollowedByClient().add(followed); // followed è un Client che viene seguito dall'client soggetto
            followed.getClientFollowers().add(soggetto); // ai follower di questo client viene aggiunto 'soggetto'

            clientRepository.save(soggetto);
            clientRepository.save(followed);
        } else {
            System.out.println("errore");
        }
    }

    @Transactional
    public void printFollowedByClient(Client client) {
        client = clientRepository.findById(client.getIdClient()).orElse(null);

        if (client != null) {
            Set<Client> followedByClient = client.getFollowedByClient();
            System.out.println("Client " + client.getIdClient() + " follows: ");

            for (Client followed : followedByClient) {
                System.out.println("  - " + followed.getNameClient());
            }
        } else {
            System.out.println("Client not found");
        }
    }

    @Transactional
    public void unfollowClient(Client client, Client followed) {
        client.getFollowedByClient().remove(followed);
        followed.getClientFollowers().remove(client);
        clientRepository.save(client);
        clientRepository.save(followed);
    }

    @Transactional
    public List<String> getFollowersPreferences(Long id){ return clientRepository.findPreferencesOfFollowersOfId(id);}

    @Transactional
    public List<String> getFollowedPreferences(Long id){ return clientRepository.findPreferencesOfFollowedById(id);}

}


