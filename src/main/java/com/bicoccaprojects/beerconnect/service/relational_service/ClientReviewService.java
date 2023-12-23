package com.bicoccaprojects.beerconnect.service.relational_service;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.repository.relational_repository.ClientReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientReviewService {
    @Autowired
    private ClientReviewRepository clientReviewRepository;

    public Iterable<ClientReview> getReviews() {
        return  clientReviewRepository.findAll();
    }

    public Optional<ClientReview> getReview(Long id) {
        return clientReviewRepository.findById(id);
    }
    public void deleteReview(Long id) {
        clientReviewRepository.deleteById(id);
    }
    public void deleteReviews() {
        clientReviewRepository.deleteAll();
    }
    public void addReview(ClientReview clientReview) {clientReviewRepository.save(clientReview);}
    public void updateReview(ClientReview clientReview) {clientReviewRepository.save(clientReview);}
}
