package com.brewerydb.api.query;

public class BeerQuery implements Query {

    private String withBreweries;

    private BeerQuery() {}

    public String getQueryString() {
        return "&withBreweries=" + withBreweries;
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder {

        private boolean withBreweries = false;

        private Builder() {}

        public Builder withBreweries() {
            this.withBreweries = true;
            return this;
        }

        public BeerQuery build() {
            BeerQuery beerQuery = new BeerQuery();
            beerQuery.withBreweries = withBreweries ? "Y" : "N";
            return beerQuery;
        }
    }
}
