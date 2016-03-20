package com.brewerydb.api.request.beer;

public class BeerRequest {

    public static GetBeerRequest.Builder getBeer() {
        return GetBeerRequest.builder();
    }

    public static GetBeersRequest.Builder getBeers() {
        return GetBeersRequest.builder();
    }

    public static AddBeerRequest.Builder addBeer() {
        return AddBeerRequest.builder();
    }

    public static UpdateBeerRequest.Builder updateBeer() {
        return UpdateBeerRequest.builder();
    }
}
