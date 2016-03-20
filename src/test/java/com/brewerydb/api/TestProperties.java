package com.brewerydb.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class TestProperties {

    private static final String TEST_CONSTANTS_FILE = "test.properties";
    private static final String API_KEY_PROPERTY = "apiKey";
    private static final Properties PROPERTIES = new Properties();

    static {
        InputStream is = TestProperties.class.getClassLoader().getResourceAsStream(TEST_CONSTANTS_FILE);
        if (is == null) {
            throw new RuntimeException("Could not find " + TEST_CONSTANTS_FILE + " file on classpath.");
        }

        try {
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Could not load properties from " + TEST_CONSTANTS_FILE + " file.");
        }
    }

    public static String getApiKey() {
        return PROPERTIES.getProperty(API_KEY_PROPERTY);
    }
}
