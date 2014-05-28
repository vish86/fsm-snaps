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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.GregorianCalendar;
import java.util.Properties;

import static com.snaplogic.snaps.uniteller.Constants.*;
import static com.snaplogic.snaps.uniteller.Messages.CHANGE_PASS_ERR;
import static com.snaplogic.snaps.uniteller.Messages.NORESPONSEIOOBJECT;
import static com.snaplogic.snaps.uniteller.Messages.NO_PASSWORD_ERROR;
import static com.snaplogic.snaps.uniteller.Messages.NO_PASSWORD_FOR_MACHINE_ID;
import static com.snaplogic.snaps.uniteller.Messages.NULLEXCEPTION;
import static com.snaplogic.snaps.uniteller.Messages.NULLREQUEST;
import static com.snaplogic.snaps.uniteller.util.Utilities.*;

/**
 * Customized UFS folio creation client
 * 
 * @author svatada
 */
public class CustomUFSFolioCreationClient {
    private static final UFSClientLogger ufsLogger = UFSClientLogger
            .getLogger(CustomUFSFolioCreationClient.class);
    private static final Logger log = LoggerFactory.getLogger(BaseService.class);
    private static boolean initialized = false;
    private static String organizationIdS;
    private static String companyIdS;
    private static String machineIdS;
    private static String fclAPIURLSuffix;
    private IUFSConfigMgr configMgr;
    private IUFSSecurityMgr securityMgr;

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

    public UFSCreateSCTxResp createSCTx(UFSCreateSCTxReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSCreateSCTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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

            logUFSCreateSCTxReq(request);
            CreateSCTxRequest createSCTxReq = convertToCreateSCTxRequestObject(request, companyId,
                    organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }

            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            createSCTxReq.setMachineId(machineIdS);
            createSCTxReq.setMachinePassword(oldPassword);
            createSCTxResponse = sendCreateSCTxRequest(createSCTxReq);
            responseCode = createSCTxResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsCreateSCTxResp = new UFSCreateSCTxResp();
                    ufsCreateSCTxResp.setResponseCode(responseCode);
                    ufsCreateSCTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSCreateSCTxResp(ufsCreateSCTxResp);
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
                logUFSCreateSCTxResp(ufsCreateSCTxResp);
                return ufsCreateSCTxResp;
            }

            ufsCreateSCTxResp = convertToUFSCreateSCTxRespObject(createSCTxResponse);
            logUFSCreateSCTxResp(ufsCreateSCTxResp);
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

    public UFSConfirmSCTxResp confirmSCTx(UFSConfirmSCTxReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSConfirmSCTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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

            logUFSConfirmSCTxReq(request);
            ConfirmSCTxRequest confirmSCTxReq = convertToConfirmSCTxRequestObject(request,
                    companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            confirmSCTxReq.setMachineId(machineIdS);
            confirmSCTxReq.setMachinePassword(oldPassword);
            confirmSCTxResponse = sendConfirmSCTxRequest(confirmSCTxReq);
            responseCode = confirmSCTxResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsConfirmSCTxResp = new UFSConfirmSCTxResp();
                    ufsConfirmSCTxResp.setResponseCode(responseCode);
                    ufsConfirmSCTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSConfirmSCTxResp(ufsConfirmSCTxResp);
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
                logUFSConfirmSCTxResp(ufsConfirmSCTxResp);
                return ufsConfirmSCTxResp;
            }

            ufsConfirmSCTxResp = convertToUFSConfirmSCTxRespObject(confirmSCTxResponse);
            logUFSConfirmSCTxResp(ufsConfirmSCTxResp);
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

    public UFSCreateTxResp createTx(UFSCreateTxReq request) throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSCreateTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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

            logUFSCreateTxReq(request);
            CreateTxRequest createTxReq = convertToCreateTxRequestObject(request, companyId,
                    organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }

            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            createTxReq.setMachineId(machineIdS);
            createTxReq.setMachinePassword(oldPassword);
            createTxResponse = sendCreateTxRequest(createTxReq);
            responseCode = createTxResponse.getResponseCode();
            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsCreateTxResp = new UFSCreateTxResp();
                    ufsCreateTxResp.setResponseCode(responseCode);
                    ufsCreateTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSCreateTxResp(ufsCreateTxResp);
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
                logUFSCreateTxResp(ufsCreateTxResp);
                return ufsCreateTxResp;
            }

