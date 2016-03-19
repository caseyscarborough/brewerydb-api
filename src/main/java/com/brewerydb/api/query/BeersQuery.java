package com.brewerydb.api.query;

import java.util.List;

public class BeersQuery implements Query {

    private Integer pageNumber;
    private List<String> ids;
    private String name;

    private BeersQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getQueryString() {
        StringBuilder sb = new StringBuilder();
        if (name != null && !name.trim().equals("")) {
            sb.append("&name=").append(name);
        }
        return sb.toString();
    }

    public static class Builder {

        private Integer pageNumber;
        private List<String> ids;
        private String name;

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
            BeersQuery beersQuery = new BeersQuery();
            beersQuery.name = name;
            beersQuery.ids = ids;
            beersQuery.pageNumber = pageNumber;
            return beersQuery;
        }

    }

}
