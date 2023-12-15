package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

// di default viene dato il nome della classe ma è buona pratica specificare
// utile nel caso in cui nome della classe è molto lungo ma nome entità è diverso
@Entity(name="Pub") // serve a mappare questa tabella nel DB
public class Pub {
    @Id
    @SequenceGenerator(name="pub_sequence", sequenceName = "pub_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "pub_sequence") // per avere ID che parte da 1 e incrementa di 1 per ogni entità nella tabella
    @Column(name = "id_pub", updatable = false)
    private Long id_pub;

    @Column(name = "name_pub", nullable = false, columnDefinition = "TEXT") // nullable = false -> not null, "TEXT"-> varchar
    private String name_pub;

    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String Country;

    @Column(name = "year_of_foundation", nullable = false, columnDefinition = "INTEGER")
    private Integer yearOfFoundation;

    public Pub(String name, String address, Integer yearOfFoundation) {
        this.name_pub = name;
        this.Country = address;
        this.yearOfFoundation = yearOfFoundation;
    }

    public Pub() {

    }

    @OneToMany(mappedBy = "pub")
    private List<Beer> beers;


    public Long getId_pub() {
        return id_pub;
    }

    public void setId_pub(Long id) {
        this.id_pub = id;
    }

    public String getName_pub() {
        return name_pub;
    }

    public void setName_pub(String name) {
        this.name_pub = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        this.Country = country;
    }

    public Integer getYearOfFoundation() {
        return yearOfFoundation;
    }

    public void setYearOfFoundation(int yearOfFoundation) {
        this.yearOfFoundation = yearOfFoundation;
    }

    @Override
    public String toString() {
        return "Pub{" +
                "id=" + id_pub +
                ", name='" + name_pub + '\'' +
                ", address='" + Country + '\'' +
                ", yearOfFoundation=" + yearOfFoundation +
                '}';
    }
}
