package com.bicoccaprojects.beerconnect.entity;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("limited")
public class LimitedEdition extends Beer{
    String limitedEditionName;
    int productionYear;

    public LimitedEdition(String name_beer, String type, String aroma, Double alcohol, String color, String country, String ingredients, Float price, Integer quantityInStock, String limitedEditionName, int productionYear) {
        super(name_beer, type, aroma, alcohol, color, country, ingredients, price, quantityInStock);
        this.limitedEditionName = limitedEditionName;
        this.productionYear = productionYear;
    }

    public LimitedEdition() {
    }

    public String getLimitedEditionName() {
        return limitedEditionName;
    }

    public void setLimitedEditionName(String limitedEditionName) {
        this.limitedEditionName = limitedEditionName;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    @Override
    public String toString() {
        return "LimitedEdition{" +
                "limitedEditionName='" + limitedEditionName + '\'' +
                ", productionYear=" + productionYear +
                '}';
    }
}
