package com.brewerydb.api.query;

import java.util.Map;

public class BreweryRequest extends AbstractRequest {

    private BreweryRequest(Map<String, String> params) {
        super(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends AbstractRequestBuilder<BreweryRequest> {

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

        public BreweryRequest build() {
            return new BreweryRequest(params);
        }
    }
}
