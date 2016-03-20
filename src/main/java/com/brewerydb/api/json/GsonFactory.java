package com.brewerydb.api.json;

import com.brewerydb.api.model.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

public class GsonFactory {

    public Gson getInstance() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Status.class, new StatusDeserializer());
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        builder.registerTypeAdapter(Boolean.class, new BooleanDeserializer());
        return builder.create();
    }
}
