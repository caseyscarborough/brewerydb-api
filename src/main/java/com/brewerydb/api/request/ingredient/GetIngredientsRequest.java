package com.brewerydb.api.request.ingredient;

import com.brewerydb.api.request.AbstractRequest;
import com.brewerydb.api.request.PagedRequestBuilder;

import java.util.Map;

public class GetIngredientsRequest extends AbstractRequest {

    private GetIngredientsRequest(Map<String, String> params) {
        super(params);
    }

    static Builder builder() {
        return new Builder();
    }

    public static class Builder extends PagedRequestBuilder<GetIngredientsRequest, Builder> {

        private Builder() {}

        @Override
        public GetIngredientsRequest build() {
            return new GetIngredientsRequest(params);
        }
    }

}
