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
import com.uniteller.support.common.IUFSSecurityMgr;
import com.uniteller.support.common.UFSSecurityMgrException;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Properties;

/* Custom UFS security manager
 * 
 * @author svatada
 * */
public class CustomUFSSecurityMgr implements IUFSSecurityMgr {
    protected static final String DUMMY_PASS = "DUMMY_PASS";
    protected static final int MIN_PASSWORD_LENGTH = 8;
    protected static final int MAX_PASSWORD_LENGTH = 16;
    private static HashMap<String, Object> instanceMap = null;
    private static String nonce_S = "ItSihNsYzB495QQ6J5MXr0/O1rI=";
    private String fileLocation = null;
    private FileInputStream securityFileInput;
    private FileOutputStream securityFileOutput;
    private Properties securityProps = null;
    @Inject
    private Utilities util;
    static {
        instanceMap = new HashMap<String, Object>();
    }

    private CustomUFSSecurityMgr(String fileLocation) throws UFSSecurityMgrException {
        this.fileLocation = fileLocation;
        try {
            File file = new File(util.getUriFor(fileLocation));
            this.securityFileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new UFSSecurityMgrException(e.getMessage());
        } catch (Exception ex) {
            throw new UFSSecurityMgrException(ex.getMessage());
        }
        this.securityProps = new Properties();
    }

    public static IUFSSecurityMgr getInstance(String filePath) throws UFSSecurityMgrException {
        CustomUFSSecurityMgr customUFSSecurityMgr = (CustomUFSSecurityMgr) instanceMap
                .get(filePath);
        if (customUFSSecurityMgr == null) {
            synchronized (CustomUFSSecurityMgr.class) {
                customUFSSecurityMgr = new CustomUFSSecurityMgr(filePath);
                instanceMap.put(filePath, customUFSSecurityMgr);
            }
        }
        return customUFSSecurityMgr;
    }

    public String getPassword(String machineId) throws UFSSecurityMgrException {
        String passcode = null;

        synchronized (this.securityProps) {
            try {
                this.securityProps.load(this.securityFileInput);
            } catch (IOException e) {
                throw new UFSSecurityMgrException(e.getMessage());
            }
            passcode = this.securityProps.getProperty(machineId);
        }

        return passcode;
    }

    public void changePassword(String machineId, String password) throws UFSSecurityMgrException {
        File file = null;
        try {
            file = new File(this.fileLocation);
            if (this.securityFileOutput != null) {
                this.securityFileOutput.close();
            }
            this.securityFileOutput = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new UFSSecurityMgrException(e.getMessage());
        } catch (Exception ex) {
            throw new UFSSecurityMgrException(ex.getMessage());
        }

        synchronized (this.securityProps) {
            this.securityProps.put(machineId, password);
            try {
                this.securityProps.store(this.securityFileOutput, null);
            } catch (IOException e) {
                throw new UFSSecurityMgrException(e.getMessage());
            }
        }
    }

    public String generatePassword() throws UFSSecurityMgrException {
        String id = null;
        try {
            byte[] byteArr = new byte[256];
            SecureRandom secureRnd = SecureRandom.getInstance("SHA1PRNG");
            secureRnd.setSeed(new Long(System.currentTimeMillis()).toString().getBytes());
            MessageDigest md = MessageDigest.getInstance("MD5");
            secureRnd.nextBytes(byteArr);
            md.update(byteArr);
            md.update(new Long(System.currentTimeMillis()).toString().getBytes());
            byteArr = md.digest();
            id = Base64.encode(byteArr, 0, 12);
        } catch (Exception e) {
            throw new UFSSecurityMgrException(e.getMessage());
        }
        return id;
    }

    public String encryptPassword(String password) throws UFSSecurityMgrException {
        String id = null;
        try {
            if ((password == null) || (password.trim().length() == 0)) {
                throw new Exception("Password is null or empty");
            }
            id = password + nonce_S;
            id = Base64.encode(id.getBytes());
        } catch (Exception e) {
            throw new UFSSecurityMgrException(e.getMessage());
        }
        return id;
    }

    public String decryptPassword(String password) throws UFSSecurityMgrException {
        String id = null;
        try {
            if ((password == null) || (password.trim().length() == 0)) {
                throw new Exception("Password is null or empty");
            }
            byte[] pwdBytes = Base64.decode(password);
            String pwdStr = new String(pwdBytes);
            int nIndex = pwdStr.indexOf(nonce_S);
            if (nIndex != -1)
                id = pwdStr.substring(0, nIndex);
            else
                id = password;
        } catch (Exception e) {
            throw new UFSSecurityMgrException(e.getMessage());
        }
        return id;
    }

    public static String getPasswordFromEncryptedPassword(IUFSSecurityMgr securityMgr,
            String encryptedPassword) throws UFSSecurityMgrException {
        String passcode = null;
        if (StringUtils.isEmpty(encryptedPassword.trim())) {
            passcode = DUMMY_PASS;
        } else {
            passcode = securityMgr.decryptPassword(encryptedPassword);
            if ((passcode.length() < 8) || (passcode.length() > 16)) {
                passcode = DUMMY_PASS;
            }
        }
        return passcode;
    }
}