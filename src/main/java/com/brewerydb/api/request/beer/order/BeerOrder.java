package com.brewerydb.api.request.beer.order;

import com.brewerydb.api.request.fields.BeerFields;
import com.brewerydb.api.request.order.Order;

public enum BeerOrder implements Order {

    NAME(BeerFields.NAME),
    DESCRIPTION(BeerFields.DESCRIPTION),
    ABV(BeerFields.ABV),
    IBU(BeerFields.IBU),
    GLASSWARE_ID(BeerFields.GLASSWARE_ID),
    SRM_ID(BeerFields.SRM_ID),
    AVAILABLE_ID(BeerFields.AVAILABLE_ID),
    STYLE_ID(BeerFields.STYLE_ID),
    STATUS(BeerFields.STATUS),
    IS_ORGANIC(BeerFields.IS_ORGANIC),
    CREATE_DATE(BeerFields.CREATE_DATE),
    UPDATE_DATE(BeerFields.UPDATE_DATE),
    RANDOM(BeerFields.RANDOM);

    private final String name;

    BeerOrder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
