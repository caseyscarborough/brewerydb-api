package com.brewerydb.api;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class TestBreweryDBClient extends BreweryDBClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestBreweryDBClient.class);
    private final Gson gson = new Gson();

    public TestBreweryDBClient(String apiKey) {
        super(apiKey);
    }

    @Override
    protected <T extends Result> Future<T> performRequestAsync(final AsyncHttpClient client, final Request request, final Class<T> clazz) {
        final String md5 = getMD5ForRequest(request);
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream("json/" + md5 + ".json");
        if (is != null) {
            LOGGER.info("Found cached JSON file " + md5 + ".json, returning contents...");
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Callable<T> task = new Callable<T>() {
                public T call() throws Exception {
                    return getResultFromJson(new Scanner(is).useDelimiter("\\A").next(), clazz);
                }
            };
            return executorService.submit(task);
        }

        return client.executeRequest(request, new AsyncCompletionHandler<T>() {
            @Override
            public T onCompleted(Response response) throws Exception {
                String json = response.getResponseBody();
                File jsonFile = new File("src/test/resources/json/" + md5 + ".json");
                PrintWriter writer = new PrintWriter(jsonFile, "UTF-8");
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
