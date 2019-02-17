package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APICaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

    private static List<String> headers = new ArrayList<>();
    private static List<String> headerValues = new ArrayList<>();

    public static String callAPI(boolean post, boolean get, URL link) {
        if(headers.isEmpty()) {
            headers.add(null);
        }
        if(headerValues.isEmpty()) {
            headerValues.add(null);
        }

        try {
            if(post && !get) {
                LOGGER.info("Sending post...");
                return APIConnector.SendPost(link, headers, headerValues);
            }
            if(get && !post) {
                LOGGER.info("Sending get...");
                return APIConnector.SendGet(link, headers, headerValues);
            }
            else {
                LOGGER.error("You must specify one HTTP method.");
                throw new UnsupportedOperationException();
            }

        } catch(Exception e) {
            LOGGER.info(e.getCause().toString());
            return "";
        }
    }
}