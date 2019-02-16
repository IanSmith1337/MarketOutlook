package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APICaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(HabiticaAction.class);

    private static List<String> headers = new ArrayList<>();
    private static List<String> headerValues = new ArrayList<>();

    public static String callAPI(String userID, String key, boolean post, boolean get, URL link) {
        headers.add("Input User Header");
        headers.add("Input Key Header");
        headerValues.add(userID);
        headerValues.add(key);
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