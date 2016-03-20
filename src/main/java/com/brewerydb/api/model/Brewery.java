package com.brewerydb.api.model;

import java.util.Date;
import java.util.List;

public class Brewery {

    private String id;
    private String name;
    private String nameShortDisplay;
    private String website;
    private String established;
    private Boolean isOrganic;
    private Status status;
    private String statusDisplay;
    private Date createDate;
    private Date updateDate;
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

    public Boolean getIsOrganic() {
        return isOrganic;
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

    public BreweryImages getImages() {
        return images;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
