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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Properties;

<<<<<<< HEAD
=======
import javax.net.ssl.HttpsURLConnection;

>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
import static com.snaplogic.snaps.uniteller.Constants.DS_ALG;
import static com.snaplogic.snaps.uniteller.Constants.DUMMY_PASS;
import static com.snaplogic.snaps.uniteller.Constants.ENC_ALG;
import static com.snaplogic.snaps.uniteller.Constants.MAX_PASSWORD_LENGTH;
import static com.snaplogic.snaps.uniteller.Constants.MIN_PASSWORD_LENGTH;
<<<<<<< HEAD
import static com.snaplogic.snaps.uniteller.Constants.NONCE;
=======
import static com.snaplogic.snaps.uniteller.Constants.nonce;
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
import static com.snaplogic.snaps.uniteller.Messages.ERR_PASSWORD_EMPTY;
import static com.snaplogic.snaps.uniteller.Messages.ERR_URL_CONNECT;

/**
 * Customised UFS security manager
 *
 * @author svatada
 */
public class CustomUFSSecurityMgr implements IUFSSecurityMgr {
<<<<<<< HEAD
    private static HashMap<String, Object> instanceMap = new HashMap<String, Object>();
=======
    private static HashMap<String, Object> instanceMap = null;
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
    private String fileLocation = null;
    private URLConnection urlConnection = null;
    private Properties securityProps = null;
    private static final Logger log = LoggerFactory.getLogger(CustomUFSSecurityMgr.class);

    private CustomUFSSecurityMgr(String fileLocation) throws UFSSecurityMgrException {
        this.fileLocation = fileLocation;
<<<<<<< HEAD
=======
        try {
            URL fileUrl = new URI(fileLocation).toURL();
            this.securityInputStream = getInputStream(fileUrl);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new UFSSecurityMgrException(e.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new UFSSecurityMgrException(ex.getMessage());
        }
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        this.securityProps = new Properties();
    }

    private InputStream getInputStream(final URL fileUrl) throws UFSConfigMgrException {
        try {
            urlConnection = fileUrl.openConnection();
            urlConnection.connect();
            return urlConnection.getInputStream();
        } catch (IOException e) {
            log.error(String.format(ERR_URL_CONNECT, fileUrl.getPath()));
            throw new UFSConfigMgrException(String.format(ERR_URL_CONNECT, fileUrl.getPath()));
        }
    }

    /**
     * @param filePath
     * @return CustomUFSSecurityMgr
     * @throws UFSSecurityMgrException
     */
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

    @Override
    public String getPassword(String machineId) throws UFSSecurityMgrException {
        String passcode = null;
        InputStream securityInputStream = null;
        synchronized (this.securityProps) {
            try {
                URL fileUrl = new URI(fileLocation).toURL();
                securityInputStream = getInputStream(fileUrl);
                this.securityProps.load(securityInputStream);
            } catch (IOException | URISyntaxException | UFSConfigMgrException e) {
                log.error(e.getMessage(), e);
                throw new UFSSecurityMgrException(e.getMessage());
            }
            IOUtils.closeQuietly(securityInputStream);
            passcode = this.securityProps.getProperty(machineId);
        }
        return passcode;
    }

    @Override
    public void changePassword(String machineId, String password) throws UFSSecurityMgrException {
        OutputStream securityOutputStream = null;
        try {
            urlConnection = new URL(this.fileLocation).openConnection();
            urlConnection.connect();
            securityOutputStream = urlConnection.getOutputStream();
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
                this.securityProps.store(securityOutputStream, null);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new UFSSecurityMgrException(e.getMessage());
            } finally {
                cleanup(securityOutputStream, urlConnection);
            }
        }
    }

    @Override
    public String generatePassword() throws UFSSecurityMgrException {
        String id = null;
        try {
            byte[] byteArr = new byte[256];
            SecureRandom secureRnd = SecureRandom.getInstance(ENC_ALG);
            secureRnd.setSeed(new Long(System.currentTimeMillis()).toString().getBytes());
            MessageDigest md = MessageDigest.getInstance(DS_ALG);
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

    @Override
    public String encryptPassword(String password) throws UFSSecurityMgrException {
        String id = null;
        try {
<<<<<<< HEAD
            if (StringUtils.isEmpty(password)) {
=======
            if ((password == null) || (password.trim().length() == 0)) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
                throw new Exception(ERR_PASSWORD_EMPTY);
            }
            id = password + NONCE;
            id = Base64.encode(id.getBytes());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new UFSSecurityMgrException(e.getMessage());
        }
        return id;
    }

    @Override
    public String decryptPassword(String password) throws UFSSecurityMgrException {
        String id = null;
        try {
<<<<<<< HEAD
            if (StringUtils.isEmpty(password)) {
=======
            if ((password == null) || (password.trim().length() == 0)) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
                throw new Exception(ERR_PASSWORD_EMPTY);
            }
            byte[] pwdBytes = Base64.decode(password);
            String pwdStr = new String(pwdBytes);
            int nIndex = pwdStr.indexOf(NONCE);
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

    @SuppressWarnings("unused")
    private static String getPasswordFromEncryptedPassword(IUFSSecurityMgr securityMgr,
            String encryptedPassword) throws UFSSecurityMgrException {
        String passcode = null;
        if (StringUtils.isEmpty(encryptedPassword)) {
            passcode = DUMMY_PASS;
        } else {
            passcode = securityMgr.decryptPassword(encryptedPassword);
            if ((passcode.length() < MIN_PASSWORD_LENGTH)
                    || (passcode.length() > MAX_PASSWORD_LENGTH)) {
                passcode = DUMMY_PASS;
            }
        }
        return passcode;
    }

    private void cleanup(OutputStream outputStream, URLConnection urlConnection) {
<<<<<<< HEAD
        IOUtils.closeQuietly(outputStream);
        IOUtils.close(urlConnection);
=======
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
        if (urlConnection != null) {
            try {
                ((HttpsURLConnection) urlConnection).disconnect();
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
    }
}
