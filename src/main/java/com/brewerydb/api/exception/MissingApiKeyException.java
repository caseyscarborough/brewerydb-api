package com.brewerydb.api.exception;

public class MissingApiKeyException extends RuntimeException {

    public MissingApiKeyException(String message) {
        super(message);
    }
}
