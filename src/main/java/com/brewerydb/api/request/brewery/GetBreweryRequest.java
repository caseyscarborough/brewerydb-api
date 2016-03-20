package com.brewerydb.api.request.brewery;

import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.AbstractRequestBuilder;

import java.util.Map;

public class GetBreweryRequest extends AbstractRequest {

    private GetBreweryRequest(Map<String, String> params) {
        super(params);
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractRequestBuilder<GetBreweryRequest> {

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

        public GetBreweryRequest build() {
            return new GetBreweryRequest(params);
        }
    }
}
