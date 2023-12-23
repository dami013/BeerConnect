package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.repository.LimitedEditionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LimitedEditionService extends BeerService {
    @Autowired
    private LimitedEditionRepository limitedEditionRepository;

    @Transactional
    public Iterable<LimitedEdition> getLEBeers() {
        return  limitedEditionRepository.findAll();
    }

    @Transactional
    public Optional<LimitedEdition> getLEBeer(Long id) {
        return limitedEditionRepository.findById(id);
    }

    @Transactional
    public void deleteLEBeer(Long id) {
        limitedEditionRepository.deleteById(id);
    }

    @Transactional
    public void deleteLEBeers() {
        limitedEditionRepository.deleteAll();
    }

    @Transactional
    public void addLEBeer(LimitedEdition leBeer) {
        limitedEditionRepository.save(leBeer);
    }

    @Transactional
    public void updateLEBeer(LimitedEdition leBeer) {
        limitedEditionRepository.save(leBeer);
    }
}
