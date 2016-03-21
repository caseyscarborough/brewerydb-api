package com.brewerydb.api.model;

public class Hop {

    private Integer id;
    private String name;
    private String description;
    private String countryOfOrigin;
    private String alphaAcidMin;
    private Double alphaAcidMax;
    private Double betaAcidMin;
    private Double betaAcidMax;
    private Double humuleneMin;
    private Double humuleneMax;
    private Double caryophylleneMin;
    private Double caryophylleneMax;
    private Double cohumuloneMin;
    private Double cohumuloneMax;
    private Double myrceneMin;
    private Double myrceneMax;
    private Double farneseneMin;
    private Double farneseneMax;
    private Boolean isNoble;
    private Boolean forBittering;
    private Boolean forFlavor;
    private Boolean forAroma;
    private String category;
    private String categoryDisplay;
    private Country country;

    /**
     * The unique id of the hop.
     */
    public Integer getId() {
        return id;
    }

    /**
     * The name of the hop.
     */
    public String getName() {
        return name;
    }

    /**
     * The description of the hop.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The two-letter ISO country code. For the complete country name and information, see the country field.
     */
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    /**
     * The minimum value for the typical range of alpha acids in the hop.
     */
    public String getAlphaAcidMin() {
        return alphaAcidMin;
    }

    /**
     * The maximum value for the typical range of alpha acids in the hop.
     */
    public Double getAlphaAcidMax() {
        return alphaAcidMax;
    }

    /**
     * The minimum value for the typical range of beta acids in the hop.
     */
    public Double getBetaAcidMin() {
        return betaAcidMin;
    }

    /**
     * The maxiumum value for the typical range of beta acids in the hop.
     */
    public Double getBetaAcidMax() {
        return betaAcidMax;
    }

    /**
     * The minimum value for the typical range of humulene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getHumuleneMin() {
        return humuleneMin;
    }

    /**
     * The maximum value for the typical range of humulene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getHumuleneMax() {
        return humuleneMax;
    }

    /**
     * The minimum value for the typical range of caryophyllene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getCaryophylleneMin() {
        return caryophylleneMin;
    }

    /**
     * The maximum value for the typical range of caryophyllene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getCaryophylleneMax() {
        return caryophylleneMax;
    }

    /**
     * The minimum value for the typical range of cohumulone in the hop. Expressed as a percentage of the alpha acids.
     */
    public Double getCohumuloneMin() {
        return cohumuloneMin;
    }

    /**
     * The maximum value for the typical range of cohumulone in the hop. Expressed as a percentage of the alpha acids.
     */
    public Double getCohumuloneMax() {
        return cohumuloneMax;
    }

    /**
     * The minimum value for the typical range of myrcene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getMyrceneMin() {
        return myrceneMin;
    }

    /**
     * The maximum value for the typical range of myrcene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getMyrceneMax() {
        return myrceneMax;
    }

    /**
     * The minimum value for the typical range of farnesene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getFarneseneMin() {
        return farneseneMin;
    }

    /**
     * The maximum value for the typical range of farnesene in the hop. Expressed as a percentage of the total oils.
     */
    public Double getFarneseneMax() {
        return farneseneMax;
    }

    /**
     * This is set to Y if the hop is a noble hop.
     */
    public Boolean getNoble() {
        return isNoble;
    }

    /**
     * Set to Y if the hop is typically used for bittering.
     */
    public Boolean getForBittering() {
        return forBittering;
    }

    /**
     * Set to Y if the hop is typically used for its flavor.
     */
    public Boolean getForFlavor() {
        return forFlavor;
    }

    /**
     * Set to Y if the hop is typically used for its aroma.
     */
    public Boolean getForAroma() {
        return forAroma;
    }

    /**
     * This value will always be set to "hop".
     */
    public String getCategory() {
        return category;
    }

    /**
     * This value will always be set to "Hops".
     */
    public String getCategoryDisplay() {
        return categoryDisplay;
    }

    /**
     * This is an object that provides more information about the country matching the hop's countryOfOrigin value.
     */
    public Country getCountry() {
        return country;
    }
}
