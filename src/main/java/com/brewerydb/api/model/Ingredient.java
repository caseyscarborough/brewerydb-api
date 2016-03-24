package com.brewerydb.api.model;

import java.util.Date;

public class Ingredient {

    private Integer id;
    private String category;
    private String categoryDisplay;
    private String name;
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryDisplay() {
        return categoryDisplay;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
