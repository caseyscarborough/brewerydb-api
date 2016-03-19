package com.brewerydb.api.query;

public class BreweryQuery extends AbstractQuery {

    private BreweryQuery() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractQueryBuilder<BreweryQuery> {

        private Builder() {}

        public Builder withSocialAccounts() {
            params.put("withSocialAccounts", Y);
            return this;
        }

        public Builder withGuilds() {
            params.put("withGuilds", Y);
            return this;
        }

        public Builder withLocations() {
            params.put("withLocations", Y);
            return this;
        }

        public Builder withAlternateNames() {
            params.put("withAlternateNames", Y);
            return this;
        }

        public BreweryQuery build() {
            BreweryQuery query = new BreweryQuery();
            query.params = params;
            return query;
        }
    }
}
