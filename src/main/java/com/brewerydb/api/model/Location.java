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

    public Location withId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location withName(String name) {
        this.name = name;
        return this;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Location withStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public String getLocality() {
        return locality;
    }

    public Location withLocality(String locality) {
        this.locality = locality;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Location withRegion(String region) {
        this.region = region;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Location withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public Location withWebsite(String website) {
        this.website = website;
        return this;
    }

    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public Location withHoursOfOperation(String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Location withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Location withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public Location withIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
        return this;
    }

    public String getIsPlanning() {
        return isPlanning;
    }

    public Location withIsPlanning(String isPlanning) {
        this.isPlanning = isPlanning;
        return this;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public Location withIsClosed(String isClosed) {
        this.isClosed = isClosed;
        return this;
    }

    public String getOpenToPublic() {
        return openToPublic;
    }

    public Location withOpenToPublic(String openToPublic) {
        this.openToPublic = openToPublic;
        return this;
    }

    public String getLocationType() {
        return locationType;
    }

    public Location withLocationType(String locationType) {
        this.locationType = locationType;
        return this;
    }

    public String getLocationTypeDisplay() {
        return locationTypeDisplay;
    }

    public Location withLocationTypeDisplay(String locationTypeDisplay) {
        this.locationTypeDisplay = locationTypeDisplay;
        return this;
    }

    public String getCountryIsoCode() {
        return countryIsoCode;
    }

    public Location withCountryIsoCode(String countryIsoCode) {
        this.countryIsoCode = countryIsoCode;
        return this;
    }

    public String getYearOpened() {
        return yearOpened;
    }

    public Location withYearOpened(String yearOpened) {
        this.yearOpened = yearOpened;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Location withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getStatusDisplay() {
        return statusDisplay;
    }

    public Location withStatusDisplay(String statusDisplay) {
        this.statusDisplay = statusDisplay;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Location withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public Location withUpdateDate(String updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Country getCountry() {
        return country;
    }

    public Location withCountry(Country country) {
        this.country = country;
        return this;
    }
}
