package com.brewerydb.api.model;

import java.util.Date;

public class Category {

    private Long id;
    private String name;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
