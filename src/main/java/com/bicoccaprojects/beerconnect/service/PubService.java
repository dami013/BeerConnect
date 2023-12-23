package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.repository.PubRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PubService {
    @Autowired
    private PubRepository pubRepository;

    @Transactional
    public Iterable<Pub> getPubs() {
        return  pubRepository.findAll();
    }

    @Transactional
    public Optional<Pub> getPub(Long id) {
        return pubRepository.findById(id);
    }

    @Transactional
    public void deletePub(Long id) {
        pubRepository.deleteById(id);
    }

    @Transactional
    public void deletePubs() {
        pubRepository.deleteAll();
    }

    @Transactional
    public void addPub(Pub beer) {
        pubRepository.save(beer);
    }

    @Transactional
    public void updatePub(Pub beer) {
        pubRepository.save(beer);
    }
}
