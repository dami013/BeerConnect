package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.exception.beer.BeerAlreadyExistsException;
import com.bicoccaprojects.beerconnect.exception.beer.BeerNotFoundException;
import com.bicoccaprojects.beerconnect.exception.beer.LimitedEditionNotFoundException;
import com.bicoccaprojects.beerconnect.exception.beer.NoBeersFoundException;
import com.bicoccaprojects.beerconnect.repository.LimitedEditionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;

@Service
public class LimitedEditionService extends BeerService {
    @Autowired
    private LimitedEditionRepository limitedEditionRepository;

    public Iterable<LimitedEdition> getAllLEBeers() {
        Iterable<LimitedEdition> beers = limitedEditionRepository.findAll();
        if (!beers.iterator().hasNext()) {
            throw new LimitedEditionNotFoundException("There are no Limited edition beers in the DB");
        }
        return beers;
    }

    public LimitedEdition getLEBeer(Long id) {
        Optional<LimitedEdition> optionalBeer = limitedEditionRepository.findById(id);
        if (optionalBeer.isPresent()){
            return optionalBeer.get();
        } else {
            throw new BeerNotFoundException(id);
        }
    }

    @Transactional
    public boolean deleteLEBeer(Long id) {
        Optional<LimitedEdition> beerOptional = limitedEditionRepository.findById(id);
        if (beerOptional.isEmpty()) {
            throw new BeerNotFoundException(id);
        }
        limitedEditionRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteLEBeers() {
        Iterable<LimitedEdition> beers = limitedEditionRepository.findAll();
        if (!beers.iterator().hasNext()) {
            throw new NoBeersFoundException();
        }
        limitedEditionRepository.deleteAll();
    }

    @Transactional
    public boolean addLEBeer(LimitedEdition leBeer){
        Long id = leBeer.getIdBeer();
        Optional<LimitedEdition> beerOptional = limitedEditionRepository.findById(id);
        if(beerOptional.isEmpty()){
            List<String> beerList = limitedEditionRepository.searchBeerByName(leBeer.getNameBeer());
            if(beerList.isEmpty()){
                limitedEditionRepository.save(leBeer);
                return true;
            }else {
                throw new BeerAlreadyExistsException("There is already a beer with " + leBeer.getNameBeer() + " as name");
            }
        } else {
            throw new BeerAlreadyExistsException("There is already a beer in the DB with ID " + id);
        }
    }

    @Transactional
    public boolean updateLEBeer(LimitedEdition leBeer) {
        Long id = leBeer.getIdBeer();
        Optional<LimitedEdition> beerOptional = limitedEditionRepository.findById(id);
        if(beerOptional.isPresent()){
            limitedEditionRepository.save(leBeer);
            return true;
        }else {
            throw new BeerNotFoundException(id);
        }
    }

    public List<String> findLEByOriginalBeer(String originalBeer){
        List<String> beerList = limitedEditionRepository.findLEByBeer(originalBeer);
        if (!beerList.isEmpty()){
            return beerList;
        }else{
            throw new BeerNotFoundException("There are no beer with this name");
        }
    }

    public List<String> searchLEByName(String nameBeer){
        List<String> beerList = limitedEditionRepository.searchBeerByName(nameBeer);
        if(!beerList.isEmpty()){
            return beerList;
        }else {
            throw new BeerNotFoundException("There are no beer with this name");
        }
    }
}
