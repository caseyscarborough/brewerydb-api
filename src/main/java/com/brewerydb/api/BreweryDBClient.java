package com.brewerydb.api;

import com.brewerydb.api.config.Configuration;
import com.brewerydb.api.exception.BreweryDBException;
import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.request.ApiRequest;
import com.brewerydb.api.request.beer.AddBeerRequest;
import com.brewerydb.api.request.beer.GetBeerRequest;
import com.brewerydb.api.request.beer.GetBeersRequest;
import com.brewerydb.api.request.beer.UpdateBeerRequest;
import com.brewerydb.api.request.brewery.GetBreweriesRequest;
import com.brewerydb.api.request.brewery.GetBreweryRequest;
import com.brewerydb.api.request.feature.GetFeaturesRequest;
import com.brewerydb.api.result.GetRandomBeerResult;
import com.brewerydb.api.result.Result;
import com.brewerydb.api.result.beer.AddBeerResult;
import com.brewerydb.api.result.beer.DeleteBeerResult;
import com.brewerydb.api.result.beer.GetBeerResult;
import com.brewerydb.api.result.beer.GetBeersResult;
import com.brewerydb.api.result.beer.UpdateBeerResult;
import com.brewerydb.api.result.brewery.GetBreweriesResult;
import com.brewerydb.api.result.brewery.GetBreweryResult;
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
    private static final String RATELIMIT_REMAINING_HEADER = "X-Ratelimit-Remaining";

    private final String apiKey;
    private final Gson gson = new Gson();

    public BreweryDBClient(String apiKey) {
        if (apiKey == null || apiKey.trim().equals("")) {
            throw new MissingApiKeyException("An API key is required to call the BreweryDB API. Obtain your key at http://www.brewerydb.com/developers.");
        }
        this.apiKey = apiKey;
    }

    public GetBeersResult getBeers(GetBeersRequest request) {
        return waitFor(getBeersAsync(request));
    }

    public Future<GetBeersResult> getBeersAsync(GetBeersRequest request) {
        return requestAsync(Configuration.BEERS_ENDPOINT, request, GetBeersResult.class, RequestType.GET);
    }

    public GetBeerResult getBeer(String id) {
        return waitFor(getBeerAsync(id));
    }

    public Future<GetBeerResult> getBeerAsync(String id) {
        return getBeerAsync(id, null);
    }

    public GetBeerResult getBeer(String id, GetBeerRequest request) {
        return waitFor(getBeerAsync(id, request));
    }

    public Future<GetBeerResult> getBeerAsync(String id, GetBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a beer.");
        }
        return requestAsync(Configuration.BEER_ENDPOINT + "/" + id, request, GetBeerResult.class, RequestType.GET);
    }

    public AddBeerResult addBeer(AddBeerRequest request) {
        return waitFor(addBeerAsync(request));
    }

    public Future<AddBeerResult> addBeerAsync(AddBeerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request parameter is required to add a beer.");
        }
        return requestAsync(Configuration.BEERS_ENDPOINT, request, AddBeerResult.class, RequestType.POST);
    }

    public UpdateBeerResult updateBeer(String id, UpdateBeerRequest request) {
        return waitFor(updateBeerAsync(id, request));
    }

    public Future<UpdateBeerResult> updateBeerAsync(String id, UpdateBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to update a beer.");
        }
        return requestAsync(Configuration.BEER_ENDPOINT + "/" + id, request, UpdateBeerResult.class, RequestType.PUT);
    }

    public DeleteBeerResult deleteBeer(String id) {
        return waitFor(deleteBeerAsync(id));
    }

    public Future<DeleteBeerResult> deleteBeerAsync(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to delete a beer.");
        }
        return requestAsync(Configuration.BEER_ENDPOINT + "/" + id, null, DeleteBeerResult.class, RequestType.DELETE);
    }

    public GetRandomBeerResult getRandomBeer() {
        return waitFor(getRandomBeerAsync());
    }

    public Future<GetRandomBeerResult> getRandomBeerAsync() {
        return requestAsync(Configuration.RANDOM_BEER_ENDPOINT, null, GetRandomBeerResult.class, RequestType.GET);
    }

    public GetBreweriesResult getBreweries(GetBreweriesRequest request) {
        return waitFor(getBreweriesAsync(request));
    }

    public Future<GetBreweriesResult> getBreweriesAsync(GetBreweriesRequest query) {
        return requestAsync(Configuration.BREWERIES_ENDPOINT, query, GetBreweriesResult.class, RequestType.GET);
    }

    public GetBreweryResult getBrewery(String id) {
        return waitFor(getBreweryAsync(id));
    }

    public Future<GetBreweryResult> getBreweryAsync(String id) {
        return getBreweryAsync(id, null);
    }

    public GetBreweryResult getBrewery(String id, GetBreweryRequest request) {
        return waitFor(getBreweryAsync(id, request));
    }

    public Future<GetBreweryResult> getBreweryAsync(String id, GetBreweryRequest query) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a brewery.");
        }
        return requestAsync(Configuration.BREWERY_ENDPOINT + "/" + id, query, GetBreweryResult.class, RequestType.GET);
    }

    public FeaturedResult getFeatured() {
        return waitFor(getFeaturedAsync());
    }

    public Future<FeaturedResult> getFeaturedAsync() {
        return requestAsync(Configuration.FEATURED_ENDPOINT, null, FeaturedResult.class, RequestType.GET);
    }

    public FeaturesResult getFeatures() {
        return waitFor(getFeaturesAsync());
    }

    public Future<FeaturesResult> getFeaturesAsync() {
        return getFeaturesAsync(null);
    }

    public FeaturesResult getFeatures(GetFeaturesRequest query) {
        return waitFor(getFeaturesAsync(query));
    }

    public Future<FeaturesResult> getFeaturesAsync(GetFeaturesRequest query) {
        return requestAsync(Configuration.FEATURES_ENDPOINT, query, FeaturesResult.class, RequestType.GET);
    }

    private enum RequestType {
        GET, POST, PUT, DELETE;
    }

    private <T> T waitFor(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new BreweryDBException(e.getMessage());
        } catch (ExecutionException e) {
            throw new BreweryDBException(e.getMessage());
        }
    }

    private <T extends Result> Future<T> requestAsync(final String endpoint, final ApiRequest request, final Class<T> clazz, final RequestType requestType) {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder;

        switch (requestType) {
            case GET:
                builder = client.prepareGet(endpoint);
                break;
            case POST:
                builder = client.preparePost(endpoint);
                break;
            case PUT:
                builder = client.preparePut(endpoint);
                break;
            case DELETE:
                builder = client.prepareDelete(endpoint);
                break;
            default:
                throw new IllegalStateException(requestType.name() + " has not been implemented");
        }

        addCommonParameters(builder);

        if (requestType == RequestType.GET) {
            if (request != null) {
                for (String key : request.getParams().keySet()) {
                    String value = request.getParams().get(key);
                    LOGGER.debug("Adding request parameter: {}={}", key, value);
                    builder.addQueryParam(key, value);
                }
            }
        } else if (requestType == RequestType.POST || requestType == RequestType.PUT) {
            if (request != null) {
                for (String key : request.getParams().keySet()) {
                    String value = request.getParams().get(key);
                    LOGGER.debug("Adding form parameter: {}={}", key, value);
                    builder.addFormParam(key, value);
                }
            }
        }

        LOGGER.debug("Performing {} request to endpoint {}", requestType.name(), endpoint);
        return performRequestAsync(client, builder.build(), clazz);
    }

    protected <T extends Result> Future<T> performRequestAsync(AsyncHttpClient client, Request request, final Class<T> clazz) {
        return client.executeRequest(request, new AsyncCompletionHandler<T>() {
            @Override
            public T onCompleted(Response response) throws Exception {
                LOGGER.debug("Rate limit remaining: {}" + response.getHeader(RATELIMIT_REMAINING_HEADER));

                T result = gson.fromJson(response.getContentType(), clazz);
                validateResult(result);
                return result;
            }
        });
    }

    private void addCommonParameters(AsyncHttpClient.BoundRequestBuilder builder) {
        builder
            .setFollowRedirects(true)
            .addQueryParam("key", apiKey)
            .addQueryParam("format", "json");
    }

    protected <T extends Result> void validateResult(T result) {
        if (!result.wasSuccessful()) {
            throw new BreweryDBException(result.getErrorMessage());
        }
    }
}
