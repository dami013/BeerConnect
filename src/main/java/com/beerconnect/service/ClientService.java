package com.beerconnect.service;

import com.beerconnect.assignment.ClientRecord;
import com.beerconnect.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    public List<ClientRecord> getAllClients(){
        List<ClientRecord>clientRecords= new ArrayList<>();
        clientRepository.findAll().forEach(clientRecords::add);
        return  clientRecords;
    }

    public void addClient(ClientRecord clientRecord){
        clientRepository.save(clientRecord);
    }
}
