package com.brewerydb.api.query;

public class FeaturesQuery extends AbstractQuery {

    private FeaturesQuery() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PagedQueryBuilder<FeaturesQuery, Builder> {

        private Builder() {}

        public Builder withYear(String year) {
            params.put("year", year);
            return this;
        }

        public Builder withWeek(Integer week) {
            params.put("week", week.toString());
            return this;
        }

        public Builder includeFutureListings() {
            params.put("ignoreFuture", N);
            return this;
        }

        public FeaturesQuery build() {
            FeaturesQuery query = new FeaturesQuery();
            query.params = params;
            return query;
        }
    }
}
