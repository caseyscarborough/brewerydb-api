package com.brewerydb.api.model;

import java.util.List;

public class Brewery {

    private String id;
    private String name;
    private String nameShortDisplay;
    private String website;
    private String established;
    private String isOrganic;
    private String status;
    private String statusDisplay;
    private String createDate;
    private String updateDate;
    private BreweryImages images;
    private List<Location> locations;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameShortDisplay() {
        return nameShortDisplay;
    }

    public String getWebsite() {
        return website;
    }

    public String getEstablished() {
        return established;
    }

    public String getIsOrganic() {
        return isOrganic;
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

    public BreweryImages getImages() {
        return images;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
