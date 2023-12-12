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
@RequestMapping("/api") // definisce un prefisso per tutti i metodi all'interno del controller
public class BeerController {

    @Autowired
    BeerRepository beerRepository;

    // add a beer (POST)
    @PostMapping("/beer")
    public Beer addBeer(@RequestBody Beer beer) {
        System.out.println(beer);
        return beerRepository.save(beer);
    }
    // Read beer with an id (Get + Id)
    @GetMapping("/beers/{id_beer}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("id_beer") long id) {
        Optional<Beer> beerData = beerRepository.findById(id);

        if (beerData.isPresent()) {
            return new ResponseEntity<>(beerData.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Retrive all the beers available
    @GetMapping("/beers")
    public List<Beer> getAllBeer(){
        return beerRepository.findAll();
    }

    // Update a beer
    @PutMapping("/update")
    public Beer updateEmployeeById(@RequestBody Beer inBeer){
        Optional<Beer> beer = beerRepository.findById(inBeer.getId_beer());
        Beer updateBeer = beer.get();
        updateBeer.setName_beer(inBeer.getName_beer());
        return beerRepository.save(updateBeer);
    }

    // Delete a beer
    @DeleteMapping("/delete/{id_beer}")
    public void deleteEmployeeById(@PathVariable(value = "id_beer") Long id){
        beerRepository.deleteById(id);
    }


}
