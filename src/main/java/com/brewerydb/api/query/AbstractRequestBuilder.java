package com.brewerydb.api.query;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractRequestBuilder<T> implements ApiRequestBuilder<T> {

    protected Map<String, String> params = new HashMap<String, String>();

}
