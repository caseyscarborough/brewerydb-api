package com.brewerydb.api.query;

import java.util.HashMap;
import java.util.Map;

public class AbstractQuery implements Query {

    protected static final String Y = "Y";
    protected static final String N = "N";

    protected Map<String, String> params = new HashMap<String, String>();

    public Map<String, String> getParams() {
        return params;
    }
}
