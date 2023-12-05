package com.beerconnect.controller;

import com.beerconnect.assignment.ClientRecord;
import com.beerconnect.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ClientController {
    @Autowired
    private ClientService clientService;

    @RequestMapping("/")
    public List<ClientRecord> getAllClient(){
        return clientService.getAllClients();
    }

    @RequestMapping(value = "/add-client", method = RequestMethod.POST)
    public void addClient(@RequestBody ClientRecord clientRecord){
        clientService.addClient(clientRecord);
    }

}
