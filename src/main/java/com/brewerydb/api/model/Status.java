package com.brewerydb.api.model;

public enum Status {

    VERIFIED("verified"),
    NEW_VERIFIED("new_verified"),
    UPDATE_PENDING("update_pending"),
    DELETE_PENDING("delete_pending"),
    DELETED("deleted");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
