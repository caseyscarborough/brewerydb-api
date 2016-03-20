package com.brewerydb.api.model;

import java.util.Date;
import java.util.List;

public class Beer {

    private String id;
    private String name;
    private String nameDisplay;
    private String description;
    private Double abv;
    private Boolean isOrganic;
    private String foodPairings;
    private Status status;
    private String statusDisplay;
    private Date createDate;
    private Date updateDate;

    private Available available;
    private BeerLabels labels;
    private Style style;
    private Glass glass;
    private List<Brewery> breweries;
    private SRM srm;

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

    public Double getAbv() {
        return abv;
    }

    public Boolean getIsOrganic() {
        return isOrganic;
    }

    public String getFoodPairings() {
        return foodPairings;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
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

    public SRM getSrm() {
        return srm;
    }
}
