package com.brewerydb.api.query;

public abstract class PagedQueryBuilder<T, B extends PagedQueryBuilder<T, B>> extends AbstractQueryBuilder<T> {

    public B withPage(Integer page) {
        params.put("p", page.toString());
        return (B) this;
    }
}
