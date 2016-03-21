package com.brewerydb.api.request.hop;

import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.PagedRequestBuilder;

import java.util.Map;

public class GetHopsRequest extends AbstractRequest {

    protected GetHopsRequest(Map<String, String> params) {
        super(params);
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PagedRequestBuilder<GetHopsRequest, Builder> {

        private Builder() {}

        @Override
        public GetHopsRequest build() {
            return new GetHopsRequest(params);
        }
    }
}
