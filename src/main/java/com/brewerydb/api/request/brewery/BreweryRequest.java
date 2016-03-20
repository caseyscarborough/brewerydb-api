package com.brewerydb.api.request.brewery;

public class BreweryRequest {

    public static GetBreweriesRequest.Builder getBreweries() {
        return GetBreweriesRequest.builder();
    }

    public static GetBreweryRequest.Builder getBrewery() {
        return GetBreweryRequest.builder();
    }
}
