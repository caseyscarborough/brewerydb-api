package com.brewerydb.api.request.brewery.order;

import com.brewerydb.api.request.order.Order;

public enum BreweryOrder implements Order {

    NAME("name"),
    DESCRIPTION("description"),
    WEBSITE("website"),
    ESTABLISHED("established"),
    MAILING_LIST_URL("mailingListUrl"),
    IS_ORGANIC("isOrganic"),
    STATUS("status"),
    CREATE_DATE("createDate"),
    UPDATE_DATE("updateDate"),
    RANDOM("random");

    private final String name;

    public String getName() {
        return name;
    }

    BreweryOrder(String name) {
        this.name = name;
    }
}
