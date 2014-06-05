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

import com.snaplogic.snaps.uniteller.util.Utilities;
import com.uniteller.support.common.ChangePassword;
import com.uniteller.support.common.CommonFunctions;
import com.uniteller.support.common.IUFSConfigMgr;
import com.uniteller.support.common.IUFSSecurityMgr;
import com.uniteller.support.common.RequestDistributor;
import com.uniteller.support.common.UFSConfigMgrException;
import com.uniteller.support.common.UFSSecurityMgrException;
import com.uniteller.support.common.UFSUnknownMachineIdException;
import com.uniteller.support.communication.IClientMessenger;
import com.uniteller.support.communication.foliocreation.*;
import com.uniteller.support.foliocreationclient.*;
import com.uniteller.support.util.GlobalFunctions;
import com.uniteller.support.util.UFSClientLogger;
import com.uniteller.support.util.UFSGeneralException;
import com.uniteller.support.util.UFSSystemError;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.snaplogic.snaps.uniteller.Constants.*;
import static com.snaplogic.snaps.uniteller.Messages.CHANGE_PASS_ERR;
import static com.snaplogic.snaps.uniteller.Messages.LOG_ERROR;
import static com.snaplogic.snaps.uniteller.Messages.NORESPONSEIOOBJECT;
import static com.snaplogic.snaps.uniteller.Messages.NO_PASSWORD_ERROR;
import static com.snaplogic.snaps.uniteller.Messages.NO_PASSWORD_FOR_MACHINE_ID;
import static com.snaplogic.snaps.uniteller.Messages.NO_UFS_OBJECT;
import static com.snaplogic.snaps.uniteller.Messages.NULLEXCEPTION;
import static com.snaplogic.snaps.uniteller.Messages.NULLREQUEST;

/**
 * Customised UFS folio creation client
 *
 * @author svatada
 */
public class CustomUFSFolioCreationClient {
    private static final String FORMAT_STRING = "{}:{}";
    private static final UFSClientLogger ufsLogger = UFSClientLogger
            .getLogger(CustomUFSFolioCreationClient.class);
    private static final Logger log = LoggerFactory.getLogger(BaseService.class);
    private static boolean initialized = false;
    private static String organizationIdS;
    private static String companyIdS;
    private static String machineIdS;
    private static String fclAPIURLSuffix;
    private final IUFSConfigMgr configMgr;
    private final IUFSSecurityMgr securityMgr;

