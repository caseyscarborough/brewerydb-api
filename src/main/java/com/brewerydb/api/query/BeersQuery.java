package com.brewerydb.api.query;

import com.brewerydb.api.model.Status;
import com.brewerydb.api.query.order.BeerOrder;
import com.brewerydb.api.query.params.BeerParams;
import com.brewerydb.api.query.sort.SortDirection;

import java.util.List;

public class BeersQuery extends AbstractQuery {

    private BeersQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final BeersQuery beersQuery;

        private Builder() {
            beersQuery = new BeersQuery();
        }

        public Builder withPage(Integer page) {
            beersQuery.params.put(BeerParams.PAGE_NUMBER, page.toString());
            return this;
        }

        public Builder withIds(List<String> ids) {
            String idsString = ids.toString();
            String list = idsString.substring(1, idsString.length() - 1).replaceAll(", ", ",");
            beersQuery.params.put(BeerParams.IDS, list);
            return this;
        }

        public Builder withName(String name) {
            beersQuery.params.put(BeerParams.NAME, name);
            ;
            return this;
        }

        public Builder withAbv(String abv) {
            beersQuery.params.put(BeerParams.ABV, abv);
            ;
            return this;
        }

        public Builder withIbu(String ibu) {
            beersQuery.params.put(BeerParams.IBU, ibu);
            return this;
        }

        public Builder withGlasswareId(String glasswareId) {
            beersQuery.params.put(BeerParams.GLASSWARE_ID, glasswareId);
            return this;
        }

        public Builder withSrmId(String srmId) {
            beersQuery.params.put(BeerParams.SRM_ID, srmId);
            return this;
        }

        public Builder withAvailabileId(Integer availabilityId) {
            beersQuery.params.put(BeerParams.AVAILABLE_ID, availabilityId.toString());
            return this;
        }

        public Builder withStyleId(Integer styleId) {
            beersQuery.params.put(BeerParams.STYLE_ID, styleId.toString());
            return this;
        }

        public Builder withOrganic(boolean organic) {
            beersQuery.params.put(BeerParams.IS_ORGANIC, organic ? "Y" : "N");
            return this;
        }

        public Builder withHasLabels(boolean hasLabels) {
            beersQuery.params.put(BeerParams.HAS_LABELS, hasLabels ? "Y" : "N");
            return this;
        }

        public Builder withYear(String year) {
            beersQuery.params.put(BeerParams.YEAR, year);
            return this;
        }

        public Builder withSince(Long since) {
            beersQuery.params.put(BeerParams.SINCE, since.toString());
            return this;
        }

        public Builder withStatus(Status status) {
            beersQuery.params.put(BeerParams.STATUS, status.getName());
            return this;
        }

        public Builder withOrder(BeerOrder order) {
            beersQuery.params.put(BeerParams.ORDER, order.getName());
            return this;
        }

        public Builder withSort(SortDirection sort) {
            beersQuery.params.put(BeerParams.SORT, sort.getName());
            return this;
        }

        public Builder withRandomCount(Integer randomCount) {
            beersQuery.params.put(BeerParams.RANDOM_COUNT, randomCount.toString());
            return this;
        }

        public Builder withBreweries() {
            beersQuery.params.put(BeerParams.WITH_BREWERIES, "Y");
            return this;
        }

        public Builder withSocialAccounts() {
            beersQuery.params.put(BeerParams.WITH_SOCIAL_ACCOUNTS, "Y");
            return this;
        }

        public Builder withIngredients() {
            beersQuery.params.put(BeerParams.WITH_INGREDIENTS, "Y");
            return this;
        }

        public BeersQuery build() {
            return beersQuery;
        }

    }

}