            ufsCreateTxResp = convertToUFSCreateTxRespObject(createTxResponse);
            logUFSCreateTxResp(ufsCreateTxResp);
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

    public UFSCancelTxResp cancelTx(UFSCancelTxReq request) throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSCancelTxReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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

            logUFSCancelTxReq(request);
            TxCancelRequest cancelTxReq = convertToTxCancelRequestObject(request, companyId,
                    organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }

            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            cancelTxReq.setMachineId(machineIdS);
            cancelTxReq.setMachinePassword(oldPassword);
            cancelTxResponse = sendCancelTxRequest(cancelTxReq);
            responseCode = cancelTxResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsCancelTxResp = new UFSCancelTxResp();
                    ufsCancelTxResp.setResponseCode(responseCode);
                    ufsCancelTxResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSCancelTxResp(ufsCancelTxResp);
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
                logUFSCancelTxResp(ufsCancelTxResp);
                return ufsCancelTxResp;
            }

            ufsCancelTxResp = convertToUFSCancelTxRespObject(cancelTxResponse);
            logUFSCancelTxResp(ufsCancelTxResp);
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

    public UFSInfoModifyResp infoModifyTx(UFSInfoModifyReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSInfoModifyReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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

            logUFSInfoModifyReq(request);
            InfoModifyRequest infoModifyReq = convertToInfoModifyRequestObject(request, companyId,
                    organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }

            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            infoModifyReq.setMachineId(machineIdS);
            infoModifyReq.setMachinePassword(oldPassword);
            infoModifyResponse = sendInfoModifyRequest(infoModifyReq);
            responseCode = infoModifyResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsInfoModifyResp = new UFSInfoModifyResp();
                    ufsInfoModifyResp.setResponseCode(responseCode);
                    ufsInfoModifyResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSInfoModifyResp(ufsInfoModifyResp);
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
                logUFSInfoModifyResp(ufsInfoModifyResp);
                return ufsInfoModifyResp;
            }

            ufsInfoModifyResp = convertToUFSInfoModifyRespObject(infoModifyResponse);
            logUFSInfoModifyResp(ufsInfoModifyResp);
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

    public UFSNotificationResp notification(UFSNotificationReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSNotificationReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if (request.getNotificationCount() == PROXYCOUNT) {
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

            logUFSNotificationReq(request);
            NotificationRequest notificationReq = convertToNotificationRequestObject(request,
                    companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            notificationReq.setMachineId(machineIdS);
            notificationReq.setMachinePassword(oldPassword);
            notificationResponse = sendNotificationRequest(notificationReq);
            responseCode = notificationResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsNotificationResp = new UFSNotificationResp();
                    ufsNotificationResp.setResponseCode(responseCode);
                    ufsNotificationResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSNotificationResp(ufsNotificationResp);
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
                logUFSNotificationResp(ufsNotificationResp);
                return ufsNotificationResp;
            }

            ufsNotificationResp = convertToUFSNotificationRespObject(notificationResponse);
            logUFSNotificationResp(ufsNotificationResp);
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

    public UFSNotificationConfirmResp notificationConfirm(UFSNotificationConfirmReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSNotificationConfirmReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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
            logUFSNotificationConfirmReq(request);
            NotificationConfirmRequest notifConfirmReq = convertToNotificationConfirmRequestObject(
                    request, companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }

            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            notifConfirmReq.setMachineId(machineIdS);
            notifConfirmReq.setMachinePassword(oldPassword);
            notifConfirmResponse = sendNotificationConfirmRequest(notifConfirmReq);
            responseCode = notifConfirmResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsNotificationConfirmResp = new UFSNotificationConfirmResp();
                    ufsNotificationConfirmResp.setResponseCode(responseCode);
                    logUFSNotificationConfirmResp(ufsNotificationConfirmResp);
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
                logUFSNotificationConfirmResp(ufsNotificationConfirmResp);
                return ufsNotificationConfirmResp;
            }

            ufsNotificationConfirmResp = convertToUFSNotificationConfirmRespObject(notifConfirmResponse);
            logUFSNotificationConfirmResp(ufsNotificationConfirmResp);
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

    public UFSGetTxDetailsResp getTxDetails(UFSGetTxDetailsReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSGetTxDetailsReq.class.getName()));
        }

        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getTxIdentifier() != null)
                    && (request.getTxIdentifier().equals(PROXYTXIDENTIFIER))) {
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

            logUFSGetTxDetailsReq(request);
            GetTxDetailsRequest getTxDetailsReq = convertToGetTxDetailsRequestObject(request,
                    companyId, organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }

            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            getTxDetailsReq.setMachineId(machineIdS);
            getTxDetailsReq.setMachinePassword(oldPassword);
            getTxDetailsResponse = sendGetTxDetailsRequest(getTxDetailsReq);
            responseCode = getTxDetailsResponse.getResponseCode();

            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsGetTxDetailsResp = new UFSGetTxDetailsResp();
                    ufsGetTxDetailsResp.setResponseCode(responseCode);
                    ufsGetTxDetailsResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSGetTxDetailsResp(ufsGetTxDetailsResp);
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
                logUFSGetTxDetailsResp(ufsGetTxDetailsResp);
                return ufsGetTxDetailsResp;
            }

            ufsGetTxDetailsResp = convertToUFSGetTxDetailsRespObject(getTxDetailsResponse);
            logUFSGetTxDetailsResp(ufsGetTxDetailsResp);
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

    public UFSQuickQuoteResp quickQuote(UFSQuickQuoteReq request)
            throws UFSFolioCreationClientException {
        if (request == null) {
            throw new UFSFolioCreationClientException(GENERAL_SYSTEM_ERROR, String.format(
                    NULLREQUEST, UFSQuickQuoteReq.class.getName()));
        }
        String txContext = GlobalFunctions.generateId(true, TXCONTEXTPREFIX, null, null);
        UFSClientLogger.pushContext(txContext);
        try {
            if ((request.getPaymentLocation() != null)
                    && (request.getPaymentLocation().equals(PROXYPAYMENTLOCATION))) {
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
            logUFSQuickQuoteReq(request);
            QuickQuoteRequest quickQuoteReq = convertToQuickQuoteRequestObject(request, companyId,
                    organizationIdS);
            String encryptedPassword = null;
            synchronized (this.securityMgr) {
                encryptedPassword = this.securityMgr.getPassword(machineIdS);
            }
            if (encryptedPassword == null) {
                throw new UFSUnknownMachineIdException(String.format(NO_PASSWORD_FOR_MACHINE_ID,
                        machineIdS));
            }
            String oldPassword = getPasswordFromEncryptedPassword(encryptedPassword);
            quickQuoteReq.setMachineId(machineIdS);
            quickQuoteReq.setMachinePassword(oldPassword);
            quickQuoteResponse = sendQuickQuoteRequest(quickQuoteReq);
            responseCode = quickQuoteResponse.getResponseCode();
            if (CHANGEPASSWORDERRORCODE.equals(responseCode)) {
                responseCode = sendChangePassword(oldPassword, companyId, companyId);
                if (!isSuccessOrWarning(responseCode)) {
                    ufsQuickQuoteResp = new UFSQuickQuoteResp();
                    ufsQuickQuoteResp.setResponseCode(responseCode);
                    ufsQuickQuoteResp.setResponseString(CHANGE_PASS_ERR);
                    logUFSQuickQuoteResp(ufsQuickQuoteResp);
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
                logUFSQuickQuoteResp(ufsQuickQuoteResp);
                return ufsQuickQuoteResp;
            }
            ufsQuickQuoteResp = convertToUFSQuickQuoteRespObject(quickQuoteResponse);
            logUFSQuickQuoteResp(ufsQuickQuoteResp);
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

    public static String getVersion() {
        return LIBVERSION;
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
            organizationIdS = CommonFunctions.getProperty(confMgr, DEFAULTORGANIZATIONID,
                    ufsLogger);
            companyIdS = CommonFunctions.getProperty(confMgr, DEFAULTCOMPANYID, ufsLogger);
            machineIdS = CommonFunctions.getProperty(confMgr, DEFAULTMACHINEID, ufsLogger);
            fclAPIURLSuffix = CommonFunctions.getProperty(confMgr, FOLIOCREATIONAPIURLSUFFIX,
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
        String errorCode = exceptionErrorMap.get(ex.getClass().getName());
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

    private void logUFSCreateTxReq(UFSCreateTxReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSCreateTxResp(UFSCreateTxResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSCreateSCTxReq(UFSCreateSCTxReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSCreateSCTxResp(UFSCreateSCTxResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSConfirmSCTxReq(UFSConfirmSCTxReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSConfirmSCTxResp(UFSConfirmSCTxResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSCancelTxReq(UFSCancelTxReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSCancelTxResp(UFSCancelTxResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSInfoModifyReq(UFSInfoModifyReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSInfoModifyResp(UFSInfoModifyResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSNotificationReq(UFSNotificationReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSNotificationResp(UFSNotificationResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSNotificationConfirmReq(UFSNotificationConfirmReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSNotificationConfirmResp(UFSNotificationConfirmResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSGetTxDetailsReq(UFSGetTxDetailsReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSGetTxDetailsResp(UFSGetTxDetailsResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSQuickQuoteReq(UFSQuickQuoteReq ufsReq) {
        ufsReq.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private void logUFSQuickQuoteResp(UFSQuickQuoteResp ufsResp) {
        ufsResp.log(ufsLogger, UFSClientLogger.peekContext());
    }

    private boolean isSuccessOrWarning(String errorCode) {
        return (errorCode != null)
                && ((errorCode.equals(SUCCESSRESPONSE)) || (WARNINGINDICATOR.equals(errorCode
                        .substring(3, 5))));
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
            ufsLogger.info(String.format(NO_PASSWORD_ERROR, machineIdS));
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
            logUFSCreateTxReq(request);
            ufsCreateTxResp = new UFSCreateTxResp();
            ufsCreateTxResp.setResponseCode(SUCCESSRESPONSE);
            ufsCreateTxResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsCreateTxResp.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsCreateTxResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsCreateTxResp.setProcessingDateEST(new GregorianCalendar());
            ufsCreateTxResp.setReserved1(MODE);
            logUFSCreateTxResp(ufsCreateTxResp);
            return ufsCreateTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSCreateSCTxResp createSCTxProxy(UFSCreateSCTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSCreateSCTxResp ufsCreateSCTxResp = null;
            logUFSCreateSCTxReq(request);
            ufsCreateSCTxResp = new UFSCreateSCTxResp();
            ufsCreateSCTxResp.setResponseCode(SUCCESSRESPONSE);
            ufsCreateSCTxResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsCreateSCTxResp.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsCreateSCTxResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsCreateSCTxResp.setProcessingDateEST(new GregorianCalendar());
            ufsCreateSCTxResp.setCreationDateEST(new GregorianCalendar());
            ufsCreateSCTxResp.setDisclaimer(DISCLAIMER);
            ufsCreateSCTxResp.setOriginCurrency(CURRENTCY);
            ufsCreateSCTxResp.setPaymentAmount(100.0D);
            ufsCreateSCTxResp.setPaymentCurrency(PRIMARY_CURRECNCY);
            ufsCreateSCTxResp.setTxAmount(10.0D);
            ufsCreateSCTxResp.setTxCorrespondentFee(2.0D);
            ufsCreateSCTxResp.setTxExchangeRate(10.0D);
            ufsCreateSCTxResp.setTxFeeTotal(3.0D);
            ufsCreateSCTxResp.setTxUTLRFee(1.0D);
            ufsCreateSCTxResp.setReserved1(MODE);
            ufsCreateSCTxResp.setTxStatus(TX_STATUS);
            logUFSCreateSCTxResp(ufsCreateSCTxResp);
            return ufsCreateSCTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSConfirmSCTxResp confirmSCTxProxy(UFSConfirmSCTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSConfirmSCTxResp ufsConfirmSCTxResp = null;
            logUFSConfirmSCTxReq(request);
            ufsConfirmSCTxResp = new UFSConfirmSCTxResp();
            ufsConfirmSCTxResp.setResponseCode(SUCCESSRESPONSE);
            ufsConfirmSCTxResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsConfirmSCTxResp.setReserved1(MODE);
            logUFSConfirmSCTxResp(ufsConfirmSCTxResp);
            return ufsConfirmSCTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSCancelTxResp cancelTxProxy(UFSCancelTxReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSCancelTxResp ufsCancelTxResp = null;
            logUFSCancelTxReq(request);
            ufsCancelTxResp = new UFSCancelTxResp();
            ufsCancelTxResp.setResponseCode(SUCCESSRESPONSE);
            ufsCancelTxResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsCancelTxResp.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsCancelTxResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsCancelTxResp.setProcessingDateEST(new GregorianCalendar());
            ufsCancelTxResp.setReserved1(MODE);
            logUFSCancelTxResp(ufsCancelTxResp);
            return ufsCancelTxResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSInfoModifyResp infoModifyTxProxy(UFSInfoModifyReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSInfoModifyResp ufsInfoModifyResp = null;
            logUFSInfoModifyReq(request);
            ufsInfoModifyResp = new UFSInfoModifyResp();
            ufsInfoModifyResp.setResponseCode(SUCCESSRESPONSE);
            ufsInfoModifyResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsInfoModifyResp.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsInfoModifyResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsInfoModifyResp.setProcessingDateEST(new GregorianCalendar());
            ufsInfoModifyResp.setReserved1(MODE);
            logUFSInfoModifyResp(ufsInfoModifyResp);
            return ufsInfoModifyResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSNotificationResp getNotificationsProxy(UFSNotificationReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSNotificationResp ufsNotifiationResp = null;
            logUFSNotificationReq(request);
            ufsNotifiationResp = new UFSNotificationResp();
            ufsNotifiationResp.setResponseCode(SUCCESSRESPONSE);
            ufsNotifiationResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsNotifiationResp.setReserved1(MODE);
            UFSNotificationItem ufsNotificationItem1 = new UFSNotificationItem();
            ufsNotificationItem1.setNotificationRefNumber("1");
            ufsNotificationItem1.setDescription("DEMO NOTIFICATION ITEM #1");
            ufsNotificationItem1.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsNotificationItem1.setCorrespondentRefNumber(PROXYTXIDENTIFIER);
            ufsNotificationItem1.setNotificationType("008");
            ufsNotificationItem1
                    .setMessageText("DEMO NOTIFICATION ITEM #1 - Transaction OFAC hold");
            ufsNotificationItem1.setReserved1(MODE);
            UFSNotificationItem ufsNotificationItem2 = new UFSNotificationItem();
            ufsNotificationItem2.setNotificationRefNumber("4");
            ufsNotificationItem2.setDescription("DEMO NOTIFICATION ITEM #2");
            ufsNotificationItem2.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsNotificationItem2.setCorrespondentRefNumber(PROXYTXIDENTIFIER);
            ufsNotificationItem2.setNotificationType("010");
            ufsNotificationItem2
                    .setMessageText("DEMO NOTIFICATION ITEM #2 - Transaction OFAC unhold");
            ufsNotificationItem2.setReserved1(MODE);
            UFSNotificationItem ufsNotificationItem3 = new UFSNotificationItem();
            ufsNotificationItem3.setNotificationRefNumber("7");
            ufsNotificationItem3.setDescription("DEMO NOTIFICATION ITEM #3");
            ufsNotificationItem3.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsNotificationItem3.setCorrespondentRefNumber(PROXYTXIDENTIFIER);
            ufsNotificationItem3.setNotificationType("002");
            ufsNotificationItem3.setPayingAgent("BANORTE");
            ufsNotificationItem3.setPayingAgentBranchCode("BANORTE-00001");
            ufsNotificationItem3.setPayingAgentOperator("OP1001");
            ufsNotificationItem3.setBeneIdentificationType("003");
            ufsNotificationItem3.setBeneIdentificationNumber("1111111");
            ufsNotificationItem3.setPaymentLocalTime(new GregorianCalendar());
            ufsNotificationItem3.setMessageText("DEMO NOTIFICATION ITEM #3 - Transaction paid");
            ufsNotificationItem3.setReserved1(MODE);
            UFSNotificationItem[] notificationItems = new UFSNotificationItem[3];
            notificationItems[0] = ufsNotificationItem1;
            notificationItems[1] = ufsNotificationItem2;
            notificationItems[2] = ufsNotificationItem3;
            ufsNotifiationResp.setNotificationData(notificationItems);
            logUFSNotificationResp(ufsNotifiationResp);
            return ufsNotifiationResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSNotificationConfirmResp notificationConfirmProxy(UFSNotificationConfirmReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSNotificationConfirmResp ufsNotifiationConfirmResp = null;
            logUFSNotificationConfirmReq(request);
            ufsNotifiationConfirmResp = new UFSNotificationConfirmResp();
            ufsNotifiationConfirmResp.setResponseCode(SUCCESSRESPONSE);
            ufsNotifiationConfirmResp.setReserved1(MODE);
            logUFSNotificationConfirmResp(ufsNotifiationConfirmResp);
            return ufsNotifiationConfirmResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSGetTxDetailsResp getTxDetailsProxy(UFSGetTxDetailsReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSGetTxDetailsResp ufsGetTxDetailsResp = null;
            logUFSGetTxDetailsReq(request);
            ufsGetTxDetailsResp = new UFSGetTxDetailsResp();
            ufsGetTxDetailsResp.setResponseCode(SUCCESSRESPONSE);
            ufsGetTxDetailsResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsGetTxDetailsResp.setTxIdentifier(PROXYTXIDENTIFIER);
            ufsGetTxDetailsResp.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
            ufsGetTxDetailsResp.setBeneAddress1("123 MAIN STREET");
            ufsGetTxDetailsResp.setBeneBirthDate(new GregorianCalendar());
            ufsGetTxDetailsResp.setBeneCity("MONTERREY");
            ufsGetTxDetailsResp.setBeneCountry("MX");
            ufsGetTxDetailsResp.setBeneEmail("jane.doe@email.com");
            ufsGetTxDetailsResp.setBeneFirstName("JANE");
            ufsGetTxDetailsResp.setBeneLastName("DOE");
            ufsGetTxDetailsResp.setBenePhone("1234567890");
            ufsGetTxDetailsResp.setBenePostalCode("00000");
            ufsGetTxDetailsResp.setBeneRefNumber("11111");
            ufsGetTxDetailsResp.setBeneState("NLE");
            ufsGetTxDetailsResp.setLastStatusChangeTimeStamp(new GregorianCalendar());
            ufsGetTxDetailsResp.setPaymentAmount(100.0D);
            ufsGetTxDetailsResp.setPaymentCountry("MX");
            ufsGetTxDetailsResp.setPaymentCurrency(PRIMARY_CURRECNCY);
            ufsGetTxDetailsResp.setPaymentLocation("LOCATION X");
            ufsGetTxDetailsResp.setPaymentType("001");
            ufsGetTxDetailsResp.setSenderAddress1("321 BROADWAY");
            ufsGetTxDetailsResp.setSenderBirthDate(new GregorianCalendar());
            ufsGetTxDetailsResp.setSenderCity("ROCHELLE PARK");
            ufsGetTxDetailsResp.setSenderEmail("john.doe@email.com");
            ufsGetTxDetailsResp.setSenderFirstName("JOHN");
            ufsGetTxDetailsResp.setSenderLastName("DOE");
            ufsGetTxDetailsResp.setSenderPhone("9876543210");
            ufsGetTxDetailsResp.setSenderPostalCode("12345");
            ufsGetTxDetailsResp.setSenderRefNumber("22222");
            ufsGetTxDetailsResp.setSenderState("NJ");
            ufsGetTxDetailsResp.setTxAmount(10.0D);
            ufsGetTxDetailsResp.setTxCreationDate(new GregorianCalendar());
            ufsGetTxDetailsResp.setTxExchangeRate(10.0D);
            ufsGetTxDetailsResp.setTxFee(3.0D);
            ufsGetTxDetailsResp.setTxOriginCountry("US");
            ufsGetTxDetailsResp.setTxOriginCurrency(CURRENTCY);
            ufsGetTxDetailsResp.setTxStatus("PAYABLE");
            ufsGetTxDetailsResp.setReserved1(MODE);
            logUFSGetTxDetailsResp(ufsGetTxDetailsResp);
            return ufsGetTxDetailsResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }

    private UFSQuickQuoteResp quickQuoteProxy(UFSQuickQuoteReq request)
            throws UFSFolioCreationClientException {
        try {
            UFSQuickQuoteResp ufsQuickQuoteResp = null;
            logUFSQuickQuoteReq(request);
            ufsQuickQuoteResp = new UFSQuickQuoteResp();
            ufsQuickQuoteResp.setResponseCode(SUCCESSRESPONSE);
            ufsQuickQuoteResp.setResponseString(SUCCESS_DEMO_STATUS);
            ufsQuickQuoteResp.setPaymentAmount(100.0D);
            ufsQuickQuoteResp.setTxCorrespondentFee(2.0D);
            ufsQuickQuoteResp.setTxUTLRFee(1.0D);
            ufsQuickQuoteResp.setTxFeeTotal(3.0D);
            ufsQuickQuoteResp.setTxExchangeRate(10.0D);
            ufsQuickQuoteResp.setReserved1(MODE);
            logUFSQuickQuoteResp(ufsQuickQuoteResp);
            return ufsQuickQuoteResp;
        } catch (Exception e) {
            throw getUFSFolioCreationClientException(e);
        }
    }
}
