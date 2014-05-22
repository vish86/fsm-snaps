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
import com.uniteller.support.common.IUFSConfigMgr;
import com.uniteller.support.common.UFSConfigMgrException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/*
 * Custom UFS configuration manager
 * 
 * @author svatada
 * */
public class CustomUFSConfigMgr implements IUFSConfigMgr {
    private static HashMap<String, Object> instanceMap = null;
    private Properties configProperties = null;
    @Inject
    private Utilities util;
    private static final Logger log = LoggerFactory.getLogger(CustomUFSConfigMgr.class);
    static {
        instanceMap = new HashMap<String, Object>();
    }

    private CustomUFSConfigMgr(String fileLocation) throws UFSConfigMgrException {
        try {
            File file = new File(util.getUriFor(fileLocation));
            log.debug("UFS config file: ", file);
            FileInputStream fis = new FileInputStream(file);
            log.debug("UFS config file stream: ", fis);
            this.configProperties = new Properties();
            this.configProperties.load(fis);
        } catch (IOException e) {
            throw new UFSConfigMgrException(e.getMessage());
        } catch (Exception ex) {
            throw new UFSConfigMgrException(ex.getMessage());
        }
    }

    public static CustomUFSConfigMgr getInstance(String filePath) throws UFSConfigMgrException {
        CustomUFSConfigMgr customUFSConfigMgr = (CustomUFSConfigMgr) instanceMap.get(filePath);
        if (customUFSConfigMgr == null) {
            synchronized (CustomUFSConfigMgr.class) {
                log.debug(filePath);
                customUFSConfigMgr = new CustomUFSConfigMgr(filePath);
                instanceMap.put(filePath, customUFSConfigMgr);
            }
        }
        return customUFSConfigMgr;
    }

    public String getProperty(String propertyName) throws UFSConfigMgrException {
        return this.configProperties.getProperty(propertyName);
    }
}
