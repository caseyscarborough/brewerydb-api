package com.brewerydb.api.model;

public class Style {

    private Long ind;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getInd() {
        return ind;
    }

    public void setInd(Long ind) {
        this.ind = ind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIbuMin() {
        return ibuMin;
    }

    public void setIbuMin(String ibuMin) {
        this.ibuMin = ibuMin;
    }

    public String getIbuMax() {
        return ibuMax;
    }

    public void setIbuMax(String ibuMax) {
        this.ibuMax = ibuMax;
    }

    public String getAbvMin() {
        return abvMin;
    }

    public void setAbvMin(String abvMin) {
        this.abvMin = abvMin;
    }

    public String getAbvMax() {
        return abvMax;
    }

    public void setAbvMax(String abvMax) {
        this.abvMax = abvMax;
    }

    public String getSrmMin() {
        return srmMin;
    }

    public void setSrmMin(String srmMin) {
        this.srmMin = srmMin;
    }

    public String getSrmMax() {
        return srmMax;
    }

    public void setSrmMax(String srmMax) {
        this.srmMax = srmMax;
    }

    public String getOgMin() {
        return ogMin;
    }

    public void setOgMin(String ogMin) {
        this.ogMin = ogMin;
    }

    public String getFgMin() {
        return fgMin;
    }

    public void setFgMin(String fgMin) {
        this.fgMin = fgMin;
    }

    public String getFgMax() {
        return fgMax;
    }

    public void setFgMax(String fgMax) {
        this.fgMax = fgMax;
    }
}
