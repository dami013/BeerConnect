package com.bicoccaprojects.beerconnect.entity;


import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "Beer") // rappresenta entita' nel db
@Table(
        name = "beer",
        uniqueConstraints = {
                @UniqueConstraint(name = "beer_name_unique", columnNames = "name_beer")
        }
)
public class Beer {
    @Id
    @SequenceGenerator(name="beer_sequence", sequenceName = "beer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "beer_sequence") // per avere ID che parte da 1 e incrementa di 1 per ogni entità nella tabella
    @Column(name = "id_beer", updatable = false)
    private Long id_beer;

    @Column(name = "name_beer", nullable = false, columnDefinition = "TEXT", unique = true)
    private String name_beer;

    @Column(name = "type", nullable = false, columnDefinition = "TEXT")
    private String type;

    @Column(name = "aroma", nullable = false, columnDefinition = "TEXT")
    private String aroma;

    @Column(name = "alchol", nullable = false, columnDefinition = "FLOAT")
    private Double alcohol;

    @Column(name = "color", nullable = false, columnDefinition = "TEXT")
    private String color;

    @Column(name = "country", nullable = false, columnDefinition = "TEXT")
    private String country;

    @Column(name = "ingredients", nullable = false, columnDefinition = "TEXT")
    private String ingredients;

    @Column(name = "price", nullable = false, columnDefinition = "FLOAT")
    private Float price;

    @Column(name = "quantity_in_stock", nullable = false, columnDefinition = "INTEGER")
    private Integer quantityInStock;

    // un birrificio può produrre tante birre ma una birra è prodotta da un solo birrificio
    // quando si crea una birra va assegnata a un birrificio
    @ManyToOne
    @JoinColumn(name = "id_pub") // id del pub
    private Pub pub;

    @ManyToMany(mappedBy = "beers")
    private List<Client> clients;

    public Beer(String name_beer, String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock) {
        this.name_beer = name_beer;
        this.type = type;
        this.aroma = aroma;
        this.alcohol = alcohol;
        this.color = color;
        this.country = country;
        this.ingredients = ingredients;
        this.price = price;
        this.quantityInStock = quantityInStock;
        // this.pub = pub;
    }

    public Beer() {

    }

    public Long getId_beer() {
        return id_beer;
    }

    public void setId_beer(Long id) {
        this.id_beer = id;
    }

    public String getName_beer() {
        return name_beer;
    }

    public void setName_beer(String name) {
        this.name_beer = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public Double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(Double alcohol) {
        this.alcohol = alcohol;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id_beer +
                ", name='" + name_beer + '\'' +
                ", type='" + type + '\'' +
                ", aroma='" + aroma + '\'' +
                ", alcohol=" + alcohol +
                ", color='" + color + '\'' +
                ", country='" + country + '\'' +
                ", ingredients=" + ingredients +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
