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
        return request(Configuration.BEERS_ENDPOINT, request, GetBeersResult.class, RequestType.GET);
    }

    public GetBeerResult getBeer(String id) {
        return getBeer(id, null);
    }

    public GetBeerResult getBeer(String id, GetBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a beer.");
        }
        return request(Configuration.BEER_ENDPOINT + "/" + id, request, GetBeerResult.class, RequestType.GET);
    }

    public AddBeerResult addBeer(AddBeerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request parameter is required to add a beer.");
        }
        return request(Configuration.BEERS_ENDPOINT, request, AddBeerResult.class, RequestType.POST);
    }

    public UpdateBeerResult updateBeer(String id, UpdateBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to update a beer.");
        }
        return request(Configuration.BEER_ENDPOINT + "/" + id, request, UpdateBeerResult.class, RequestType.PUT);
    }

    public DeleteBeerResult deleteBeer(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to delete a beer.");
        }
        return request(Configuration.BEER_ENDPOINT + "/" + id, null, DeleteBeerResult.class, RequestType.DELETE);
    }

    public GetRandomBeerResult getRandomBeer() {
        return request(Configuration.RANDOM_BEER_ENDPOINT, null, GetRandomBeerResult.class, RequestType.GET);
    }

    public GetBreweriesResult getBreweries(GetBreweriesRequest query) {
        return request(Configuration.BREWERIES_ENDPOINT, query, GetBreweriesResult.class, RequestType.GET);
    }

    public GetBreweryResult getBrewery(String id) {
        return getBrewery(id, null);
    }

    public GetBreweryResult getBrewery(String id, GetBreweryRequest query) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a brewery.");
        }
        return request(Configuration.BREWERY_ENDPOINT + "/" + id, query, GetBreweryResult.class, RequestType.GET);
    }

    public FeaturedResult getFeatured() {
        return request(Configuration.FEATURED_ENDPOINT, null, FeaturedResult.class, RequestType.GET);
    }

    public FeaturesResult getFeatures() {
        return getFeatures(null);
    }

    public FeaturesResult getFeatures(GetFeaturesRequest query) {
        return request(Configuration.FEATURES_ENDPOINT, query, FeaturesResult.class, RequestType.GET);
    }

    private enum RequestType {
        GET, POST, PUT, DELETE;
    }

    private <T extends Result> T request(final String endpoint, final ApiRequest request, final Class<T> clazz, final RequestType requestType) {
        Future<T> f = requestAsync(endpoint, request, clazz, requestType);

        try {
            return f.get();
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

    protected <T extends Result> void validateResult(Result<T> result) {
        if (!result.wasSuccessful()) {
            throw new BreweryDBException(result.getErrorMessage());
        }
    }
}
