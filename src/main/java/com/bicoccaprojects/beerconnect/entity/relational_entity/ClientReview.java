package com.bicoccaprojects.beerconnect.entity.relational_entity;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Client;
import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * La classe ClientReview è un'entità JPA mappata sulla tabella "client_review" nel database. Questa entità
 * rappresenta una relazione intermedia tra le entità Client e Beer, aggiungendo due campi (review e rating)
 * alla relazione tra un cliente e una birra. La tabella ha un vincolo di unicità basato sugli id di cliente e
 * birra, evitando recensioni duplicate per la stessa coppia cliente-birra.
 * La classe contiene annotazioni JPA che definiscono le relazioni many-to-one con le entità Client e Beer.
 * Implementa anche una regola di validazione per assicurarsi che il rating sia compreso tra 1 e 5.
 */

@Entity
@Table(name = "client_review",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_client", "id_beer"}))
public class ClientReview { // this intermediate entity add 2 fields to the relationship between Client and Beer
    @Id
    @SequenceGenerator(name="review_sequence", sequenceName = "review_sequence", allocationSize = 1,initialValue = 13)
    @GeneratedValue(strategy = SEQUENCE, generator = "review_sequence")
    @Column(name = "id_review", updatable = false)
    private Long idReview;

    // despite using @ManyToOne tag, this is a @ManyToMany relationship
    // this intermediate entity allow the ManyToMany relationship
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client idClient;

    @ManyToOne
    @JoinColumn(name = "id_beer", nullable = false)
    private Beer idBeer;


    @Column(name = "review")
    private String review;

    @Column(name = "rating", nullable = false)
    private Integer rating;


    // Method that validates whether the rating is between 1 and 5
    @PrePersist
    @PreUpdate
    private void validateRating() {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalStateException("Il rating deve essere compreso tra 1 e 5.");
        }
    }

    // constructors

    public ClientReview(Client idClient, Beer idBeer, Integer rating, String review) {
        this.idClient = idClient;
        this.idBeer = idBeer;
        this.rating = rating;
        this.review = review;
    }
    public ClientReview(Long idReview,Client idClient, Beer idBeer, Integer rating, String review) {
        this.idReview = idReview;
        this.idClient = idClient;
        this.idBeer = idBeer;
        this.rating = rating;
        this.review = review;
    }

    public ClientReview() {
    }

    // getter and setter

    public Long getIdReview() {
        return idReview;
    }

    public void setIdReview(Long idReview) {
        this.idReview = idReview;
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

    public Client getIdClient() {
        return idClient;
    }

    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }

    public Beer getIdBeer() {
        return idBeer;
    }

    public void setIdBeer(Beer idBeer) {
        this.idBeer = idBeer;
    }

    @Override
    public String toString() {
        return "ClientReview{" +
                "idReview=" + idReview +
                ", idClient=" + idClient +
                ", idBeer=" + idBeer +
                ", review='" + review + '\'' +
                ", rating=" + rating +
                '}';
    }
}
