package com.brewerydb.api.request.feature;

import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.PagedRequestBuilder;

import java.util.Map;

public class GetFeaturesRequest extends AbstractRequest {

    private GetFeaturesRequest(Map<String, String> params) {
        super(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PagedRequestBuilder<GetFeaturesRequest, Builder> {

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

        public GetFeaturesRequest build() {
            return new GetFeaturesRequest(params);
        }
    }
}
