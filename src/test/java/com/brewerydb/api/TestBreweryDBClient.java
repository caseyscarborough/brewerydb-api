package com.brewerydb.api;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class TestBreweryDBClient extends BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBreweryDBClient.class);

    public TestBreweryDBClient(String apiKey) {
        super(apiKey);
    }

    @Override
    protected String makeRequest(AsyncHttpClient client, Request request) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(request.getUrl().getBytes());
            byte[] digest = md5.digest();
            BigInteger b = new BigInteger(1, digest);
            String hash = b.toString(16);

            InputStream is = this.getClass().getClassLoader().getResourceAsStream("json/" + hash + ".json");
            if (is != null) {
                LOGGER.info("Found cached JSON file, returning contents...");
                return new Scanner(is).useDelimiter("\\A").next();
            }

            String json = super.makeRequest(client, request);
            File jsonFile = new File("src/test/resources/json/" + hash + ".json");
            PrintWriter writer = new PrintWriter(jsonFile, "UTF-8");
            writer.write(json);
            writer.close();
            return json;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
