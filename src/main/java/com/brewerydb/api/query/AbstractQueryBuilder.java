package com.brewerydb.api.query;

import java.util.HashMap;
import java.util.Map;

abstract class AbstractQueryBuilder<T> implements QueryBuilder<T> {

    protected Map<String, String> params = new HashMap<String, String>();

}
