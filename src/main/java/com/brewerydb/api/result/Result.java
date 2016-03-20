package com.brewerydb.api.result;

public interface Result<T> {

    T getData();

    boolean wasSuccessful();

    String getStatus();

    String getMessage();

    String getErrorMessage();
}
