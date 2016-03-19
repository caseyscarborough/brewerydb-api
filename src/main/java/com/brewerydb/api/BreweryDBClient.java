package com.brewerydb.api;

import com.brewerydb.api.config.Configuration;
import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.query.BeerQuery;
import com.brewerydb.api.query.BeersQuery;
import com.brewerydb.api.query.BreweryQuery;
import com.brewerydb.api.query.Query;
import com.brewerydb.api.result.BeerResult;
import com.brewerydb.api.result.BeersResult;
import com.brewerydb.api.result.BreweriesResult;
import com.brewerydb.api.query.BreweriesQuery;
import com.brewerydb.api.result.BreweryResult;
import com.brewerydb.api.result.FeaturedResult;
import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreweryDBClient.class);
    private static final Gson GSON = new Gson();

    private final String apiKey;

    public BreweryDBClient(String apiKey) {
        if (apiKey == null || apiKey.trim().equals("")) {
            throw new MissingApiKeyException("An API key is required to call the BreweryDB API. Obtain your key at http://www.brewerydb.com/developers.");
        }
        this.apiKey = apiKey;
    }

    public BeersResult getBeers(BeersQuery query) {
        return get(Configuration.BEERS_ENDPOINT, query, BeersResult.class);
    }

    public BeerResult getBeer(String id) {
        return getBeer(id, null);
    }

    public BeerResult getBeer(String id, BeerQuery beerQuery) {
        return get(Configuration.BEER_ENDPOINT + "/" + id, beerQuery, BeerResult.class);
    }

    public BreweriesResult getBreweries(BreweriesQuery query) {
        return get(Configuration.BREWERIES_ENDPOINT, query, BreweriesResult.class);
    }

    public BreweryResult getBrewery(String id) {
        return getBrewery(id, null);
    }

    public BreweryResult getBrewery(String id, BreweryQuery query) {
        return get(Configuration.BREWERY_ENDPOINT + "/" + id, query, BreweryResult.class);
    }

    public FeaturedResult getFeatured() {
        return get(Configuration.FEATURED_ENDPOINT, null, FeaturedResult.class);
    }

    private <T> T get(final String endpoint, final Query query, final Class<T> clazz) {
        LOGGER.debug("Performing GET request to endpoint " + endpoint);
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = client.prepareGet(endpoint);
        builder.addQueryParam("key", apiKey);
        if (query != null) {
            for (String key : query.getParams().keySet()) {
                String value = query.getParams().get(key);
                LOGGER.debug("Adding parameter: " + key + "=" + value);
                builder.addQueryParam(key, value);
            }
        }

        final long start = System.currentTimeMillis();
        Future<T> f = builder.execute(new AsyncCompletionHandler<T>() {
            @Override
            public T onCompleted(Response response) throws Exception {
                // TODO: Figure out a good way to handle rate-limiting...
                LOGGER.debug("Request time: {}", System.currentTimeMillis() - start);
                LOGGER.debug("Rate limit remaining: {}", response.getHeader("X-Ratelimit-Remaining"));
                return GSON.fromJson(response.getResponseBody(), clazz);
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
}
