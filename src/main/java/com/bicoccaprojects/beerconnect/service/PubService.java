package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.repository.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PubService {
    @Autowired
    private PubRepository pubRepository;

    public Iterable<Pub> getPubs() {
        return  pubRepository.findAll();
    }

    public Optional<Pub> getPub(Long id) {
        return pubRepository.findById(id);
    }
    public void deletePub(Long id) {
        pubRepository.deleteById(id);
    }
    public void deletePubs() {
        pubRepository.deleteAll();
    }
    public void addPub(Pub beer) {
        pubRepository.save(beer);
    }
    public void updatePub(Pub beer) {
        pubRepository.save(beer);
    }
}
