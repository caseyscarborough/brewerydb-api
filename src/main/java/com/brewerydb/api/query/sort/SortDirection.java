package com.brewerydb.api.query.sort;

public enum SortDirection {

    ASCENDING("ASC"),
    DESCENDING("DESC");

    private final String name;

    SortDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
