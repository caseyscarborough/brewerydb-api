package com.brewerydb.api.query;

import java.util.Map;

public class FeaturesRequest extends AbstractRequest {

    private FeaturesRequest(Map<String, String> params) {
        super(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PagedRequestBuilder<FeaturesRequest, Builder> {

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

        public FeaturesRequest build() {
            return new FeaturesRequest(params);
        }
    }
}
