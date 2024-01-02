package com.bicoccaprojects.beerconnect.service;

import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.exception.pub.NoPubsFoundException;
import com.bicoccaprojects.beerconnect.exception.pub.PubNotFoundException;
import com.bicoccaprojects.beerconnect.repository.PubRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PubService {
    @Autowired
    private PubRepository pubRepository;

    public Iterable<Pub> getAllPubs() {
        Iterable<Pub> pubs = pubRepository.findAll();
        if(!pubs.iterator().hasNext()){
            throw new NoPubsFoundException();
        }
        return pubs;
    }

    public Pub getPub(Long id) {
        Optional<Pub> optionalPub = pubRepository.findById(id);
        if(optionalPub.isPresent()){
            return optionalPub.get();
        }else {
            throw new PubNotFoundException(id);
        }
    }

    @Transactional
    public void deletePub(Long id) {
        Optional<Pub> pubOptional = pubRepository.findById(id);

        if(pubOptional.isEmpty()){
            throw new PubNotFoundException(id);
        }
        pubRepository.deleteById(id);
    }

    @Transactional
    public void deletePubs() {
        Iterable<Pub> pubs = pubRepository.findAll();
        if(!pubs.iterator().hasNext()){
            throw new NoPubsFoundException();
        }
        pubRepository.deleteAll();
    }

    @Transactional
    public void addPub(Pub pub) throws Exception {
        Long id = pub.getIdPub();
        Optional<Pub> pubOptional = pubRepository.findById(id);
        if(pubOptional.isEmpty()){
            pubRepository.save(pub);
        }else {
            throw new Exception("There is alredy a pub in the DB with ID "+(pub.getIdPub()).toString());
        }
    }

    @Transactional
    public void updatePub(Pub pub) {
        Long id = pub.getIdPub();
        Optional<Pub> pubOptional = pubRepository.findById(id);
        if(pubOptional.isPresent()){
            pubRepository.save(pub);
        }else {
            throw new PubNotFoundException(id);
        }
    }

    public List<String> findBeerByPub(Long idPub){
        List<String> pubList = pubRepository.findBeerByPub(idPub);
        if(!pubList.isEmpty()){
            return pubList;
        }else {
            throw new PubNotFoundException("There are no pub with this name")
        }
    }
}
