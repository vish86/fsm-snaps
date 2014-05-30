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
package com.snaplogic.snaps.uniteller.util;

import com.google.inject.Inject;
import com.snaplogic.common.SnapType;
import com.uniteller.support.communication.foliocreation.*;
import com.uniteller.support.foliocreationclient.*;
import com.uniteller.support.util.GlobalFunctions;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * Contains all utility methods which are useful to convert the UFS request objects to TX objects.
 *
 * @author svatada
 */
public class Utilities {
    private static final String INT = "int";
<<<<<<< HEAD
    private static Utilities util;
    static {
        util = new Utilities();
    }

    @Inject
    private Utilities() {

    }

    /**
     * getInstance
     *
     * @return Utilities
     */
    public static Utilities getInstance() {
        return util;
    }
=======
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return TxCancelRequest
     */
<<<<<<< HEAD
    public TxCancelRequest convertToTxCancelRequestObject(UFSCancelTxReq request, String companyId,
            String orgId) {
        TxCancelRequest req = new TxCancelRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static TxCancelRequest convertToTxCancelRequestObject(UFSCancelTxReq request,
            String companyId, String orgId) {
        TxCancelRequest req = new TxCancelRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setOperator(request.getOperator());
        req.setTxCancelType(request.getTxCancelType());
        if (request.getProcessingDateLocal() != null) {
            req.setProcessingDateLocal(GlobalFunctions.mMDDYYYYHH24MISSFromDate(request
                    .getProcessingDateLocal().getTime()));
        }
        req.setCancellationFee(String.valueOf(request.getCancellationFee()));
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return ConfirmSCTxRequest
     */
<<<<<<< HEAD
    public ConfirmSCTxRequest convertToConfirmSCTxRequestObject(UFSConfirmSCTxReq request,
            String companyId, String orgId) {
        ConfirmSCTxRequest req = new ConfirmSCTxRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static ConfirmSCTxRequest convertToConfirmSCTxRequestObject(UFSConfirmSCTxReq request,
            String companyId, String orgId) {
        ConfirmSCTxRequest req = new ConfirmSCTxRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setTxAmount(String.valueOf(request.getTxAmount()));
        req.setPaymentAmount(String.valueOf(request.getPaymentAmount()));
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return CreateSCTxRequest
     */
<<<<<<< HEAD
    public CreateSCTxRequest convertToCreateSCTxRequestObject(UFSCreateSCTxReq request,
            String companyId, String orgId) {
        CreateSCTxRequest req = new CreateSCTxRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode())) {
=======
    public static CreateSCTxRequest convertToCreateSCTxRequestObject(UFSCreateSCTxReq request,
            String companyId, String orgId) {
        CreateSCTxRequest req = new CreateSCTxRequest();
        if (request.getCorrespondentCode() == null) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        } else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setPaymentAmount(String.valueOf(request.getPaymentAmount()));
        req.setPaymentCurrency(request.getPaymentCurrency());
        req.setPaymentCountry(request.getPaymentCountry());
        req.setPaymentType(request.getPaymentType());
        req.setPaymentLocation(request.getPaymentLocation());
        req.setAccountNumber(request.getAccountNumber());
        req.setAccountType(request.getAccountType());
        req.setPayingAgentBranchCode(request.getPayingAgentBranchCode());
        req.setTxAgentCode(request.getTxAgentCode());
        req.setTxAgentState(request.getTxAgentState());
        req.setTxOriginCountry(request.getTxOriginCountry());
        req.setTxOriginCurrency(request.getTxOriginCurrency());
        req.setTxAmount(String.valueOf(request.getTxAmount()));
        req.setTxFee(String.valueOf(request.getTxFee()));
        req.setTxExchangeRate(String.valueOf(request.getTxExchangeRate()));
        req.setTxCaptureMethod(request.getTxCaptureMethod());
        if (request.getTxCreationDateLocal() != null) {
            req.setTxCreationDateLocal(GlobalFunctions.mMDDYYYYHH24MISSFromDate(request
                    .getTxCreationDateLocal().getTime()));
        }
        req.setBeneRefNumber(request.getBeneRefNumber());
        req.setBeneFirstName(request.getBeneFirstName());
        req.setBeneMidName(request.getBeneMidName());
        req.setBeneLastName(request.getBeneLastName());
        req.setBeneSecondLastName(request.getBeneSecondLastName());
        req.setBeneAddress1(request.getBeneAddress1());
        req.setBeneAddress2(request.getBeneAddress2());
        req.setBeneCity(request.getBeneCity());
        req.setBeneState(request.getBeneState());
        req.setBeneCountry(request.getBeneCountry());
        req.setBenePostalCode(request.getBenePostalCode());
        req.setBenePhone(request.getBenePhone());
        if (request.getBeneBirthDate() != null) {
            req.setBeneBirthDate(GlobalFunctions.mMDDYYYYFromDate(request.getBeneBirthDate()
                    .getTime()));
        }
        req.setBeneIdentificationType(request.getBeneIdentificationType());
        req.setBeneIdentificationNumber(request.getBeneIdentificationNumber());
        req.setBeneEmail(request.getBeneEmail());
        req.setSenderRefNumber(request.getSenderRefNumber());
        req.setSenderFirstName(request.getSenderFirstName());
        req.setSenderMidName(request.getSenderMidName());
        req.setSenderLastName(request.getSenderLastName());
        req.setSenderSecondLastName(request.getSenderSecondLastName());
        req.setSenderAddress1(request.getSenderAddress1());
        req.setSenderAddress2(request.getSenderAddress2());
        req.setSenderCity(request.getSenderCity());
        req.setSenderState(request.getSenderState());
        req.setSenderPostalCode(request.getSenderPostalCode());
        req.setSenderPhone(request.getSenderPhone());
        if (request.getSenderBirthDate() != null) {
            req.setSenderBirthDate(GlobalFunctions.mMDDYYYYFromDate(request.getSenderBirthDate()
                    .getTime()));
        }
        req.setSenderIdentificationType(request.getSenderIdentificationType());
        req.setSenderIdentificationNumber(request.getSenderIdentificationNumber());
        req.setSenderEmail(request.getSenderEmail());
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return CreateTxRequest
     */
<<<<<<< HEAD
    public CreateTxRequest convertToCreateTxRequestObject(UFSCreateTxReq request, String companyId,
            String orgId) {
        CreateTxRequest req = new CreateTxRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static CreateTxRequest convertToCreateTxRequestObject(UFSCreateTxReq request,
            String companyId, String orgId) {
        CreateTxRequest req = new CreateTxRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setPaymentAmount(String.valueOf(request.getPaymentAmount()));
        req.setPaymentCurrency(request.getPaymentCurrency());
        req.setPaymentCountry(request.getPaymentCountry());
        req.setPaymentType(request.getPaymentType());
        req.setPaymentLocation(request.getPaymentLocation());
        req.setAccountNumber(request.getAccountNumber());
        req.setAccountType(request.getAccountType());
        req.setPayingAgentBranchCode(request.getPayingAgentBranchCode());
        req.setTxAgentCode(request.getTxAgentCode());
        req.setTxAgentState(request.getTxAgentState());
        req.setTxOriginCountry(request.getTxOriginCountry());
        req.setTxOriginCurrency(request.getTxOriginCurrency());
        req.setTxAmount(String.valueOf(request.getTxAmount()));
        req.setTxFee(String.valueOf(request.getTxFee()));
        req.setTxExchangeRate(String.valueOf(request.getTxExchangeRate()));
        req.setTxCaptureMethod(request.getTxCaptureMethod());
        if (request.getTxCreationDateLocal() != null) {
            req.setTxCreationDateLocal(GlobalFunctions.mMDDYYYYHH24MISSFromDate(request
                    .getTxCreationDateLocal().getTime()));
        }
        req.setBeneRefNumber(request.getBeneRefNumber());
        req.setBeneFirstName(request.getBeneFirstName());
        req.setBeneMidName(request.getBeneMidName());
        req.setBeneLastName(request.getBeneLastName());
        req.setBeneSecondLastName(request.getBeneSecondLastName());
        req.setBeneAddress1(request.getBeneAddress1());
        req.setBeneAddress2(request.getBeneAddress2());
        req.setBeneCity(request.getBeneCity());
        req.setBeneState(request.getBeneState());
        req.setBeneCountry(request.getBeneCountry());
        req.setBenePostalCode(request.getBenePostalCode());
        req.setBenePhone(request.getBenePhone());
        if (request.getBeneBirthDate() != null) {
            req.setBeneBirthDate(GlobalFunctions.mMDDYYYYFromDate(request.getBeneBirthDate()
                    .getTime()));
        }
        req.setBeneIdentificationType(request.getBeneIdentificationType());
        req.setBeneIdentificationNumber(request.getBeneIdentificationNumber());
        req.setBeneEmail(request.getBeneEmail());
        req.setSenderRefNumber(request.getSenderRefNumber());
        req.setSenderFirstName(request.getSenderFirstName());
        req.setSenderMidName(request.getSenderMidName());
        req.setSenderLastName(request.getSenderLastName());
        req.setSenderSecondLastName(request.getSenderSecondLastName());
        req.setSenderAddress1(request.getSenderAddress1());
        req.setSenderAddress2(request.getSenderAddress2());
        req.setSenderCity(request.getSenderCity());
        req.setSenderState(request.getSenderState());
        req.setSenderPostalCode(request.getSenderPostalCode());
        req.setSenderPhone(request.getSenderPhone());
        if (request.getSenderBirthDate() != null) {
            req.setSenderBirthDate(GlobalFunctions.mMDDYYYYFromDate(request.getSenderBirthDate()
                    .getTime()));
        }
        req.setSenderIdentificationType(request.getSenderIdentificationType());
        req.setSenderIdentificationNumber(request.getSenderIdentificationNumber());
        req.setSenderEmail(request.getSenderEmail());
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return GetTxDetailsRequest
     */
<<<<<<< HEAD
    public GetTxDetailsRequest convertToGetTxDetailsRequestObject(UFSGetTxDetailsReq request,
            String companyId, String orgId) {
        GetTxDetailsRequest req = new GetTxDetailsRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode())) {
=======
    public static GetTxDetailsRequest convertToGetTxDetailsRequestObject(
            UFSGetTxDetailsReq request, String companyId, String orgId) {
        GetTxDetailsRequest req = new GetTxDetailsRequest();
        if (request.getCorrespondentCode() == null) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        } else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return InfoModifyRequest
     */
<<<<<<< HEAD
    public InfoModifyRequest convertToInfoModifyRequestObject(UFSInfoModifyReq request,
            String companyId, String orgId) {
        InfoModifyRequest req = new InfoModifyRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static InfoModifyRequest convertToInfoModifyRequestObject(UFSInfoModifyReq request,
            String companyId, String orgId) {
        InfoModifyRequest req = new InfoModifyRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setBeneFirstName(request.getBeneFirstName());
        req.setBeneMidName(request.getBeneMidName());
        req.setBeneLastName(request.getBeneLastName());
        req.setBeneSecondLastName(request.getBeneSecondLastName());
        req.setBeneAddress1(request.getBeneAddress1());
        req.setBeneAddress2(request.getBeneAddress2());
        req.setBeneCity(request.getBeneCity());
        req.setBeneState(request.getBeneState());
        req.setBenePostalCode(request.getBenePostalCode());
        req.setBenePhone(request.getBenePhone());
        if (request.getBeneBirthDate() != null) {
            req.setBeneBirthDate(GlobalFunctions.mMDDYYYYFromDate(request.getBeneBirthDate()
                    .getTime()));
        }
        req.setBeneIdentificationType(request.getBeneIdentificationType());
        req.setBeneIdentificationNumber(request.getBeneIdentificationNumber());
        req.setBeneEmail(request.getBeneEmail());
        req.setAccountNumber(request.getAccountNumber());
        req.setAccountType(request.getAccountType());
        req.setPayingAgentBranchCode(request.getPayingAgentBranchCode());
        req.setInfoModifyFee(String.valueOf(request.getInfoModifyFee()));
        req.setInfoModifyReason(request.getInfoModifyReason());
        if (request.getProcessingDateLocal() != null) {
            req.setProcessingDateLocal(GlobalFunctions.mMDDYYYYHH24MISSFromDate(request
                    .getProcessingDateLocal().getTime()));
        }
        req.setOperator(request.getOperator());
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return NotificationConfirmRequest
     */
<<<<<<< HEAD
    public NotificationConfirmRequest convertToNotificationConfirmRequestObject(
            UFSNotificationConfirmReq request, String companyId, String orgId) {
        NotificationConfirmRequest req = new NotificationConfirmRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static NotificationConfirmRequest convertToNotificationConfirmRequestObject(
            UFSNotificationConfirmReq request, String companyId, String orgId) {
        NotificationConfirmRequest req = new NotificationConfirmRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setTxIdentifier(request.getTxIdentifier());
        req.setCorrespondentRefNumber(request.getCorrespondentRefNumber());
        req.setNotificationRefNumber(request.getNotificationRefNumber());
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return NotificationRequest
     */
<<<<<<< HEAD
    public NotificationRequest convertToNotificationRequestObject(UFSNotificationReq request,
            String companyId, String orgId) {
        NotificationRequest req = new NotificationRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static NotificationRequest convertToNotificationRequestObject(
            UFSNotificationReq request, String companyId, String orgId) {
        NotificationRequest req = new NotificationRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setNotificationCount(String.valueOf(request.getNotificationCount()));
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param request
     * @param companyId
     * @param orgId
     * @return QuickQuoteRequest
     */
<<<<<<< HEAD
    public QuickQuoteRequest convertToQuickQuoteRequestObject(UFSQuickQuoteReq request,
            String companyId, String orgId) {
        QuickQuoteRequest req = new QuickQuoteRequest();
        if (StringUtils.isBlank(request.getCorrespondentCode()))
=======
    public static QuickQuoteRequest convertToQuickQuoteRequestObject(UFSQuickQuoteReq request,
            String companyId, String orgId) {
        QuickQuoteRequest req = new QuickQuoteRequest();
        if (request.getCorrespondentCode() == null)
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            req.setCorrespondentCode(companyId);
        else {
            req.setCorrespondentCode(request.getCorrespondentCode());
        }
        req.setPaymentCountry(request.getPaymentCountry());
        req.setPaymentCurrency(request.getPaymentCurrency());
        req.setPaymentLocation(request.getPaymentLocation());
        req.setPaymentType(request.getPaymentType());
        req.setTxAgentCode(request.getTxAgentCode());
        req.setTxAmount(String.valueOf(request.getTxAmount()));
        req.setTxOriginCurrency(request.getTxOriginCurrency());
        req.setReserved1(request.getReserved1());
        req.setReserved2(request.getReserved2());
        req.setReserved3(request.getReserved3());
        return req;
    }

    /**
     * @param cancelTxResponse
     * @return UFSCancelTxResp
     */
<<<<<<< HEAD
    public UFSCancelTxResp convertToUFSCancelTxRespObject(TxCancelResponse cancelTxResponse) {
=======
    public static UFSCancelTxResp convertToUFSCancelTxRespObject(TxCancelResponse cancelTxResponse) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        UFSCancelTxResp ufsCancelTxResp = new UFSCancelTxResp();
        ufsCancelTxResp.setResponseCode(cancelTxResponse.getResponseCode());
        ufsCancelTxResp.setResponseString(cancelTxResponse.getResponseString());
        ufsCancelTxResp.setTxIdentifier(cancelTxResponse.getTxIdentifier());
        ufsCancelTxResp.setCorrespondentRefNumber(cancelTxResponse.getCorrespondentRefNumber());
        ufsCancelTxResp.setProcessingDateEST(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(cancelTxResponse.getProcessingDateEST()));
        ufsCancelTxResp.setReserved1(cancelTxResponse.getReserved1());
        ufsCancelTxResp.setReserved2(cancelTxResponse.getReserved2());
        ufsCancelTxResp.setReserved3(cancelTxResponse.getReserved3());
        return ufsCancelTxResp;
    }

    /**
     * @param confirmSCTxResponse
     * @return UFSConfirmSCTxResp
     */
<<<<<<< HEAD
    public UFSConfirmSCTxResp convertToUFSConfirmSCTxRespObject(
=======
    public static UFSConfirmSCTxResp convertToUFSConfirmSCTxRespObject(
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            ConfirmSCTxResponse confirmSCTxResponse) {
        UFSConfirmSCTxResp ufsConfirmSCTxResp = new UFSConfirmSCTxResp();
        ufsConfirmSCTxResp.setResponseCode(confirmSCTxResponse.getResponseCode());
        ufsConfirmSCTxResp.setResponseString(confirmSCTxResponse.getResponseString());
        ufsConfirmSCTxResp.setReserved1(confirmSCTxResponse.getReserved1());
        ufsConfirmSCTxResp.setReserved2(confirmSCTxResponse.getReserved2());
        ufsConfirmSCTxResp.setReserved3(confirmSCTxResponse.getReserved3());
        return ufsConfirmSCTxResp;
    }

    /**
     * @param createSCTxResponse
     * @return UFSCreateSCTxResp
     */
<<<<<<< HEAD
    public UFSCreateSCTxResp convertToUFSCreateSCTxRespObject(CreateSCTxResponse createSCTxResponse) {
=======
    public static UFSCreateSCTxResp convertToUFSCreateSCTxRespObject(
            CreateSCTxResponse createSCTxResponse) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        UFSCreateSCTxResp ufsCreateSCTxResp = new UFSCreateSCTxResp();
        ufsCreateSCTxResp.setCorrespondentRefNumber(createSCTxResponse.getCorrespondentRefNumber());
        ufsCreateSCTxResp.setCreationDateEST(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(createSCTxResponse.getCreationDateEST()));
        ufsCreateSCTxResp.setDisclaimer(createSCTxResponse.getDisclaimer());
        ufsCreateSCTxResp.setOriginCurrency(createSCTxResponse.getOriginCurrency());
        ufsCreateSCTxResp.setPaymentAmount(GlobalFunctions.doubleFromString(createSCTxResponse
                .getPaymentAmount()));
        ufsCreateSCTxResp.setPaymentCurrency(createSCTxResponse.getPaymentCurrency());
        ufsCreateSCTxResp.setProcessingDateEST(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(createSCTxResponse.getProcessingDateEST()));
        ufsCreateSCTxResp.setReceiptHTML(createSCTxResponse.getReceiptHTML());
        ufsCreateSCTxResp.setReserved1(createSCTxResponse.getReserved1());
        ufsCreateSCTxResp.setReserved2(createSCTxResponse.getReserved2());
        ufsCreateSCTxResp.setReserved3(createSCTxResponse.getReserved3());
        ufsCreateSCTxResp.setResponseCode(createSCTxResponse.getResponseCode());
        ufsCreateSCTxResp.setResponseString(createSCTxResponse.getResponseString());
        ufsCreateSCTxResp.setTxAmount(GlobalFunctions.doubleFromString(createSCTxResponse
                .getTxAmount()));
        ufsCreateSCTxResp.setTxCorrespondentFee(GlobalFunctions.doubleFromString(createSCTxResponse
                .getTxCorrespondentFee()));
        ufsCreateSCTxResp.setTxExchangeRate(GlobalFunctions.doubleFromString(createSCTxResponse
                .getTxExchangeRate()));
        ufsCreateSCTxResp.setTxFeeTotal(GlobalFunctions.doubleFromString(createSCTxResponse
                .getTxFeeTotal()));
        ufsCreateSCTxResp.setTxIdentifier(createSCTxResponse.getTxIdentifier());
        ufsCreateSCTxResp.setTxUTLRFee(GlobalFunctions.doubleFromString(createSCTxResponse
                .getTxUTLRFee()));
        ufsCreateSCTxResp.setTxStatus(createSCTxResponse.getTxStatus());
        return ufsCreateSCTxResp;
    }

    /**
     * @param createTxResponse
     * @return UFSCreateTxResp
     */
<<<<<<< HEAD
    public UFSCreateTxResp convertToUFSCreateTxRespObject(CreateTxResponse createTxResponse) {
=======
    public static UFSCreateTxResp convertToUFSCreateTxRespObject(CreateTxResponse createTxResponse) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        UFSCreateTxResp ufsCreateTxResp = new UFSCreateTxResp();
        ufsCreateTxResp.setResponseCode(createTxResponse.getResponseCode());
        ufsCreateTxResp.setResponseString(createTxResponse.getResponseString());
        ufsCreateTxResp.setTxIdentifier(createTxResponse.getTxIdentifier());
        ufsCreateTxResp.setCorrespondentRefNumber(createTxResponse.getCorrespondentRefNumber());
        ufsCreateTxResp.setProcessingDateEST(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(createTxResponse.getProcessingDateEST()));
        ufsCreateTxResp.setReserved1(createTxResponse.getReserved1());
        ufsCreateTxResp.setReserved2(createTxResponse.getReserved2());
        ufsCreateTxResp.setReserved3(createTxResponse.getReserved3());
        return ufsCreateTxResp;
    }

    /**
     * @param getTxDetailsResponse
     * @return UFSGetTxDetailsResp
     */
<<<<<<< HEAD
    public UFSGetTxDetailsResp convertToUFSGetTxDetailsRespObject(
=======
    public static UFSGetTxDetailsResp convertToUFSGetTxDetailsRespObject(
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            GetTxDetailsResponse getTxDetailsResponse) {
        UFSGetTxDetailsResp ufsGetTxDetailsResp = new UFSGetTxDetailsResp();
        ufsGetTxDetailsResp.setAccountNumber(getTxDetailsResponse.getAccountNumber());
        ufsGetTxDetailsResp.setAccountType(getTxDetailsResponse.getAccountType());
        ufsGetTxDetailsResp.setBeneAddress1(getTxDetailsResponse.getBeneAddress1());
        ufsGetTxDetailsResp.setBeneAddress2(getTxDetailsResponse.getBeneAddress2());
        ufsGetTxDetailsResp.setBeneBirthDate(GlobalFunctions
                .calendarFromMMDDYYYY(getTxDetailsResponse.getBeneBirthDate()));
        ufsGetTxDetailsResp.setBeneCity(getTxDetailsResponse.getBeneCity());
        ufsGetTxDetailsResp.setBeneCountry(getTxDetailsResponse.getBeneCountry());
        ufsGetTxDetailsResp.setBeneEmail(getTxDetailsResponse.getBeneEmail());
        ufsGetTxDetailsResp.setBeneFirstName(getTxDetailsResponse.getBeneFirstName());
        ufsGetTxDetailsResp.setBeneIdentificationNumber(getTxDetailsResponse
                .getBeneIdentificationNumber());
        ufsGetTxDetailsResp.setBeneIdentificationType(getTxDetailsResponse
                .getBeneIdentificationType());
        ufsGetTxDetailsResp.setBeneLastName(getTxDetailsResponse.getBeneLastName());
        ufsGetTxDetailsResp.setBeneMidName(getTxDetailsResponse.getBeneMidName());
        ufsGetTxDetailsResp.setBenePhone(getTxDetailsResponse.getBenePhone());
        ufsGetTxDetailsResp.setBenePostalCode(getTxDetailsResponse.getBenePostalCode());
        ufsGetTxDetailsResp.setBeneRefNumber(getTxDetailsResponse.getBeneRefNumber());
        ufsGetTxDetailsResp.setBeneSecondLastName(getTxDetailsResponse.getBeneSecondLastName());
        ufsGetTxDetailsResp.setBeneState(getTxDetailsResponse.getBeneState());
        ufsGetTxDetailsResp.setCancelLocalTime(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(getTxDetailsResponse.getCancelLocalTime()));
        ufsGetTxDetailsResp.setCorrespondentRefNumber(getTxDetailsResponse
                .getCorrespondentRefNumber());
        ufsGetTxDetailsResp.setLastNotificationDate(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(getTxDetailsResponse.getLastNotificationDate()));
        ufsGetTxDetailsResp.setLastNotificationMessage(getTxDetailsResponse
                .getLastNotificationMessage());
        ufsGetTxDetailsResp.setLastNotificationType(getTxDetailsResponse.getLastNotificationType());
        ufsGetTxDetailsResp.setLastStatusChangeTimeStamp(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(getTxDetailsResponse.getLastStatusChangeTimeStamp()));
        ufsGetTxDetailsResp.setPayingAgentBranchCode(getTxDetailsResponse
                .getPayingAgentBranchCode());
        ufsGetTxDetailsResp.setPayingAgentOperator(getTxDetailsResponse.getPayingAgentOperator());
        ufsGetTxDetailsResp.setPayingLocalTime(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(getTxDetailsResponse.getPayingLocalTime()));
        ufsGetTxDetailsResp.setPaymentAmount(GlobalFunctions.doubleFromString(getTxDetailsResponse
                .getPaymentAmount()));
        ufsGetTxDetailsResp.setPaymentCountry(getTxDetailsResponse.getPaymentCountry());
        ufsGetTxDetailsResp.setPaymentCurrency(getTxDetailsResponse.getPaymentCurrency());
        ufsGetTxDetailsResp.setPaymentLocation(getTxDetailsResponse.getPaymentLocation());
        ufsGetTxDetailsResp.setPaymentType(getTxDetailsResponse.getPaymentType());
        ufsGetTxDetailsResp.setReserved1(getTxDetailsResponse.getReserved1());
        ufsGetTxDetailsResp.setReserved2(getTxDetailsResponse.getReserved2());
        ufsGetTxDetailsResp.setReserved3(getTxDetailsResponse.getReserved3());
        ufsGetTxDetailsResp.setResponseCode(getTxDetailsResponse.getResponseCode());
        ufsGetTxDetailsResp.setResponseString(getTxDetailsResponse.getResponseString());
        ufsGetTxDetailsResp.setSenderAddress1(getTxDetailsResponse.getSenderAddress1());
        ufsGetTxDetailsResp.setSenderAddress2(getTxDetailsResponse.getSenderAddress2());
        ufsGetTxDetailsResp.setSenderBirthDate(GlobalFunctions
                .calendarFromMMDDYYYY(getTxDetailsResponse.getSenderBirthDate()));
        ufsGetTxDetailsResp.setSenderCity(getTxDetailsResponse.getSenderCity());
        ufsGetTxDetailsResp.setSenderEmail(getTxDetailsResponse.getSenderEmail());
        ufsGetTxDetailsResp.setSenderFirstName(getTxDetailsResponse.getSenderFirstName());
        ufsGetTxDetailsResp.setSenderIdentificationNumber(getTxDetailsResponse
                .getSenderIdentificationNumber());
        ufsGetTxDetailsResp.setSenderIdentificationType(getTxDetailsResponse
                .getSenderIdentificationType());
        ufsGetTxDetailsResp.setSenderLastName(getTxDetailsResponse.getSenderLastName());
        ufsGetTxDetailsResp.setSenderMidName(getTxDetailsResponse.getSenderMidName());
        ufsGetTxDetailsResp.setSenderPhone(getTxDetailsResponse.getSenderPhone());
        ufsGetTxDetailsResp.setSenderPostalCode(getTxDetailsResponse.getSenderPostalCode());
        ufsGetTxDetailsResp.setSenderRefNumber(getTxDetailsResponse.getSenderRefNumber());
        ufsGetTxDetailsResp.setSenderSecondLastName(getTxDetailsResponse.getSenderSecondLastName());
        ufsGetTxDetailsResp.setSenderState(getTxDetailsResponse.getSenderState());
        ufsGetTxDetailsResp.setTxAmount(GlobalFunctions.doubleFromString(getTxDetailsResponse
                .getTxAmount()));
        ufsGetTxDetailsResp.setTxCreationDate(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(getTxDetailsResponse.getTxCreationDate()));
        ufsGetTxDetailsResp.setTxExchangeRate(GlobalFunctions.doubleFromString(getTxDetailsResponse
                .getTxExchangeRate()));
        ufsGetTxDetailsResp.setTxFee(GlobalFunctions.doubleFromString(getTxDetailsResponse
                .getTxFee()));
        ufsGetTxDetailsResp.setTxIdentifier(getTxDetailsResponse.getTxIdentifier());
        ufsGetTxDetailsResp.setTxOriginCountry(getTxDetailsResponse.getTxOriginCountry());
        ufsGetTxDetailsResp.setTxOriginCurrency(getTxDetailsResponse.getTxOriginCurrency());
        ufsGetTxDetailsResp.setTxStatus(getTxDetailsResponse.getTxStatus());
        return ufsGetTxDetailsResp;
    }

    /**
     * @param infoModifyResponse
     * @return UFSInfoModifyResp
     */
<<<<<<< HEAD
    public UFSInfoModifyResp convertToUFSInfoModifyRespObject(InfoModifyResponse infoModifyResponse) {
=======
    public static UFSInfoModifyResp convertToUFSInfoModifyRespObject(
            InfoModifyResponse infoModifyResponse) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        UFSInfoModifyResp ufsInfoModifyResp = new UFSInfoModifyResp();
        ufsInfoModifyResp.setResponseCode(infoModifyResponse.getResponseCode());
        ufsInfoModifyResp.setResponseString(infoModifyResponse.getResponseString());
        ufsInfoModifyResp.setTxIdentifier(infoModifyResponse.getTxIdentifier());
        ufsInfoModifyResp.setCorrespondentRefNumber(infoModifyResponse.getCorrespondentRefNumber());
        ufsInfoModifyResp.setProcessingDateEST(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(infoModifyResponse.getProcessingDateEST()));
        ufsInfoModifyResp.setReserved1(infoModifyResponse.getReserved1());
        ufsInfoModifyResp.setReserved2(infoModifyResponse.getReserved2());
        ufsInfoModifyResp.setReserved3(infoModifyResponse.getReserved3());
        return ufsInfoModifyResp;
    }

    /**
     * @param notificationConfirmResponse
     * @return UFSNotificationConfirmResp
     */
<<<<<<< HEAD
    public UFSNotificationConfirmResp convertToUFSNotificationConfirmRespObject(
=======
    public static UFSNotificationConfirmResp convertToUFSNotificationConfirmRespObject(
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            NotificationConfirmResponse notificationConfirmResponse) {
        UFSNotificationConfirmResp ufsNotificationResp = new UFSNotificationConfirmResp();
        ufsNotificationResp.setResponseCode(notificationConfirmResponse.getResponseCode());
        ufsNotificationResp.setReserved1(notificationConfirmResponse.getReserved1());
        ufsNotificationResp.setReserved2(notificationConfirmResponse.getReserved2());
        ufsNotificationResp.setReserved3(notificationConfirmResponse.getReserved3());
        return ufsNotificationResp;
    }

    private UFSNotificationItem convertToUFSNotificationItemObject(NotificationItem notificationItem) {
        UFSNotificationItem ufsNotificationItem = new UFSNotificationItem();
        ufsNotificationItem.setNotificationRefNumber(notificationItem.getNotificationRefNumber());
        ufsNotificationItem.setDescription(notificationItem.getDescription());
        ufsNotificationItem.setTxIdentifier(notificationItem.getTxIdentifier());
        ufsNotificationItem.setCorrespondentRefNumber(notificationItem.getCorrespondentRefNumber());
        ufsNotificationItem.setNotificationType(notificationItem.getNotificationType());
        ufsNotificationItem.setPayingAgent(notificationItem.getPayingAgent());
        ufsNotificationItem.setPayingAgentBranchCode(notificationItem.getPayingAgentBranchCode());
        ufsNotificationItem.setPayingAgentOperator(notificationItem.getPayingAgentOperator());
        ufsNotificationItem.setBeneIdentificationType(notificationItem.getBeneIdentificationType());
        ufsNotificationItem.setBeneIdentificationNumber(notificationItem
                .getBeneIdentificationNumber());
        ufsNotificationItem.setPaymentLocalTime(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(notificationItem.getPaymentLocalTime()));
        ufsNotificationItem.setReasonForReversal(notificationItem.getReasonForReversal());
        ufsNotificationItem.setMessageText(notificationItem.getMessageText());
        ufsNotificationItem.setReversalLocalTime(GlobalFunctions
                .calendarFromMMDDYYYYHH24MISS(notificationItem.getReversalLocalTime()));
        ufsNotificationItem.setReserved1(notificationItem.getReserved1());
        ufsNotificationItem.setReserved2(notificationItem.getReserved2());
        ufsNotificationItem.setReserved3(notificationItem.getReserved3());
        return ufsNotificationItem;
    }

    /**
     * @param notificationResponse
     * @return UFSNotificationResp
     */
<<<<<<< HEAD
    public UFSNotificationResp convertToUFSNotificationRespObject(
=======
    public static UFSNotificationResp convertToUFSNotificationRespObject(
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
            NotificationResponse notificationResponse) {
        UFSNotificationResp ufsNotificationResp = new UFSNotificationResp();
        ufsNotificationResp.setResponseCode(notificationResponse.getResponseCode());
        ufsNotificationResp.setResponseString(notificationResponse.getResponseString());
        NotificationItem[] items = notificationResponse.getNotificationData();
        if (items != null) {
            UFSNotificationItem[] ufsItems = new UFSNotificationItem[items.length];
            for (int i = 0; i < items.length; i++) {
                ufsItems[i] = convertToUFSNotificationItemObject(items[i]);
            }
            ufsNotificationResp.setNotificationData(ufsItems);
        } else {
            ufsNotificationResp.setNotificationData(null);
        }
        ufsNotificationResp.setReserved1(notificationResponse.getReserved1());
        ufsNotificationResp.setReserved2(notificationResponse.getReserved2());
        ufsNotificationResp.setReserved3(notificationResponse.getReserved3());
        return ufsNotificationResp;
    }

    /**
     * @param quickQuoteResponse
     * @return UFSQuickQuoteResp
     */
<<<<<<< HEAD
    public UFSQuickQuoteResp convertToUFSQuickQuoteRespObject(QuickQuoteResponse quickQuoteResponse) {
=======
    public static UFSQuickQuoteResp convertToUFSQuickQuoteRespObject(
            QuickQuoteResponse quickQuoteResponse) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        UFSQuickQuoteResp ufsQuickQuoteResp = new UFSQuickQuoteResp();
        ufsQuickQuoteResp.setPaymentAmount(GlobalFunctions.doubleFromString(quickQuoteResponse
                .getPaymentAmount()));
        ufsQuickQuoteResp.setReserved1(quickQuoteResponse.getReserved1());
        ufsQuickQuoteResp.setReserved2(quickQuoteResponse.getReserved2());
        ufsQuickQuoteResp.setReserved3(quickQuoteResponse.getReserved3());
        ufsQuickQuoteResp.setResponseCode(quickQuoteResponse.getResponseCode());
        ufsQuickQuoteResp.setResponseString(quickQuoteResponse.getResponseString());
        ufsQuickQuoteResp.setTxCorrespondentFee(GlobalFunctions.doubleFromString(quickQuoteResponse
                .getTxCorrespondentFee()));
        ufsQuickQuoteResp.setTxExchangeRate(GlobalFunctions.doubleFromString(quickQuoteResponse
                .getTxExchangeRate()));
        ufsQuickQuoteResp.setTxFeeTotal(GlobalFunctions.doubleFromString(quickQuoteResponse
                .getTxFeeTotal()));
        ufsQuickQuoteResp.setTxUTLRFee(GlobalFunctions.doubleFromString(quickQuoteResponse
                .getTxUTLRFee()));
        return ufsQuickQuoteResp;
    }

    /**
     * @param method
     * @return SnapType
     */
<<<<<<< HEAD
    public SnapType getDataTypes(Method method) {
=======
    public static SnapType getDataTypes(Method method) {
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
        String type = method.getGenericParameterTypes()[0].toString();
        if (StringUtils.contains(type, String.class.getName())) {
            return SnapType.STRING;
        } else if (StringUtils.contains(type, INT)) {
            return SnapType.INTEGER;
        } else if (StringUtils.contains(type, Float.class.getName())
                || StringUtils.contains(type, Long.class.getName())
                || StringUtils.contains(type, Double.class.getName())) {
            return SnapType.NUMBER;
        } else if (StringUtils.contains(type, Calendar.class.getName())) {
            return SnapType.DATETIME;
        }
        return SnapType.ANY;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 84f3989399e926d18a457a9135dfcc3c848ac39a
