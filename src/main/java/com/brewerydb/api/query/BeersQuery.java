package com.brewerydb.api.query;

import com.brewerydb.api.model.Status;
import com.brewerydb.api.query.fields.BeerFields;
import com.brewerydb.api.query.order.BeerOrder;

import java.util.HashMap;
import java.util.Map;

public class BeersQuery extends AbstractQuery {

    private BeersQuery() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends MultipleResultQueryBuilder<BeersQuery, Builder> {

        private Map<String, String> params = new HashMap<String, String>();

        private Builder() {
        }

        public Builder withAbv(String abv) {
            params.put(BeerFields.ABV, abv);
            ;
            return this;
        }

        public Builder withIbu(String ibu) {
            params.put(BeerFields.IBU, ibu);
            return this;
        }

        public Builder withGlasswareId(String glasswareId) {
            params.put(BeerFields.GLASSWARE_ID, glasswareId);
            return this;
        }

        public Builder withSrmId(String srmId) {
            params.put(BeerFields.SRM_ID, srmId);
            return this;
        }

        public Builder withAvailableId(Integer availabilityId) {
            params.put(BeerFields.AVAILABLE_ID, availabilityId.toString());
            return this;
        }

        public Builder withStyleId(Integer styleId) {
            params.put(BeerFields.STYLE_ID, styleId.toString());
            return this;
        }

        public Builder withOrganic(boolean organic) {
            params.put(BeerFields.IS_ORGANIC, organic ? Y : N);
            return this;
        }

        public Builder withHasLabels(boolean hasLabels) {
            params.put(BeerFields.HAS_LABELS, hasLabels ? Y : N);
            return this;
        }

        public Builder withYear(String year) {
            params.put(BeerFields.YEAR, year);
            return this;
        }

        public Builder withSince(Long since) {
            params.put(BeerFields.SINCE, since.toString());
            return this;
        }

        public Builder withStatus(Status status) {
            params.put(BeerFields.STATUS, status.getName());
            return this;
        }

        public Builder withOrder(BeerOrder order) {
            params.put(BeerFields.ORDER, order.getName());
            return this;
        }

        public Builder withRandomCount(Integer randomCount) {
            params.put(BeerFields.RANDOM_COUNT, randomCount.toString());
            return this;
        }

        public Builder withBreweries() {
            params.put(BeerFields.WITH_BREWERIES, Y);
            return this;
        }

        public Builder withSocialAccounts() {
            params.put(BeerFields.WITH_SOCIAL_ACCOUNTS, Y);
            return this;
        }

        public Builder withIngredients() {
            params.put(BeerFields.WITH_INGREDIENTS, Y);
            return this;
        }

        public BeersQuery build() {
            BeersQuery beersQuery = new BeersQuery();
            beersQuery.params = params;
            return beersQuery;
        }
    }
}
