package com.bicoccaprojects.beerconnect.service.relational_service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.repository.relational_repository.ClientReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientReviewService {
    @Autowired
    private ClientReviewRepository clientReviewRepository;

    @Transactional
    public Iterable<ClientReview> getReviews() {
        return  clientReviewRepository.findAll();
    }

    @Transactional
    public Optional<ClientReview> getReview(Long id) {
        return clientReviewRepository.findById(id);
    }

    @Transactional
    public void deleteReview(Long id) {
        clientReviewRepository.deleteById(id);
    }

    @Transactional
    public void deleteReviews() {
        clientReviewRepository.deleteAll();
    }

    @Transactional
    public void addReview(ClientReview clientReview) {clientReviewRepository.save(clientReview);}

    @Transactional
    public void updateReview(ClientReview clientReview) {clientReviewRepository.save(clientReview);}
}
