package com.brewerydb.api.model;

import java.util.Date;

public class Feature {

    private Long id;
    private Integer week;
    private String year;
    private Date createDate;

    private Brewery brewery;
    private Beer beer;

    public Long getId() {
        return id;
    }

    public Integer getWeek() {
        return week;
    }

    public String getYear() {
        return year;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Brewery getBrewery() {
        return brewery;
    }

    public Beer getBeer() {
        return beer;
    }
}
