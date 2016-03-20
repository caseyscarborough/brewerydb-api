package com.brewerydb.api;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Param;
import com.ning.http.client.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
    protected String performRequest(AsyncHttpClient client, Request request) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(request.getUrl());
            for (Param param : request.getFormParams()) {
                sb.append(param.getName()).append("=").append(param.getValue());
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(sb.toString().getBytes());
            byte[] digest = md5.digest();
            BigInteger b = new BigInteger(1, digest);
            String hash = b.toString(16);

            InputStream is = this.getClass().getClassLoader().getResourceAsStream("json/" + hash + ".json");
            if (is != null) {
                LOGGER.info("Found cached JSON file " + hash + ".json, returning contents...");
                return new Scanner(is).useDelimiter("\\A").next();
            }

            String json = super.performRequest(client, request);
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
