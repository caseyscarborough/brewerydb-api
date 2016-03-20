package com.brewerydb.api.request;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRequestBuilder<T> implements ApiRequestBuilder<T> {

    protected Map<String, String> params = new HashMap<String, String>();

    protected AbstractRequestBuilder() {}

}
