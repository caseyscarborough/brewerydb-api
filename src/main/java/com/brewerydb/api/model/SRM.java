package com.brewerydb.api.model;

/**
 * The Standard Reference Method of a beer, used to specify beer color.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Standard_Reference_Method">https://en.wikipedia.org/wiki/Standard_Reference_Method</a>
 */
public class SRM {

    private Long id;
    private String name;
    private String hex;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHex() {
        return hex;
    }
}
