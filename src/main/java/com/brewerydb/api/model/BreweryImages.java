package com.brewerydb.api.model;

public class BreweryImages {

    private String icon;
    private String medium;
    private String large;
    private String squareMedium;
    private String squareLarge;

    public String getIcon() {
        return icon;
    }

    public BreweryImages withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getMedium() {
        return medium;
    }

    public BreweryImages withMedium(String medium) {
        this.medium = medium;
        return this;
    }

    public String getLarge() {
        return large;
    }

    public BreweryImages withLarge(String large) {
        this.large = large;
        return this;
    }

    public String getSquareMedium() {
        return squareMedium;
    }

    public BreweryImages withSquareMedium(String squareMedium) {
        this.squareMedium = squareMedium;
        return this;
    }

    public String getSquareLarge() {
        return squareLarge;
    }

    public BreweryImages withSquareLarge(String squareLarge) {
        this.squareLarge = squareLarge;
        return this;
    }
}
