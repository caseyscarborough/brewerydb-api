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

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIsoThree() {
        return isoThree;
    }

    public Integer getNumberCode() {
        return numberCode;
    }

    public String getCreateDate() {
        return createDate;
    }
}
