package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BeerService {
    @Autowired
    private BeerRepository beerRepository;

    @Transactional
    public Iterable<Beer> getBeers() {
        return  beerRepository.findAll();
    }

    @Transactional
    public Optional<Beer> getBeer(Long id) {
        return beerRepository.findById(id);
    }

    @Transactional
    public void deleteBeer(Long id) {
        beerRepository.deleteById(id);
    }

    @Transactional
    public void deleteBeers() {
        beerRepository.deleteAll();
    }

    @Transactional
    public void addBeer(Beer beer) {
        beerRepository.save(beer);
    }

    @Transactional
    public void updateBeer(Beer beer) {
        beerRepository.save(beer);
    }

    @Transactional
    public List<String> searchBeerByType(String type){ return beerRepository.searchBeerByType(type);}

    @Transactional
    public List<String> searchBeerByName(String name){ return beerRepository.searchBeerByName(name);}
}
