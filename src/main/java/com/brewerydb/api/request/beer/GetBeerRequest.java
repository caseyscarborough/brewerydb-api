package com.brewerydb.api.request.beer;

import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.AbstractRequestBuilder;

import java.util.HashMap;
import java.util.Map;

public class GetBeerRequest extends AbstractRequest {

    private GetBeerRequest(Map<String, String> params) {
        super(params);
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractRequestBuilder<GetBeerRequest> {

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

        public GetBeerRequest build() {
            return new GetBeerRequest(params);
        }
    }
}
