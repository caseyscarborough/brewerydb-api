package com.brewerydb.api.model;

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
    private String isPrimary;
    private String isPlanning;
    private String isClosed;
    private String openToPublic;
    private String locationType;
    private String locationTypeDisplay;
    private String countryIsoCode;
    private String yearOpened;
    private String status;
    private String statusDisplay;
    private String createDate;
    private String updateDate;
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

    public String getIsPrimary() {
        return isPrimary;
    }

    public String getIsPlanning() {
        return isPlanning;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public String getOpenToPublic() {
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

    public Country getCountry() {
        return country;
    }
}
