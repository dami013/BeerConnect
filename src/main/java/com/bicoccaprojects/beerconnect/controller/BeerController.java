package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.api_endpoint.BeerEP;
import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @PostMapping(BeerEP.ADD_BEER)
    public ResponseEntity<String> addBeer(@RequestBody Beer beer) {
        beerService.addBeer(beer);
        return new ResponseEntity<>("Beer added successfully", HttpStatus.CREATED);
    }

    @GetMapping(BeerEP.GET_BEER_BY_ID)
    public ResponseEntity<Beer> getBeerById(@PathVariable("id_beer") long id) {
        Optional<Beer> beerData = beerService.getBeer(id);

        return beerData.map(beer -> new ResponseEntity<>(beer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(BeerEP.GET_ALL_BEERS)
    public Iterable<Beer> getAllBeers() {
        return beerService.getBeers();
    }

    @PutMapping(BeerEP.UPDATE_BEER)
    public ResponseEntity<String> updateBeerById(@RequestBody Beer inBeer) {
        Optional<Beer> beer = beerService.getBeer(inBeer.getIdBeer());

        if (beer.isPresent()) {
            Beer updateBeer = beer.get();
            updateBeer.setNameBeer(inBeer.getNameBeer());
            beerService.updateBeer(updateBeer);
            return new ResponseEntity<>("Beer updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Beer not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(BeerEP.DELETE_BEER_BY_ID)
    public ResponseEntity<String> deleteBeerById(@PathVariable(value = "id_beer") Long id) {
        if (beerService.getBeer(id).isPresent()) {
            beerService.deleteBeer(id);
            return new ResponseEntity<>("Beer deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Beer not found", HttpStatus.NOT_FOUND);
        }
    }
}
