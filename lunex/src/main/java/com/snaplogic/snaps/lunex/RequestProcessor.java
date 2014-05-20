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

import com.snaplogic.snaps.lunex.Constants.LunexSnaps;
import com.snaplogic.snaps.lunex.Constants.RResource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.snaplogic.snaps.lunex.Constants.*;

/**
 * The Request Processor manage submission and handling of all the request. This class performs the
 * processing of all the requests received by the snap and return back with formatted Json object.
 * 
 * @author svatada
 */
public class RequestProcessor {
    static final Logger log = LoggerFactory.getLogger(RequestProcessor.class);
    private static RequestProcessor rHandler = null;
    static {
        rHandler = new RequestProcessor();
    }
    private int statusCode;

    public static RequestProcessor getInstance() {
        return rHandler;
    }

    /* Provides the functionality to process the HTTP request and response handling */
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
                    close(cgiInput);
                }
            }

            BufferedReader reader;
            InputStream iStream = httpConnection.getInputStream();
            if ((statusCode = httpConnection.getResponseCode()) == HttpStatus.SC_OK) {
                reader = new BufferedReader(new InputStreamReader(iStream));
            } else {
                reader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            close(reader);
            log.debug(String.format(HTTP_STATUS, statusCode));
            return formatResponse(response, rBuilder);
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

    /* Handles IO close operation */
    private void close(Object obj) {
        if (null != obj) {
            try {
                if (obj instanceof DataOutputStream) {
                    ((DataOutputStream) obj).close();
                } else {
                    ((BufferedReader) obj).close();
                }
            } catch (IOException ioe) {
                log.error(ioe.getLocalizedMessage(), ioe);
            }
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    /* format the json response */
    private String formatResponse(StringBuilder response, RequestBuilder rBuilder) {
        StringBuilder sBuilder = new StringBuilder();
        if (rBuilder.getResource().toString().equals(RResource.GetTime.toString())) {
            sBuilder.append(OPENTAG).append(QUOTE).append(TIME_STAMP_TAG).append(QUOTE)
                    .append(COLON).append(QUOTE)
                    .append(response.toString().replaceAll(REGEX, "").substring(4)).append(QUOTE)
                    .append(CLOSETAG);
        } else if (rBuilder.getSnapType() == LunexSnaps.Delete) {
            sBuilder.append(OPENTAG).append(QUOTE).append(DELETE_RESPONSE_FLAG).append(QUOTE)
                    .append(COLON).append(QUOTE).append(response).append(QUOTE).append(CLOSETAG);
        } else {
            sBuilder.append(response);
        }
        return formatResponse(sBuilder);
    }

    /* convert the json object into array of objects */
    private static String formatResponse(StringBuilder response) {
        if (!response.toString().endsWith(CLOSEBRACKET)
                && !response.toString().startsWith(OPENBRACKET)) {
            response.insert(0, OPENBRACKET).append(CLOSEBRACKET);
        }
        return response.toString();
    }
}