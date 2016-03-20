package com.brewerydb.api.request.brewery;

import com.brewerydb.api.model.Status;
import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.MultipleResultRequestBuilder;
import com.brewerydb.api.request.brewery.order.BreweryOrder;

import java.util.Map;

public class GetBreweriesRequest extends AbstractRequest {

    private GetBreweriesRequest(Map<String, String> params) {
        super(params);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends MultipleResultRequestBuilder<GetBreweriesRequest, BreweryOrder, Builder> {

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

        public GetBreweriesRequest build() {
            return new GetBreweriesRequest(params);
        }
    }
}
