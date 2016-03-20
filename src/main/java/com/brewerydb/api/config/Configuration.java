package com.brewerydb.api.config;

/**
 * Items used for configuration of the client.
 */
public class Configuration {

    private static final String API_VERSION = "v2";
    private static final String API_ENDPOINT = "https://api.brewerydb.com/" + API_VERSION;

    public static final String BEERS_ENDPOINT = API_ENDPOINT + "/beers";
    public static final String BEER_ENDPOINT = API_ENDPOINT + "/beer";
    public static final String RANDOM_BEER_ENDPOINT = BEER_ENDPOINT + "/random";

    public static final String BREWERIES_ENDPOINT = API_ENDPOINT + "/breweries";
    public static final String BREWERY_ENDPOINT = API_ENDPOINT + "/brewery";

    public static final String FEATURED_ENDPOINT = API_ENDPOINT + "/featured";
    public static final String FEATURES_ENDPOINT = API_ENDPOINT + "/features/";

}
