package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.repository.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class BeerService {
    @Autowired
    private BeerRepository beerRepository;

    public Iterable<Beer> getBeers() {
        return  beerRepository.findAll();
    }

    public Optional<Beer> getBeer(Long id) {
        return beerRepository.findById(id);
    }
    public void deleteBeer(Long id) {
        beerRepository.deleteById(id);
    }
    public void deleteBeers() {
        beerRepository.deleteAll();
    }
    public void addBeer(Beer beer) {
        beerRepository.save(beer);
    }
    public void updateBeer(Beer beer) {
        beerRepository.save(beer);
    }
}
