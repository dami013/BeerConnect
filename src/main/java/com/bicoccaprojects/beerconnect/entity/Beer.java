package com.bicoccaprojects.beerconnect.entity;


import com.bicoccaprojects.beerconnect.entity.relational_entity.ClientReview;
import jakarta.persistence.*;

import java.util.List;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity(name = "Beer")
@Table(
        name = "beer",
        uniqueConstraints = {
                @UniqueConstraint(name = "beer_name_unique", columnNames = "name_beer") // name_beer must be unique for each Beer
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="beer_type")
@DiscriminatorValue("normal")
public class Beer {
    @Id
    @SequenceGenerator(name = "beer_sequence", sequenceName = "beer_sequence", allocationSize = 1, initialValue = 16)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beer_sequence")
    @Column(name = "id_beer", updatable = false)
    private Long idBeer;


    @Column(name = "name_beer", nullable = false, unique = true)
    private String nameBeer;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "aroma", nullable = false)
    private String aroma;

    @Column(name = "alcohol", nullable = false)
    private Double alcohol;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "ingredients", nullable = false)
    private String ingredients;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "quantity_in_stock", nullable = false)
    private Integer quantityInStock;

    // Add a discriminatory column to identify if the beer is normal or limited edition
    @Column(name = "beer_type", insertable = false, updatable = false)
    private String beerType;

    // Many-to-one relationship between Pub and Beer entity,
    // each pub can make N beers but each beer can only be produced by one pub
    @ManyToOne
    @JoinColumn(name = "id_pub")
    private Pub pub;

    // Many-to-Many relationship between Beer and Client,
    // Each beer can be reviewed by N customers and each customer can review N beers.
    // Here the OneToMany tag is used because there is an intermediate entity that contains 2 other attributes for this relationship
    @OneToMany(mappedBy = "idBeer", cascade = CascadeType.REMOVE)
    private List<ClientReview> clientReviews;

    // constructors
    public Beer(String nameBeer,String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock,Pub idPub) {
        this.nameBeer = nameBeer;
        this.type = type;
        this.aroma = aroma;
        this.alcohol = alcohol;
        this.color = color;
        this.country = country;
        this.ingredients = ingredients;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.pub = idPub;
    }

    public Beer(Long idBeer, String nameBeer, String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock, Pub pub) {
        this.idBeer = idBeer;
        this.nameBeer = nameBeer;
        this.type = type;
        this.aroma = aroma;
        this.alcohol = alcohol;
        this.color = color;
        this.country = country;
        this.ingredients = ingredients;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.pub = pub;
    }

    public Beer(Long idBeer) {
        this.idBeer = idBeer;
    }

    public Beer() {

    }

    // getter and setter

    public Long getIdBeer() {
        return idBeer;
    }

    public void setIdBeer(Long id) {
        this.idBeer = id;
    }

    public String getNameBeer() {
        return nameBeer;
    }

    public void setNameBeer(String name) {
        this.nameBeer = name;
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

    public String getBeerType() {
        return beerType;
    }

    public void setBeerType(String beerType) {
        this.beerType = beerType;
    }

    public Pub getPub() {
        return pub;
    }

    public void setPub(Pub pub) {
        this.pub = pub;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + idBeer +
                ", name='" + nameBeer + '\'' +
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
