package com.brewerydb.api.request;

public abstract class PagedRequestBuilder<T, B extends PagedRequestBuilder<T, B>> extends AbstractRequestBuilder<T> {

    protected PagedRequestBuilder() {}

    @SuppressWarnings("unchecked")
    public B withPage(Integer page) {
        params.put("p", page.toString());
        return (B) this;
    }
}
