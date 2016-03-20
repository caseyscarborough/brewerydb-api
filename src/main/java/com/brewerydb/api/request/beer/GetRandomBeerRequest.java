package com.brewerydb.api.request.beer;

import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.AbstractRequestBuilder;

import java.util.Map;

public class GetRandomBeerRequest extends AbstractRequest {

    private GetRandomBeerRequest(Map<String, String> params) {
        super(params);
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractRequestBuilder<GetRandomBeerRequest> {

        private Builder() {}

        public Builder withAbv(String abv) {
            params.put("abv", abv);
            return this;
        }

        public Builder withIbu(String ibu) {
            params.put("ibu", ibu);
            return this;
        }

        public Builder withGlasswareId(Integer glasswareId) {
            params.put("glasswareId", glasswareId.toString());
            return this;
        }

        public Builder withSrmId(Integer srmId) {
            params.put("srmId", srmId.toString());
            return this;
        }

        public Builder withAvailableId(Integer availableId) {
            params.put("availableId", availableId.toString());
            return this;
        }

        public Builder withStyleId(Integer styleId) {
            params.put("styleId", styleId.toString());
            return this;
        }

        public Builder isOrganic(boolean isOrganic) {
            params.put("isOrganic", isOrganic ? Y : N);
            return this;
        }

        public Builder hasLabels(boolean hasLabels) {
            params.put("hasLabels", hasLabels ? Y : N);
            return this;
        }

        public Builder withYear(String year) {
            params.put("year", year);
            return this;
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

        @Override
        public GetRandomBeerRequest build() {
            return new GetRandomBeerRequest(params);
        }
    }
}
