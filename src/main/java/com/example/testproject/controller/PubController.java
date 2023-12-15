package com.example.testproject.controller;

import com.example.testproject.entity.Beer;
import com.example.testproject.entity.Pub;
import com.example.testproject.repository.PubRepository;
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
    PubRepository pubRepository;

    // add a pub (POST)
    @PostMapping("/pub")
    public Pub addPub(@RequestBody Pub pub) {
        System.out.println(pub);
        return pubRepository.save(pub);
    }

    // Read pub with an id (Get + Id)
    @GetMapping("/pubs/{id_pub}")
    public ResponseEntity<Pub> getPubById(@PathVariable("id_pub") long id) {
        Optional<Pub> pubData = pubRepository.findById(id);

        if (pubData.isPresent()) {
            return new ResponseEntity<>(pubData.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Retrive all the pubs available
    @GetMapping("/pubs")
    public List<Pub> getAllPub(){
        return pubRepository.findAll();
    }

    // Update a pub with an id sent with body
    @PutMapping("/update")
    public Pub updatePubById(@RequestBody Pub inPub){
        Optional<Pub> pub = pubRepository.findById(inPub.getId_pub());
        Pub updatePub = pub.get();
        updatePub.setName_pub(inPub.getName_pub());
        return pubRepository.save(updatePub);
    }

    // Delete a beer with given id
    @DeleteMapping("/delete/{id_pub}")
    public void deletePubById(@PathVariable(value = "id_pub") Long id){
        pubRepository.deleteById(id);
    }

}
