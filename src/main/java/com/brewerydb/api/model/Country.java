package com.brewerydb.api.model;

public class Country {

    private String isoCode;
    private String name;
    private String displayName;
    private String isoThree;
    private Integer numberCode;
    private String createDate;

    public String getIsoCode() {
        return isoCode;
    }

    public Country withIsoCode(String isoCode) {
        this.isoCode = isoCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country withName(String name) {
        this.name = name;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Country withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getIsoThree() {
        return isoThree;
    }

    public Country withIsoThree(String isoThree) {
        this.isoThree = isoThree;
        return this;
    }

    public Integer getNumberCode() {
        return numberCode;
    }

    public Country withNumberCode(Integer numberCode) {
        this.numberCode = numberCode;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Country withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }
}
