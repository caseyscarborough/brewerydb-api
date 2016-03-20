package com.brewerydb.api.query;

import java.util.HashMap;
import java.util.Map;

public class BeerRequest extends AbstractRequest {

    private BeerRequest(Map<String, String> params) {
        super(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractRequestBuilder<BeerRequest> {

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

        public BeerRequest build() {
            return new BeerRequest(params);
        }
    }
}
