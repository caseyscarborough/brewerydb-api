package com.brewerydb.api;

import com.brewerydb.api.config.Configuration;
import com.brewerydb.api.exception.BreweryDBException;
import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.json.GsonFactory;
import com.brewerydb.api.request.ApiRequest;
import com.brewerydb.api.request.beer.AddBeerRequest;
import com.brewerydb.api.request.beer.GetBeerRequest;
import com.brewerydb.api.request.beer.GetBeersRequest;
import com.brewerydb.api.request.beer.GetRandomBeerRequest;
import com.brewerydb.api.request.beer.UpdateBeerRequest;
import com.brewerydb.api.request.brewery.GetBreweriesRequest;
import com.brewerydb.api.request.brewery.GetBreweryRequest;
import com.brewerydb.api.request.feature.GetFeaturesRequest;
import com.brewerydb.api.request.hop.GetHopsRequest;
import com.brewerydb.api.request.ingredient.GetIngredientsRequest;
import com.brewerydb.api.result.hop.GetHopResult;
import com.brewerydb.api.result.hop.GetHopsResult;
import com.brewerydb.api.result.beer.GetRandomBeerResult;
import com.brewerydb.api.result.Result;
import com.brewerydb.api.result.beer.AddBeerResult;
import com.brewerydb.api.result.beer.DeleteBeerResult;
import com.brewerydb.api.result.beer.GetBeerResult;
import com.brewerydb.api.result.beer.GetBeersResult;
import com.brewerydb.api.result.beer.UpdateBeerResult;
import com.brewerydb.api.result.brewery.GetBreweriesResult;
import com.brewerydb.api.result.brewery.GetBreweryResult;
import com.brewerydb.api.result.category.GetCategoriesResult;
import com.brewerydb.api.result.category.GetCategoryResult;
import com.brewerydb.api.result.feature.FeaturedResult;
import com.brewerydb.api.result.feature.FeaturesResult;
import com.brewerydb.api.result.glass.GetGlassResult;
import com.brewerydb.api.result.glass.GetGlassesResult;
import com.brewerydb.api.result.ingredient.GetIngredientResult;
import com.brewerydb.api.result.ingredient.GetIngredientsResult;
import com.brewerydb.api.result.style.GetStyleResult;
import com.brewerydb.api.result.style.GetStylesResult;
import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * The client for interacting with the BreweryDB API.
 * <p>
 * You can create a new client using your API key:
 * <p>
 * <pre>
 * BreweryDBClient client = new BreweryDBClient("api-key");
 * </pre>
 * <p>
 * Each request has a {@link Result}, which matches the name of the request. The
 * requested content can be retrieved by calling the {@link Result#getData()} method:
 * <p>
 * <pre>
 * GetRandomBeerResult result = client.getRandomBeer();
 * Beer beer = result.getData();
 * </pre>
 * <p>
 * Most requests have filtering or information to send. These are implementations
 * of the {@link ApiRequest} class, and they use a builder format.
 * <p>
 * <pre>
 * GetBeersRequest request = BeerRequest.getBeers()
 *         .withName("Newcastle*")       // Return beers matching name
 *         .withBreweries()              // Include brewery information
 *         .withOrder(BeerOrder.NAME)    // Order by name
 *         .withSort(SortDirection.DESC) // Sort descending
 *         .build();
 *
 * GetBeersResult result = client.getBeers(request);
 * List&lt;Beer&gt; beers = result.getData();
 * </pre>
 * <p>
 * Each method also has an asynchronous version:
 * <p>
 * <pre>
 * Future&lt;GetRandomBeerResult&gt; future = client.getRandomBeerAsync();
 *
 * // do some other work
 *
 * GetRandomBeerResult result = future.get();
 * </pre>
 */
public class BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BreweryDBClient.class);
    private static final String RATELIMIT_REMAINING_HEADER = "X-Ratelimit-Remaining";

    private final String apiKey;
    private final Gson gson = new GsonFactory().getInstance();

    /**
     * Constructor for {@link BreweryDBClient}.
     *
     * @param apiKey The API key provided by BreweryDB.
     */
    public BreweryDBClient(String apiKey) {
        if (apiKey == null || apiKey.trim().equals("")) {
            throw new MissingApiKeyException("An API key is required to call the BreweryDB API. Obtain your key at http://www.brewerydb.com/developers.");
        }
        this.apiKey = apiKey;
    }

    /**
     * Returns a list of beers matching a criteria.
     *
     * @param request The request parameters for this call.
     * @return {@link GetBeersResult} - The {@link Result} containing a list of beers.
     */
    public GetBeersResult getBeers(GetBeersRequest request) {
        return waitFor(getBeersAsync(request));
    }

    /**
     * The asynchronous version of {@link #getBeers(GetBeersRequest)}.
     */
    public Future<GetBeersResult> getBeersAsync(GetBeersRequest request) {
        return requestAsync(RequestMethod.GET, Configuration.BEERS_ENDPOINT, request, GetBeersResult.class);
    }

    /**
     * Returns a single beer by its ID.
     *
     * @param id The ID of the beer to retrieve.
     * @return {@link GetBeerResult} - The {@link Result} containing the beer.
     */
    public GetBeerResult getBeer(String id) {
        return waitFor(getBeerAsync(id));
    }

    /**
     * The asynchronous version of {@link #getBeer(String)}.
     */
    public Future<GetBeerResult> getBeerAsync(String id) {
        return getBeerAsync(id, null);
    }

    /**
     * Returns a single beer by its ID and request parameters.
     *
     * @param id      The ID of the beer to retrieve.
     * @param request The request parameters for this call.
     * @return {@link GetBeerResult} - The {@link Result} containing the beer.
     */
    public GetBeerResult getBeer(String id, GetBeerRequest request) {
        return waitFor(getBeerAsync(id, request));
    }

    /**
     * The asynchronous version of {@link #getBeer(String, GetBeerRequest)}.
     */
    public Future<GetBeerResult> getBeerAsync(String id, GetBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a beer.");
        }
        return requestAsync(RequestMethod.GET, Configuration.BEER_ENDPOINT + "/" + id, request, GetBeerResult.class);
    }

    /**
     * Requests a new beer to be created in the API.
     *
     * @param request The request parameters for this call.
     * @return {@link AddBeerResult} - The {@link Result} containing the message response from the API.
     */
    public AddBeerResult addBeer(AddBeerRequest request) {
        return waitFor(addBeerAsync(request));
    }

    /**
     * The asynchronous version of {@link #addBeer(AddBeerRequest)}.
     */
    public Future<AddBeerResult> addBeerAsync(AddBeerRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request parameter is required to add a beer.");
        }
        return requestAsync(RequestMethod.POST, Configuration.BEERS_ENDPOINT, request, AddBeerResult.class);
    }

    /**
     * Requests that a beer's properties be updated in the API.
     *
     * @param id      The ID of the beer to update.
     * @param request The request parameters for this call.
     * @return {@link UpdateBeerResult} - The {@link Result} containing the message response from the API.
     */
    public UpdateBeerResult updateBeer(String id, UpdateBeerRequest request) {
        return waitFor(updateBeerAsync(id, request));
    }

    /**
     * The asynchronous version of {@link #updateBeer(String, UpdateBeerRequest)}.
     */
    public Future<UpdateBeerResult> updateBeerAsync(String id, UpdateBeerRequest request) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to update a beer.");
        }
        return requestAsync(RequestMethod.PUT, Configuration.BEER_ENDPOINT + "/" + id, request, UpdateBeerResult.class);
    }

    /**
     * Requests that a beer be deleted from the API.
     *
     * @param id The ID of the beer to request deletion for.
     * @return {@link DeleteBeerResult} - The {@link Result} containing the message response from the API.
     */
    public DeleteBeerResult deleteBeer(String id) {
        return waitFor(deleteBeerAsync(id));
    }

    /**
     * The asynchronous version of {@link ##deleteBeer(String)}.
     */
    public Future<DeleteBeerResult> deleteBeerAsync(String id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to delete a beer.");
        }
        return requestAsync(RequestMethod.DELETE, Configuration.BEER_ENDPOINT + "/" + id, null, DeleteBeerResult.class);
    }

    /**
     * Returns a random beer.
     *
     * @return {@link GetRandomBeerResult} - The {@link Result} containing a random beer.
     */
    public GetRandomBeerResult getRandomBeer() {
        return waitFor(getRandomBeerAsync());
    }

    /**
     * The asynchronous version of {@link #getRandomBeer()}.
     */
    public Future<GetRandomBeerResult> getRandomBeerAsync() {
        return getRandomBeerAsync(null);
    }

    /**
     * Returns a random beer matching a criteria.
     *
     * @param request The request criteria.
     * @return {@link GetRandomBeerResult} - The {@link Result} containing a random beer.
     */
    public GetRandomBeerResult getRandomBeer(GetRandomBeerRequest request) {
        return waitFor(getRandomBeerAsync(request));
    }

    /**
     * The asynchronous version of {@link #getRandomBeer(GetRandomBeerRequest)}.
     */
    public Future<GetRandomBeerResult> getRandomBeerAsync(GetRandomBeerRequest request) {
        return requestAsync(RequestMethod.GET, Configuration.RANDOM_BEER_ENDPOINT, request, GetRandomBeerResult.class);
    }

    /**
     * Returns a list of breweries.
     *
     * @param request The request parameters for this call.
     * @return {@link GetBreweriesResult} - The {@link Result} containing the list of breweries.
     */
    public GetBreweriesResult getBreweries(GetBreweriesRequest request) {
        return waitFor(getBreweriesAsync(request));
    }

    /**
     * The asynchronous version of {@link #getBreweries(GetBreweriesRequest)}.
     */
    public Future<GetBreweriesResult> getBreweriesAsync(GetBreweriesRequest query) {
        return requestAsync(RequestMethod.GET, Configuration.BREWERIES_ENDPOINT, query, GetBreweriesResult.class);
    }

    /**
     * Returns a single brewery by ID.
     *
     * @param id The ID of the brewery to retrieve.
     * @return {@link GetBreweryResult} - The {@link Result} containing the single brewery.
     */
    public GetBreweryResult getBrewery(String id) {
        return waitFor(getBreweryAsync(id));
    }

    /**
     * The asynchronous version of {@link #getBrewery(String)}.
     */
    public Future<GetBreweryResult> getBreweryAsync(String id) {
        return getBreweryAsync(id, null);
    }

    /**
     * Returns a brewery based on ID and other parameters.
     *
     * @param id      The ID of the brewery.
     * @param request The request parameters.
     * @return {@link GetBreweryRequest} - The {@link Result} containing the single brewery.
     */
    public GetBreweryResult getBrewery(String id, GetBreweryRequest request) {
        return waitFor(getBreweryAsync(id, request));
    }

    /**
     * The asynchronous version of {@link #getBrewery(String, GetBreweryRequest)}.
     */
    public Future<GetBreweryResult> getBreweryAsync(String id, GetBreweryRequest query) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a brewery.");
        }
        return requestAsync(RequestMethod.GET, Configuration.BREWERY_ENDPOINT + "/" + id, query, GetBreweryResult.class);
    }

    /**
     * Returns the current featured beer and brewery.
     *
     * @return {@link FeaturedResult} - The {@link Result} containing the featured brewery and beer.
     */
    public FeaturedResult getFeatured() {
        return waitFor(getFeaturedAsync());
    }

    /**
     * The asynchronous version of {@link #getFeatured()}.
     */
    public Future<FeaturedResult> getFeaturedAsync() {
        return requestAsync(RequestMethod.GET, Configuration.FEATURED_ENDPOINT, null, FeaturedResult.class);
    }

    /**
     * Returns a list of all features.
     *
     * @return {@link FeaturesResult} - The {@link Result} containing all features.
     */
    public FeaturesResult getFeatures() {
        return waitFor(getFeaturesAsync());
    }

    /**
     * The asynchronous version of {@link #getFeatures()}.
     */
    public Future<FeaturesResult> getFeaturesAsync() {
        return getFeaturesAsync(null);
    }

    /**
     * Returns a list of all features with filtering.
     *
     * @param query The parameters to filter by.
     * @return {@link FeaturesResult} - The {@link Result} containing all matching features.
     */
    public FeaturesResult getFeatures(GetFeaturesRequest query) {
        return waitFor(getFeaturesAsync(query));
    }

    /**
     * The asynchronous version of {@link #getFeatures(GetFeaturesRequest)}.
     */
    public Future<FeaturesResult> getFeaturesAsync(GetFeaturesRequest query) {
        return requestAsync(RequestMethod.GET, Configuration.FEATURES_ENDPOINT, query, FeaturesResult.class);
    }

    /**
     * Returns a list of all {@link com.brewerydb.api.model.Category Categories}.
     *
     * @return {@link GetCategoriesResult} - The {@link Result} containing all categories.
     */
    public GetCategoriesResult getCategories() {
        return waitFor(getCategoriesAsync());
    }

    /**
     * The asynchronous version of {@link #getCategories()}.
     */
    public Future<GetCategoriesResult> getCategoriesAsync() {
        return requestAsync(RequestMethod.GET, Configuration.CATEGORIES_ENDPOINT, null, GetCategoriesResult.class);
    }

    /**
     * Returns a {@link com.brewerydb.api.model.Category} by ID.
     *
     * @param id The ID of the {@link com.brewerydb.api.model.Category} to retrieve.
     * @return {@link GetCategoryResult} - The {@link Result} containing the requested category.
     */
    public GetCategoryResult getCategory(Integer id) {
        return waitFor(getCategoryAsync(id));
    }

    /**
     * The asynchronous version of {@link #getCategory(Integer)}.
     */
    public Future<GetCategoryResult> getCategoryAsync(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a category.");
        }
        return requestAsync(RequestMethod.GET, Configuration.CATEGORY_ENDPOINT + "/" + id, null, GetCategoryResult.class);
    }

    /**
     * Returns a list of {@link com.brewerydb.api.model.Style Styles}.
     *
     * @return {@link GetStylesResult} - The {@link Result} containing all styles.
     */
    public GetStylesResult getStyles() {
        return waitFor(getStylesAsync());
    }

    /**
     * The asynchronous version of {@link #getStyles()}.
     */
    public Future<GetStylesResult> getStylesAsync() {
        return requestAsync(RequestMethod.GET, Configuration.STYLES_ENDPOINT, null, GetStylesResult.class);
    }

    /**
     * Returns a {@link com.brewerydb.api.model.Style} by ID.
     *
     * @param id The ID of the {@link com.brewerydb.api.model.Style} to retrieve.
     * @return {@link GetStyleResult} - The {@link Result} containing the requested style.
     */
    public GetStyleResult getStyle(Integer id) {
        return waitFor(getStyleAsync(id));
    }

    /**
     * The asynchronous version of {@link #getStyle(Integer)}.
     */
    public Future<GetStyleResult> getStyleAsync(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a style.");
        }
        return requestAsync(RequestMethod.GET, Configuration.STYLE_ENDPOINT + "/" + id, null, GetStyleResult.class);
    }

    /**
     * Returns a list of {@link com.brewerydb.api.model.Glass Glasses}.
     */
    public GetGlassesResult getGlasses() {
        return waitFor(getGlassesAsync());
    }

    /**
     * The asynchronous version of {@link #getGlasses()}.
     */
    public Future<GetGlassesResult> getGlassesAsync() {
        return requestAsync(RequestMethod.GET, Configuration.GLASSES_ENDPOINT, null, GetGlassesResult.class);
    }

    /**
     * Returns a glass by ID.
     * @param id The ID of the glass to retrieve.
     */
    public GetGlassResult getGlass(Integer id) {
        return waitFor(getGlassAsync(id));
    }

    /**
     * The asynchronous version of {@link #getGlass(Integer)}.
     */
    public Future<GetGlassResult> getGlassAsync(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a glass.");
        }
        return requestAsync(RequestMethod.GET, Configuration.GLASS_ENDPOINT + "/" + id, null, GetGlassResult.class);
    }

    /**
     * Returns a list of {@link com.brewerydb.api.model.Hop Hops}.
     */
    public GetHopsResult getHops() {
        return waitFor(getHopsAsync());
    }

    /**
     * The asynchronous version of {@link #getHops()}.
     */
    public Future<GetHopsResult> getHopsAsync() {
        return getHopsAsync(null);
    }

    /**
     * Returns a list of {@link com.brewerydb.api.model.Hop Hops} by page.
     * @param request The filter for this request.
     */
    public GetHopsResult getHops(GetHopsRequest request) {
        return waitFor(getHopsAsync(request));
    }

    /**
     * The asynchronous version of {@link #getHop(Integer)}.
     */
    public Future<GetHopsResult> getHopsAsync(GetHopsRequest request) {
        return requestAsync(RequestMethod.GET, Configuration.HOPS_ENDPOINT, request, GetHopsResult.class);
    }

    /**
     * Returns a {@link com.brewerydb.api.model.Hop} by ID.
     * @param id The ID of the hop to return.
     */
    public GetHopResult getHop(Integer id) {
        return waitFor(getHopAsync(id));
    }

    /**
     * The asynchronous version of {@link #getHop(Integer)}.
     */
    public Future<GetHopResult> getHopAsync(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve a hop.");
        }
        return requestAsync(RequestMethod.GET, Configuration.HOP_ENDPOINT + "/" + id, null, GetHopResult.class);
    }

    /**
     * Returns a list of {@link com.brewerydb.api.model.Ingredient Ingredients} by page.
     * @param request The filter for this request.
     * @return The {@link Result} containing the list of ingredients.
     */
    public GetIngredientsResult getIngredients(GetIngredientsRequest request) {
        return waitFor(getIngredientsAsync(request));
    }

    /**
     * The asynchronous version of {@link #getIngredient(Integer)}.
     */
    public Future<GetIngredientsResult> getIngredientsAsync(GetIngredientsRequest request) {
        return requestAsync(RequestMethod.GET, Configuration.INGREDIENTS_ENDPOINT, request, GetIngredientsResult.class);
    }

    /**
     * Retrieves an {@link com.brewerydb.api.model.Ingredient} by its ID.
     * @param id The ID of the ingredient to retrieve.
     * @return The {@link Result} with the ingredient.
     */
    public GetIngredientResult getIngredient(Integer id) {
        return waitFor(getIngredientAsync(id));
    }

    /**
     * The asynchronous version of {@link #getIngredient(Integer)}.
     */
    public Future<GetIngredientResult> getIngredientAsync(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID parameter is required to retrieve an ingredient.");
        }
        return requestAsync(RequestMethod.GET, Configuration.INGREDIENT_ENDPOINT + "/" + id, null, GetIngredientResult.class);
    }

    private enum RequestMethod {
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

    private <T extends Result> Future<T> requestAsync(final RequestMethod method, final String endpoint, final ApiRequest request, final Class<T> clazz) {
        AsyncHttpClient client = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder;

        switch (method) {
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
                throw new IllegalStateException(method.name() + " has not been implemented");
        }

        builder
            .setFollowRedirects(true)
            .addQueryParam("key", apiKey)
            .addQueryParam("format", "json");

        if (method == RequestMethod.GET) {
            if (request != null) {
                for (String key : request.getParams().keySet()) {
                    String value = request.getParams().get(key);
                    LOGGER.debug("Adding request parameter: {}={}", key, value);
                    builder.addQueryParam(key, value);
                }
            }
        } else if (method == RequestMethod.POST || method == RequestMethod.PUT) {
            if (request != null) {
                for (String key : request.getParams().keySet()) {
                    String value = request.getParams().get(key);
                    LOGGER.debug("Adding form parameter: {}={}", key, value);
                    builder.addFormParam(key, value);
                }
            }
        }

        LOGGER.debug("Performing {} request to endpoint {}", method.name(), endpoint);
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

    protected <T extends Result> void validateResult(T result) {
        if (!result.wasSuccessful()) {
            throw new BreweryDBException(result.getErrorMessage());
        }
    }
}
