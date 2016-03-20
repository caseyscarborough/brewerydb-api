package com.brewerydb.api.result;

import java.util.List;

public class AbstractListResult<T> extends AbstractResult<List<T>> {

    private Integer currentPage;
    private Integer numberOfPages;
    private Integer totalResults;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }
}
