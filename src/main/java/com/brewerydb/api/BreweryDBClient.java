package com.brewerydb.api;

import com.brewerydb.api.config.Configuration;
import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.request.ApiRequest;
import com.brewerydb.api.request.beer.AddBeerRequest;
import com.brewerydb.api.request.beer.GetBeerRequest;
import com.brewerydb.api.request.beer.GetBeersRequest;
import com.brewerydb.api.request.beer.UpdateBeerRequest;
import com.brewerydb.api.request.brewery.GetBreweriesRequest;
import com.brewerydb.api.request.brewery.GetBreweryRequest;
import com.brewerydb.api.request.feature.FeaturesRequest;
import com.brewerydb.api.result.beer.AddBeerResult;
import com.brewerydb.api.result.beer.BeerResult;
import com.brewerydb.api.result.beer.BeersResult;
import com.brewerydb.api.result.beer.DeleteBeerResult;
import com.brewerydb.api.result.beer.UpdateBeerResult;
import com.brewerydb.api.result.brewery.BreweriesResult;
import com.brewerydb.api.result.brewery.BreweryResult;
import com.brewerydb.api.result.feature.FeaturedResult;
import com.brewerydb.api.result.feature.FeaturesResult;
import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreweryDBClient.class);
    private static final Gson GSON = new Gson();
    private static final String RATELIMIT_REMAINING_HEADER = "X-Ratelimit-Remaining";

    private final String apiKey;

    public BreweryDBClient(String apiKey) {
        if (apiKey == null || apiKey.trim().equals("")) {
            throw new MissingApiKeyException("An API key is required to call the BreweryDB API. Obtain your key at http://www.brewerydb.com/developers.");
        }
        this.apiKey = apiKey;
    }

    public BeersResult getBeers(GetBeersRequest request) {
        return get(Configuration.BEERS_ENDPOINT, request, BeersResult.class);
    }

    public BeerResult getBeer(String id) {
        return getBeer(id, null);
    }

    public BeerResult getBeer(String id, GetBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a beer.");
        }
        return get(Configuration.BEER_ENDPOINT + "/" + id, request, BeerResult.class);
    }

    public AddBeerResult addBeer(AddBeerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request parameter is required to add a beer.");
        }
        return post(Configuration.BEERS_ENDPOINT, request, AddBeerResult.class);
    }

    public UpdateBeerResult updateBeer(String id, UpdateBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to update a beer.");
        }
        return put(Configuration.BEER_ENDPOINT + "/" + id, request, UpdateBeerResult.class);
    }

    public DeleteBeerResult deleteBeer(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to delete a beer.");
        }
        return delete(Configuration.BEER_ENDPOINT + "/" + id, DeleteBeerResult.class);
    }

    public BreweriesResult getBreweries(GetBreweriesRequest query) {
        return get(Configuration.BREWERIES_ENDPOINT, query, BreweriesResult.class);
    }

    public BreweryResult getBrewery(String id) {
        return getBrewery(id, null);
    }

    public BreweryResult getBrewery(String id, GetBreweryRequest query) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a brewery.");
        }
        return get(Configuration.BREWERY_ENDPOINT + "/" + id, query, BreweryResult.class);
    }

    public FeaturedResult getFeatured() {
        return get(Configuration.FEATURED_ENDPOINT, null, FeaturedResult.class);
    }

    public FeaturesResult getFeatures() {
        return getFeatures(null);
    }

    public FeaturesResult getFeatures(FeaturesRequest query) {
        return get(Configuration.FEATURES_ENDPOINT, query, FeaturesResult.class);
    }

    private <T> T delete(final String endpoint, final Class<T> clazz) {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = client.prepareDelete(endpoint);
        addCommonParameters(builder);
        String json = performRequest(client, builder.build());
        return GSON.fromJson(json, clazz);
    }

    private <T> T put(final String endpoint, final ApiRequest request, final Class<T> clazz) {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = client.preparePut(endpoint);
        LOGGER.debug("Performing PUT request to endpoint " + endpoint);
        String json = performPostOrPutRequest(client, builder, request);
        return GSON.fromJson(json, clazz);
    }

    private <T> T post(final String endpoint, final ApiRequest request, final Class<T> clazz) {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = client.preparePost(endpoint);
        LOGGER.debug("Performing POST request to endpoint " + endpoint);
        String json = performPostOrPutRequest(client, builder, request);
        return GSON.fromJson(json, clazz);
    }

    private <T> T get(final String endpoint, final ApiRequest request, final Class<T> clazz) {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = client.prepareGet(endpoint);
        addCommonParameters(builder);
        if (request != null) {
            for (String key : request.getParams().keySet()) {
                String value = request.getParams().get(key);
                LOGGER.debug("Adding request parameter: " + key + "=" + value);
                builder.addQueryParam(key, value);
            }
        }

        LOGGER.debug("Performing GET request to endpoint " + endpoint);
        String json = performRequest(client, builder.build());
        return GSON.fromJson(json, clazz);
    }

    private String performPostOrPutRequest(AsyncHttpClient client, AsyncHttpClient.BoundRequestBuilder builder, ApiRequest request) {
        addCommonParameters(builder);
        if (request != null) {
            for (String key : request.getParams().keySet()) {
                String value = request.getParams().get(key);
                LOGGER.debug("Adding form parameter: " + key + "=" + value);
                builder.addFormParam(key, value);
            }
        }

        return performRequest(client, builder.build());
    }

    protected String performRequest(AsyncHttpClient client, Request request) {
        final long start = System.currentTimeMillis();
        Future<String> f = client.executeRequest(request, new AsyncCompletionHandler<String>() {
            @Override
            public String onCompleted(Response response) throws Exception {
                // TODO: Figure out a good way to handle rate-limiting...
                LOGGER.debug("Request time: {}", System.currentTimeMillis() - start);
                LOGGER.debug("Rate limit remaining: {}", response.getHeader(RATELIMIT_REMAINING_HEADER));
                return response.getResponseBody();
            }
        });

        try {
            return f.get();
        } catch (InterruptedException e) {
            throw new RuntimeException("An error occurred retrieving results from API", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("An error occurred retrieving results from API", e);
        }
    }

    private void addCommonParameters(AsyncHttpClient.BoundRequestBuilder builder) {
        builder.setFollowRedirects(true).addQueryParam("key", apiKey).addQueryParam("format", "json");
    }
}
