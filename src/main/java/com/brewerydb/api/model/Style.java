package com.brewerydb.api.model;

public class Style {

    private Long id;
    private String name;
    private String shortName;
    private String description;
    private String ibuMin;
    private String ibuMax;
    private String abvMin;
    private String abvMax;
    private String srmMin;
    private String srmMax;
    private String ogMin;
    private String fgMin;
    private String fgMax;
    private Category category;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public String getIbuMin() {
        return ibuMin;
    }

    public String getIbuMax() {
        return ibuMax;
    }

    public String getAbvMin() {
        return abvMin;
    }

    public String getAbvMax() {
        return abvMax;
    }

    public String getSrmMin() {
        return srmMin;
    }

    public String getSrmMax() {
        return srmMax;
    }

    public String getOgMin() {
        return ogMin;
    }

    public String getFgMin() {
        return fgMin;
    }

    public String getFgMax() {
        return fgMax;
    }

    public Category getCategory() {
        return category;
    }
}
