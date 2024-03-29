package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * The Pub class is a JPA entity mapped to the "pub" table in the database, managing attributes such as
 * pub name, country, and year of foundation. The class implements a one-to-many relationship with the
 * Beer class, as each pub can produce multiple beers, but each beer is produced by only one pub.
 */


@Entity(name="Pub")
public class Pub {
    @Id
    @SequenceGenerator(name="pub_sequence", sequenceName = "pub_sequence", allocationSize = 1,initialValue = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "pub_sequence")
    @Column(name = "id_pub", updatable = false)
    private Long idPub;

    @Column(name = "name_pub", nullable = false)
    private String namePub;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "year_of_foundation", nullable = false)
    private Integer yearOfFoundation;

    // Many-to-one relationship between Pub and Beer entity,
    // each pub can make N beers but each beer can only be produced by one pub
    @OneToMany(mappedBy = "pub", cascade = CascadeType.REMOVE)
    private List<Beer> beers;

    // constructors

    public Pub(String name, String address, Integer yearOfFoundation) {
        this.namePub = name;
        this.country = address;
        this.yearOfFoundation = yearOfFoundation;
    }
    public Pub(Long idPub,String name, String address, Integer yearOfFoundation) {
        this.idPub = idPub;
        this.namePub = name;
        this.country = address;
        this.yearOfFoundation = yearOfFoundation;
    }

    public Pub(Long idPub) {
        this.idPub = idPub;
    }

    public Pub() {}

    // getter and setter

    public Long getIdPub() {
        return idPub;
    }

    public void setIdPub(Long id) {
        this.idPub = id;
    }

    public String getNamePub() {
        return namePub;
    }

    public void setNamePub(String name) {
        this.namePub = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
                "id=" + idPub +
                ", name='" + namePub + '\'' +
                ", address='" + country + '\'' +
                ", yearOfFoundation=" + yearOfFoundation +
                '}';
    }
}
