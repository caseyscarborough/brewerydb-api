package com.brewerydb.api.query;

import java.util.List;

public class BeersQuery extends AbstractQuery {

    private BeersQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Integer pageNumber;
        private List<String> ids;
        private String name;

        private Builder() {}

        public Builder pageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public Builder withIds(List<String> ids) {
            this.ids = ids;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public BeersQuery build() {
            // TODO: Finish implementing beer query parameters
            BeersQuery beersQuery = new BeersQuery();
            if (name != null) {
                beersQuery.params.put("name", name);
            }
            return beersQuery;
        }

    }

}
