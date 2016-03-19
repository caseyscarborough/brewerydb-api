package com.brewerydb.api.model;

public class Feature {

    private Long id;
    private Integer week;
    private Integer year;
    private String createDate;

    private Brewery brewery;
    private Beer beer;

    public Long getId() {
        return id;
    }

    public Feature withId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getWeek() {
        return week;
    }

    public Feature withWeek(Integer week) {
        this.week = week;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public Feature withYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }

    public Feature withCreateDate(String createDate) {
        this.createDate = createDate;
        return this;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public Feature withBrewery(Brewery brewery) {
        this.brewery = brewery;
        return this;
    }

    public Beer getBeer() {
        return beer;
    }

    public Feature withBeer(Beer beer) {
        this.beer = beer;
        return this;
    }
}
