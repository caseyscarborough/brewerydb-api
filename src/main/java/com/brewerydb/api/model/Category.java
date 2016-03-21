package com.brewerydb.api.model;

import java.util.Date;

public class Category {

    private Integer id;
    private String name;
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
