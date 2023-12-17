package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.repository.LimitedEditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LimitedEditionService extends BeerService {
    @Autowired
    private LimitedEditionRepository limitedEditionRepository;

    public Iterable<LimitedEdition> getLEBeers() {
        return  limitedEditionRepository.findAll();
    }

    public Optional<LimitedEdition> getLEBeer(Long id) {
        return limitedEditionRepository.findById(id);
    }
    public void deleteLEBeer(Long id) {
        limitedEditionRepository.deleteById(id);
    }
    public void deleteLEBeers() {
        limitedEditionRepository.deleteAll();
    }
    public void addLEBeer(LimitedEdition leBeer) {
        limitedEditionRepository.save(leBeer);
    }
    public void updateLEBeer(LimitedEdition leBeer) {
        limitedEditionRepository.save(leBeer);
    }
}
