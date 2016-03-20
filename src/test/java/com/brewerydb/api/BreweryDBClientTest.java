package com.brewerydb.api;

import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.exception.MissingRequestParameterException;
import com.brewerydb.api.model.Status;
import com.brewerydb.api.request.AddBeerRequest;
import com.brewerydb.api.request.BeerRequest;
import com.brewerydb.api.request.BeersRequest;
import com.brewerydb.api.request.BreweriesRequest;
import com.brewerydb.api.request.FeaturesRequest;
import com.brewerydb.api.request.UpdateBeerRequest;
import com.brewerydb.api.result.AddBeerResult;
import com.brewerydb.api.result.BeerResult;
import com.brewerydb.api.result.BeersResult;
import com.brewerydb.api.result.BreweriesResult;
import com.brewerydb.api.result.BreweryResult;
import com.brewerydb.api.result.DeleteBeerResult;
import com.brewerydb.api.result.FeaturedResult;
import com.brewerydb.api.result.FeaturesResult;
import com.brewerydb.api.result.UpdateBeerResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        BeersRequest query = BeersRequest.builder().withName("Newcastle Brown Ale").build();
        BeersResult result = client.getBeers(query);

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().get(0).getName());
        assertEquals("English Brown", result.getData().get(0).getStyle().getShortName());
    }

    @Test
    public void testGetBeersWithMoreQueryParameters() throws Exception {
        BeersRequest query = BeersRequest.builder()
                .withName("Buzz Light")
                .withAbv("5.5")
                .withOrganic(true)
                .withStatus(Status.VERIFIED)
                .build();

        BeersResult beersResult = client.getBeers(query);
        assertTrue(beersResult.wasSuccessful());
        assertEquals(1, beersResult.getData().size());
        assertEquals("Buzz Light", beersResult.getData().get(0).getName());

    }

    @Test
    public void testGetBeerWithNullId() throws Exception {
        expectedException.expect(IllegalArgumentException.class);
        client.getBeer(null);
    }

    @Test
    public void testGetBeerSimple() throws Exception {
        BeerResult result = client.getBeer("7ET5OY");

        assertTrue(result.wasSuccessful());
        assertTrue(result.getMessage().contains("Request Successful"));
        assertEquals("Newcastle Brown Ale", result.getData().getName());
        assertEquals("English Brown", result.getData().getStyle().getShortName());
    }

    @Test
    public void testGetBeerWithValidId() throws Exception {
        BeerResult result = client.getBeer("7ET5OY", BeerRequest.builder().withBreweries().build());

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().getName());
        assertEquals("English Brown", result.getData().getStyle().getShortName());
    }

    @Test
    public void testGetBeerWithInvalidId() throws Exception {
        BeerResult result = client.getBeer("123", BeerRequest.builder().withBreweries().build());

        assertFalse(result.wasSuccessful());
        assertEquals("The endpoint you requested could not be found", result.getErrorMessage());
    }

    @Test
    public void testGetBreweries() throws Exception {
        BreweriesRequest query = BreweriesRequest.builder().withName("SweetWater Brewing Company").build();
        BreweriesResult result = client.getBreweries(query);

        assertTrue(result.wasSuccessful());
        assertEquals(1, result.getData().size());
        assertEquals("SweetWater Brewing Company", result.getData().get(0).getName());
    }

    @Test
    public void testGetBrewerySimple() throws Exception {
        BreweryResult result = client.getBrewery("TMc6H2");

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
        FeaturesRequest query = FeaturesRequest.builder().withYear("2015").withWeek(5).build();
        FeaturesResult result = client.getFeatures(query);

        assertTrue(result.wasSuccessful());
        assertEquals(2, result.getData().size());
        assertEquals("Seven Barrel Brewery", result.getData().get(1).getBrewery().getName());
    }

    @Test
    public void testAddBeerWithoutName() throws Exception {
        expectedException.expect(MissingRequestParameterException.class);
        AddBeerRequest.builder().build();
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
}