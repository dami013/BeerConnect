package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.service.LimitedEditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class LimitedEditionController {
    @Autowired
    private LimitedEditionService limitedEditionService;

    @PostMapping("/le_beer")
    public ResponseEntity<String> addLEBeer(@RequestBody LimitedEdition leBeer) {
        limitedEditionService.addLEBeer(leBeer);
        return new ResponseEntity<>("Limited Edition Beer added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/le_beers/{id_beer}")
    public ResponseEntity<LimitedEdition> getLEBeerById(@PathVariable("id_beer") long id) {
        Optional<LimitedEdition> leBeerData = limitedEditionService.getLEBeer(id);

        return leBeerData.map(leBeer -> new ResponseEntity<>(leBeer, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/le_beers")
    public Iterable<LimitedEdition> getAllLEBeer() {
        return limitedEditionService.getLEBeers();
    }

    @PutMapping("/updatelebeer")
    public ResponseEntity<String> updateLEBeerById(@RequestBody LimitedEdition inLEBeer) {
        Optional<LimitedEdition> leBeer = limitedEditionService.getLEBeer(inLEBeer.getIdBeer());

        if (leBeer.isPresent()) {
            LimitedEdition updateLEBeer = leBeer.get();
            updateLEBeer.setNameBeer(inLEBeer.getNameBeer());
            limitedEditionService.updateBeer(updateLEBeer);
            return new ResponseEntity<>("Limited Edition Beer updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Limited Edition Beer not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletelebeer/{id_beer}")
    public ResponseEntity<String> deleteLEBeerById(@PathVariable(value = "id_beer") Long id) {
        if (limitedEditionService.getLEBeer(id).isPresent()) {
            limitedEditionService.deleteLEBeer(id);
            return new ResponseEntity<>("Limited Edition Beer deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Limited Edition Beer not found", HttpStatus.NOT_FOUND);
        }
    }

}
