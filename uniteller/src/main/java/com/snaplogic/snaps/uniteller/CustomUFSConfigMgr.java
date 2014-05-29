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

import com.uniteller.support.common.IUFSConfigMgr;
import com.uniteller.support.common.UFSConfigMgrException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import static com.snaplogic.snaps.uniteller.Messages.ERR_URL_CONNECT;

/**
 * Custom UFS configuration manager
 *
 * @author svatada
 */
public class CustomUFSConfigMgr implements IUFSConfigMgr {
    private static HashMap<String, Object> instanceMap = null;
    private Properties configProperties = null;
    private URLConnection urlConnection = null;
    private static final Logger log = LoggerFactory.getLogger(CustomUFSConfigMgr.class);
    static {
        instanceMap = new HashMap<String, Object>();
    }

    private CustomUFSConfigMgr(String fileLocation) throws UFSConfigMgrException {
        try {
            URI fileUri = new URI(fileLocation);
            URL fileUrl = fileUri.toURL();
            this.configProperties = new Properties();
            this.configProperties.load(getInputStream(fileUrl));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new UFSConfigMgrException(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new UFSConfigMgrException(ex.getMessage());
        } finally {
            if (urlConnection != null) {
                try {
                    ((HttpsURLConnection) urlConnection).disconnect();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * @param filePath
     * @return CustomUFSConfigMgr
     * @throws UFSConfigMgrException
     */
    public static CustomUFSConfigMgr getInstance(String filePath) throws UFSConfigMgrException {
        CustomUFSConfigMgr customUFSConfigMgr = (CustomUFSConfigMgr) instanceMap.get(filePath);
        if (customUFSConfigMgr == null) {
            synchronized (CustomUFSConfigMgr.class) {
                customUFSConfigMgr = new CustomUFSConfigMgr(filePath);
                instanceMap.put(filePath, customUFSConfigMgr);
            }
        }
        return customUFSConfigMgr;
    }

    private InputStream getInputStream(final URL fileUrl) throws IOException, UFSConfigMgrException {
        urlConnection = fileUrl.openConnection();
        if (urlConnection == null) {
            log.error(String.format(ERR_URL_CONNECT, fileUrl.getPath()));
            throw new UFSConfigMgrException(String.format(ERR_URL_CONNECT, fileUrl.getPath()));
        }
        urlConnection.connect();
        return urlConnection.getInputStream();
    }

    @Override
    public String getProperty(String propertyName) throws UFSConfigMgrException {
        return this.configProperties.getProperty(propertyName);
    }
}