    /**
     * @param confMgr
     * @param securityMgr
     * @throws UFSFolioCreationClientException
     */
    public CustomUFSFolioCreationClient(IUFSConfigMgr confMgr, IUFSSecurityMgr securityMgr)
            throws UFSFolioCreationClientException {
        if (confMgr == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, IUFSConfigMgr.class.getName()));
        }
        if (securityMgr == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, IUFSSecurityMgr.class.getName()));
        }
        this.configMgr = confMgr;
        this.securityMgr = securityMgr;
        try {
            if (!initialized) {
                init(confMgr, securityMgr);
            }
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    /**
     * @param request
     * @return UFSCreateSCTxResp
     * @throws UFSFolioCreationClientException
     */
    public UFSCreateSCTxResp createSCTx(UFSCreateSCTxReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSCreateSCTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return createSCTxProxy(request);
            }
            String companyId = null;
            CreateSCTxResponse createSCTxResponse = null;
            UFSCreateSCTxResp ufsCreateSCTxResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            CreateSCTxRequest createSCTxReq = Utilities.getInstance()
                    .convertToCreateSCTxRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            createSCTxReq.setMachineId(machineIdS);
            createSCTxReq.setMachinePassword(oldPassword);
            createSCTxResponse = sendCreateSCTxRequest(createSCTxReq);
            responseCode = createSCTxResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsCreateSCTxResp = new UFSCreateSCTxResp();
                    ufsCreateSCTxResp.setResponseCode(responseCode);
                    ufsCreateSCTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsCreateSCTxResp);
                    return ufsCreateSCTxResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                createSCTxReq.setMachinePassword(newPassword);
                createSCTxResponse = sendCreateSCTxRequest(createSCTxReq);
                responseCode = createSCTxResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsCreateSCTxResp = new UFSCreateSCTxResp();
                ufsCreateSCTxResp.setResponseCode(responseCode);
                ufsCreateSCTxResp.setResponseString(createSCTxResponse.getResponseString());
                logUFSObj(ufsCreateSCTxResp);
                return ufsCreateSCTxResp;
            }
            ufsCreateSCTxResp = Utilities.getInstance().convertToUFSCreateSCTxRespObject(
                    createSCTxResponse);
            logUFSObj(ufsCreateSCTxResp);
            return ufsCreateSCTxResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return ufsConfirmSCTxResp
     * @throws UFSFolioCreationClientException
     */
    public UFSConfirmSCTxResp confirmSCTx(UFSConfirmSCTxReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSConfirmSCTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return confirmSCTxProxy(request);
            }
            String companyId = null;
            ConfirmSCTxResponse confirmSCTxResponse = null;
            UFSConfirmSCTxResp ufsConfirmSCTxResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            ConfirmSCTxRequest confirmSCTxReq = Utilities.getInstance()
                    .convertToConfirmSCTxRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            confirmSCTxReq.setMachineId(machineIdS);
            confirmSCTxReq.setMachinePassword(oldPassword);
            confirmSCTxResponse = sendConfirmSCTxRequest(confirmSCTxReq);
            responseCode = confirmSCTxResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsConfirmSCTxResp = new UFSConfirmSCTxResp();
                    ufsConfirmSCTxResp.setResponseCode(responseCode);
                    ufsConfirmSCTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsConfirmSCTxResp);
                    return ufsConfirmSCTxResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                confirmSCTxReq.setMachinePassword(newPassword);
                confirmSCTxResponse = sendConfirmSCTxRequest(confirmSCTxReq);
                responseCode = confirmSCTxResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsConfirmSCTxResp = new UFSConfirmSCTxResp();
                ufsConfirmSCTxResp.setResponseCode(responseCode);
                ufsConfirmSCTxResp.setResponseString(confirmSCTxResponse.getResponseString());
                logUFSObj(ufsConfirmSCTxResp);
                return ufsConfirmSCTxResp;
            }
            ufsConfirmSCTxResp = Utilities.getInstance().convertToUFSConfirmSCTxRespObject(
                    confirmSCTxResponse);
            logUFSObj(ufsConfirmSCTxResp);
            return ufsConfirmSCTxResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return ufsCreateTxResp
     * @throws UFSFolioCreationClientException
     */
    public UFSCreateTxResp createTx(UFSCreateTxReq request) throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSCreateTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return createTxProxy(request);
            }
            String companyId = null;
            CreateTxResponse createTxResponse = null;
            UFSCreateTxResp ufsCreateTxResp = null;
            String responseCode = null;

            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            CreateTxRequest createTxReq = Utilities.getInstance().convertToCreateTxRequestObject(
                    request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            createTxReq.setMachineId(machineIdS);
            createTxReq.setMachinePassword(oldPassword);
            createTxResponse = sendCreateTxRequest(createTxReq);
            responseCode = createTxResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsCreateTxResp = new UFSCreateTxResp();
                    ufsCreateTxResp.setResponseCode(responseCode);
                    ufsCreateTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsCreateTxResp);
                    return ufsCreateTxResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                createTxReq.setMachinePassword(newPassword);
                createTxResponse = sendCreateTxRequest(createTxReq);
                responseCode = createTxResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsCreateTxResp = new UFSCreateTxResp();
                ufsCreateTxResp.setResponseCode(responseCode);
                ufsCreateTxResp.setResponseString(createTxResponse.getResponseString());
                logUFSObj(ufsCreateTxResp);
                return ufsCreateTxResp;
            }
            ufsCreateTxResp = Utilities.getInstance().convertToUFSCreateTxRespObject(
                    createTxResponse);
            logUFSObj(ufsCreateTxResp);
            return ufsCreateTxResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return ufsCancelTxResp
     * @throws UFSFolioCreationClientException
     */
    public UFSCancelTxResp cancelTx(UFSCancelTxReq request) throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSCancelTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return cancelTxProxy(request);
            }
            String companyId = null;
            TxCancelResponse cancelTxResponse = null;
            UFSCancelTxResp ufsCancelTxResp = null;
            String responseCode = null;

            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            TxCancelRequest cancelTxReq = Utilities.getInstance().convertToTxCancelRequestObject(
                    request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            cancelTxReq.setMachineId(machineIdS);
            cancelTxReq.setMachinePassword(oldPassword);
            cancelTxResponse = sendCancelTxRequest(cancelTxReq);
            responseCode = cancelTxResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsCancelTxResp = new UFSCancelTxResp();
                    ufsCancelTxResp.setResponseCode(responseCode);
                    ufsCancelTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsCancelTxResp);
                    return ufsCancelTxResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                cancelTxReq.setMachinePassword(newPassword);
                cancelTxResponse = sendCancelTxRequest(cancelTxReq);
                responseCode = cancelTxResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsCancelTxResp = new UFSCancelTxResp();
                ufsCancelTxResp.setResponseCode(responseCode);
                ufsCancelTxResp.setResponseString(cancelTxResponse.getResponseString());
                logUFSObj(ufsCancelTxResp);
                return ufsCancelTxResp;
            }
            ufsCancelTxResp = Utilities.getInstance().convertToUFSCancelTxRespObject(
                    cancelTxResponse);
            logUFSObj(ufsCancelTxResp);
            return ufsCancelTxResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return ufsInfoModifyResp
     * @throws UFSFolioCreationClientException
     */
    public UFSInfoModifyResp infoModifyTx(UFSInfoModifyReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSInfoModifyReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return infoModifyTxProxy(request);
            }
            String companyId = null;
            InfoModifyResponse infoModifyResponse = null;
            UFSInfoModifyResp ufsInfoModifyResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            InfoModifyRequest infoModifyReq = Utilities.getInstance()
                    .convertToInfoModifyRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            infoModifyReq.setMachineId(machineIdS);
            infoModifyReq.setMachinePassword(oldPassword);
            infoModifyResponse = sendInfoModifyRequest(infoModifyReq);
            responseCode = infoModifyResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsInfoModifyResp = new UFSInfoModifyResp();
                    ufsInfoModifyResp.setResponseCode(responseCode);
                    ufsInfoModifyResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsInfoModifyResp);
                    return ufsInfoModifyResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                infoModifyReq.setMachinePassword(newPassword);
                infoModifyResponse = sendInfoModifyRequest(infoModifyReq);
                responseCode = infoModifyResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsInfoModifyResp = new UFSInfoModifyResp();
                ufsInfoModifyResp.setResponseCode(responseCode);
                ufsInfoModifyResp.setResponseString(infoModifyResponse.getResponseString());
                logUFSObj(ufsInfoModifyResp);
                return ufsInfoModifyResp;
            }
            ufsInfoModifyResp = Utilities.getInstance().convertToUFSInfoModifyRespObject(
                    infoModifyResponse);
            logUFSObj(ufsInfoModifyResp);
            return ufsInfoModifyResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return UFSNotificationResp
     * @throws UFSFolioCreationClientException
     */
    public UFSNotificationResp notification(UFSNotificationReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSNotificationReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if (request.getNotificationCount() == PROXY_COUNT) {
                return getNotificationsProxy(request);
            }
            String companyId = null;
            NotificationResponse notificationResponse = null;
            UFSNotificationResp ufsNotificationResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            NotificationRequest notificationReq = Utilities.getInstance()
                    .convertToNotificationRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            notificationReq.setMachineId(machineIdS);
            notificationReq.setMachinePassword(oldPassword);
            notificationResponse = sendNotificationRequest(notificationReq);
            responseCode = notificationResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsNotificationResp = new UFSNotificationResp();
                    ufsNotificationResp.setResponseCode(responseCode);
                    ufsNotificationResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsNotificationResp);
                    return ufsNotificationResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                notificationReq.setMachinePassword(newPassword);
                notificationResponse = sendNotificationRequest(notificationReq);
                responseCode = notificationResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsNotificationResp = new UFSNotificationResp();
                ufsNotificationResp.setResponseCode(responseCode);
                ufsNotificationResp.setResponseString(notificationResponse.getResponseString());
                logUFSObj(ufsNotificationResp);
                return ufsNotificationResp;
            }
            ufsNotificationResp = Utilities.getInstance().convertToUFSNotificationRespObject(
                    notificationResponse);
            logUFSObj(ufsNotificationResp);
            return ufsNotificationResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return UFSNotificationConfirmResp
     * @throws UFSFolioCreationClientException
     */
    public UFSNotificationConfirmResp notificationConfirm(UFSNotificationConfirmReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSNotificationConfirmReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return notificationConfirmProxy(request);
            }
            String companyId = null;
            NotificationConfirmResponse notifConfirmResponse = null;
            UFSNotificationConfirmResp ufsNotificationConfirmResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            NotificationConfirmRequest notifConfirmReq = Utilities.getInstance()
                    .convertToNotificationConfirmRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            notifConfirmReq.setMachineId(machineIdS);
            notifConfirmReq.setMachinePassword(oldPassword);
            notifConfirmResponse = sendNotificationConfirmRequest(notifConfirmReq);
            responseCode = notifConfirmResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsNotificationConfirmResp = new UFSNotificationConfirmResp();
                    ufsNotificationConfirmResp.setResponseCode(responseCode);
                    logUFSObj(ufsNotificationConfirmResp);
                    return ufsNotificationConfirmResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                notifConfirmReq.setMachinePassword(newPassword);
                notifConfirmResponse = sendNotificationConfirmRequest(notifConfirmReq);
                responseCode = notifConfirmResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsNotificationConfirmResp = new UFSNotificationConfirmResp();
                ufsNotificationConfirmResp.setResponseCode(responseCode);
                logUFSObj(ufsNotificationConfirmResp);
                return ufsNotificationConfirmResp;
            }
            ufsNotificationConfirmResp = Utilities.getInstance()
                    .convertToUFSNotificationConfirmRespObject(notifConfirmResponse);
            logUFSObj(ufsNotificationConfirmResp);
            return ufsNotificationConfirmResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return UFSGetTxDetailsResp
     * @throws UFSFolioCreationClientException
     */
    public UFSGetTxDetailsResp getTxDetails(UFSGetTxDetailsReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSGetTxDetailsReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXY_TX_IDENTIFIER))) {
                return getTxDetailsProxy(request);
            }
            String companyId = null;
            GetTxDetailsResponse getTxDetailsResponse = null;
            UFSGetTxDetailsResp ufsGetTxDetailsResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            GetTxDetailsRequest getTxDetailsReq = Utilities.getInstance()
                    .convertToGetTxDetailsRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            getTxDetailsReq.setMachineId(machineIdS);
            getTxDetailsReq.setMachinePassword(oldPassword);
            getTxDetailsResponse = sendGetTxDetailsRequest(getTxDetailsReq);
            responseCode = getTxDetailsResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsGetTxDetailsResp = new UFSGetTxDetailsResp();
                    ufsGetTxDetailsResp.setResponseCode(responseCode);
                    ufsGetTxDetailsResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsGetTxDetailsResp);
                    return ufsGetTxDetailsResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                getTxDetailsReq.setMachinePassword(newPassword);
                getTxDetailsResponse = sendGetTxDetailsRequest(getTxDetailsReq);
                responseCode = getTxDetailsResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsGetTxDetailsResp = new UFSGetTxDetailsResp();
                ufsGetTxDetailsResp.setResponseCode(responseCode);
                ufsGetTxDetailsResp.setResponseString(getTxDetailsResponse.getResponseString());
                logUFSObj(ufsGetTxDetailsResp);
                return ufsGetTxDetailsResp;
            }
            ufsGetTxDetailsResp = Utilities.getInstance().convertToUFSGetTxDetailsRespObject(
                    getTxDetailsResponse);
            logUFSObj(ufsGetTxDetailsResp);
            return ufsGetTxDetailsResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @param request
     * @return UFSQuickQuoteResp
     * @throws UFSFolioCreationClientException
     */
    public UFSQuickQuoteResp quickQuote(UFSQuickQuoteReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSQuickQuoteReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TX_CONTEXT_PREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getPaymentLocation() != null)
                    && (request.getPaymentLocation().equals(PROXY_PAYMENT_LOCATION))) {
                return quickQuoteProxy(request);
            }
            String companyId = null;
            QuickQuoteResponse quickQuoteResponse = null;
            UFSQuickQuoteResp ufsQuickQuoteResp = null;
            String responseCode = null;
            if (request.getCorrespondentCode() == null) {
                companyId = companyIdS;
            } else {
                companyId = request.getCorrespondentCode();
            }
            logUFSObj(request);
            QuickQuoteRequest quickQuoteReq = Utilities.getInstance()
                    .convertToQuickQuoteRequestObject(request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (StringUtils.isEmpty(encryptedPassword)) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            quickQuoteReq.setMachineId(machineIdS);
            quickQuoteReq.setMachinePassword(oldPassword);
            quickQuoteResponse = sendQuickQuoteRequest(quickQuoteReq);
            responseCode = quickQuoteResponse.getResponseCode();
            if (CHANGE_PASSWORD_ERROR_CODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsQuickQuoteResp = new UFSQuickQuoteResp();
                    ufsQuickQuoteResp.setResponseCode(responseCode);
                    ufsQuickQuoteResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSObj(ufsQuickQuoteResp);
                    return ufsQuickQuoteResp;
                }
                synchronized (this.securityMgr) {
                    encryptedPassword = this.securityMgr.getPassword(machineIdS);
                }
                String newPassword = this.securityMgr.decryptPassword(encryptedPassword);
                quickQuoteReq.setMachinePassword(newPassword);
                quickQuoteResponse = sendQuickQuoteRequest(quickQuoteReq);
                responseCode = quickQuoteResponse.getResponseCode();
            }
            if (!isSuccessOrWarning(responseCode)) {
                ufsQuickQuoteResp = new UFSQuickQuoteResp();
                ufsQuickQuoteResp.setResponseCode(responseCode);
                ufsQuickQuoteResp.setResponseString(quickQuoteResponse.getResponseString());
                logUFSObj(ufsQuickQuoteResp);
                return ufsQuickQuoteResp;
            }
            ufsQuickQuoteResp = Utilities.getInstance().convertToUFSQuickQuoteRespObject(
                    quickQuoteResponse);
            logUFSObj(ufsQuickQuoteResp);
            return ufsQuickQuoteResp;
        } catch (UFSFolioCreationClientException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        } finally {
            UFSClientLogger.removeContext();
        }
    }

    /**
     * @return version
     */
    public static String getVersion() {
        return UFSFolioCreationClient.getVersion();
    }

    private static synchronized void init(IUFSConfigMgr confMgr, IUFSSecurityMgr securityMgr)
            throws Exception {
        if (!initialized) {
            Properties errorProps = new Properties();
            for (int i = 0; i < UFSClientLogger.requiredPropsArr.length; i++) {
                putInPropertiesIfNonNull(errorProps, UFSClientLogger.requiredPropsArr[i],
                        confMgr.getProperty(UFSClientLogger.requiredPropsArr[i]));
            }
            UFSClientLogger.init(errorProps);
            organizationIdS = CommonFunctions.getProperty(confMgr, DEFAULT_ORGANIZATION_ID,
                    ufsLogger);
            companyIdS = CommonFunctions.getProperty(confMgr, DEFAULT_COMPANY_ID, ufsLogger);
            machineIdS = CommonFunctions.getProperty(confMgr, DEFAULT_MACHINE_ID, ufsLogger);
            fclAPIURLSuffix = CommonFunctions.getProperty(confMgr, FOLIO_CREATION_API_URL_SUFFIX,
                    ufsLogger);
            initialized = true;
        }
    }

    private static void putInPropertiesIfNonNull(Properties props, String key, String value) {
        if (value != null) {
            props.put(key, value);
        }
    }

    private UFSFolioCreationClientException getUFSFolioCreationClientException(Exception ex) {
        if (ex == null) {
            ex = new UFSGeneralException(NULLEXCEPTION);
        }
        log.error(ex.getMessage(), ex);
        String errorCode = EXCEPTION_ERROR_MAP.get(ex.getClass().getName());
        if (errorCode == null) {
            errorCode = GENERAL_SYSTEM_ERROR;
        }
        String errorMsg = UFS_ERROR_MSG_LIST.get(errorCode);
        return new UFSFolioCreationClientException(errorCode, errorMsg);
    }

    private CreateTxResponse sendCreateTxRequest(CreateTxRequest createTxRequest) throws Exception {
        CreateTxResponse createTxResponse = null;
        IClientMessenger createTxMessenger = new CreateTxMessenger();
        createTxMessenger.setIOObject(createTxRequest);
        createTxMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(createTxMessenger);
        if ((createTxMessenger.getIOObject() != null)
                && ((createTxMessenger.getIOObject() instanceof CreateTxResponse))) {
            createTxResponse = (CreateTxResponse) createTxMessenger.getIOObject();
        } else {
            throw new UFSSystemError(
                    String.format(NORESPONSEIOOBJECT, createTxMessenger.getClass()));
        }
        return createTxResponse;
    }

    private CreateSCTxResponse sendCreateSCTxRequest(CreateSCTxRequest createSCTxRequest)
            throws Exception {
        CreateSCTxResponse createSCTxResponse = null;
        IClientMessenger createSCTxMessenger = new CreateSCTxMessenger();
        createSCTxMessenger.setIOObject(createSCTxRequest);
        createSCTxMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(createSCTxMessenger);
        if ((createSCTxMessenger.getIOObject() != null)
                && ((createSCTxMessenger.getIOObject() instanceof CreateSCTxResponse))) {
            createSCTxResponse = (CreateSCTxResponse) createSCTxMessenger.getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    createSCTxMessenger.getClass()));
        }
        return createSCTxResponse;
    }

    private ConfirmSCTxResponse sendConfirmSCTxRequest(ConfirmSCTxRequest confirmSCTxRequest)
            throws Exception {
        ConfirmSCTxResponse confirmSCTxResponse = null;
        IClientMessenger confirmSCTxMessenger = new ConfirmSCTxMessenger();
        confirmSCTxMessenger.setIOObject(confirmSCTxRequest);
        confirmSCTxMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(confirmSCTxMessenger);
        if ((confirmSCTxMessenger.getIOObject() != null)
                && ((confirmSCTxMessenger.getIOObject() instanceof ConfirmSCTxResponse))) {
            confirmSCTxResponse = (ConfirmSCTxResponse) confirmSCTxMessenger.getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    confirmSCTxMessenger.getClass()));
        }
        return confirmSCTxResponse;
    }

    private TxCancelResponse sendCancelTxRequest(TxCancelRequest cancelTxRequest) throws Exception {
        TxCancelResponse cancelTxResponse = null;
        IClientMessenger cancelTxMessenger = new CancelTxMessenger();
        cancelTxMessenger.setIOObject(cancelTxRequest);
        cancelTxMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(cancelTxMessenger);
        if ((cancelTxMessenger.getIOObject() != null)
                && ((cancelTxMessenger.getIOObject() instanceof TxCancelResponse))) {
            cancelTxResponse = (TxCancelResponse) cancelTxMessenger.getIOObject();
        } else {
            throw new UFSSystemError(
                    String.format(NORESPONSEIOOBJECT, cancelTxMessenger.getClass()));
        }
        return cancelTxResponse;
    }

    private InfoModifyResponse sendInfoModifyRequest(InfoModifyRequest infoModifyRequest)
            throws Exception {
        InfoModifyResponse infoModifyResponse = null;
        IClientMessenger infoModifyMessenger = new InfoModifyMessenger();
        infoModifyMessenger.setIOObject(infoModifyRequest);
        infoModifyMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(infoModifyMessenger);
        if ((infoModifyMessenger.getIOObject() != null)
                && ((infoModifyMessenger.getIOObject() instanceof InfoModifyResponse))) {
            infoModifyResponse = (InfoModifyResponse) infoModifyMessenger.getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    infoModifyMessenger.getClass()));
        }
        return infoModifyResponse;
    }

    private NotificationResponse sendNotificationRequest(NotificationRequest notificationRequest)
            throws Exception {
        NotificationResponse notificationResponse = null;
        IClientMessenger notificationMessenger = new NotificationMessenger();
        notificationMessenger.setIOObject(notificationRequest);
        notificationMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(notificationMessenger);
        if ((notificationMessenger.getIOObject() != null)
                && ((notificationMessenger.getIOObject() instanceof NotificationResponse))) {
            notificationResponse = (NotificationResponse) notificationMessenger.getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    notificationMessenger.getClass()));
        }
        return notificationResponse;
    }

    private NotificationConfirmResponse sendNotificationConfirmRequest(
            NotificationConfirmRequest notifConfirmRequest) throws Exception {
        NotificationConfirmResponse notifConfirmResponse = null;
        IClientMessenger notifConfirmMessenger = new NotificationConfirmMessenger();
        notifConfirmMessenger.setIOObject(notifConfirmRequest);
        notifConfirmMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(notifConfirmMessenger);
        if ((notifConfirmMessenger.getIOObject() != null)
                && ((notifConfirmMessenger.getIOObject() instanceof NotificationConfirmResponse))) {
            notifConfirmResponse = (NotificationConfirmResponse) notifConfirmMessenger
                    .getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    notifConfirmMessenger.getClass()));
        }
        return notifConfirmResponse;
    }

    private GetTxDetailsResponse sendGetTxDetailsRequest(GetTxDetailsRequest getTxDetailsRequest)
            throws Exception {
        GetTxDetailsResponse getTxDetailsResponse = null;
        IClientMessenger getTxDetailsMessenger = new GetTxDetailsMessenger();
        getTxDetailsMessenger.setIOObject(getTxDetailsRequest);
        getTxDetailsMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(getTxDetailsMessenger);
        if ((getTxDetailsMessenger.getIOObject() != null)
                && ((getTxDetailsMessenger.getIOObject() instanceof GetTxDetailsResponse))) {
            getTxDetailsResponse = (GetTxDetailsResponse) getTxDetailsMessenger.getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    getTxDetailsMessenger.getClass()));
        }
        return getTxDetailsResponse;
    }

    private QuickQuoteResponse sendQuickQuoteRequest(QuickQuoteRequest quickQuoteRequest)
            throws Exception {
        QuickQuoteResponse quickQuoteResponse = null;
        IClientMessenger quickQuoteMessenger = new QuickQuoteMessenger();
        quickQuoteMessenger.setIOObject(quickQuoteRequest);
        quickQuoteMessenger.setURLSuffix(fclAPIURLSuffix);
        RequestDistributor reqDist = RequestDistributor.getInstance(this.configMgr);
        reqDist.sendMessage(quickQuoteMessenger);
        if ((quickQuoteMessenger.getIOObject() != null)
                && ((quickQuoteMessenger.getIOObject() instanceof QuickQuoteResponse))) {
            quickQuoteResponse = (QuickQuoteResponse) quickQuoteMessenger.getIOObject();
        } else {
            throw new UFSSystemError(String.format(NORESPONSEIOOBJECT,
                    quickQuoteMessenger.getClass()));
        }
        return quickQuoteResponse;
    }

    private boolean isSuccessOrWarning(String errorCode) {
        return (errorCode != null)
                && ((errorCode.equals(PROXY_SUCCESS_RESPONSE)) || (WARNING_INDICATOR
                        .equals(errorCode.substring(3, 5))));
    }

    private String getPasswordFromEncryptedPassword(String encryptedPassword)
            throws UFSSecurityMgrException {
        String ret = null;
        if (BLANK.equals(encryptedPassword.trim())) {
            ret = DUMMY_PASS;
        } else {
            ret = this.securityMgr.decryptPassword(encryptedPassword);
            if ((ret.length() < MIN_PASSWORD_LENGTH) || (ret.length() > MAX_PASSWORD_LENGTH)) {
                ret = DUMMY_PASS;
            }
        }
        if (DUMMY_PASS.equals(ret)) {
            log.info(String.format(NO_PASSWORD_ERROR, machineIdS));
        }
        return ret;
    }

    private String sendChangePassword(String oldPassword, String companyId, String branchId)
            throws UFSSecurityMgrException, UFSConfigMgrException, UFSGeneralException {
        String errorCode = null;
        ChangePassword changePassword = ChangePassword.getInstance(this.securityMgr,
                this.configMgr, organizationIdS, companyId, machineIdS);
        synchronized (changePassword) {
            changePassword.setOldPassword(oldPassword);
            changePassword.setBranchId(branchId);
            changePassword.changePassword();
            errorCode = changePassword.getErrorCode();
        }
        return errorCode;
    }

    private UFSCreateTxResp createTxProxy(UFSCreateTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSCreateTxResp ufsCreateTxResp = null;
            logUFSObj(request);
            ufsCreateTxResp = new UFSCreateTxResp();
            ufsCreateTxResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsCreateTxResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsCreateTxResp.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsCreateTxResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsCreateTxResp.setProcessingDateEST(new GregorianCalendar());
            ufsCreateTxResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsCreateTxResp);
            return ufsCreateTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSCreateSCTxResp createSCTxProxy(UFSCreateSCTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSCreateSCTxResp ufsCreateSCTxResp = null;
            logUFSObj(request);
            ufsCreateSCTxResp = new UFSCreateSCTxResp();
            ufsCreateSCTxResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsCreateSCTxResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsCreateSCTxResp.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsCreateSCTxResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsCreateSCTxResp.setProcessingDateEST(new GregorianCalendar());
            ufsCreateSCTxResp.setCreationDateEST(new GregorianCalendar());
            ufsCreateSCTxResp.setDisclaimer(PROXY_DISCLAIMER);
            ufsCreateSCTxResp.setOriginCurrency(PROXY_CURRENTCY);
            ufsCreateSCTxResp.setPaymentAmount(100.0D);
            ufsCreateSCTxResp.setPaymentCurrency(PROXY_PRIMARY_CURRECNCY);
            ufsCreateSCTxResp.setTxAmount(10.0D);
            ufsCreateSCTxResp.setTxCorrespondentFee(2.0D);
            ufsCreateSCTxResp.setTxExchangeRate(10.0D);
            ufsCreateSCTxResp.setTxFeeTotal(3.0D);
            ufsCreateSCTxResp.setTxUTLRFee(1.0D);
            ufsCreateSCTxResp.setReserved1(PROXY_MODE);
            ufsCreateSCTxResp.setTxStatus(PROXY_TX_STATUS);
            logUFSObj(ufsCreateSCTxResp);
            return ufsCreateSCTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSConfirmSCTxResp confirmSCTxProxy(UFSConfirmSCTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSConfirmSCTxResp ufsConfirmSCTxResp = null;
            logUFSObj(request);
            ufsConfirmSCTxResp = new UFSConfirmSCTxResp();
            ufsConfirmSCTxResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsConfirmSCTxResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsConfirmSCTxResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsConfirmSCTxResp);
            return ufsConfirmSCTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSCancelTxResp cancelTxProxy(UFSCancelTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSCancelTxResp ufsCancelTxResp = null;
            logUFSObj(request);
            ufsCancelTxResp = new UFSCancelTxResp();
            ufsCancelTxResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsCancelTxResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsCancelTxResp.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsCancelTxResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsCancelTxResp.setProcessingDateEST(new GregorianCalendar());
            ufsCancelTxResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsCancelTxResp);
            return ufsCancelTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSInfoModifyResp infoModifyTxProxy(UFSInfoModifyReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSInfoModifyResp ufsInfoModifyResp = null;
            logUFSObj(request);
            ufsInfoModifyResp = new UFSInfoModifyResp();
            ufsInfoModifyResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsInfoModifyResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsInfoModifyResp.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsInfoModifyResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsInfoModifyResp.setProcessingDateEST(new GregorianCalendar());
            ufsInfoModifyResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsInfoModifyResp);
            return ufsInfoModifyResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSNotificationResp getNotificationsProxy(UFSNotificationReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSNotificationResp ufsNotifiationResp = null;
            logUFSObj(request);
            ufsNotifiationResp = new UFSNotificationResp();
            ufsNotifiationResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsNotifiationResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsNotifiationResp.setReserved1(PROXY_MODE);
            UFSNotificationItem ufsNotificationItem1 = new UFSNotificationItem();
            ufsNotificationItem1.setNotificationRefNumber(PROXY_NOTIFICATION_NUM);
            ufsNotificationItem1.setDescription(PROXY_DESC);
            ufsNotificationItem1.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsNotificationItem1.setCorrespondentRefNumber(PROXY_TX_IDENTIFIER);
            ufsNotificationItem1.setNotificationType(PROXY_NOTIF_REF_NUM);
            ufsNotificationItem1.setMessageText(PROXY_MSG_TEXT);
            ufsNotificationItem1.setReserved1(PROXY_MODE);
            UFSNotificationItem ufsNotificationItem2 = new UFSNotificationItem();
            ufsNotificationItem2.setNotificationRefNumber(PROXY_NOTIF_REF_NUM1);
            ufsNotificationItem2.setDescription(PROXY_DESC);
            ufsNotificationItem2.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsNotificationItem2.setCorrespondentRefNumber(PROXY_TX_IDENTIFIER);
            ufsNotificationItem2.setNotificationType(PROXY_NOTIF_TYPE1);
            ufsNotificationItem2.setMessageText(PROXY_MSG_TEXT_OFAC_UNHOLD);
            ufsNotificationItem2.setReserved1(PROXY_MODE);
            UFSNotificationItem ufsNotificationItem3 = new UFSNotificationItem();
            ufsNotificationItem3.setNotificationRefNumber(PROXY_NOTIF_REF_NUM2);
            ufsNotificationItem3.setDescription(PROXY_DESC);
            ufsNotificationItem3.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsNotificationItem3.setCorrespondentRefNumber(PROXY_TX_IDENTIFIER);
            ufsNotificationItem3.setNotificationType(PROXY_NOTIF_TYPE2);
            ufsNotificationItem3.setPayingAgent(PROXY_PAYING_AGENT);
            ufsNotificationItem3.setPayingAgentBranchCode(PROXY_PAYING_AGENT_BR_CODE);
            ufsNotificationItem3.setPayingAgentOperator(PROXY_PAYING_AGENT_OPT);
            ufsNotificationItem3.setBeneIdentificationType(PROXY_BENE_ID_TYPE);
            ufsNotificationItem3.setBeneIdentificationNumber(PROXY_BENE_ID_NUM);
            ufsNotificationItem3.setPaymentLocalTime(new GregorianCalendar());
            ufsNotificationItem3.setMessageText(PROXY_MSG_TEXT_PAID);
            ufsNotificationItem3.setReserved1(PROXY_MODE);
            UFSNotificationItem[] notificationItems = new UFSNotificationItem[3];
            notificationItems[0] = ufsNotificationItem1;
            notificationItems[1] = ufsNotificationItem2;
            notificationItems[2] = ufsNotificationItem3;
            ufsNotifiationResp.setNotificationData(notificationItems);
            logUFSObj(ufsNotifiationResp);
            return ufsNotifiationResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSNotificationConfirmResp notificationConfirmProxy(UFSNotificationConfirmReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSNotificationConfirmResp ufsNotifiationConfirmResp = null;
            logUFSObj(request);
            ufsNotifiationConfirmResp = new UFSNotificationConfirmResp();
            ufsNotifiationConfirmResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsNotifiationConfirmResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsNotifiationConfirmResp);
            return ufsNotifiationConfirmResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSGetTxDetailsResp getTxDetailsProxy(UFSGetTxDetailsReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSGetTxDetailsResp ufsGetTxDetailsResp = null;
            logUFSObj(request);
            ufsGetTxDetailsResp = new UFSGetTxDetailsResp();
            ufsGetTxDetailsResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsGetTxDetailsResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsGetTxDetailsResp.setTxIdentifier(PROXY_TX_IDENTIFIER);
            ufsGetTxDetailsResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsGetTxDetailsResp.setBeneAddress1(PROXY_BENE_ADDR1);
            ufsGetTxDetailsResp.setBeneBirthDate(new GregorianCalendar());
            ufsGetTxDetailsResp.setBeneCity(PROXY_BENE_CITY);
            ufsGetTxDetailsResp.setBeneCountry(PROXY_BENE_COUNTRY);
            ufsGetTxDetailsResp.setBeneEmail(PROXY_BENE_EMAIL);
            ufsGetTxDetailsResp.setBeneFirstName(PROXY_BENE_FNAME);
            ufsGetTxDetailsResp.setBeneLastName(PROXY_BENE_LNAME);
            ufsGetTxDetailsResp.setBenePhone(PROXY_PHONE);
            ufsGetTxDetailsResp.setBenePostalCode(PROXY_POSTAL_CODE);
            ufsGetTxDetailsResp.setBeneRefNumber(PROXY_BENE_REF_NUM);
            ufsGetTxDetailsResp.setBeneState(PROXY_BENE_STATE);
            ufsGetTxDetailsResp.setLastStatusChangeTimeStamp(new GregorianCalendar());
            ufsGetTxDetailsResp.setPaymentAmount(100.0D);
            ufsGetTxDetailsResp.setPaymentCountry(PROXY_BENE_COUNTRY);
            ufsGetTxDetailsResp.setPaymentCurrency(PROXY_PRIMARY_CURRECNCY);
            ufsGetTxDetailsResp.setPaymentLocation(PROXY_LOCATION);
            ufsGetTxDetailsResp.setPaymentType(PROXY_PAYMENT_TYPE);
            ufsGetTxDetailsResp.setSenderAddress1(PROXY_SENDER_ADDR);
            ufsGetTxDetailsResp.setSenderBirthDate(new GregorianCalendar());
            ufsGetTxDetailsResp.setSenderCity(PROXY_SENDER_CITY);
            ufsGetTxDetailsResp.setSenderEmail(PROXY_SENDER_EMAIL);
            ufsGetTxDetailsResp.setSenderFirstName(PROXY_SENDER_FNAME);
            ufsGetTxDetailsResp.setSenderLastName(PROXY_BENE_LNAME);
            ufsGetTxDetailsResp.setSenderPhone(PROXY_SENDER_PHONE);
            ufsGetTxDetailsResp.setSenderPostalCode(PROXY_SENDER_POSTAL_CODE);
            ufsGetTxDetailsResp.setSenderRefNumber(PROXY_SENDER_REF_NUM);
            ufsGetTxDetailsResp.setSenderState(PROXY_SENDER_STATE);
            ufsGetTxDetailsResp.setTxAmount(10.0D);
            ufsGetTxDetailsResp.setTxCreationDate(new GregorianCalendar());
            ufsGetTxDetailsResp.setTxExchangeRate(10.0D);
            ufsGetTxDetailsResp.setTxFee(3.0D);
            ufsGetTxDetailsResp.setTxOriginCountry(PROXY_CURRECTRY_CODE);
            ufsGetTxDetailsResp.setTxOriginCurrency(PROXY_CURRENTCY);
            ufsGetTxDetailsResp.setTxStatus(PROXY_TX_STATIS_PAYABLE);
            ufsGetTxDetailsResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsGetTxDetailsResp);
            return ufsGetTxDetailsResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSQuickQuoteResp quickQuoteProxy(UFSQuickQuoteReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSQuickQuoteResp ufsQuickQuoteResp = null;
            logUFSObj(request);
            ufsQuickQuoteResp = new UFSQuickQuoteResp();
            ufsQuickQuoteResp.setResponseCode(PROXY_SUCCESS_RESPONSE);
            ufsQuickQuoteResp.setResponseString(PROXY_SUCCESS_STATUS);
            ufsQuickQuoteResp.setPaymentAmount(100.0D);
            ufsQuickQuoteResp.setTxCorrespondentFee(2.0D);
            ufsQuickQuoteResp.setTxUTLRFee(1.0D);
            ufsQuickQuoteResp.setTxFeeTotal(3.0D);
            ufsQuickQuoteResp.setTxExchangeRate(10.0D);
            ufsQuickQuoteResp.setReserved1(PROXY_MODE);
            logUFSObj(ufsQuickQuoteResp);
            return ufsQuickQuoteResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    /**
     * @param obj void
     */
    private void logUFSObj(Object obj) {
        try {
            if (obj == null) {
                log.info(NO_UFS_OBJECT);
            } else {
                log.info(FORMAT_STRING, obj.getClass().getCanonicalName(), processObj(obj).toString());
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(LOG_ERROR, e);
        }
    }

    /**
     * @param Obj
     * @return Map
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private Map<String, Object> processObj(Object Obj) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = new HashMap<String, Object>();
        /* Preparing the map to write the values to output */
        if (Obj != null) {
            Class<? extends Object> UFSClassType = Obj.getClass();
            for (Method method : BaseService.findGetters(UFSClassType)) {
                if (method.getReturnType().isPrimitive()
                        || method.getReturnType().isAssignableFrom(String.class)
                        || method.getReturnType().isAssignableFrom(Calendar.class)) {
                    map.put(method.getName().substring(3), method.invoke(Obj));
                } else {
                    map.put(method.getName().substring(3),
                            processNestedObj(method.invoke(Obj)));
                }
            }
        }
        return map;
    }

    /**
     * @param Obj
     * @return Object
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException Object
     */
    private Object processNestedObj(Object Obj) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        if (Obj == null) {
            return StringUtils.EMPTY;
        } else if (Obj instanceof Object[]) {
            ArrayList<Object> list = new ArrayList<Object>();
            Object[] objArray = (Object[]) Obj;
            for (Object obj : objArray) {
                list.add(processObj(obj));
            }
            return list;
        } else {
            return processObj(Obj);
        }
    }
}
