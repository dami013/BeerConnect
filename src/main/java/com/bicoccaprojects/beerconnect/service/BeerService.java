package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.exception.beer.BeerAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.beer.BeerNotFoundException;
import com.bicoccaprojects.beerconnect.exception.beer.NoBeersFoundException;
import com.bicoccaprojects.beerconnect.repository.BeerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BeerService {
    @Autowired
    private BeerRepository beerRepository;

    public Iterable<Beer> getAllBeers() {
        Iterable<Beer> beers = beerRepository.findAll();
        if (!beers.iterator().hasNext()) {
            throw new NoBeersFoundException();
        }
        return beers;
    }

    public Beer getBeer(Long id) {
        Optional<Beer> optionalBeer = beerRepository.findById(id);
        if (optionalBeer.isPresent()){
            return optionalBeer.get();
        } else {
            throw new BeerNotFoundException(id);
        }
    }

    @Transactional
    public boolean deleteBeer(Long id) {
        Optional<Beer> beerOptional = beerRepository.findById(id);

        if (beerOptional.isEmpty()) {
            throw new BeerNotFoundException(id);
        }
        beerRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteBeers() {
        Iterable<Beer> beers = beerRepository.findAll();

        if (StreamSupport.stream(beers.spliterator(), false).noneMatch(beer -> true)) {
            throw new NoBeersFoundException();
        }

        beerRepository.deleteAll();
    }

    @Transactional
    public boolean addBeer(Beer beer) {
        Long id = beer.getIdBeer();
        Optional<Beer> beerOptional = beerRepository.findById(id);

        if (beerOptional.isPresent()) {
            throw new BeerAlreadyExistsException("There is already a beer in the DB with ID " + id);
        }

        List<String> beerList = beerRepository.searchBeerByName(beer.getNameBeer());

        if (!beerList.isEmpty()) {
            throw new BeerAlreadyExistsException("There is already a beer with " + beer.getNameBeer() + " as name");
        }
        beerRepository.save(beer);
        return true;
    }

    @Transactional
    public void updateBeer(Beer beer) {
        Long id = beer.getIdBeer();
        Optional<Beer> beerOptional = beerRepository.findById(id);
        if(beerOptional.isPresent()){
            beerRepository.save(beer);
        }else {
            throw new BeerNotFoundException(id);
        }
    }

    public List<String> searchBeerByType(String type) {
        List<String> beerList = beerRepository.searchBeerByType(type);
        if (!beerList.isEmpty()){
            return beerList;
        }else {
            throw new BeerNotFoundException("There are no beer of this type");
        }
    }

    public List<String> searchBeerByName(String name) {
        List<String> beerList = beerRepository.searchBeerByName(name);
        if(!beerList.isEmpty()){
            return beerList;
        }else {
            throw new BeerNotFoundException("There are no beer with this name");
        }
    }
}
