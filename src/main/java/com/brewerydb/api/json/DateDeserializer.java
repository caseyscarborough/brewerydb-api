package com.brewerydb.api.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer implements JsonDeserializer<Date> {

    private static final String API_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return new SimpleDateFormat(API_DATE_FORMAT).parse(json.getAsString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
