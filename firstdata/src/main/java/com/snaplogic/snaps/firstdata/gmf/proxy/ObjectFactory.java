/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.firstdata.gmf.proxy;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.*;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlRegistry
public class ObjectFactory {
    private final static QName _TotReqDate_QNAME = new QName(NAMESPACE, TOT_REQ_DATE_ELT);
    private final static QName _FPI_QNAME = new QName(NAMESPACE, FPI_ELT);
    private final static QName _KeyID_QNAME = new QName(NAMESPACE, KEY_ID_ELT);
    private final static QName _DiscTransQualifier_QNAME = new QName(NAMESPACE,
            DISC_TRANS_QUALIFIER_ELT);
    private final static QName _Track1Data_QNAME = new QName(NAMESPACE, TRACK1_DATA_ELT);
    private final static QName _VisaBID_QNAME = new QName(NAMESPACE, VISA_BID_ELT);
    private final static QName _EBTType_QNAME = new QName(NAMESPACE, EBT_TYPE_ELT);
    private final static QName _CardLevelResult_QNAME = new QName(NAMESPACE, CARD_LEVEL_RESULT_ELT);
    private final static QName _CurrFileCreationDt_QNAME = new QName(NAMESPACE,
            CURR_FILE_CREATION_DT_ELT);
    private final static QName _ReqFileOffset_QNAME = new QName(NAMESPACE, REQ_FILE_OFFSET_ELT);
    private final static QName _SourceReasonCode_QNAME = new QName(NAMESPACE,
            SOURCE_REASON_CODE_ELT);
    private final static QName _DiscPOSData_QNAME = new QName(NAMESPACE, DISC_POS_DATA_ELT);
    private final static QName _MCSecrAD_QNAME = new QName(NAMESPACE, MC_SECR_AD_ELT);
    private final static QName _AmExTranID_QNAME = new QName(NAMESPACE, AM_EX_TRAN_ID_ELT);
    private final static QName _CardSeqNum_QNAME = new QName(NAMESPACE, CARD_SEQ_NUM_ELT);
    private final static QName _MerchCnty_QNAME = new QName(NAMESPACE, MERCH_CNTY_ELT);
    private final static QName _HoldInfo_QNAME = new QName(NAMESPACE, HOLD_INFO_ELT);
    private final static QName _PayeeID_QNAME = new QName(NAMESPACE, PAYEE_ID_ELT);
    private final static QName _DiscProcCode_QNAME = new QName(NAMESPACE, DISC_PROC_CODE_ELT);
    private final static QName _UnitOfMsure_QNAME = new QName(NAMESPACE, UNIT_OF_MSURE_ELT);
    private final static QName _EcommTxnInd_QNAME = new QName(NAMESPACE, ECOMM_TXN_IND_ELT);
    private final static QName _ChkSvcPvdr_QNAME = new QName(NAMESPACE, CHK_SVC_PVDR_ELT);
    private final static QName _VarCustRctTxt_QNAME = new QName(NAMESPACE, VAR_CUST_RCT_TXT_ELT);
    private final static QName _Safekey_QNAME = new QName(NAMESPACE, SAFEKEY_ELT);
    private final static QName _TAExpDate_QNAME = new QName(NAMESPACE, TA_EXP_DATE_ELT);
    private final static QName _EschtblTxn_QNAME = new QName(NAMESPACE, ESCHTBL_TXN_ELT);
    private final static QName _FrghtAmt_QNAME = new QName(NAMESPACE, FRGHT_AMT_ELT);
    private final static QName _MerchCatCode_QNAME = new QName(NAMESPACE, MERCH_CAT_CODE_ELT);
    private final static QName _BillingInd_QNAME = new QName(NAMESPACE, BILLING_IND_ELT);
    private final static QName _EncrptBlock_QNAME = new QName(NAMESPACE, ENCRPT_BLOCK_ELT);
    private final static QName _DiscntAmt_QNAME = new QName(NAMESPACE, DISCNT_AMT_ELT);
    private final static QName _LocalDateTime_QNAME = new QName(NAMESPACE, LOCAL_DATE_TIME_ELT);
    private final static QName _SttlmDate_QNAME = new QName(NAMESPACE, STTLM_DATE_ELT);
    private final static QName _ProgramID_QNAME = new QName(NAMESPACE, PROGRAM_ID_ELT);
    private final static QName _UnitPrice_QNAME = new QName(NAMESPACE, UNIT_PRICE_ELT);
    private final static QName _EcommURL_QNAME = new QName(NAMESPACE, ECOMM_URL_ELt);
    private final static QName _TrnmsnDateTime_QNAME = new QName(NAMESPACE, TRNMSN_DATE_TIME_ELT);
    private final static QName _DevTypeInd_QNAME = new QName(NAMESPACE, DEV_TYPE_IND_ELT);
    private final static QName _DutyAmt_QNAME = new QName(NAMESPACE, DUTY_AMT_ELT);
    private final static QName _TermLocInd_QNAME = new QName(NAMESPACE, TERM_LOC_IND_ELT);
    private final static QName _BillPymtTxnInd_QNAME = new QName(NAMESPACE, BILL_PYMT_TXN_IND_ELT);
    private final static QName _Track2Data_QNAME = new QName(NAMESPACE, TRACK2_DATA_ELT);
    private final static QName _VisaXID_QNAME = new QName(NAMESPACE, VISA_XID_ELT);
    private final static QName _AmExPOSData_QNAME = new QName(NAMESPACE, AM_EX_POS_DATA_ELT);
    private final static QName _Tkn_QNAME = new QName(NAMESPACE, TKN_ELT);
    private final static QName _POSCondCode_QNAME = new QName(NAMESPACE, POS_COND_CODE_ELT);
    private final static QName _EMVData_QNAME = new QName(NAMESPACE, EMV_DATA_ELT);
    private final static QName _KeySerialNumData_QNAME = new QName(NAMESPACE,
            KEY_SERIAL_NUM_DATA_ELT);
    private final static QName _MerchAddr_QNAME = new QName(NAMESPACE, MERCH_ADDR_ELT);
    private final static QName _MerchState_QNAME = new QName(NAMESPACE, MERCH_STATE_ELT);
    private final static QName _TASctyKey_QNAME = new QName(NAMESPACE, TA_SCTY_KEY_ELT);
    private final static QName _VisaAUAR_QNAME = new QName(NAMESPACE, VISA_AUAR_ELT);
    private final static QName _TxnAmt_QNAME = new QName(NAMESPACE, TXN_AMT_ELT);
    private final static QName _CardExpiryDate_QNAME = new QName(NAMESPACE, CARD_EXPIRY_DATE_ELT);
    private final static QName _MerchTaxID_QNAME = new QName(NAMESPACE, MERCH_TAX_ID_ELT);
    private final static QName _RespCode_QNAME = new QName(NAMESPACE, RESP_CODE_ELT);
    private final static QName _MerchPostalCode_QNAME = new QName(NAMESPACE, MERCH_POSTAL_CODE_ELT);
    private final static QName _TermCatCode_QNAME = new QName(NAMESPACE, TERM_CAT_CODE_ELT);
    private final static QName _OfferResInd_QNAME = new QName(NAMESPACE, OFFER_RES_IND_ELT);
    private final static QName _CardTag_QNAME = new QName(NAMESPACE, CARD_TAG_ELT);
    private final static QName _DrvLic_QNAME = new QName(NAMESPACE, DRV_LIC_ELT);
    private final static QName _POSEntryModeChg_QNAME = new QName(NAMESPACE, POS_ENTRY_MODE_CHG_ELT);
    private final static QName _NextFileDLOffset_QNAME = new QName(NAMESPACE,
            NEXT_FILE_DL_OFFSET_ELT);
    private final static QName _ServCode_QNAME = new QName(NAMESPACE, SERV_CODE_ELT);
    private final static QName _Qnty_QNAME = new QName(NAMESPACE, QNTY_ELT);
    private final static QName _OfferPubName_QNAME = new QName(NAMESPACE, OFFER_PUB_NAME_ELT);
    private final static QName _EncrptTrgt_QNAME = new QName(NAMESPACE, ENCRPT_TRGT_ELT);
    private final static QName _OrderDate_QNAME = new QName(NAMESPACE, ORDER_DATE_ELT);
    private final static QName _OfferAmount_QNAME = new QName(NAMESPACE, OFFER_AMOUNT_ELT);
    private final static QName _CardTxnAmt_QNAME = new QName(NAMESPACE, CARD_TXN_AMT_ELT);
    private final static QName _VisaSecrTxnAD_QNAME = new QName(NAMESPACE, VISA_SECR_TXN_AD_ELT);
    private final static QName _MICR_QNAME = new QName(NAMESPACE, MICR_ELT);
    private final static QName _PurchIdfr_QNAME = new QName(NAMESPACE, PURCH_IDFR_ELT);
    private final static QName _AmexXID_QNAME = new QName(NAMESPACE, AMEX_XID_ELT);
    private final static QName _TermEntryCapablt_QNAME = new QName(NAMESPACE,
            TERM_ENTRY_CAPABLT_ELT);
    private final static QName _CardCaptCap_QNAME = new QName(NAMESPACE, CARD_CAPT_CAP_ELT);
    private final static QName _OrderNum_QNAME = new QName(NAMESPACE, ORDER_NUM_ELT);
    private final static QName _POSID_QNAME = new QName(NAMESPACE, POSID_ELT);
    private final static QName _ReceiptCopy_QNAME = new QName(NAMESPACE, RECEIPT_COPY_ELT);
    private final static QName _PINData_QNAME = new QName(NAMESPACE, PIN_DATA_ELT);
    private final static QName _CAVVResultCode_QNAME = new QName(NAMESPACE, CAVV_RESULT_CODE_ELT);
    private final static QName _FBData_QNAME = new QName(NAMESPACE, FB_DATA_ELT);
    private final static QName _AddAmtType_QNAME = new QName(NAMESPACE, ADD_AMT_TYPE_ELT);
    private final static QName _CCVData_QNAME = new QName(NAMESPACE, CCV_DATA_ELT);
    private final static QName _NACSProdCode_QNAME = new QName(NAMESPACE, NACS_PROD_CODE_ELT);
    private final static QName _ShipFromPostalCode_QNAME = new QName(NAMESPACE,
            SHIP_FROM_POSTAL_CODE_ELT);
    private final static QName _TxnType_QNAME = new QName(NAMESPACE, TXN_TYPE_ELT);
    private final static QName _AddAmt_QNAME = new QName(NAMESPACE, ADD_AMT_ELT);
    private final static QName _RefNum_QNAME = new QName(NAMESPACE, REF_NUM_ELT);
    private final static QName _AVSBillingPostalCode_QNAME = new QName(NAMESPACE,
            AVS_BILLING_POSTAL_CODE_ELT);
    private final static QName _ProcInd_QNAME = new QName(NAMESPACE, PROC_IND_ELT);
    private final static QName _TermID_QNAME = new QName(NAMESPACE, TERM_ID_ELT);
    private final static QName _MerchCtry_QNAME = new QName(NAMESPACE, MERCH_CTRY_ELT);
    private final static QName _AVSResultCode_QNAME = new QName(NAMESPACE, AVS_RESULT_CODE_ELT);
    private final static QName _CardClass_QNAME = new QName(NAMESPACE, CARD_CLASS_ELT);
    private final static QName _MCMSDI_QNAME = new QName(NAMESPACE, MCMSDI_ELT);
    private final static QName _CCVInd_QNAME = new QName(NAMESPACE, CCV_IND_ELT);
    private final static QName _FunCode_QNAME = new QName(NAMESPACE, FUN_CODE_ELT);
    private final static QName _TxnCrncy_QNAME = new QName(NAMESPACE, TXN_CRNCY_ELT);
    private final static QName _FileType_QNAME = new QName(NAMESPACE, FILE_TYPE_ELT);
    private final static QName _AddtlRespData_QNAME = new QName(NAMESPACE, ADDTL_RESP_DATA_ELT);
    private final static QName _OfferID_QNAME = new QName(NAMESPACE, OFFER_ID_ELT);
    private final static QName _CardCost_QNAME = new QName(NAMESPACE, CARD_COST_ELT);
    private final static QName _CCVResultCode_QNAME = new QName(NAMESPACE, CCV_RESULT_CODE_ELT);
    private final static QName _OfferProvID_QNAME = new QName(NAMESPACE, OFFER_PROV_ID_ELT);
    private final static QName _OfferProvName_QNAME = new QName(NAMESPACE, OFFER_PROV_NAME_ELT);
    private final static QName _STAN_QNAME = new QName(NAMESPACE, STAN_ELT);
    private final static QName _ServLvl_QNAME = new QName(NAMESPACE, SERV_LVL_ELT);
    private final static QName _AcctNum_QNAME = new QName(NAMESPACE, ACCT_NUM_ELT);
    private final static QName _SctyLvl_QNAME = new QName(NAMESPACE, SCTY_LVL_ELT);
    private final static QName _CustSvcPhoneNumber_QNAME = new QName(NAMESPACE,
            CUST_SVC_PHONE_NUMBER_ELT);
    private final static QName _OrigTranDateTime_QNAME = new QName(NAMESPACE,
            ORIG_TRAN_DATE_TIME_ELT);
    private final static QName _AppExpDate_QNAME = new QName(NAMESPACE, APP_EXP_DATE_ELT);
    private final static QName _AddAmtAcctType_QNAME = new QName(NAMESPACE, ADD_AMT_ACCT_TYPE_ELT);
    private final static QName _DestCtryCode_QNAME = new QName(NAMESPACE, DEST_CTRY_CODE_ELT);
    private final static QName _TPPID_QNAME = new QName(NAMESPACE, TPPID_ELT);
    private final static QName _GroupID_QNAME = new QName(NAMESPACE, GROUP_ID_ELT);
    private final static QName _POSOfferCap_QNAME = new QName(NAMESPACE, POS_OFFER_CAP_ELT);
    private final static QName _OfferPubID_QNAME = new QName(NAMESPACE, OFFER_PUB_ID_ELT);
    private final static QName _ReversalInd_QNAME = new QName(NAMESPACE, REVERSAL_IND_ELT);
    private final static QName _MerchName_QNAME = new QName(NAMESPACE, MERCH_NAME_ELT);
    private final static QName _FileSize_QNAME = new QName(NAMESPACE, FILE_SIZE_ELT);
    private final static QName _TransID_QNAME = new QName(NAMESPACE, TRANS_ID_ELT);
    private final static QName _CARC_QNAME = new QName(NAMESPACE, CARC_ELT);
    private final static QName _DiscRespCode_QNAME = new QName(NAMESPACE, DISC_RESP_CODE_ELT);
    private final static QName _PayeePhoneNum_QNAME = new QName(NAMESPACE, PAYEE_PHONE_NUM_ELT);
    private final static QName _MCPOSData_QNAME = new QName(NAMESPACE, MCPOS_DATA_ELT);
    private final static QName _ErrorData_QNAME = new QName(NAMESPACE, ERROR_DATA_ELT);
    private final static QName _AmexSecrAD_QNAME = new QName(NAMESPACE, AMEX_SECR_AD_ELT);
    private final static QName _XCodeResp_QNAME = new QName(NAMESPACE, X_CODE_RESP_ELT);
    private final static QName _ReqFBMaxSize_QNAME = new QName(NAMESPACE, REQ_FB_MAX_SIZE_ELT);
    private final static QName _FACode_QNAME = new QName(NAMESPACE, FA_CODE_ELT);
    private final static QName _Password_QNAME = new QName(NAMESPACE, PASSWORD_ELT);
    private final static QName _DestPostalCode_QNAME = new QName(NAMESPACE, DEST_POSTAL_CODE_ELT);
    private final static QName _ExistingDebtInd_QNAME = new QName(NAMESPACE, EXISTING_DEBT_IND_ELT);
    private final static QName _AddAmtCrncy_QNAME = new QName(NAMESPACE, ADD_AMT_CRNCY_ELT);
    private final static QName _DLDateOfBirth_QNAME = new QName(NAMESPACE, DL_DATE_OF_BIRTH_ELT);
    private final static QName _OrigRespCode_QNAME = new QName(NAMESPACE, ORIG_RESP_CODE_ELT);
    private final static QName _DiscPOSEntry_QNAME = new QName(NAMESPACE, DISC_POS_ENTRY_ELT);
    private final static QName _AthNtwkID_QNAME = new QName(NAMESPACE, ATH_NTWK_ID_ELT);
    private final static QName _CCVErrorCode_QNAME = new QName(NAMESPACE, CCV_ERROR_CODE_ELT);
    private final static QName _BanknetData_QNAME = new QName(NAMESPACE, BANKNET_DATA_ELT);
    private final static QName _StateCode_QNAME = new QName(NAMESPACE, STATE_CODE_ELT);
    private final static QName _NumOfProds_QNAME = new QName(NAMESPACE, NUM_OF_PRODS_ELT);
    private final static QName _AltMerchID_QNAME = new QName(NAMESPACE, ALT_MERCH_ID_ELT);
    private final static QName _AVSBillingAddr_QNAME = new QName(NAMESPACE, AVS_BILLING_ADDR_ELT);
    private final static QName _DiscNRID_QNAME = new QName(NAMESPACE, DISC_NRID_ELT);
    private final static QName _PymtType_QNAME = new QName(NAMESPACE, PYMT_TYPE_ELT);
    private final static QName _ACI_QNAME = new QName(NAMESPACE, ACI_ELT);
    private final static QName _OrigLocalDateTime_QNAME = new QName(NAMESPACE,
            ORIG_LOCAL_DATE_TIME_ELT);
    private final static QName _MerchAdviceCode_QNAME = new QName(NAMESPACE, MERCH_ADVICE_CODE_ELT);
    private final static QName _NetSettleAmt_QNAME = new QName(NAMESPACE, NET_SETTLE_AMT_ELT);
    private final static QName _EncrptType_QNAME = new QName(NAMESPACE, ENCRPT_TYPE_ELT);
    private final static QName _AuthID_QNAME = new QName(NAMESPACE, AUTH_ID_ELT);
    private final static QName _SctyKeyUpdInd_QNAME = new QName(NAMESPACE, SCTY_KEY_UPD_IND_ELT);
    private final static QName _GMF_QNAME = new QName(NAMESPACE, GMF_ELT);
    private final static QName _SettleInd_QNAME = new QName(NAMESPACE, SETTLE_IND_ELT);
    private final static QName _CardType_QNAME = new QName(NAMESPACE, CARD_TYPE_ELT);
    private final static QName _POSEntryMode_QNAME = new QName(NAMESPACE, POS_ENTRY_MODE_ELT);
    private final static QName _PCOrderNum_QNAME = new QName(NAMESPACE, PC_ORDER_NUM_ELT);
    private final static QName _TranEditErrCode_QNAME = new QName(NAMESPACE, TRAN_EDIT_ERR_CODE_ELT);
    private final static QName _OfferDesc_QNAME = new QName(NAMESPACE, OFFER_DESC_ELT);
    private final static QName _TaxAmtCapablt_QNAME = new QName(NAMESPACE, TAX_AMT_CAPABLT_ELT);
    private final static QName _ChkEntryMethod_QNAME = new QName(NAMESPACE, CHK_ENTRY_METHOD_ELT);
    private final static QName _PartAuthrztnApprvlCapablt_QNAME = new QName(NAMESPACE,
            PART_AUTHRZTN_APPRVL_CAPABLT_ELT);
    private final static QName _TknType_QNAME = new QName(NAMESPACE, TKN_TYPE_ELT);
    private final static QName _FBSeq_QNAME = new QName(NAMESPACE, FB_SEQ_ELT);
    private final static QName _UCAFCollectInd_QNAME = new QName(NAMESPACE, UCAF_COLLECT_IND_ELT);
    private final static QName _ProcInfo_QNAME = new QName(NAMESPACE, PROC_INFO_ELT);
    private final static QName _MerchCity_QNAME = new QName(NAMESPACE, MERCH_CITY_ELT);
    private final static QName _ProdDesc_QNAME = new QName(NAMESPACE, PROD_DESC_ELT);
    private final static QName _TaxAmt_QNAME = new QName(NAMESPACE, TAX_AMT_ELT);
    private final static QName _OrigAuthID_QNAME = new QName(NAMESPACE, ORIG_AUTH_ID_ELT);
    private final static QName _ProdAmt_QNAME = new QName(NAMESPACE, PROD_AMT_ELT);
    private final static QName _PayeeAcctNum_QNAME = new QName(NAMESPACE, PAYEE_ACCT_NUM_ELT);
    private final static QName _FileCRC16_QNAME = new QName(NAMESPACE, FILE_CRC16_ELT);
    private final static QName _TaxInd_QNAME = new QName(NAMESPACE, TAX_IND_ELT);
    private final static QName _OrigSTAN_QNAME = new QName(NAMESPACE, ORIG_STAN_ELT);
    private final static QName _TxnCt_QNAME = new QName(NAMESPACE, TXN_CT_ELT);
    private final static QName _MerchantID_QNAME = new QName(NAMESPACE, MERCHANT_ID_ELT);
    private final static QName _MrktSpecificDataInd_QNAME = new QName(NAMESPACE,
            MRKT_SPECIFIC_DATA_IND_ELT);
    private final static QName _CardActivDate_QNAME = new QName(NAMESPACE, CARD_ACTIV_DATE_ELT);
    private final static QName _MerchID_QNAME = new QName(NAMESPACE, MERCH_ID_ELT);

