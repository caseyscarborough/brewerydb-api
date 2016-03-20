package com.brewerydb.api;

import com.brewerydb.api.exception.BreweryDBException;
import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.exception.MissingRequestParameterException;
import com.brewerydb.api.model.Beer;
import com.brewerydb.api.model.Brewery;
import com.brewerydb.api.model.Status;
import com.brewerydb.api.request.ApiRequest;
import com.brewerydb.api.request.beer.AddBeerRequest;
import com.brewerydb.api.request.beer.BeerRequest;
import com.brewerydb.api.request.beer.GetBeerRequest;
import com.brewerydb.api.request.beer.GetBeersRequest;
import com.brewerydb.api.request.beer.GetRandomBeerRequest;
import com.brewerydb.api.request.beer.UpdateBeerRequest;
import com.brewerydb.api.request.beer.order.BeerOrder;
import com.brewerydb.api.request.brewery.BreweryRequest;
import com.brewerydb.api.request.brewery.GetBreweriesRequest;
import com.brewerydb.api.request.brewery.GetBreweryRequest;
import com.brewerydb.api.request.brewery.order.BreweryOrder;
import com.brewerydb.api.request.feature.FeatureRequest;
import com.brewerydb.api.request.feature.GetFeaturesRequest;
import com.brewerydb.api.request.sort.SortDirection;
import com.brewerydb.api.result.GetRandomBeerResult;
import com.brewerydb.api.result.beer.AddBeerResult;
import com.brewerydb.api.result.beer.DeleteBeerResult;
import com.brewerydb.api.result.beer.GetBeerResult;
import com.brewerydb.api.result.beer.GetBeersResult;
import com.brewerydb.api.result.beer.UpdateBeerResult;
import com.brewerydb.api.result.brewery.GetBreweriesResult;
import com.brewerydb.api.result.brewery.GetBreweryResult;
import com.brewerydb.api.result.feature.FeaturedResult;
import com.brewerydb.api.result.feature.FeaturesResult;
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
        assertEquals("Buzz Light", result.getData().get(0).getName());

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
    }

    @Test
    public void testGetBeerWithValidId() throws Exception {
        GetBeerResult result = client.getBeer("7ET5OY", BeerRequest.getBeer().withBreweries().build());

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().getName());
        assertEquals("English Brown", result.getData().getStyle().getShortName());
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
        assertEquals("SweetWater Brewing Company", result.getData().get(0).getName());
    }

    @Test
    public void testGetBrewerySimple() throws Exception {
        GetBreweryResult result = client.getBrewery("TMc6H2");

        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("Request Successful"));
        assertEquals("SweetWater Brewing Company", result.getData().getName());
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
        GetFeaturesRequest query = FeatureRequest.getFeatures().withYear("2015").withWeek(5).build();
        FeaturesResult result = client.getFeatures(query);

        assertTrue(result.wasSuccessful());
        assertEquals(2, result.getData().size());
        assertEquals("Seven Barrel Brewery", result.getData().get(1).getBrewery().getName());
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
    public void testDeleteBeer() throws Exception {
        DeleteBeerResult result = client.deleteBeer("jUYZMk");
        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("The request to remove this beer has been received and is waiting to be approved by our administrators."));
    }

    @Test
    public void testGetRandomBeer() throws Exception {
        GetRandomBeerResult result = client.getRandomBeer();

        assertTrue(result.wasSuccessful());
        assertNotNull(result.getData());
        assertTrue(result.getData() instanceof Beer);
    }

    @Test
    public void testGetRandomBeerMatchingCriteria() throws Exception {
        GetRandomBeerRequest request = BeerRequest.getRandomBeer().withAbv("8,10").withStyleId(121).build();
        GetRandomBeerResult result = client.getRandomBeer(request);

        assertTrue(result.wasSuccessful());
        assertNotNull(result.getData());
    }
}