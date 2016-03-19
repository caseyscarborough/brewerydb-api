package com.brewerydb.api.query;

import java.util.HashMap;
import java.util.Map;

public class BeerQuery extends AbstractQuery {

    private BeerQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractQueryBuilder<BeerQuery> {

        private Map<String, String> params = new HashMap<String, String>();

        private Builder() {
        }

        public Builder withBreweries() {
            params.put("withBreweries", Y);
            return this;
        }

        public Builder withSocialAccounts() {
            params.put("withSocialAccounts", Y);
            return this;
        }

        public Builder withIngredients() {
            params.put("withIngredients", Y);
            return this;
        }

        public BeerQuery build() {
            BeerQuery beerQuery = new BeerQuery();
            beerQuery.params = params;
            return beerQuery;
        }
    }
}
