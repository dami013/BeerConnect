package com.bicoccaprojects.beerconnect.service.relational_service;

import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.exception.clientreviews.ClientReviewNotFoundException;
import com.bicoccaprojects.beerconnect.exception.clientreviews.NoClientReviewsFoundException;
import com.bicoccaprojects.beerconnect.repository.relational_repository.ClientReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientReviewService {
    @Autowired
    private ClientReviewRepository clientReviewRepository;

    public Iterable<ClientReview> getAllReviews() {
        Iterable<ClientReview> clientReviews = clientReviewRepository.findAll();
        if(!clientReviews.iterator().hasNext()){
            throw new NoClientReviewsFoundException();
        }
        return clientReviews;
    }

    public ClientReview getReview(Long id) {
        Optional<ClientReview> optionalClientReview = clientReviewRepository.findById(id);
        if(optionalClientReview.isPresent()){
            return optionalClientReview.get();
        }else{
            throw new ClientReviewNotFoundException(id);
        }
    }

    @Transactional
    public boolean deleteReview(Long id) {
        Optional<ClientReview> clientReviewOptional = clientReviewRepository.findById(id);
        if(clientReviewOptional.isEmpty()){
            throw new ClientReviewNotFoundException(id);

        }
        clientReviewRepository.deleteById(id);
        return true;
    }

    @Transactional
    public void deleteReviews() {
        Iterable<ClientReview> clientReviews = clientReviewRepository.findAll();
        if(!clientReviews.iterator().hasNext()){
            throw new NoClientReviewsFoundException();
        }
        clientReviewRepository.deleteAll();
    }

    @Transactional
    public void addReview(ClientReview clientReview) throws Exception {
        Long id = clientReview.getIdReview();
        Optional<ClientReview> clientReviewOptional =  clientReviewRepository.findById(id);
        if(clientReviewOptional.isEmpty()){
            clientReviewRepository.save(clientReview);
        }else{
            throw new Exception("There is already a client review with "+id);
        }
    }

    @Transactional
    public void updateReview(ClientReview clientReview) {
        Long id = clientReview.getIdReview();
        Optional<ClientReview> clientReviewOptional = clientReviewRepository.findById(id);
        if (clientReviewOptional.isPresent()){
            clientReviewRepository.save(clientReview);
        }else{
            throw new ClientReviewNotFoundException(id);
        }
    }

    public List<String> findReviewsByBeerCountryAndRating(String country, int rating){
        List<String> reviewList = clientReviewRepository.findClientByReview(country, rating);
        if(!reviewList.isEmpty()){
            return reviewList;
        }else{
            throw new ClientReviewNotFoundException("There are no reviews for beer made in "+country+" with a rating equal or above "+rating);
        }
    }
}
