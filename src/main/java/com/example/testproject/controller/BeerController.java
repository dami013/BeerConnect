package com.example.testproject.controller;

import com.example.testproject.entity.Beer;
import com.example.testproject.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    // Read beer with an id (Get + Id) - funziona
    @GetMapping("/beers/{id_beer}")
    public ResponseEntity<Beer> getBeerById(@PathVariable("id_beer") long id) {
        Optional<Beer> beerData = beerRepository.findById(id);

        if (beerData.isPresent()) {
            return new ResponseEntity<>(beerData.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Retrive all the beers available - funziona
    @GetMapping("/beers")
    public List<Beer> getAllBeer(){
        return beerRepository.findAll();
    }

    // commentate perchè danno problemi quando si lancia il main
    /*// Update a beer with an id sent with body
    @PutMapping("/update")
    public Beer updateBeerById(@RequestBody Beer inBeer){
        Optional<Beer> beer = beerRepository.findById(inBeer.getId_beer());
        Beer updateBeer = beer.get();
        updateBeer.setName_beer(inBeer.getName_beer());
        return beerRepository.save(updateBeer);
    }

    // Delete a beer with given id
    @DeleteMapping("/delete/{id_beer}")
    public void deleteBeerById(@PathVariable(value = "id_beer") Long id){
        beerRepository.deleteById(id);
    }
*/
}
