package com.brewerydb.api.model;

public enum Status {

    VERIFIED("verified"),
    NEW_VERIFIED("new_verified"),
    UPDATE_PENDING("update_pending"),
    DELETE_PENDING("delete_pending"),
    DELETED("deleted"),
    UNKNOWN("unknown");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Status findByName(String name) {
        for (Status value : values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
