package com.brewerydb.api.model;

import java.util.List;

public class Beer {

    private String id;
    private String name;
    private String nameDisplay;
    private String description;
    private String abv;
    private String isOrganic;
    private String foodPairings;
    private String status;
    private String statusDisplay;
    private String createDate;
    private String updateDate;

    private Available available;
    private BeerLabels labels;
    private Style style;
    private Glass glass;
    private List<Brewery> breweries;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameDisplay() {
        return nameDisplay;
    }

    public String getDescription() {
        return description;
    }

    public String getAbv() {
        return abv;
    }

    public String getIsOrganic() {
        return isOrganic;
    }

    public String getFoodPairings() {
        return foodPairings;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public Available getAvailable() {
        return available;
    }

    public BeerLabels getLabels() {
        return labels;
    }

    public Style getStyle() {
        return style;
    }

    public Glass getGlass() {
        return glass;
    }

    public List<Brewery> getBreweries() {
        return breweries;
    }
}
