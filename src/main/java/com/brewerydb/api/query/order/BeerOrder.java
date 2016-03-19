package com.brewerydb.api.query.order;

import com.brewerydb.api.query.params.BeerParams;

public enum BeerOrder {

    NAME(BeerParams.NAME),
    DESCRIPTION("description"),
    ABV(BeerParams.ABV),
    IBU(BeerParams.IBU),
    GLASSWARE_ID(BeerParams.GLASSWARE_ID),
    SRM_ID(BeerParams.SRM_ID),
    AVAILABLE_ID(BeerParams.AVAILABLE_ID),
    STYLE_ID(BeerParams.STYLE_ID),
    STATUS(BeerParams.STATUS),
    IS_ORGANIC(BeerParams.IS_ORGANIC),
    CREATE_DATE("createDate"),
    UPDATE_DATE("updateDate"),
    RANDOM("random");

    private final String name;

    BeerOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
