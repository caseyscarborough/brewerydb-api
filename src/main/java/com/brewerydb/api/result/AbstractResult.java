package com.brewerydb.api.result;

public abstract class AbstractResult<T> implements Result<T> {

    private static final String SUCCESS_STATUS = "success";

    private T data;
    private String status;
    private String message;
    private String errorMessage;

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean wasSuccessful() {
        return status.equals(SUCCESS_STATUS);
    }
}
