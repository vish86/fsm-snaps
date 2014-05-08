/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.lunex.http.handler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snaplogic.snaps.lunex.constants.Constants.LunexSnaps;

/**
 * This will takes care of http request execution.
 * 
 * @author svatada
 */
public class RequestProcessor {
    private static final Logger log = LoggerFactory.getLogger(RequestProcessor.class);
    private static RequestProcessor rHandler = null;
    private int statusCode;
    static {
        rHandler = new RequestProcessor();
    }

    public static RequestProcessor getInstance() {
        return rHandler;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String execute(RequestBuilder rBuilder) throws MalformedURLException, IOException {
        try {
            URL api_url = new URL(rBuilder.getURL());
            log.debug("###Host: " + api_url.getHost());
            log.debug("###URI: " + api_url.getPath());
            HttpURLConnection httpConnection = (HttpURLConnection) api_url.openConnection();
            httpConnection.setRequestMethod(rBuilder.getMethod().toString());
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            for (Pair<String, String> header : rBuilder.getHeaders()) {
                if (!StringUtils.isEmpty(header.getKey())
                        && !StringUtils.isEmpty(header.getValue())) {
                    httpConnection.setRequestProperty(header.getKey(), header.getValue());
                    log.debug("#@@Header [ Key: " + header.getKey() + " Value: "
                            + header.getValue() + "]");
                }
            }

            BufferedReader reader;
            StringBuffer response = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            if ((statusCode = httpConnection.getResponseCode()) != HttpStatus.SC_OK) {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
//            if ((statusCode = httpConnection.getResponseCode()) == HttpStatus.SC_OK) {
//                reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
//            } else {
//                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
//            }

            String line;
            log.debug("###Resource Type: " + rBuilder.getSnapType());
            if (rBuilder.getSnapType() != LunexSnaps.Read) {
                String paramsJson = null;
                if (!StringUtils.isEmpty(paramsJson = rBuilder.getRequestBody())) {
                    DataOutputStream cgiInput = new DataOutputStream(
                            httpConnection.getOutputStream());
                    cgiInput.writeBytes(paramsJson);
                    cgiInput.flush();
                    cgiInput.close();
                }
            }
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            log.debug("###HTTP Status: " + statusCode);
            reader.close();
            return response.toString();

        } catch (MalformedURLException me) {
            log.error(me.getMessage(), me);
            throw me;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
            throw ioe;
        }
    }
}