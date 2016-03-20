package com.brewerydb.api;

import com.brewerydb.api.json.GsonFactory;
import com.brewerydb.api.result.Result;
import com.google.gson.Gson;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Param;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class TestBreweryDBClient extends BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBreweryDBClient.class);
    private static final long CACHE_TIME = 1000 * 60 * 60 * 24; // 1 day
    private final Gson gson = new GsonFactory().getInstance();

    public TestBreweryDBClient(String apiKey) {
        super(apiKey);
    }

    @Override
    protected <T extends Result> Future<T> performRequestAsync(final AsyncHttpClient client, final Request request, final Class<T> clazz) {
        final String md5 = getMD5ForRequest(request);
        final File file = new File("src/test/resources/json/" + md5 + ".json");
        if (file.exists() && (file.lastModified() > new Date().getTime() - CACHE_TIME)) {
            LOGGER.info("Found cached JSON file " + md5 + ".json, returning contents...");
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Callable<T> task = new Callable<T>() {
                public T call() throws Exception {
                    return getResultFromJson(new Scanner(new FileInputStream(file)).useDelimiter("\\A").next(), clazz);
                }
            };
            return executorService.submit(task);
        }

        return client.executeRequest(request, new AsyncCompletionHandler<T>() {
            @Override
            public T onCompleted(Response response) throws Exception {
                LOGGER.info("Writing new JSON cache file " + md5 + ".json...");
                String json = response.getResponseBody();
                PrintWriter writer = new PrintWriter(file, "UTF-8");
                writer.write(json);
                writer.close();
                return getResultFromJson(json, clazz);
            }
        });
    }

    private <T extends Result> T getResultFromJson(String json, Class<T> clazz) {
        T result = gson.fromJson(json, clazz);
        validateResult(result);
        return result;
    }

    private String getMD5ForRequest(Request request) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getUrl());
        for (Param param : request.getFormParams()) {
            sb.append(param.getName()).append("=").append(param.getValue());
        }
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md5.update(sb.toString().getBytes());
        byte[] digest = md5.digest();
        BigInteger b = new BigInteger(1, digest);
        return b.toString(16);
    }
}
