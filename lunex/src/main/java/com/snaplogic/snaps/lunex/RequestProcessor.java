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
package com.snaplogic.snaps.lunex;

import static com.snaplogic.snaps.lunex.Constants.CLOSEBRACKET;
import com.snaplogic.snaps.lunex.Constants.LunexSnaps;
import com.snaplogic.snaps.lunex.Constants.RResource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.snaplogic.snaps.lunex.Constants.CLOSETAG;
import static com.snaplogic.snaps.lunex.Constants.COLON;
import static com.snaplogic.snaps.lunex.Constants.OPENBRACKET;
import static com.snaplogic.snaps.lunex.Constants.OPENTAG;
import static com.snaplogic.snaps.lunex.Constants.QUOTE;

/**
 * This will take care of http request execution.
 *
 * @author svatada
 */
public class RequestProcessor {
    private static final String LUNEX_HTTP_INFO = "[{\"ResourceType\": \"%s\"}, {\"Lunex URL\":\"%s\"}, {\"Header\": \"%s\"}]";
    private static final String LUNEX_HTTP_REQ_INFO = "Lunex Request Body: %s";
    private static final String HTTP_STATUS = "###HTTPStatus: %s ";
    private static final Logger log = LoggerFactory.getLogger(RequestProcessor.class);
    private static final String REGEX = "[^\\p{L}\\p{Nd}]";
    private static RequestProcessor rHandler = null;
    private static final String TIME_STAMP_TAG = "TimeStamp";
    private static final String DELETE_RESPONSE_FLAG = "DeleteResponse";
    static {
        rHandler = new RequestProcessor();
    }
    private int statusCode;

    public static RequestProcessor getInstance() {
        return rHandler;
    }

    public String execute(RequestBuilder rBuilder) throws MalformedURLException, IOException {
        try {
            URL api_url = new URL(rBuilder.getURL());
            HttpURLConnection httpConnection = (HttpURLConnection) api_url.openConnection();
            httpConnection.setRequestMethod(rBuilder.getMethod().toString());
            httpConnection.setDoInput(true);
            httpConnection.setDoOutput(true);
            httpConnection.setUseCaches(false);
            for (Pair<String, String> header : rBuilder.getHeaders()) {
                if (!StringUtils.isEmpty(header.getKey())
                        && !StringUtils.isEmpty(header.getValue())) {
                    httpConnection.setRequestProperty(header.getKey(), header.getValue());
                }
            }
            log.debug(String.format(LUNEX_HTTP_INFO, rBuilder.getSnapType(), rBuilder.getURL(),
                    httpConnection.getRequestProperties().toString()));
            DataOutputStream cgiInput = null;
            if (rBuilder.getSnapType() != LunexSnaps.Read) {
                String paramsJson = null;
                if (!StringUtils.isEmpty(paramsJson = rBuilder.getRequestBody())) {
                    cgiInput = new DataOutputStream(httpConnection.getOutputStream());
                    log.debug(String.format(LUNEX_HTTP_REQ_INFO, paramsJson));
                    cgiInput.writeBytes(paramsJson);
                    cgiInput.flush();
                    cgiInput.close();
                }
            }

            BufferedReader reader;
            StringBuffer response = new StringBuffer();
            InputStream iStream = httpConnection.getInputStream();
            if ((statusCode = httpConnection.getResponseCode()) == HttpStatus.SC_OK) {
                reader = new BufferedReader(new InputStreamReader(iStream));
            } else {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            log.debug(String.format(HTTP_STATUS, statusCode));

            if (rBuilder.getResource().toString().equals(RResource.GetTime.toString())) {
                return getTimeJson(response.toString());
            } else if (rBuilder.getSnapType() == LunexSnaps.Delete) {
                return getDeleteJson(response.toString());
            }
            return formatResponse(response.toString());
            
        } catch (MalformedURLException me) {
            log.error(me.getMessage(), me);
            throw me;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
            throw ioe;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    private String getTimeJson(String response) {
        // ""\/Date(928178400000-0800)\/""
        return formatResponse(new StringBuilder().append(OPENTAG).append(QUOTE).append(TIME_STAMP_TAG)
                .append(QUOTE).append(COLON).append(QUOTE)
                .append(response.replaceAll(REGEX, "").substring(4)).append(QUOTE).append(CLOSETAG)
                .toString());
    }

    private String getDeleteJson(String response) {
        // "[true]"
        return formatResponse(new StringBuilder().append(OPENTAG).append(QUOTE).append(DELETE_RESPONSE_FLAG)
                .append(QUOTE).append(COLON).append(QUOTE)
                .append(response).append(QUOTE).append(CLOSETAG)
                .toString());
    }

    private String formatResponse(String response) {
        if (!response.endsWith(CLOSEBRACKET) && !response.startsWith(OPENBRACKET)) {
            response = new StringBuilder().append(OPENBRACKET).append(response).append(CLOSEBRACKET).toString();
        }
        return response;
    }
}