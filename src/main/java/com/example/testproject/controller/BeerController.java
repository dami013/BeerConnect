package com.example.testproject.controller;

import com.example.testproject.entity.Beer;
import com.example.testproject.repository.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/beers") // definisce un prefisso per tutti i metodi all'interno del controller
public class BeerController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BeerController.class);

    @Autowired
    BeerRepository beerRepository;

    @RequestMapping
    public String HelloWorld(){ // se vai a http://localhost:8080/beerconnect ti esce la scritta
        return "Hello world!";
    }

    // in teoria se andassimo a http://localhost:8080/beerconnect/beers/{id} da una pagina web con birre aventi ID quello cercato
    @GetMapping("/{id_beer}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("id_beer") long id) {
        Optional<Beer> beerData = beerRepository.findById(id);

        if (beerData.isPresent()) {
            return new ResponseEntity<>(beerData.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST: http://localhost:8080/beers
    @RequestMapping(value="", method=RequestMethod.POST)
    public Beer createBeer(@RequestBody Beer beer){ // @RequestBody indica a Spring di deserializzare il corpo della richiesta HTTP (JSON, XML, ecc.) in un oggetto Beer
        LOGGER.debug("Ricevuta richiesta di crare "+beer.toString());
        return beerRepository.save(beer);
    }

    // PUT: http://localhost:8080/beers/1
    @RequestMapping(value="/{id_beer}", method=RequestMethod.PUT)
    public Beer updateBeer(@PathVariable long userId, @RequestBody Beer beer) {
        beer.setId_beer(userId);
        return beerRepository.save(beer);
    }
}
