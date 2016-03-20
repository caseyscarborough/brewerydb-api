package com.brewerydb.api.exception;

public class MissingApiKeyException extends BreweryDBException {

    public MissingApiKeyException(String message) {
        super(message);
    }
}
