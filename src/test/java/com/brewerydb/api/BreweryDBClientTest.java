package com.brewerydb.api;

import com.brewerydb.api.exception.BreweryDBException;
import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.exception.MissingRequestParameterException;
import com.brewerydb.api.model.Beer;
import com.brewerydb.api.model.Brewery;
import com.brewerydb.api.model.Category;
import com.brewerydb.api.model.Feature;
import com.brewerydb.api.model.Glass;
import com.brewerydb.api.model.Hop;
import com.brewerydb.api.model.Status;
import com.brewerydb.api.model.Style;
import com.brewerydb.api.request.beer.AddBeerRequest;
import com.brewerydb.api.request.beer.BeerRequest;
import com.brewerydb.api.request.beer.GetBeersRequest;
import com.brewerydb.api.request.beer.GetRandomBeerRequest;
import com.brewerydb.api.request.beer.UpdateBeerRequest;
import com.brewerydb.api.request.brewery.BreweryRequest;
import com.brewerydb.api.request.brewery.GetBreweriesRequest;
import com.brewerydb.api.request.brewery.GetBreweryRequest;
import com.brewerydb.api.request.feature.FeatureRequest;
import com.brewerydb.api.request.feature.GetFeaturesRequest;
import com.brewerydb.api.request.hop.GetHopsRequest;
import com.brewerydb.api.request.hop.HopRequest;
import com.brewerydb.api.request.ingredient.GetIngredientsRequest;
import com.brewerydb.api.request.ingredient.IngredientRequest;
import com.brewerydb.api.result.hop.GetHopResult;
import com.brewerydb.api.result.hop.GetHopsResult;
import com.brewerydb.api.result.GetRandomBeerResult;
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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BreweryDBClientTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BreweryDBClient client;

    @Before
    public void setUp() throws Exception {
        client = new TestBreweryDBClient(TestProperties.getApiKey());
    }

    @Test
    public void testNullApiKeyNotAllowed() throws Exception {
        expectedException.expect(MissingApiKeyException.class);
        client = new TestBreweryDBClient(null);
    }

    @Test
    public void testBlankApiKeyNotAllowed() throws Exception {
        expectedException.expect(MissingApiKeyException.class);
        client = new TestBreweryDBClient("  ");
    }

    @Test
    public void testGetBeers() throws Exception {
        GetBeersRequest query = BeerRequest.getBeers().withName("Newcastle Brown Ale").build();
        GetBeersResult result = client.getBeers(query);

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().get(0).getName());
        assertEquals("English Brown", result.getData().get(0).getStyle().getShortName());
    }

    @Test
    public void testGetBeersWithMoreQueryParameters() throws Exception {
        GetBeersRequest query = BeerRequest.getBeers()
            .withName("Buzz Light")
            .withAbv("5.5")
            .withOrganic(true)
            .withStatus(Status.VERIFIED)
            .build();

        GetBeersResult result = client.getBeers(query);
        assertTrue(result.wasSuccessful());
        assertEquals(1, result.getData().size());

        Beer beer = result.getData().get(0);
        assertEquals("Buzz Light", beer.getName());
        assertEquals((Double) 5.5, beer.getAbv());
        assertEquals(Status.VERIFIED, beer.getStatus());
    }

    @Test
    public void testGetBeerWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getBeer(null);
    }

    @Test
    public void testGetBeerSimple() throws Exception {
        GetBeerResult result = client.getBeer("7ET5OY");

        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("Request Successful"));
        assertEquals("Newcastle Brown Ale", result.getData().getName());
        assertEquals("English Brown", result.getData().getStyle().getShortName());
        assertEquals(1325576625000L, result.getData().getCreateDate().getTime());
    }

    @Test
    public void testGetBeerWithValidId() throws Exception {
        GetBeerResult result = client.getBeer("7ET5OY", BeerRequest.getBeer().withBreweries().build());

        assertTrue(result.wasSuccessful());

        Beer beer = result.getData();
        assertEquals("Newcastle Brown Ale", beer.getName());
        assertEquals("English Brown", beer.getStyle().getShortName());
        assertEquals(1325576625000L, beer.getCreateDate().getTime());
        assertEquals(Status.VERIFIED, beer.getStatus());
    }

    @Test
    public void testGetBeerWithInvalidId() throws Exception {
        expectedException.expect(BreweryDBException.class);
        expectedException.expectMessage("The endpoint you requested could not be found");
        client.getBeer("123", BeerRequest.getBeer().withBreweries().build());
    }

    @Test
    public void testGetBreweries() throws Exception {
        GetBreweriesRequest query = BreweryRequest.getBreweries().withName("SweetWater Brewing Company").build();
        GetBreweriesResult result = client.getBreweries(query);

        assertTrue(result.wasSuccessful());
        assertEquals(1, result.getData().size());

        Brewery brewery = result.getData().get(0);
        assertEquals("SweetWater Brewing Company", brewery.getName());
        assertEquals(1325576529000L, brewery.getCreateDate().getTime());
        assertEquals(Status.VERIFIED, brewery.getStatus());
    }

    @Test
    public void testGetBreweryWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getBrewery(null);
    }

    @Test
    public void testGetBrewerySimple() throws Exception {
        GetBreweryResult result = client.getBrewery("TMc6H2");
        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("Request Successful"));

        Brewery brewery = result.getData();
        assertEquals("SweetWater Brewing Company", brewery.getName());
        assertEquals(1325576529000L, brewery.getCreateDate().getTime());
        assertEquals(Status.VERIFIED, brewery.getStatus());
    }

    @Test
    public void testGetBreweryWithCriteria() throws Exception {
        GetBreweryRequest request = BreweryRequest.getBrewery()
            .withLocations()
            .build();

        GetBreweryResult result = client.getBrewery("TMc6H2", request);
        Brewery brewery = result.getData();
        assertEquals("SweetWater Brewing Company", brewery.getName());
        assertEquals(1325576529000L, brewery.getCreateDate().getTime());
        assertEquals(Status.VERIFIED, brewery.getStatus());
    }

    @Test
    public void testFeatured() throws Exception {
        FeaturedResult result = client.getFeatured();

        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("Request Successful"));
        assertNotNull(result.getData().getBeer());
        assertNotNull(result.getData().getBrewery());
    }

    @Test
    public void testGetFeatures() throws Exception {
        FeaturesResult result = client.getFeatures();

        List<Feature> features = result.getData();
        assertTrue(features.size() > 0);
    }

    @Test
    public void testGetFeaturesWithCriteria() throws Exception {
        GetFeaturesRequest query = FeatureRequest.getFeatures()
            .withYear("2015")
            .withWeek(5)
            .build();

        FeaturesResult result = client.getFeatures(query);
        assertTrue(result.wasSuccessful());

        List<Feature> features = result.getData();
        assertEquals(2, features.size());

        Feature feature = features.get(1);
        assertEquals("Seven Barrel Brewery", feature.getBrewery().getName());

        Double longitude = -72.3200515;
        assertEquals(longitude, feature.getBrewery().getLocations().get(0).getLongitude());
        assertEquals("2015", feature.getYear());
        assertEquals(5, (int) feature.getWeek());
        assertEquals("FrostNipper", feature.getBeer().getName());
    }

    @Test
    public void testAddBeerWithoutName() throws Exception {
        expectedException.expect(MissingRequestParameterException.class);
        BeerRequest.addBeer().build();
    }

    @Test
    public void testAddBeerWithoutStyleId() throws Exception {
        expectedException.expect(MissingRequestParameterException.class);
        AddBeerRequest.builder().withName("Test Name").build();
    }

    @Test
    public void testAddBeerWithNullRequest() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.addBeer(null);
    }

    @Test
    public void testAddBeer() throws Exception {
        AddBeerRequest request = AddBeerRequest.builder()
            .withName("Test Beer")
            .withAbv("12.1")
            .withDescription("A test beer that should be deleted")
            .withYear("2016")
            .withStyleId(127)
            .build();

        AddBeerResult result = client.addBeer(request);
        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("The beer has been successfully added and is waiting to be approved by our administrators."));
    }

    @Test
    public void testUpdateBeer() throws Exception {
        UpdateBeerRequest request = UpdateBeerRequest.builder()
            .withName("Newcastle Coffee Porter")
            .withStyleId(123)
            .build();

        UpdateBeerResult result = client.updateBeer("jUYZMk", request);
        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("The request to update this beer has been received and is waiting to be approved by our administrators."));
    }

    @Test
    public void testUpdateBeerWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.updateBeer(null, null);
    }

    @Test
    public void testDeleteBeer() throws Exception {
        DeleteBeerResult result = client.deleteBeer("jUYZMk");
        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("The request to remove this beer has been received and is waiting to be approved by our administrators."));
    }

    @Test
    public void testDeleteBeerWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.deleteBeer(null);
    }

    @Test
    public void testGetRandomBeer() throws Exception {
        GetRandomBeerResult result = client.getRandomBeer();
        assertTrue(result.wasSuccessful());

        Beer beer = result.getData();
        assertNotNull(beer);
    }

    @Test
    public void testGetRandomBeerMatchingCriteria() throws Exception {
        GetRandomBeerRequest request = BeerRequest.getRandomBeer()
            .withAbv("8,10")
            .withStyleId(121)
            .withGlasswareId(5)
            .build();

        GetRandomBeerResult result = client.getRandomBeer(request);
        assertTrue(result.wasSuccessful());

        Beer beer = result.getData();
        assertNotNull(beer);
        assertTrue(beer.getAbv() > 8 && beer.getAbv() < 10);
        assertEquals(121, (long) beer.getStyle().getId());
        assertEquals(5L, (long) beer.getGlass().getId());
    }

    @Test
    public void testGetCategoriesWithNoCriteria() throws Exception {
        GetCategoriesResult result = client.getCategories();
        List<Category> categories = result.getData();
        assertTrue(result.wasSuccessful());
        assertTrue(categories.size() > 0);
    }

    @Test
    public void testGetCategory() throws Exception {
        GetCategoryResult result = client.getCategory(5);

        Category category = result.getData();
        assertEquals(5, (int) category.getId());
        assertEquals("Belgian And French Origin Ales", category.getName());
    }

    @Test
    public void testGetCategoryWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getCategory(null);
    }

    @Test
    public void testGetStyles() throws Exception {
        GetStylesResult result = client.getStyles();
        List<Style> styles = result.getData();
        assertTrue(styles.size() > 0);
    }

    @Test
    public void testGetStyle() throws Exception {
        GetStyleResult result = client.getStyle(1);
        Style style = result.getData();
        assertEquals(1, (int)style.getId());
        assertEquals("Classic English-Style Pale Ale", style.getName());
    }

    @Test
    public void testGetStyleWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getStyle(null);
    }

    @Test
    public void testGetGlasses() throws Exception {
        GetGlassesResult result = client.getGlasses();
        List<Glass> glasses = result.getData();
        assertTrue(glasses.size() > 0);
    }

    @Test
    public void testGetGlass() throws Exception {
        GetGlassResult result = client.getGlass(5);
        Glass glass = result.getData();
        assertEquals(5, (int) glass.getId());
        assertEquals("Pint", glass.getName());
    }

    @Test
    public void testGetGlassWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getGlass(null);
    }

    @Test
    public void testGetHops() throws Exception {
        GetHopsResult result = client.getHops();
        List<Hop> hops = result.getData();
        assertTrue(hops.size() > 0);
    }

    @Test
    public void testGetHopsWithPage() throws Exception {
        GetHopsRequest request = HopRequest.getHops().withPage(2).build();
        GetHopsResult result = client.getHops(request);
        List<Hop> hops = result.getData();
        assertTrue(hops.size() > 0);
    }

    @Test
    public void testGetHop() throws Exception {
        GetHopResult result = client.getHop(84);
        Hop hop = result.getData();
        assertEquals(84, (int) hop.getId());
        assertEquals("Mount Hood", hop.getName());
    }

    @Test
    public void testGetHopWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getHop(null);
    }

    @Test
    public void testGetHopWithNotFoundId() throws Exception {
        expectedException.expect(BreweryDBException.class);
        expectedException.expectMessage("The object you requested was not found");
        client.getHop(12323);
    }

    @Test
    public void testGetIngredientsWithNoRequest() throws Exception {
        GetIngredientsResult result = client.getIngredients(null);
        assertTrue(result.getData().size() > 0);
    }

    @Test
    public void testGetIngredients() throws Exception {
        GetIngredientsRequest request = IngredientRequest.getIngredients().withPage(2).build();
        GetIngredientsResult result = client.getIngredients(request);
        assertTrue(result.getData().size() > 0);
    }

    @Test
    public void testGetIngredient() throws Exception {
        GetIngredientResult result = client.getIngredient(1);
        assertEquals("Admiral", result.getData().getName());
        assertEquals((Integer)1, result.getData().getId());
    }

    @Test
    public void testGetIngredientWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getIngredient(null);
    }
}