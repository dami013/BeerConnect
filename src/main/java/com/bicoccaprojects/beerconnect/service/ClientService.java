package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Client;
import com.bicoccaprojects.beerconnect.exception.client.ClientAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.client.ClientNotFoundException;
import com.bicoccaprojects.beerconnect.exception.client.FollowNotFound;
import com.bicoccaprojects.beerconnect.exception.client.NoClientsFoundException;
import com.bicoccaprojects.beerconnect.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Iterable<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()){
            return optionalClient.get();
        } else {
            throw new ClientNotFoundException(id);
        }
    }
    @Transactional
    public void deleteClient(Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isEmpty()) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }

    @Transactional
    public void deleteClients() {
        Iterable<Client> clients = clientRepository.findAll();
        if(!clients.iterator().hasNext()){
            throw new NoClientsFoundException();
        }
        clientRepository.deleteAll();
    }

    @Transactional
    public void addClient(Client client) {
        Long id = client.getIdClient();
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isEmpty()){
            clientRepository.save(client);
        } else {
            throw new ClientAlreadyExistsException("Client with ID " + id + " already exists in the database.");
        }
    }


    @Transactional
    public void updateClient(Client client) {
        Long id = client.getIdClient();
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isPresent()) {
            clientRepository.save(client);
        }else {
            throw new ClientNotFoundException(id);
        }
    }

    @Transactional
    public void followedByClient(Client soggetto, Client followed) {
        soggetto = clientRepository.findById(soggetto.getIdClient()).orElse(null);
        followed = clientRepository.findById(followed.getIdClient()).orElse(null);

        if (soggetto != null && followed != null) {
            soggetto.getFollowedByClient().add(followed); // followed è un Client che viene seguito dall'client soggetto
            followed.getClientFollowers().add(soggetto); // ai follower di questo client viene aggiunto 'soggetto'

            clientRepository.save(soggetto);
            clientRepository.save(followed);
        } else {
            throw new ClientNotFoundException(soggetto.getIdClient());
        }
    }

    @Transactional
    public void unfollowClient(Client soggetto, Client followed) {
        soggetto = clientRepository.findById(soggetto.getIdClient()).orElse(null);
        followed = clientRepository.findById(followed.getIdClient()).orElse(null);

        if (soggetto != null && followed != null) {
            soggetto.getFollowedByClient().remove(followed); // followed is a Client who get followed by client soggetto
            followed.getClientFollowers().remove(soggetto); // to the follower of this client get add 'soggetto'

            clientRepository.save(soggetto);
            clientRepository.save(followed);
        } else {
            throw new ClientNotFoundException(soggetto.getIdClient());
        }
    }

    public List<String> getFollowersPreferences(Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isPresent()){
            Client client = clientOptional.get();
            List<String> followerList = clientRepository.findPreferencesOfFollowersOfId(id);
            if(!followerList.isEmpty()){
                return followerList;
            }else {
                throw new FollowNotFound("No follower found for client "+(client.getIdClient()).toString());
            }
        }else {
            throw new ClientNotFoundException(id);
        }
    }

    public List<String> getFollowedPreferences(Long id){
        Optional<Client> clientOptional = clientRepository.findById(id);
        if(clientOptional.isPresent()){
            Client client = clientOptional.get();
            List<String> followedList = clientRepository.findPreferencesOfFollowedById(id);
            if(!followedList.isEmpty()){
                return followedList;
            }else {
                throw new FollowNotFound("No client followed by client "+(client.getIdClient()).toString());
            }
        }else{
            throw new ClientNotFoundException(id);
        }
    }
}


