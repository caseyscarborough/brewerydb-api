package com.brewerydb.api.request;

import com.brewerydb.api.model.Status;
import com.brewerydb.api.request.order.BreweryOrder;

import java.util.Map;

public class BreweriesRequest extends AbstractRequest {

    private BreweriesRequest(Map<String, String> params) {
        super(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends MultipleResultRequestBuilder<BreweriesRequest, BreweryOrder, Builder> {

        private Builder() {
        }

        public Builder withEstablished(String year) {
            // TODO: Validate year
            params.put("established", year);
            return this;
        }

        public Builder withOrganic(boolean organic) {
            params.put("isOrganic", organic ? Y : N);
            return this;
        }

        public Builder withImages(boolean images) {
            params.put("hasImages", images ? Y : N);
            return this;
        }

        public Builder since(Long timestamp) {
            params.put("since", timestamp.toString());
            return this;
        }

        public Builder withStatus(Status status) {
            params.put("status", status.getName());
            return this;
        }

        public Builder withRandomCount(Integer randomCount) {
            params.put("randomCount", randomCount.toString());
            return this;
        }

        public BreweriesRequest build() {
            return new BreweriesRequest(params);
        }
    }
}
