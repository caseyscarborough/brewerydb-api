package com.brewerydb.api.query;

import com.brewerydb.api.query.order.Order;
import com.brewerydb.api.query.sort.SortDirection;

import java.util.List;

@SuppressWarnings("unchecked")
abstract class MultipleResultQueryBuilder<T, O extends Order, B extends MultipleResultQueryBuilder<T, O, B>> extends AbstractQueryBuilder<T> {

    public B withName(String name) {
        params.put("name", name);
        return (B) this;
    }

    public B withPage(Integer page) {
        params.put("p", page.toString());
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
