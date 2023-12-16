package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.LimitedEdition;
import com.bicoccaprojects.beerconnect.repository.LimitedEditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LimitedEditionService {
    @Autowired
    private LimitedEditionRepository limitedEditionRepository;

    public Iterable<LimitedEdition> getLimitedBeers() {
        return  limitedEditionRepository.findAll();
    }

    public Optional<LimitedEdition> getLimitedBeer(Long id) {
        return limitedEditionRepository.findById(id);
    }
    public void deleteLimitedBeer(Long id) {
        limitedEditionRepository.deleteById(id);
    }
    public void deleteLimitedBeers() {
        limitedEditionRepository.deleteAll();
    }
    public void addLimitedBeer(LimitedEdition limitedEdition) {
        limitedEditionRepository.save(limitedEdition);
    }
    public void updateLimitedBeer(LimitedEdition limitedEdition) {
        limitedEditionRepository.save(limitedEdition);
    }
}
