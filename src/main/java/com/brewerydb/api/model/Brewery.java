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

    public Brewery withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Brewery withName(String name) {
        this.name = name;
        return this;
    }

    public String getNameShortDisplay() {
        return nameShortDisplay;
    }

    public Brewery withNameShortDisplay(String nameShortDisplay) {
        this.nameShortDisplay = nameShortDisplay;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Brewery withWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getEstablished() {
        return established;
    }

    public Brewery withEstablished(String established) {
        this.established = established;
        return this;
    }

    public String getIsOrganic() {
        return isOrganic;
    }

    public Brewery withIsOrganic(String isOrganic) {
        this.isOrganic = isOrganic;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Brewery withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public Brewery withStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Brewery withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public Brewery withUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public BreweryImages getImages() {
        return images;
    }

    public Brewery withImages(BreweryImages images) {
        this.images = images;
        return this;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public Brewery withLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }
}