    public ObjectFactory() {
    }

    public OrderGrp createOrderGrp() {
        return new OrderGrp();
    }

    public DebitGrp createDebitGrp() {
        return new DebitGrp();
    }

    public BillPayGrp createBillPayGrp() {
        return new BillPayGrp();
    }

    public CustInfoGrp createCustInfoGrp() {
        return new CustInfoGrp();
    }

    public ProdCodeDetGrp createProdCodeDetGrp() {
        return new ProdCodeDetGrp();
    }

    public DSGrp createDSGrp() {
        return new DSGrp();
    }

    public FileDLGrp createFileDLGrp() {
        return new FileDLGrp();
    }

    public CheckGrp createCheckGrp() {
        return new CheckGrp();
    }

    public CommonGrp createCommonGrp() {
        return new CommonGrp();
    }

    public CreditRequestDetails createCreditRequestDetails() {
        return new CreditRequestDetails();
    }

    public HostTotGrp createHostTotGrp() {
        return new HostTotGrp();
    }

    public PurchCardlvl2Grp createPurchCardlvl2Grp() {
        return new PurchCardlvl2Grp();
    }

    public RejectResponseDetails createRejectResponseDetails() {
        return new RejectResponseDetails();
    }

    public StoredValueGrp createStoredValueGrp() {
        return new StoredValueGrp();
    }

