package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue("limited")
@PrimaryKeyJoinColumn(name = "id_beer") // Indicates the foreign key relationship
public class LimitedEdition extends Beer{
    @Column(name = "original_beer", columnDefinition = "TEXT")
    private String originalBeer;

    @Column(name = "production_year", columnDefinition = "INTEGER")
    private Integer productionYear;

    public LimitedEdition(String nameBeer, Pub idPub, String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock, String originalBeer, Integer productionYear) {
        super(nameBeer, idPub, type, aroma, alcohol, color, country, ingredients, price, quantityInStock);
        this.originalBeer = originalBeer;
        this.productionYear = productionYear;
    }

    public LimitedEdition() {
    }

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
                "originalBeer='" + originalBeer + '\'' +
                ", productionYear=" + productionYear +
                '}';
    }
}
