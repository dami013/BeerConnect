package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

/**
 * The LimitedEdition class extends the Beer class and represents a limited edition beer with additional attributes
 * such as the name of the original beer and the production year. It is mapped to the same database table with a
 * discriminator value of "limited" and a shared primary key.
 */


@Entity
@DiscriminatorValue("limited")
@PrimaryKeyJoinColumn(name = "id_beer") // Indicates the foreign key relationship
public class LimitedEdition extends Beer{ // class that extend Beer, a LimitedEditon beer is a special beer crafted from a regular beer
    @Column(name = "original_beer")
    private String originalBeer;

    @Column(name = "production_year")
    private Integer productionYear;

    // constructors


    public LimitedEdition(String nameBeer, String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock, Pub idPub, String originalBeer, Integer productionYear) {
        super(nameBeer, type, aroma, alcohol, color, country, ingredients, price, quantityInStock, idPub);
        this.originalBeer = originalBeer;
        this.productionYear = productionYear;
    }

    public LimitedEdition(Long idBeer, String nameBeer, String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock, Pub pub, String originalBeer, Integer productionYear) {
        super(idBeer, nameBeer, type, aroma, alcohol, color, country, ingredients, price, quantityInStock, pub);
        this.originalBeer = originalBeer;
        this.productionYear = productionYear;
    }

    public LimitedEdition() {
    }

    // getter and setter

    public String getOriginalBeer() {
        return originalBeer;
    }

    public void setOriginalBeer(String limitedEditionName) {
        this.originalBeer = limitedEditionName;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    @Override
    public String toString() {
        return "LimitedEdition{" +
                "id="+this.getIdBeer()+'\''+
                ", originalBeer=" + originalBeer + '\'' +
                ", productionYear=" + productionYear +
                ", name='" + this.getNameBeer() + '\'' +
                ", type='" + this.getType() + '\'' +
                ", aroma='" + this.getAroma() + '\'' +
                ", alcohol=" + this.getAlcohol() +
                ", color='" + this.getColor() + '\'' +
                ", country='" + this.getCountry() + '\'' +
                ", ingredients=" + this.getIngredients() +
                ", price=" + this.getPrice() +
                ", quantityInStock=" + this.getQuantityInStock() +
                '}';
    }
}
