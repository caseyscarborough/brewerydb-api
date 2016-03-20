package com.brewerydb.api.model;

import java.util.Date;

public class Location {

    private String id;
    private String name;
    private String streetAddress;
    private String locality;
    private String region;
    private String phone;
    private String website;
    private String hoursOfOperation;
    private Double latitude;
    private Double longitude;
    private Boolean isPrimary;
    private Boolean isPlanning;
    private Boolean isClosed;
    private Boolean openToPublic;
    private String locationType;
    private String locationTypeDisplay;
    private String countryIsoCode;
    private String yearOpened;
    private Status status;
    private String statusDisplay;
    private Date createDate;
    private Date updateDate;
    private Country country;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Boolean isPrimary() {
        return isPrimary;
    }

    public Boolean isPlanning() {
        return isPlanning;
    }

    public Boolean isClosed() {
        return isClosed;
    }

    public Boolean isOpenToPublic() {
        return openToPublic;
    }

    public String getLocationType() {
        return locationType;
    }

    public String getLocationTypeDisplay() {
        return locationTypeDisplay;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public String getYearOpened() {
        return yearOpened;
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

    public Country getCountry() {
        return country;
    }
}
