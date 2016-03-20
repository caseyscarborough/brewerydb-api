package com.brewerydb.api.request.sort;

public enum SortDirection {

    ASC("ASC"),
    DESC("DESC");

    private final String name;

    SortDirection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
