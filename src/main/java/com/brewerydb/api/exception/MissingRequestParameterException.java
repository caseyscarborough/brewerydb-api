package com.brewerydb.api.exception;

public class MissingRequestParameterException extends RuntimeException {
    public MissingRequestParameterException(String field) {
        super("Missing invalid parameter '" + field + "' for request.");
    }
}
