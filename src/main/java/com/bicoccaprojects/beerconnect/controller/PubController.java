package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PubController {

    @Autowired
    private PubService pubService;

    @GetMapping("/pubs/{id_pub}")
    public Pub getPubById(@PathVariable("id_pub") long id) {
        return pubService.getPub(id);
    }

    @GetMapping("/pubs")
    public ModelAndView getAllPub(){
        ModelAndView model = new ModelAndView();
        Iterable<Pub> pubs = pubService.getAllPubs();
        model.addObject("pubs", pubs);
        model.setViewName("pubs");
        return model;
    }

    @GetMapping("/pub")
    public List<String> searchPubByParams(String country, String type, Integer rating){
        return pubService.searchPubByCountryTypeRating(country, type, rating);
    }

    @DeleteMapping("/deletepub/{id_pub}")
    public void deletePubById(@PathVariable(value = "id_pub") Long id) {
        pubService.deletePub(id);
    }

    @DeleteMapping("/deletepub")
    public void deleteAllPub(){
        pubService.deletePubs();
    }

    @PostMapping("/pub")
    public void addPub(@RequestBody Pub pub) {
        pubService.addPub(pub);
    }

    @PutMapping("/updatepub")
    public void updatePubById(@RequestBody Pub inPub) {
        pubService.updatePub(inPub);
    }
}
