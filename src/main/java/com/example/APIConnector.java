package com.example;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class APIConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    private static final String USER_AGENT = "Mozilla/5.0";

    @NotNull
    public static String SendGet(@NotNull URL link, List<String> headers, List<String> values) throws Exception {

            LOGGER.info(link.toString() + ", " + headers.size() + ", " + values.size());
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Cache-Control", "no-cache"); //HTTP 1.1
            connection.setRequestProperty("Pragma", "no-cache"); //HTTP 1.0
            connection.setRequestProperty("Accept", "application/vnd.github.v3+json");
            connection.setUseCaches(false);

            if (headers != null) {
                for (String header : headers) {
                    LOGGER.info(header);
                    LOGGER.info(values.get(headers.indexOf(header)));
                    connection.setRequestProperty(header, values.get(headers.indexOf(header)).replace("[", "").replace("]", ""));
                }
            }
            try {
                connection.connect();
            } catch (Exception e) {
                LOGGER.info(e.toString());
            }
            LOGGER.info(String.valueOf(connection.usingProxy()));
            LOGGER.info(String.valueOf(connection.getReadTimeout()));
            InputStream responseStream = connection.getInputStream();
            LOGGER.info(String.valueOf(responseStream.available()));
            BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                LOGGER.info(line);
                response.append(line);
            }
            in.close();

            int responseCode = connection.getResponseCode();
            LOGGER.info(String.valueOf(responseCode));
            if (responseCode == 200) {
                LOGGER.info("Success");
            }
            if (responseCode == 401) {
                LOGGER.error("Not Authorized.");
            }
            if (responseCode == 404) {
                LOGGER.error("Not Found.");
            }
            if (responseCode == 400) {
                LOGGER.error("Bad request, check headers and params.");
            }

            connection.disconnect();
            return response.toString();
    }

    @NotNull
    public static String SendPost(@NotNull URL link, List<String> headers, List<String> values) throws Exception {

        HttpURLConnection connection = (HttpURLConnection) link.openConnection();

        connection.setRequestMethod("POST");

        connection.setRequestProperty("USER-AGENT", USER_AGENT);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Cache-Control","no-cache"); //HTTP 1.1
        connection.setRequestProperty("Pragma","no-cache"); //HTTP 1.0
        connection.setUseCaches(false);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        if (headers != null) {
            for (String header : headers) {
                LOGGER.info(header);
                LOGGER.info(values.get(headers.indexOf(header)));
                connection.setRequestProperty(header, values.get(headers.indexOf(header)).replace("[", "").replace("]", ""));
            }
        }

        connection.connect();
        InputStream responseStream = connection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = in.readLine()) != null) {
            LOGGER.info(line);
            response.append(line);
        }

        int responseCode = connection.getResponseCode();
        LOGGER.info(String.valueOf(responseCode));
        if (responseCode == 200) {
            LOGGER.info("Success");
        }
        if (responseCode == 401) {
            LOGGER.error("Not Authorized.");
        }
        if (responseCode == 404) {
            LOGGER.error("Not Found.");
        }
        if (responseCode == 400) {
            LOGGER.error("Bad request, check headers and params.");
        }

        in.close();

        return response.toString();
    }
}