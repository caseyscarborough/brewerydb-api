package com.brewerydb.api.query;

import java.util.HashMap;
import java.util.Map;

public class AbstractQuery implements Query {

    protected Map<String, String> params = new HashMap<String, String>();

    public Map<String, String> getParams() {
        return params;
    }
}
