package com.brewerydb.api.query;

public class BeerQuery extends AbstractQuery {

    private BeerQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final BeerQuery beerQuery;

        private Builder() {
            beerQuery = new BeerQuery();
        }

        public Builder withBreweries() {
            beerQuery.params.put("withBreweries", Y);
            return this;
        }

        public Builder withSocialAccounts() {
            beerQuery.params.put("withSocialAccounts", Y);
            return this;
        }

        public Builder withIngredients() {
            beerQuery.params.put("withIngredients", Y);
            return this;
        }

        public BeerQuery build() {
            return beerQuery;
        }
    }
}
