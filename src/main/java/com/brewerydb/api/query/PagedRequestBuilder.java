package com.brewerydb.api.query;

abstract class PagedRequestBuilder<T, B extends PagedRequestBuilder<T, B>> extends AbstractRequestBuilder<T> {

    public B withPage(Integer page) {
        params.put("p", page.toString());
        return (B) this;
    }
}
