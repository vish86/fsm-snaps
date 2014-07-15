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

import com.snaplogic.api.ExecutionException;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.ExpressionProperty;
import com.snaplogic.snaps.lunex.Constants.HttpMethodNames;
import com.snaplogic.snaps.lunex.Constants.LunexSnaps;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static com.snaplogic.snaps.lunex.Constants.CLOSETAG;
import static com.snaplogic.snaps.lunex.Constants.COLON;
import static com.snaplogic.snaps.lunex.Constants.DOUBLE_SLASH;
import static com.snaplogic.snaps.lunex.Constants.HTTP;
import static com.snaplogic.snaps.lunex.Constants.OPENTAG;
import static com.snaplogic.snaps.lunex.Messages.INVALID_URI;
import static com.snaplogic.snaps.lunex.Messages.INVALID_URI_RESOLUTION;

/**
 * Request builder for Lunex snap
 *
 * @author svatada
 */

public class RequestBuilder {
    private Document doc = null;
    private List<Pair<String, String>> Headers = null;
    private String IPAddress = null;
    private HttpMethodNames method;
    private List<Pair<String, ExpressionProperty>> queryParams;
    private StringBuilder requestBody = null;
    private Object resource = null;
    private LunexSnaps snapType = null;

    public RequestBuilder addDoc(Document doc) {
        this.doc = doc;
        return this;
    }

    public RequestBuilder addEndPointIP(String host) {
        this.IPAddress = host;
        return this;
    }

    public RequestBuilder addHeaders(List<Pair<String, String>> headers) {
        Headers = headers;
        return this;
    }

    public RequestBuilder addMethod(HttpMethodNames method) {
        this.method = method;
        return this;
    }

    public RequestBuilder addQueryParams(List<Pair<String, ExpressionProperty>> queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public RequestBuilder addRequestBody(StringBuilder requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public RequestBuilder addResource(Object resource) {
        this.resource = resource;
        return this;
    }

    public RequestBuilder addSnapTye(LunexSnaps snaps) {
        this.snapType = snaps;
        return this;
    }

    public List<Pair<String, String>> getHeaders() {
        return Headers;
    }

    public HttpMethodNames getMethod() {
        return method;
    }

    public String getRequestBody() {
        return requestBody.toString();
    }

    public String getRequestBodyLenght() {
        return String.valueOf(requestBody.length());
    }

    public Object getResource() {
        return resource;
    }

    public LunexSnaps getSnapType() {
        return snapType;
    }

    public String getURL() {
        return resolveUrl(doc);
    }

    /* Converts a relative Url into the URL that is usable by the Lunex RequestProcessor. */
    private String resolveUrl(final Document document) {
        try {
            String resourceSpecificUri = null;
            switch (snapType) {
                case Create:
                    resourceSpecificUri = ServiceURIInfo.CR_URI_LIST.get(resource);
                    break;
                case Read:
                    resourceSpecificUri = ServiceURIInfo.RR_URI_LIST.get(resource);
                    break;
                case Delete:
                    resourceSpecificUri = ServiceURIInfo.DR_URI_LIST.get(resource);
                    break;
                case Update:
                    resourceSpecificUri = ServiceURIInfo.UR_URI_LIST.get(resource);
                    break;
            }
            if (queryParams != null) {
                CharSequence source, target;
                for (Pair<String, ExpressionProperty> paramPair : queryParams) {
                    source = new StringBuilder().append(OPENTAG)
                            .append(paramPair.getLeft())
                            .append(CLOSETAG)
                            .toString();
                    target = paramPair.getRight().eval(document).toString();
                    if (target == null) {
                        target = StringUtils.EMPTY;
                    }
                    resourceSpecificUri = resourceSpecificUri.replace(source, target);
                }
            }
            return new StringBuilder().append(HTTP)
                    .append(COLON)
                    .append(DOUBLE_SLASH)
                    .append(IPAddress)
                    .append(resourceSpecificUri)
                    .toString();
        } catch (Exception e) {
            String msg = String.format(INVALID_URI, new StringBuilder().append(HTTP)
                    .append(COLON)
                    .append(DOUBLE_SLASH)
                    .append(IPAddress)
                    .toString());
            throw new ExecutionException(e, msg).withReason(msg).withResolution(
                    INVALID_URI_RESOLUTION);
        }
    }
}