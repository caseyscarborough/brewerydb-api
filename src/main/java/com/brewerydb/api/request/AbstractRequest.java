package com.brewerydb.api.request;

import java.util.HashMap;
import java.util.Map;

public class AbstractRequest implements ApiRequest {

    protected static final String Y = "Y";
    protected static final String N = "N";

    protected Map<String, String> params = new HashMap<String, String>();

    protected AbstractRequest(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
