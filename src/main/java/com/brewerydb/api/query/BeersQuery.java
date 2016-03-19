package com.brewerydb.api.query;

import com.brewerydb.api.model.Status;
import com.brewerydb.api.query.order.BeerOrder;
import com.brewerydb.api.query.fields.BeerFields;
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
            beersQuery.params.put(BeerFields.PAGE_NUMBER, page.toString());
            return this;
        }

        public Builder withIds(List<String> ids) {
            String idsString = ids.toString();
            String list = idsString.substring(1, idsString.length() - 1).replaceAll(", ", ",");
            beersQuery.params.put(BeerFields.IDS, list);
            return this;
        }

        public Builder withName(String name) {
            beersQuery.params.put(BeerFields.NAME, name);
            ;
            return this;
        }

        public Builder withAbv(String abv) {
            beersQuery.params.put(BeerFields.ABV, abv);
            ;
            return this;
        }

        public Builder withIbu(String ibu) {
            beersQuery.params.put(BeerFields.IBU, ibu);
            return this;
        }

        public Builder withGlasswareId(String glasswareId) {
            beersQuery.params.put(BeerFields.GLASSWARE_ID, glasswareId);
            return this;
        }

        public Builder withSrmId(String srmId) {
            beersQuery.params.put(BeerFields.SRM_ID, srmId);
            return this;
        }

        public Builder withAvailabileId(Integer availabilityId) {
            beersQuery.params.put(BeerFields.AVAILABLE_ID, availabilityId.toString());
            return this;
        }

        public Builder withStyleId(Integer styleId) {
            beersQuery.params.put(BeerFields.STYLE_ID, styleId.toString());
            return this;
        }

        public Builder withOrganic(boolean organic) {
            beersQuery.params.put(BeerFields.IS_ORGANIC, organic ? Y : N);
            return this;
        }

        public Builder withHasLabels(boolean hasLabels) {
            beersQuery.params.put(BeerFields.HAS_LABELS, hasLabels ? Y : N);
            return this;
        }

        public Builder withYear(String year) {
            beersQuery.params.put(BeerFields.YEAR, year);
            return this;
        }

        public Builder withSince(Long since) {
            beersQuery.params.put(BeerFields.SINCE, since.toString());
            return this;
        }

        public Builder withStatus(Status status) {
            beersQuery.params.put(BeerFields.STATUS, status.getName());
            return this;
        }

        public Builder withOrder(BeerOrder order) {
            beersQuery.params.put(BeerFields.ORDER, order.getName());
            return this;
        }

        public Builder withSort(SortDirection sort) {
            beersQuery.params.put(BeerFields.SORT, sort.getName());
            return this;
        }

        public Builder withRandomCount(Integer randomCount) {
            beersQuery.params.put(BeerFields.RANDOM_COUNT, randomCount.toString());
            return this;
        }

        public Builder withBreweries() {
            beersQuery.params.put(BeerFields.WITH_BREWERIES, Y);
            return this;
        }

        public Builder withSocialAccounts() {
            beersQuery.params.put(BeerFields.WITH_SOCIAL_ACCOUNTS, Y);
            return this;
        }

        public Builder withIngredients() {
            beersQuery.params.put(BeerFields.WITH_INGREDIENTS, Y);
            return this;
        }

        public BeersQuery build() {
            return beersQuery;
        }

    }

}
