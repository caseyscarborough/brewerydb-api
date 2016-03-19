package com.brewerydb.api;

import com.brewerydb.api.exception.MissingApiKeyException;
import com.brewerydb.api.model.Status;
import com.brewerydb.api.query.BeerQuery;
import com.brewerydb.api.query.BeersQuery;
import com.brewerydb.api.query.BreweriesQuery;
import com.brewerydb.api.result.BeerResult;
import com.brewerydb.api.result.BeersResult;
import com.brewerydb.api.result.BreweriesResult;
import com.brewerydb.api.result.BreweryResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BreweryDBClientTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private BreweryDBClient client;

    @Before
    public void setUp() throws Exception {
        client = new BreweryDBClient(TestProperties.getApiKey());
    }

    @Test
    public void testNullApiKeyNotAllowed() throws Exception {
        expectedException.expect(MissingApiKeyException.class);
        client = new BreweryDBClient(null);
    }

    @Test
    public void testBlankApiKeyNotAllowed() throws Exception {
        expectedException.expect(MissingApiKeyException.class);
        client = new BreweryDBClient("  ");
    }

    @Test
    public void testGetBeers() throws Exception {
        BeersQuery query = BeersQuery.builder().withName("Newcastle Brown Ale").build();
        BeersResult result = client.getBeers(query);

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().get(0).getName());
        assertEquals("English Brown", result.getData().get(0).getStyle().getShortName());
    }

    @Test
    public void testGetBeersWithMoreQueryParameters() throws Exception {
        BeersQuery query = BeersQuery.builder()
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
    public void testGetBeerSimple() throws Exception {
        BeerResult result = client.getBeer("7ET5OY");

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().getName());
        assertEquals("English Brown", result.getData().getStyle().getShortName());
    }

    @Test
    public void testGetBeerWithValidId() throws Exception {
        BeerResult result = client.getBeer("7ET5OY", BeerQuery.builder().withBreweries().build());

        assertTrue(result.wasSuccessful());
        assertEquals("Newcastle Brown Ale", result.getData().getName());
        assertEquals("English Brown", result.getData().getStyle().getShortName());
    }

    @Test
    public void testGetBeerWithInvalidId() throws Exception {
        BeerResult result = client.getBeer("123", BeerQuery.builder().withBreweries().build());

        assertFalse(result.wasSuccessful());
        assertEquals("The endpoint you requested could not be found", result.getErrorMessage());
    }

    @Test
    public void testGetBreweries() throws Exception {
        BreweriesQuery query = BreweriesQuery.builder().withName("SweetWater Brewing Company").build();
        BreweriesResult result = client.getBreweries(query);

        assertTrue(result.wasSuccessful());
        assertEquals(1, result.getData().size());
        assertEquals("SweetWater Brewing Company", result.getData().get(0).getName());
    }

    @Test
    public void testGetBrewerySimple() throws Exception {
        BreweryResult result = client.getBrewery("TMc6H2");

        assertTrue(result.wasSuccessful());
        assertEquals("SweetWater Brewing Company", result.getData().getName());
    }
}