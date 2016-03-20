package com.brewerydb.api.result;

public interface Result<T> {

    Integer getCurrentPage();

    Integer getNumberOfPages();

    Integer getTotalResults();

    T getData();

    boolean wasSuccessful();

    String getStatus();

    String getMessage();

    String getErrorMessage();
}
