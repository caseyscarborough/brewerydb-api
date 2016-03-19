package com.brewerydb.api;

import com.brewerydb.api.model.Beer;
import com.brewerydb.api.query.BeersQuery;
import com.brewerydb.api.result.Result;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BreweryDBClientTest {

    private BreweryDBClient client;

    @Before
    public void setUp() throws Exception {
        client = new BreweryDBClient(TestProperties.getApiKey());
    }

    @Test
    public void getBeers() throws Exception {
        BeersQuery query = BeersQuery.builder().withName("Newcastle").build();
        Result<List<Beer>> result = client.getBeers(query);
        assertEquals("Newcastle", result.getData().get(0).getName());
        assertEquals("Coffee Beer", result.getData().get(0).getStyle().getShortName());
    }

    @Test
    public void getBeer() throws Exception {
        Result<Beer> result = client.getBeer("jUYZMk");
        assertEquals("Newcastle", result.getData().getName());
        assertEquals("Coffee Beer", result.getData().getStyle().getShortName());
    }
}