    public AmexGrp createAmexGrp() {
        return new AmexGrp();
    }

    public VoidTOReversalRequestDetails createVoidTOReversalRequestDetails() {
        return new VoidTOReversalRequestDetails();
    }

    public EbtGrp createEbtGrp() {
        return new EbtGrp();
    }

    public PrepaidResponseDetails createPrepaidResponseDetails() {
        return new PrepaidResponseDetails();
    }

    public GenPrepaidRequestDetails createGenPrepaidRequestDetails() {
        return new GenPrepaidRequestDetails();
    }

    public CreditResponseDetails createCreditResponseDetails() {
        return new CreditResponseDetails();
    }

    public VisaGrp createVisaGrp() {
        return new VisaGrp();
    }

    public DebitResponseDetails createDebitResponseDetails() {
        return new DebitResponseDetails();
    }

    public RespGrp createRespGrp() {
        return new RespGrp();
    }

    public VoidTOReversalResponseDetails createVoidTOReversalResponseDetails() {
        return new VoidTOReversalResponseDetails();
    }

    public PrepaidRequestDetails createPrepaidRequestDetails() {
        return new PrepaidRequestDetails();
    }

    public CheckResponseDetails createCheckResponseDetails() {
        return new CheckResponseDetails();
    }

    public OfferGrp createOfferGrp() {
        return new OfferGrp();
    }

