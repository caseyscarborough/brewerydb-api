package com.brewerydb.api.exception;

public class MissingRequestParameterException extends BreweryDBException {
    public MissingRequestParameterException(String field) {
        super("Missing invalid parameter '" + field + "' for request.");
    }
}
