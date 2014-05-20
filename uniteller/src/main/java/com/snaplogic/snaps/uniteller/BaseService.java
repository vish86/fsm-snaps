/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.uniteller;

import com.google.inject.Inject;
import com.snaplogic.account.api.capabilities.Accounts;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.SnapDataException;
import com.snaplogic.snap.api.capabilities.Category;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snaps.uniteller.Constants.SnapType;
import com.snaplogic.snaps.uniteller.bean.AccountBean;
import com.uniteller.support.common.UFSBasicConfigMgr;
import com.uniteller.support.common.UFSBasicSecurityMgr;
import com.uniteller.support.foliocreationclient.UFSFolioCreationClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.snaplogic.snaps.uniteller.Constants.ERROR_TAG;
import static com.snaplogic.snaps.uniteller.Constants.MESSAGE_TAG;
import static com.snaplogic.snaps.uniteller.Constants.REASON_TAG;
import static com.snaplogic.snaps.uniteller.Constants.RESOLUTION_TAG;
import static com.snaplogic.snaps.uniteller.Constants.RESOUCE_LIST;
import static com.snaplogic.snaps.uniteller.Constants.UNITELLER_PKG_PREFIX;
import static com.snaplogic.snaps.uniteller.Constants.UNITELLER_REQ_TAG;
import static com.snaplogic.snaps.uniteller.Constants.UNITELLER_RESP_TAG;
import static com.snaplogic.snaps.uniteller.Messages.ERRORMSG;
import static com.snaplogic.snaps.uniteller.Messages.ERROR_REASON;
import static com.snaplogic.snaps.uniteller.Messages.ERROR_RESOLUTION;
import static com.snaplogic.snaps.uniteller.Messages.RESOURCE_DESC;
import static com.snaplogic.snaps.uniteller.Messages.RESOURCE_LABEL;
import static com.snaplogic.snaps.uniteller.Messages.RESOURCE_PROP;

/**
 * Abstract base class for UniTeller snap pack which contains common snap properties,
 * authentication.
 * 
 * @author svatada
 */
@Inputs(min = 1, max = 1, accepts = { ViewType.DOCUMENT })
@Outputs(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Version(snap = 1)
@Category(snap = SnapCategory.TRANSFORM)
@Accounts(provides = { UniTellerBasicAuthAccount.class }, optional = false)
public abstract class BaseService extends SimpleSnap {
    @Inject
    private UniTellerBasicAuthAccount account;
    private final SnapType snapsType;
    @Inject
    private String resourceType;
    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    /** Constructor to initialise the respective snap */
    public BaseService(SnapType snaps) {
        this.snapsType = snaps;
    }

    @Override
    public void defineProperties(final PropertyBuilder propertyBuilder) {
        propertyBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC)
                .withAllowedValues(RESOUCE_LIST.get(snapsType.toString())).add();
    }

    @Override
    public void configure(final PropertyValues propertyValues) throws ConfigurationException {
        resourceType = propertyValues.get(RESOURCE_PROP).toString();
    }

    @Override
    protected void process(Document document, String inputViewName) {
        try {
            AccountBean bean = account.connect();
            log.debug(bean.getConfigFilePath() + bean.getSecurityPermFilePath());
            log.debug(getUFSReqClassType());
            log.debug(getUFSRespClassType());
            UFSFolioCreationClient fclClient = new UFSFolioCreationClient(
                    UFSBasicConfigMgr.getInstance(bean.getConfigFilePath()),
                    UFSBasicSecurityMgr.getInstance(bean.getSecurityPermFilePath()));

            // } catch (IOException ex) {
            // writeToErrorView(IO_ERROR, IO_ERROR_REASON, IO_ERROR_RESOLUTION, ex);
        } catch (Exception ex) {
            writeToErrorView(ERRORMSG, ERROR_REASON, ERROR_RESOLUTION, ex);
        }
    }

    @Override
    public void cleanup() throws ExecutionException {
    }

    /*
     * Returns absolute class type for UFS request object
     */
    private String getUFSReqClassType() {
        return new StringBuilder().append(UNITELLER_PKG_PREFIX).append(resourceType)
                .append(UNITELLER_REQ_TAG).toString();
    }

    /*
     * Returns absolute class type for UFS response object
     */
    private String getUFSRespClassType() {
        return new StringBuilder().append(UNITELLER_PKG_PREFIX).append(resourceType)
                .append(UNITELLER_RESP_TAG).toString();
    }

    /* Writes the exception records to error view */
    private void writeToErrorView(final String errMsg, final String errReason,
            final String errResoulution, Throwable ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_TAG, errMsg);
        map.put(MESSAGE_TAG, ex.getMessage());
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException = new SnapDataException(documentUtility.newDocument(map),
                ex.getMessage()).withReason(errReason).withResolution(errResoulution);
        errorViews.write(snapException);
    }

    /* Writes the error records to error view */
    private void writeToErrorView(final int httpErrCode, final String errReason,
            final String errResoulution, final String errResponse) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_TAG, httpErrCode);
        map.put(MESSAGE_TAG, errResponse);
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException = new SnapDataException(documentUtility.newDocument(map),
                errResponse).withReason(errReason).withResolution(errResoulution);
        errorViews.write(snapException);
    }
}