    public PrivateLabelRequestDetails createPrivateLabelRequestDetails() {
        return new PrivateLabelRequestDetails();
    }

    public HostTotDetGrp createHostTotDetGrp() {
        return new HostTotDetGrp();
    }

    public SecrTxnGrp createSecrTxnGrp() {
        return new SecrTxnGrp();
    }

    public PrivateLabelResponseDetails createPrivateLabelResponseDetails() {
        return new PrivateLabelResponseDetails();
    }

    public PINGrp createPINGrp() {
        return new PINGrp();
    }

    public MCGrp createMCGrp() {
        return new MCGrp();
    }

    public TAGrp createTAGrp() {
        return new TAGrp();
    }

    public AdminResponseDetails createAdminResponseDetails() {
        return new AdminResponseDetails();
    }

    public GMFMessageVariants createGMFMessageVariants() {
        return new GMFMessageVariants();
    }

    public DebitRequestDetails createDebitRequestDetails() {
        return new DebitRequestDetails();
    }

    public OrigAuthGrp createOrigAuthGrp() {
        return new OrigAuthGrp();
    }

    public ProdCodeGrp createProdCodeGrp() {
        return new ProdCodeGrp();
    }

    public EcommGrp createEcommGrp() {
        return new EcommGrp();
    }

    public EBTRequestDetails createEBTRequestDetails() {
        return new EBTRequestDetails();
    }

    public PinlessDebitResponseDetails createPinlessDebitResponseDetails() {
        return new PinlessDebitResponseDetails();
    }

    public AddtlAmtGrp createAddtlAmtGrp() {
        return new AddtlAmtGrp();
    }

    public PinlessDebitRequestDetails createPinlessDebitRequestDetails() {
        return new PinlessDebitRequestDetails();
    }

    public TARequestDetails createTARequestDetails() {
        return new TARequestDetails();
    }

    public EBTResponseDetails createEBTResponseDetails() {
        return new EBTResponseDetails();
    }

    public CheckRequestDetails createCheckRequestDetails() {
        return new CheckRequestDetails();
    }

    public TAResponseDetails createTAResponseDetails() {
        return new TAResponseDetails();
    }

    public AdminRequestDetails createAdminRequestDetails() {
        return new AdminRequestDetails();
    }

    public GenPrepaidResponseDetails createGenPrepaidResponseDetails() {
        return new GenPrepaidResponseDetails();
    }

    public EMVGrp createEMVGrp() {
        return new EMVGrp();
    }

    public AltMerchNameAndAddrGrp createAltMerchNameAndAddrGrp() {
        return new AltMerchNameAndAddrGrp();
    }

