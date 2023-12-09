package com.example.testproject.controller;

import com.example.testproject.entity.Beer;
import com.example.testproject.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/beerconnect") // definisce un prefisso per tutti i metodi all'interno del controller
public class BeerController {
    @Autowired
    BeerRepository beerRepository;

    @RequestMapping
    public String HelloWorld(){ // se vai a http://localhost:8080/beerconnect ti esce la scritta
        return "Hello world!";
    }

    // in teoria se andassimo a http://localhost:8080/beerconnect/beers/{id} da una pagina web con birre aventi ID quello cercato
    @GetMapping("/beers/{id_beer}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("id_beer") long id) {
        Optional<Beer> beerData = beerRepository.findById(id);

        if (beerData.isPresent()) {
            return new ResponseEntity<>(beerData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
