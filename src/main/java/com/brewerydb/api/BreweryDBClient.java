package com.brewerydb.api;

import com.brewerydb.api.config.Configuration;
import com.brewerydb.api.model.Beer;
import com.brewerydb.api.query.BeerQuery;
import com.brewerydb.api.query.BeersQuery;
import com.brewerydb.api.query.Query;
import com.brewerydb.api.result.BeerResult;
import com.brewerydb.api.result.BeersResult;
import com.brewerydb.api.result.Result;
import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreweryDBClient.class);
    private static final Gson GSON = new Gson();

    private final String apiKey;

    public BreweryDBClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public Result<List<Beer>> getBeers(BeersQuery query) {
        return get(Configuration.BEERS_ENDPOINT, query, BeersResult.class);
    }

    public Result<Beer> getBeer(String id) {
        return getBeer(id, null);
    }

    public Result<Beer> getBeer(String id, BeerQuery beerQuery) {
        return get(Configuration.BEER_ENDPOINT + "/" + id, beerQuery, BeerResult.class);
    }

    private <T> T get(final String endpoint, final Query query, final Class<T> clazz) {
        StringBuilder url = new StringBuilder(endpoint);
        url.append("?key=").append(apiKey);
        if (query != null) {
            url.append(query.getQueryString());
        }

        LOGGER.debug("Performing GET request to URL: " + url);
        final long start = System.currentTimeMillis();
        AsyncHttpClient client = new AsyncHttpClient();
        Future<T> f = client.prepareGet(url.toString()).execute(new AsyncCompletionHandler<T>() {
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
