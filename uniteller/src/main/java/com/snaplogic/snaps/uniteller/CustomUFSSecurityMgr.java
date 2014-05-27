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

import com.uniteller.support.common.IUFSSecurityMgr;
import com.uniteller.support.common.UFSConfigMgrException;
import com.uniteller.support.common.UFSSecurityMgrException;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import static com.snaplogic.snaps.uniteller.Constants.DUMMY_PASS;
import static com.snaplogic.snaps.uniteller.Messages.ERR_URL_CONNECT;

/* Custom UFS security manager
 * 
 * @author svatada
 * */
public class CustomUFSSecurityMgr implements IUFSSecurityMgr {
    private static HashMap<String, Object> instanceMap = null;
    private static String nonce = "ItSihNsYzB495QQ6J5MXr0/O1rI=";
    private String fileLocation = null;
    private InputStream securityInputStream;
    private OutputStream securityOutputStream;
    private URLConnection urlConnection;
    private Properties securityProps = null;
    private static final Logger log = LoggerFactory.getLogger(CustomUFSSecurityMgr.class);
    static {
        instanceMap = new HashMap<String, Object>();
    }

    private CustomUFSSecurityMgr(String fileLocation) throws UFSSecurityMgrException {
        this.fileLocation = fileLocation;
        try {
            URL fileUrl = new URI(fileLocation).toURL();
            log.debug("URL:" + fileUrl.toString());
            this.securityInputStream = getInputStream(fileUrl);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new UFSSecurityMgrException(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new UFSSecurityMgrException(ex.getMessage());
        }
        this.securityProps = new Properties();
    }

    static InputStream getInputStream(final URL fileUrl) throws IOException, UFSConfigMgrException {
        URLConnection urlConnection = null;
        urlConnection = fileUrl.openConnection();
        if (urlConnection == null) {
            log.error(String.format(ERR_URL_CONNECT, fileUrl.getPath()));
            throw new UFSConfigMgrException(String.format(ERR_URL_CONNECT, fileUrl.getPath()));
        }
        urlConnection.connect();
        return urlConnection.getInputStream();
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
                this.securityProps.load(this.securityInputStream);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new UFSSecurityMgrException(e.getMessage());
            }
            passcode = this.securityProps.getProperty(machineId);
        }
        return passcode;
    }

    public void changePassword(String machineId, String password) throws UFSSecurityMgrException {
        try {
            if (this.securityOutputStream != null) {
                cleanup(securityOutputStream, urlConnection);
            }
            initOutputStream();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new UFSSecurityMgrException(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new UFSSecurityMgrException(ex.getMessage());
        }

        synchronized (this.securityProps) {
            this.securityProps.put(machineId, password);
            try {
                this.securityProps.store(this.securityOutputStream, null);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
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
            id = password + nonce;
            id = Base64.encode(id.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
            int nIndex = pwdStr.indexOf(nonce);
            if (nIndex != -1)
                id = pwdStr.substring(0, nIndex);
            else
                id = password;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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

    private void initOutputStream() throws IOException {
        urlConnection = new URL(this.fileLocation).openConnection();
        if (urlConnection != null) {
            urlConnection.connect();
            securityOutputStream = urlConnection.getOutputStream();
        }
    }

    private void cleanup(OutputStream outputStream, URLConnection urlConnection) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {

            }
        }
        if (urlConnection != null) {
            try {
                ((HttpsURLConnection) urlConnection).disconnect();
            } catch (Exception e) {
            }
        }
    }
}
