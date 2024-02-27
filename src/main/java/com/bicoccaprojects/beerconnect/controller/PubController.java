package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class PubController {

    @Autowired
    private PubService pubService;

    @PostMapping("/pub")
    public ResponseEntity<String> addPub(@RequestBody Pub pub) {
        pubService.addPub(pub);
        return new ResponseEntity<>("Pub added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/pubs/{id_pub}")
    public ResponseEntity<Pub> getPubById(@PathVariable("id_pub") long id) {
        Optional<Pub> pubData = pubService.getPub(id);

        return pubData.map(pub -> new ResponseEntity<>(pub, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pubs")
    public Iterable<Pub> getAllPubs() {
        return pubService.getPubs();
    }

    @PutMapping("/updatepub")
    public ResponseEntity<String> updatePubById(@RequestBody Pub inPub) {
        Optional<Pub> pub = pubService.getPub(inPub.getIdPub());

        if (pub.isPresent()) {
            Pub updatePub = pub.get();
            updatePub.setNamePub(inPub.getNamePub());
            pubService.updatePub(updatePub);
            return new ResponseEntity<>("Pub updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pub not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletepub/{id_pub}")
    public ResponseEntity<String> deletePubById(@PathVariable(value = "id_pub") Long id) {
        if (pubService.getPub(id).isPresent()) {
            pubService.deletePub(id);
            return new ResponseEntity<>("Pub deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Pub not found", HttpStatus.NOT_FOUND);
        }
    }
}
