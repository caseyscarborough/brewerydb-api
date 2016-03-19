package com.brewerydb.api.query;

public class BeerQuery extends AbstractQuery {

    private BeerQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean withBreweries = false;
        private boolean withSocialAccounts = false;
        private boolean withIngredients = false;

        private Builder() {
        }

        public Builder withBreweries() {
            this.withBreweries = true;
            return this;
        }

        public Builder withSocialAccounts() {
            this.withSocialAccounts = true;
            return this;
        }

        public Builder withIngredients() {
            this.withIngredients = true;
            return this;
        }

        public BeerQuery build() {
            BeerQuery beerQuery = new BeerQuery();

            if (withBreweries) {
                beerQuery.params.put("withBreweries", "Y");
            }

            if (withIngredients) {
                beerQuery.params.put("withIngredients", "Y");
            }

            if (withSocialAccounts) {
                beerQuery.params.put("withSocialAccounts", "Y");
            }

            return beerQuery;
        }
    }
}
