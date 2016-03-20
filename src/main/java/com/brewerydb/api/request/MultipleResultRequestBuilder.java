package com.brewerydb.api.request;

import com.brewerydb.api.request.order.Order;
import com.brewerydb.api.request.sort.SortDirection;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class MultipleResultRequestBuilder<T, O extends Order, B extends MultipleResultRequestBuilder<T, O, B>> extends PagedRequestBuilder<T, B> {

    protected MultipleResultRequestBuilder() {}

    public B withName(String name) {
        params.put("name", name);
        return (B) this;
    }

    public B withIds(List<String> ids) {
        String idsString = ids.toString();
        String list = idsString.substring(1, idsString.length() - 1).replaceAll(", ", ",");
        params.put("ids", list);
        return (B) this;
    }


    public B withSort(SortDirection sort) {
        params.put("sort", sort.getName());
        return (B) this;
    }

    public B withOrder(O order) {
        params.put("order", order.getName());
        return (B) this;
    }

    public abstract T build();
}
