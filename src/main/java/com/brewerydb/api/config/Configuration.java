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
    public static final String CATEGORIES_ENDPOINT = API_ENDPOINT + "/categories";
    public static final String CATEGORY_ENDPOINT = API_ENDPOINT + "/category";
    public static final String STYLES_ENDPOINT = API_ENDPOINT + "/styles";
    public static final String STYLE_ENDPOINT = API_ENDPOINT + "/style";
    public static final String GLASSES_ENDPOINT = API_ENDPOINT + "/glassware";
    public static final String GLASS_ENDPOINT = API_ENDPOINT + "/glass";
    public static final String HOPS_ENDPOINT = API_ENDPOINT + "/hops";
    public static final String HOP_ENDPOINT = API_ENDPOINT + "/hop";
    public static final String INGREDIENTS_ENDPOINT = API_ENDPOINT + "/ingredients";
    public static final String INGREDIENT_ENDPOINT = API_ENDPOINT + "/ingredient";
}
