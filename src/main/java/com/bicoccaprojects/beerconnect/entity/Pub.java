package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * La classe Pub è un'entità JPA mappata sulla tabella "pub" nel database, che gestisce attributi come
 * nome del pub, paese, e anno di fondazione. La classe implementa una relazione one-to-many con la
 * classe Beer, poiché ogni pub può produrre diverse birre, ma ogni birra è prodotta da un solo pub.
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
