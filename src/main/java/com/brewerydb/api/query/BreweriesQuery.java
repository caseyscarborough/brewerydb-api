package com.brewerydb.api.query;

import com.brewerydb.api.model.Status;

public class BreweriesQuery extends AbstractQuery {

    private BreweriesQuery() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends MultipleResultQueryBuilder<BreweriesQuery, Builder> {

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

        public BreweriesQuery build() {
            BreweriesQuery query = new BreweriesQuery();
            query.params = params;
            return query;
        }
    }
}