    public CardGrp createCardGrp() {
        return new CardGrp();
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TOT_REQ_DATE_ELT)
    public JAXBElement<String> createTotReqDate(String value) {
        return new JAXBElement<String>(_TotReqDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FPI_ELT)
    public JAXBElement<String> createFPI(String value) {
        return new JAXBElement<String>(_FPI_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = KEY_ID_ELT)
    public JAXBElement<String> createKeyID(String value) {
        return new JAXBElement<String>(_KeyID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISC_TRANS_QUALIFIER_ELT)
    public JAXBElement<String> createDiscTransQualifier(String value) {
        return new JAXBElement<String>(_DiscTransQualifier_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TRACK1_DATA_ELT)
    public JAXBElement<String> createTrack1Data(String value) {
        return new JAXBElement<String>(_Track1Data_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = VISA_BID_ELT)
    public JAXBElement<String> createVisaBID(String value) {
        return new JAXBElement<String>(_VisaBID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = EBT_TYPE_ELT)
    public JAXBElement<EBTTypeType> createEBTType(EBTTypeType value) {
        return new JAXBElement<EBTTypeType>(_EBTType_QNAME, EBTTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_LEVEL_RESULT_ELT)
    public JAXBElement<String> createCardLevelResult(String value) {
        return new JAXBElement<String>(_CardLevelResult_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CURR_FILE_CREATION_DT_ELT)
    public JAXBElement<String> createCurrFileCreationDt(String value) {
        return new JAXBElement<String>(_CurrFileCreationDt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = REQ_FILE_OFFSET_ELT)
    public JAXBElement<String> createReqFileOffset(String value) {
        return new JAXBElement<String>(_ReqFileOffset_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SOURCE_REASON_CODE_ELT)
    public JAXBElement<String> createSourceReasonCode(String value) {
        return new JAXBElement<String>(_SourceReasonCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISC_POS_DATA_ELT)
    public JAXBElement<String> createDiscPOSData(String value) {
        return new JAXBElement<String>(_DiscPOSData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MC_SECR_AD_ELT)
    public JAXBElement<String> createMCSecrAD(String value) {
        return new JAXBElement<String>(_MCSecrAD_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AM_EX_TRAN_ID_ELT)
    public JAXBElement<String> createAmExTranID(String value) {
        return new JAXBElement<String>(_AmExTranID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_SEQ_NUM_ELT)
    public JAXBElement<String> createCardSeqNum(String value) {
        return new JAXBElement<String>(_CardSeqNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_CNTY_ELT)
    public JAXBElement<String> createMerchCnty(String value) {
        return new JAXBElement<String>(_MerchCnty_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = HOLD_INFO_ELT)
    public JAXBElement<String> createHoldInfo(String value) {
        return new JAXBElement<String>(_HoldInfo_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PAYEE_ID_ELT)
    public JAXBElement<String> createPayeeID(String value) {
        return new JAXBElement<String>(_PayeeID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISC_PROC_CODE_ELT)
    public JAXBElement<String> createDiscProcCode(String value) {
        return new JAXBElement<String>(_DiscProcCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = UNIT_OF_MSURE_ELT)
    public JAXBElement<String> createUnitOfMsure(String value) {
        return new JAXBElement<String>(_UnitOfMsure_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ECOMM_TXN_IND_ELT)
    public JAXBElement<String> createEcommTxnInd(String value) {
        return new JAXBElement<String>(_EcommTxnInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CHK_SVC_PVDR_ELT)
    public JAXBElement<ChkSvcPvdrType> createChkSvcPvdr(ChkSvcPvdrType value) {
        return new JAXBElement<ChkSvcPvdrType>(_ChkSvcPvdr_QNAME, ChkSvcPvdrType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = VAR_CUST_RCT_TXT_ELT)
    public JAXBElement<String> createVarCustRctTxt(String value) {
        return new JAXBElement<String>(_VarCustRctTxt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SAFEKEY_ELT)
    public JAXBElement<String> createSafekey(String value) {
        return new JAXBElement<String>(_Safekey_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TA_EXP_DATE_ELT)
    public JAXBElement<String> createTAExpDate(String value) {
        return new JAXBElement<String>(_TAExpDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ESCHTBL_TXN_ELT)
    public JAXBElement<EschtblTxnType> createEschtblTxn(EschtblTxnType value) {
        return new JAXBElement<EschtblTxnType>(_EschtblTxn_QNAME, EschtblTxnType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FRGHT_AMT_ELT)
    public JAXBElement<String> createFrghtAmt(String value) {
        return new JAXBElement<String>(_FrghtAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_CAT_CODE_ELT)
    public JAXBElement<String> createMerchCatCode(String value) {
        return new JAXBElement<String>(_MerchCatCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = BILLING_IND_ELT)
    public JAXBElement<String> createBillingInd(String value) {
        return new JAXBElement<String>(_BillingInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ENCRPT_BLOCK_ELT)
    public JAXBElement<String> createEncrptBlock(String value) {
        return new JAXBElement<String>(_EncrptBlock_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISCNT_AMT_ELT)
    public JAXBElement<String> createDiscntAmt(String value) {
        return new JAXBElement<String>(_DiscntAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = LOCAL_DATE_TIME_ELT)
    public JAXBElement<String> createLocalDateTime(String value) {
        return new JAXBElement<String>(_LocalDateTime_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = STTLM_DATE_ELT)
    public JAXBElement<String> createSttlmDate(String value) {
        return new JAXBElement<String>(_SttlmDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PROGRAM_ID_ELT)
    public JAXBElement<ProgramIDType> createProgramID(ProgramIDType value) {
        return new JAXBElement<ProgramIDType>(_ProgramID_QNAME, ProgramIDType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = UNIT_PRICE_ELT)
    public JAXBElement<String> createUnitPrice(String value) {
        return new JAXBElement<String>(_UnitPrice_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ECOMM_URL_ELt)
    public JAXBElement<String> createEcommURL(String value) {
        return new JAXBElement<String>(_EcommURL_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TRNMSN_DATE_TIME_ELT)
    public JAXBElement<String> createTrnmsnDateTime(String value) {
        return new JAXBElement<String>(_TrnmsnDateTime_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DEV_TYPE_IND_ELT)
    public JAXBElement<String> createDevTypeInd(String value) {
        return new JAXBElement<String>(_DevTypeInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DUTY_AMT_ELT)
    public JAXBElement<String> createDutyAmt(String value) {
        return new JAXBElement<String>(_DutyAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TERM_LOC_IND_ELT)
    public JAXBElement<String> createTermLocInd(String value) {
        return new JAXBElement<String>(_TermLocInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = BILL_PYMT_TXN_IND_ELT)
    public JAXBElement<BillPymtTxnIndType> createBillPymtTxnInd(BillPymtTxnIndType value) {
        return new JAXBElement<BillPymtTxnIndType>(_BillPymtTxnInd_QNAME, BillPymtTxnIndType.class,
                null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TRACK2_DATA_ELT)
    public JAXBElement<String> createTrack2Data(String value) {
        return new JAXBElement<String>(_Track2Data_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = VISA_XID_ELT)
    public JAXBElement<String> createVisaXID(String value) {
        return new JAXBElement<String>(_VisaXID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AM_EX_POS_DATA_ELT)
    public JAXBElement<String> createAmExPOSData(String value) {
        return new JAXBElement<String>(_AmExPOSData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TKN_ELT)
    public JAXBElement<String> createTkn(String value) {
        return new JAXBElement<String>(_Tkn_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = POS_COND_CODE_ELT)
    public JAXBElement<String> createPOSCondCode(String value) {
        return new JAXBElement<String>(_POSCondCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = EMV_DATA_ELT)
    public JAXBElement<String> createEMVData(String value) {
        return new JAXBElement<String>(_EMVData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = KEY_SERIAL_NUM_DATA_ELT)
    public JAXBElement<String> createKeySerialNumData(String value) {
        return new JAXBElement<String>(_KeySerialNumData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_ADDR_ELT)
    public JAXBElement<String> createMerchAddr(String value) {
        return new JAXBElement<String>(_MerchAddr_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_STATE_ELT)
    public JAXBElement<String> createMerchState(String value) {
        return new JAXBElement<String>(_MerchState_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TA_SCTY_KEY_ELT)
    public JAXBElement<String> createTASctyKey(String value) {
        return new JAXBElement<String>(_TASctyKey_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = VISA_AUAR_ELT)
    public JAXBElement<String> createVisaAUAR(String value) {
        return new JAXBElement<String>(_VisaAUAR_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TXN_AMT_ELT)
    public JAXBElement<String> createTxnAmt(String value) {
        return new JAXBElement<String>(_TxnAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_EXPIRY_DATE_ELT)
    public JAXBElement<String> createCardExpiryDate(String value) {
        return new JAXBElement<String>(_CardExpiryDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_TAX_ID_ELT)
    public JAXBElement<String> createMerchTaxID(String value) {
        return new JAXBElement<String>(_MerchTaxID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = RESP_CODE_ELT)
    public JAXBElement<String> createRespCode(String value) {
        return new JAXBElement<String>(_RespCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_POSTAL_CODE_ELT)
    public JAXBElement<String> createMerchPostalCode(String value) {
        return new JAXBElement<String>(_MerchPostalCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TERM_CAT_CODE_ELT)
    public JAXBElement<String> createTermCatCode(String value) {
        return new JAXBElement<String>(_TermCatCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_RES_IND_ELT)
    public JAXBElement<String> createOfferResInd(String value) {
        return new JAXBElement<String>(_OfferResInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_TAG_ELT)
    public JAXBElement<String> createCardTag(String value) {
        return new JAXBElement<String>(_CardTag_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DRV_LIC_ELT)
    public JAXBElement<String> createDrvLic(String value) {
        return new JAXBElement<String>(_DrvLic_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = POS_ENTRY_MODE_CHG_ELT)
    public JAXBElement<POSEntryModeChgType> createPOSEntryModeChg(POSEntryModeChgType value) {
        return new JAXBElement<POSEntryModeChgType>(_POSEntryModeChg_QNAME,
                POSEntryModeChgType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = NEXT_FILE_DL_OFFSET_ELT)
    public JAXBElement<String> createNextFileDLOffset(String value) {
        return new JAXBElement<String>(_NextFileDLOffset_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SERV_CODE_ELT)
    public JAXBElement<String> createServCode(String value) {
        return new JAXBElement<String>(_ServCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = QNTY_ELT)
    public JAXBElement<String> createQnty(String value) {
        return new JAXBElement<String>(_Qnty_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_PUB_NAME_ELT)
    public JAXBElement<String> createOfferPubName(String value) {
        return new JAXBElement<String>(_OfferPubName_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ENCRPT_TRGT_ELT)
    public JAXBElement<EncrptTrgtType> createEncrptTrgt(EncrptTrgtType value) {
        return new JAXBElement<EncrptTrgtType>(_EncrptTrgt_QNAME, EncrptTrgtType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORDER_DATE_ELT)
    public JAXBElement<String> createOrderDate(String value) {
        return new JAXBElement<String>(_OrderDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_AMOUNT_ELT)
    public JAXBElement<String> createOfferAmount(String value) {
        return new JAXBElement<String>(_OfferAmount_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_TXN_AMT_ELT)
    public JAXBElement<String> createCardTxnAmt(String value) {
        return new JAXBElement<String>(_CardTxnAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = VISA_SECR_TXN_AD_ELT)
    public JAXBElement<String> createVisaSecrTxnAD(String value) {
        return new JAXBElement<String>(_VisaSecrTxnAD_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MICR_ELT)
    public JAXBElement<String> createMICR(String value) {
        return new JAXBElement<String>(_MICR_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PURCH_IDFR_ELT)
    public JAXBElement<String> createPurchIdfr(String value) {
        return new JAXBElement<String>(_PurchIdfr_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AMEX_XID_ELT)
    public JAXBElement<String> createAmexXID(String value) {
        return new JAXBElement<String>(_AmexXID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TERM_ENTRY_CAPABLT_ELT)
    public JAXBElement<String> createTermEntryCapablt(String value) {
        return new JAXBElement<String>(_TermEntryCapablt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_CAPT_CAP_ELT)
    public JAXBElement<String> createCardCaptCap(String value) {
        return new JAXBElement<String>(_CardCaptCap_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORDER_NUM_ELT)
    public JAXBElement<String> createOrderNum(String value) {
        return new JAXBElement<String>(_OrderNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = POSID_ELT)
    public JAXBElement<String> createPOSID(String value) {
        return new JAXBElement<String>(_POSID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = RECEIPT_COPY_ELT)
    public JAXBElement<String> createReceiptCopy(String value) {
        return new JAXBElement<String>(_ReceiptCopy_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PIN_DATA_ELT)
    public JAXBElement<String> createPINData(String value) {
        return new JAXBElement<String>(_PINData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CAVV_RESULT_CODE_ELT)
    public JAXBElement<String> createCAVVResultCode(String value) {
        return new JAXBElement<String>(_CAVVResultCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FB_DATA_ELT)
    public JAXBElement<String> createFBData(String value) {
        return new JAXBElement<String>(_FBData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ADD_AMT_TYPE_ELT)
    public JAXBElement<AddAmtTypeType> createAddAmtType(AddAmtTypeType value) {
        return new JAXBElement<AddAmtTypeType>(_AddAmtType_QNAME, AddAmtTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CCV_DATA_ELT)
    public JAXBElement<String> createCCVData(String value) {
        return new JAXBElement<String>(_CCVData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = NACS_PROD_CODE_ELT)
    public JAXBElement<String> createNACSProdCode(String value) {
        return new JAXBElement<String>(_NACSProdCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SHIP_FROM_POSTAL_CODE_ELT)
    public JAXBElement<String> createShipFromPostalCode(String value) {
        return new JAXBElement<String>(_ShipFromPostalCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TXN_TYPE_ELT)
    public JAXBElement<TxnTypeType> createTxnType(TxnTypeType value) {
        return new JAXBElement<TxnTypeType>(_TxnType_QNAME, TxnTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ADD_AMT_ELT)
    public JAXBElement<String> createAddAmt(String value) {
        return new JAXBElement<String>(_AddAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = REF_NUM_ELT)
    public JAXBElement<String> createRefNum(String value) {
        return new JAXBElement<String>(_RefNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AVS_BILLING_POSTAL_CODE_ELT)
    public JAXBElement<String> createAVSBillingPostalCode(String value) {
        return new JAXBElement<String>(_AVSBillingPostalCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PROC_IND_ELT)
    public JAXBElement<String> createProcInd(String value) {
        return new JAXBElement<String>(_ProcInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TERM_ID_ELT)
    public JAXBElement<String> createTermID(String value) {
        return new JAXBElement<String>(_TermID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_CTRY_ELT)
    public JAXBElement<String> createMerchCtry(String value) {
        return new JAXBElement<String>(_MerchCtry_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AVS_RESULT_CODE_ELT)
    public JAXBElement<AVSResultCodeType> createAVSResultCode(AVSResultCodeType value) {
        return new JAXBElement<AVSResultCodeType>(_AVSResultCode_QNAME, AVSResultCodeType.class,
                null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_CLASS_ELT)
    public JAXBElement<String> createCardClass(String value) {
        return new JAXBElement<String>(_CardClass_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MCMSDI_ELT)
    public JAXBElement<MCMSDIType> createMCMSDI(MCMSDIType value) {
        return new JAXBElement<MCMSDIType>(_MCMSDI_QNAME, MCMSDIType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CCV_IND_ELT)
    public JAXBElement<CCVIndType> createCCVInd(CCVIndType value) {
        return new JAXBElement<CCVIndType>(_CCVInd_QNAME, CCVIndType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FUN_CODE_ELT)
    public JAXBElement<FunCodeType> createFunCode(FunCodeType value) {
        return new JAXBElement<FunCodeType>(_FunCode_QNAME, FunCodeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TXN_CRNCY_ELT)
    public JAXBElement<String> createTxnCrncy(String value) {
        return new JAXBElement<String>(_TxnCrncy_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FILE_TYPE_ELT)
    public JAXBElement<FileTypeType> createFileType(FileTypeType value) {
        return new JAXBElement<FileTypeType>(_FileType_QNAME, FileTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ADDTL_RESP_DATA_ELT)
    public JAXBElement<String> createAddtlRespData(String value) {
        return new JAXBElement<String>(_AddtlRespData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_ID_ELT)
    public JAXBElement<String> createOfferID(String value) {
        return new JAXBElement<String>(_OfferID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_COST_ELT)
    public JAXBElement<String> createCardCost(String value) {
        return new JAXBElement<String>(_CardCost_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CCV_RESULT_CODE_ELT)
    public JAXBElement<CCVResultCodeType> createCCVResultCode(CCVResultCodeType value) {
        return new JAXBElement<CCVResultCodeType>(_CCVResultCode_QNAME, CCVResultCodeType.class,
                null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_PROV_ID_ELT)
    public JAXBElement<String> createOfferProvID(String value) {
        return new JAXBElement<String>(_OfferProvID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_PROV_NAME_ELT)
    public JAXBElement<String> createOfferProvName(String value) {
        return new JAXBElement<String>(_OfferProvName_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = STAN_ELT)
    public JAXBElement<String> createSTAN(String value) {
        return new JAXBElement<String>(_STAN_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SERV_LVL_ELT)
    public JAXBElement<String> createServLvl(String value) {
        return new JAXBElement<String>(_ServLvl_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ACCT_NUM_ELT)
    public JAXBElement<String> createAcctNum(String value) {
        return new JAXBElement<String>(_AcctNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SCTY_LVL_ELT)
    public JAXBElement<SctyLvlType> createSctyLvl(SctyLvlType value) {
        return new JAXBElement<SctyLvlType>(_SctyLvl_QNAME, SctyLvlType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CUST_SVC_PHONE_NUMBER_ELT)
    public JAXBElement<String> createCustSvcPhoneNumber(String value) {
        return new JAXBElement<String>(_CustSvcPhoneNumber_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORIG_TRAN_DATE_TIME_ELT)
    public JAXBElement<String> createOrigTranDateTime(String value) {
        return new JAXBElement<String>(_OrigTranDateTime_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = APP_EXP_DATE_ELT)
    public JAXBElement<String> createAppExpDate(String value) {
        return new JAXBElement<String>(_AppExpDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ADD_AMT_ACCT_TYPE_ELT)
    public JAXBElement<String> createAddAmtAcctType(String value) {
        return new JAXBElement<String>(_AddAmtAcctType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DEST_CTRY_CODE_ELT)
    public JAXBElement<String> createDestCtryCode(String value) {
        return new JAXBElement<String>(_DestCtryCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TPPID_ELT)
    public JAXBElement<String> createTPPID(String value) {
        return new JAXBElement<String>(_TPPID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = GROUP_ID_ELT)
    public JAXBElement<String> createGroupID(String value) {
        return new JAXBElement<String>(_GroupID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = POS_OFFER_CAP_ELT)
    public JAXBElement<String> createPOSOfferCap(String value) {
        return new JAXBElement<String>(_POSOfferCap_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_PUB_ID_ELT)
    public JAXBElement<String> createOfferPubID(String value) {
        return new JAXBElement<String>(_OfferPubID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = REVERSAL_IND_ELT)
    public JAXBElement<ReversalIndType> createReversalInd(ReversalIndType value) {
        return new JAXBElement<ReversalIndType>(_ReversalInd_QNAME, ReversalIndType.class, null,
                value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_NAME_ELT)
    public JAXBElement<String> createMerchName(String value) {
        return new JAXBElement<String>(_MerchName_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FILE_SIZE_ELT)
    public JAXBElement<String> createFileSize(String value) {
        return new JAXBElement<String>(_FileSize_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TRANS_ID_ELT)
    public JAXBElement<String> createTransID(String value) {
        return new JAXBElement<String>(_TransID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARC_ELT)
    public JAXBElement<String> createCARC(String value) {
        return new JAXBElement<String>(_CARC_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISC_RESP_CODE_ELT)
    public JAXBElement<String> createDiscRespCode(String value) {
        return new JAXBElement<String>(_DiscRespCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PAYEE_PHONE_NUM_ELT)
    public JAXBElement<String> createPayeePhoneNum(String value) {
        return new JAXBElement<String>(_PayeePhoneNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MCPOS_DATA_ELT)
    public JAXBElement<String> createMCPOSData(String value) {
        return new JAXBElement<String>(_MCPOSData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ERROR_DATA_ELT)
    public JAXBElement<String> createErrorData(String value) {
        return new JAXBElement<String>(_ErrorData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AMEX_SECR_AD_ELT)
    public JAXBElement<String> createAmexSecrAD(String value) {
        return new JAXBElement<String>(_AmexSecrAD_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = X_CODE_RESP_ELT)
    public JAXBElement<String> createXCodeResp(String value) {
        return new JAXBElement<String>(_XCodeResp_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = REQ_FB_MAX_SIZE_ELT)
    public JAXBElement<String> createReqFBMaxSize(String value) {
        return new JAXBElement<String>(_ReqFBMaxSize_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FA_CODE_ELT)
    public JAXBElement<String> createFACode(String value) {
        return new JAXBElement<String>(_FACode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PASSWORD_ELT)
    public JAXBElement<String> createPassword(String value) {
        return new JAXBElement<String>(_Password_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DEST_POSTAL_CODE_ELT)
    public JAXBElement<String> createDestPostalCode(String value) {
        return new JAXBElement<String>(_DestPostalCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = EXISTING_DEBT_IND_ELT)
    public JAXBElement<String> createExistingDebtInd(String value) {
        return new JAXBElement<String>(_ExistingDebtInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ADD_AMT_CRNCY_ELT)
    public JAXBElement<String> createAddAmtCrncy(String value) {
        return new JAXBElement<String>(_AddAmtCrncy_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DL_DATE_OF_BIRTH_ELT)
    public JAXBElement<String> createDLDateOfBirth(String value) {
        return new JAXBElement<String>(_DLDateOfBirth_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORIG_RESP_CODE_ELT)
    public JAXBElement<String> createOrigRespCode(String value) {
        return new JAXBElement<String>(_OrigRespCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISC_POS_ENTRY_ELT)
    public JAXBElement<String> createDiscPOSEntry(String value) {
        return new JAXBElement<String>(_DiscPOSEntry_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ATH_NTWK_ID_ELT)
    public JAXBElement<String> createAthNtwkID(String value) {
        return new JAXBElement<String>(_AthNtwkID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CCV_ERROR_CODE_ELT)
    public JAXBElement<CCVErrorCodeType> createCCVErrorCode(CCVErrorCodeType value) {
        return new JAXBElement<CCVErrorCodeType>(_CCVErrorCode_QNAME, CCVErrorCodeType.class, null,
                value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = BANKNET_DATA_ELT)
    public JAXBElement<String> createBanknetData(String value) {
        return new JAXBElement<String>(_BanknetData_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = STATE_CODE_ELT)
    public JAXBElement<String> createStateCode(String value) {
        return new JAXBElement<String>(_StateCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = NUM_OF_PRODS_ELT)
    public JAXBElement<String> createNumOfProds(String value) {
        return new JAXBElement<String>(_NumOfProds_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ALT_MERCH_ID_ELT)
    public JAXBElement<String> createAltMerchID(String value) {
        return new JAXBElement<String>(_AltMerchID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AVS_BILLING_ADDR_ELT)
    public JAXBElement<String> createAVSBillingAddr(String value) {
        return new JAXBElement<String>(_AVSBillingAddr_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = DISC_NRID_ELT)
    public JAXBElement<String> createDiscNRID(String value) {
        return new JAXBElement<String>(_DiscNRID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PYMT_TYPE_ELT)
    public JAXBElement<PymtTypeType> createPymtType(PymtTypeType value) {
        return new JAXBElement<PymtTypeType>(_PymtType_QNAME, PymtTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ACI_ELT)
    public JAXBElement<ACIType> createACI(ACIType value) {
        return new JAXBElement<ACIType>(_ACI_QNAME, ACIType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORIG_LOCAL_DATE_TIME_ELT)
    public JAXBElement<String> createOrigLocalDateTime(String value) {
        return new JAXBElement<String>(_OrigLocalDateTime_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_ADVICE_CODE_ELT)
    public JAXBElement<String> createMerchAdviceCode(String value) {
        return new JAXBElement<String>(_MerchAdviceCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = NET_SETTLE_AMT_ELT)
    public JAXBElement<String> createNetSettleAmt(String value) {
        return new JAXBElement<String>(_NetSettleAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ENCRPT_TYPE_ELT)
    public JAXBElement<EncrptTypeType> createEncrptType(EncrptTypeType value) {
        return new JAXBElement<EncrptTypeType>(_EncrptType_QNAME, EncrptTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = AUTH_ID_ELT)
    public JAXBElement<String> createAuthID(String value) {
        return new JAXBElement<String>(_AuthID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SCTY_KEY_UPD_IND_ELT)
    public JAXBElement<String> createSctyKeyUpdInd(String value) {
        return new JAXBElement<String>(_SctyKeyUpdInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = GMF_ELT)
    public JAXBElement<GMFMessageVariants> createGMF(GMFMessageVariants value) {
        return new JAXBElement<GMFMessageVariants>(_GMF_QNAME, GMFMessageVariants.class, null,
                value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = SETTLE_IND_ELT)
    public JAXBElement<String> createSettleInd(String value) {
        return new JAXBElement<String>(_SettleInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_TYPE_ELT)
    public JAXBElement<CardTypeType> createCardType(CardTypeType value) {
        return new JAXBElement<CardTypeType>(_CardType_QNAME, CardTypeType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = POS_ENTRY_MODE_ELT)
    public JAXBElement<String> createPOSEntryMode(String value) {
        return new JAXBElement<String>(_POSEntryMode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PC_ORDER_NUM_ELT)
    public JAXBElement<String> createPCOrderNum(String value) {
        return new JAXBElement<String>(_PCOrderNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TRAN_EDIT_ERR_CODE_ELT)
    public JAXBElement<String> createTranEditErrCode(String value) {
        return new JAXBElement<String>(_TranEditErrCode_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = OFFER_DESC_ELT)
    public JAXBElement<String> createOfferDesc(String value) {
        return new JAXBElement<String>(_OfferDesc_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TAX_AMT_CAPABLT_ELT)
    public JAXBElement<String> createTaxAmtCapablt(String value) {
        return new JAXBElement<String>(_TaxAmtCapablt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CHK_ENTRY_METHOD_ELT)
    public JAXBElement<String> createChkEntryMethod(String value) {
        return new JAXBElement<String>(_ChkEntryMethod_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PART_AUTHRZTN_APPRVL_CAPABLT_ELT)
    public JAXBElement<String> createPartAuthrztnApprvlCapablt(String value) {
        return new JAXBElement<String>(_PartAuthrztnApprvlCapablt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TKN_TYPE_ELT)
    public JAXBElement<String> createTknType(String value) {
        return new JAXBElement<String>(_TknType_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FB_SEQ_ELT)
    public JAXBElement<String> createFBSeq(String value) {
        return new JAXBElement<String>(_FBSeq_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = UCAF_COLLECT_IND_ELT)
    public JAXBElement<String> createUCAFCollectInd(String value) {
        return new JAXBElement<String>(_UCAFCollectInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PROC_INFO_ELT)
    public JAXBElement<String> createProcInfo(String value) {
        return new JAXBElement<String>(_ProcInfo_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_CITY_ELT)
    public JAXBElement<String> createMerchCity(String value) {
        return new JAXBElement<String>(_MerchCity_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PROD_DESC_ELT)
    public JAXBElement<String> createProdDesc(String value) {
        return new JAXBElement<String>(_ProdDesc_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TAX_AMT_ELT)
    public JAXBElement<String> createTaxAmt(String value) {
        return new JAXBElement<String>(_TaxAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORIG_AUTH_ID_ELT)
    public JAXBElement<String> createOrigAuthID(String value) {
        return new JAXBElement<String>(_OrigAuthID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PROD_AMT_ELT)
    public JAXBElement<String> createProdAmt(String value) {
        return new JAXBElement<String>(_ProdAmt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = PAYEE_ACCT_NUM_ELT)
    public JAXBElement<String> createPayeeAcctNum(String value) {
        return new JAXBElement<String>(_PayeeAcctNum_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = FILE_CRC16_ELT)
    public JAXBElement<String> createFileCRC16(String value) {
        return new JAXBElement<String>(_FileCRC16_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TAX_IND_ELT)
    public JAXBElement<String> createTaxInd(String value) {
        return new JAXBElement<String>(_TaxInd_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = ORIG_STAN_ELT)
    public JAXBElement<String> createOrigSTAN(String value) {
        return new JAXBElement<String>(_OrigSTAN_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = TXN_CT_ELT)
    public JAXBElement<String> createTxnCt(String value) {
        return new JAXBElement<String>(_TxnCt_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCHANT_ID_ELT)
    public JAXBElement<String> createMerchantID(String value) {
        return new JAXBElement<String>(_MerchantID_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MRKT_SPECIFIC_DATA_IND_ELT)
    public JAXBElement<MrktSpecificDataIndType> createMrktSpecificDataInd(
            MrktSpecificDataIndType value) {
        return new JAXBElement<MrktSpecificDataIndType>(_MrktSpecificDataInd_QNAME,
                MrktSpecificDataIndType.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = CARD_ACTIV_DATE_ELT)
    public JAXBElement<String> createCardActivDate(String value) {
        return new JAXBElement<String>(_CardActivDate_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = NAMESPACE, name = MERCH_ID_ELT)
    public JAXBElement<String> createMerchID(String value) {
        return new JAXBElement<String>(_MerchID_QNAME, String.class, null, value);
    }
}