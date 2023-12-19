package com.bicoccaprojects.beerconnect.controller.relational_controller;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import com.bicoccaprojects.beerconnect.service.BeerService;
import com.bicoccaprojects.beerconnect.service.relational_service.ClientReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ClientReviewController {

    @Autowired
    private ClientReviewService clientReviewService;

    @PostMapping("/review")
    public ResponseEntity<String> addClientReview(@RequestBody ClientReview clientReview) {
        clientReviewService.addReview(clientReview);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/reviews/{id_review}")
    public ResponseEntity<ClientReview> getClientReviewById(@PathVariable("id_review") long id) {
        Optional<ClientReview> clientReviewData = clientReviewService.getReview(id);

        return clientReviewData.map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/reviews")
    public Iterable<ClientReview> getAllReviews() {
        return clientReviewService.getReviews();
    }

    @PutMapping("/updatereview")
    public ResponseEntity<String> updateReviewById(@RequestBody ClientReview inReview) {
        Optional<ClientReview> clientReview = clientReviewService.getReview(inReview.getIdReview());

        if (clientReview.isPresent()) {
            ClientReview updateReview = clientReview.get();
            updateReview.setReview(inReview.getReview());
            clientReviewService.updateReview(updateReview);
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletereview/{id_review}")
    public ResponseEntity<String> deleteReviewById(@PathVariable(value = "id_review") Long id) {
        if (clientReviewService.getReview(id).isPresent()) {
            clientReviewService.deleteReview(id);
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Review not found", HttpStatus.NOT_FOUND);
        }
    }
}
