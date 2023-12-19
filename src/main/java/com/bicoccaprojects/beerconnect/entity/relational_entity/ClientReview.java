package com.bicoccaprojects.beerconnect.entity.relational_entity;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Client;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "client_review",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_client", "id_beer"}))
public class ClientReview {
    @Id
    @SequenceGenerator(name="review_sequence", sequenceName = "review_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "review_sequence")
    @Column(name = "id_review", updatable = false)
    private Long idReview;

    // nonostante si usino i tag @ManyToOne questa rimane una relazione @ManyToMany
    // questa entità intermedia permette relazione ManyToMany

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client idClient;

    @ManyToOne
    @JoinColumn(name = "id_beer")
    private Beer idBeer;

    // Altre colonne aggiuntive della tabella

    @Column(name = "review", columnDefinition = "VARCHAR(255)")
    private String review;

    @Column(name = "rating", nullable = false, columnDefinition = "INTEGER")
    private Integer rating;

    @PrePersist
    @PreUpdate
    private void validateRating() {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalStateException("Il rating deve essere compreso tra 1 e 5.");
        }
    }

    public ClientReview(Long idReview, Client idClient, Beer idBeer, Integer rating, String review) {
        this.idReview = idReview;
        this.idClient = idClient;
        this.idBeer = idBeer;
        this.rating = rating;
        this.review = review;
    }

    public ClientReview() {
    }

    public Long getIdReview() {
        return idReview;
    }

    public void setIdReview(Long idReview) {
        this.idReview = idReview;
    }

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client client) {
        this.idClient = client;
    }

    public Beer getIdBeer() {
        return idBeer;
    }

    public void setIdBeer(Beer beer) {
        this.idBeer = beer;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
