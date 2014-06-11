/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.snaplogic.snaps.firstdata.gmf.toolkit.proxy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Add Amt Type Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see com.firstdata.merchant.gmf.v1.V1Package#getAddAmtTypeType()
 * @model extendedMetaData="name='AddAmtTypeType'"
 * @generated
 */
public enum AddAmtTypeType
{
  /**
   * The '<em><b>Cashback</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CASHBACK_VALUE
   * @generated
   * @ordered
   */
  CASHBACK(0, "Cashback", "Cashback"),

  /**
   * The '<em><b>Surchrg</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #SURCHRG_VALUE
   * @generated
   * @ordered
   */
  SURCHRG(1, "Surchrg", "Surchrg"),

  /**
   * The '<em><b>Hltcare</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HLTCARE_VALUE
   * @generated
   * @ordered
   */
  HLTCARE(2, "Hltcare", "Hltcare"),

  /**
   * The '<em><b>Transit</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TRANSIT_VALUE
   * @generated
   * @ordered
   */
  TRANSIT(3, "Transit", "Transit"),

  /**
   * The '<em><b>RX</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #RX_VALUE
   * @generated
   * @ordered
   */
  RX(4, "RX", "RX"),

  /**
   * The '<em><b>Vision</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #VISION_VALUE
   * @generated
   * @ordered
   */
  VISION(5, "Vision", "Vision"),

  /**
   * The '<em><b>Clinical</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #CLINICAL_VALUE
   * @generated
   * @ordered
   */
  CLINICAL(6, "Clinical", "Clinical"),

  /**
   * The '<em><b>Dental</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DENTAL_VALUE
   * @generated
   * @ordered
   */
  DENTAL(7, "Dental", "Dental"),

  /**
   * The '<em><b>Prdct1</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRDCT1_VALUE
   * @generated
   * @ordered
   */
  PRDCT1(8, "Prdct1", "Prdct1"),

  /**
   * The '<em><b>Prdct2</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRDCT2_VALUE
   * @generated
   * @ordered
   */
  PRDCT2(9, "Prdct2", "Prdct2"),

  /**
   * The '<em><b>Prdct3</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRDCT3_VALUE
   * @generated
   * @ordered
   */
  PRDCT3(10, "Prdct3", "Prdct3"),

  /**
   * The '<em><b>Prdct4</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRDCT4_VALUE
   * @generated
   * @ordered
   */
  PRDCT4(11, "Prdct4", "Prdct4"),

  /**
   * The '<em><b>First Auth Amt</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #FIRST_AUTH_AMT_VALUE
   * @generated
   * @ordered
   */
  FIRST_AUTH_AMT(12, "FirstAuthAmt", "FirstAuthAmt"),

  /**
   * The '<em><b>Pre Auth Amt</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #PRE_AUTH_AMT_VALUE
   * @generated
   * @ordered
   */
  PRE_AUTH_AMT(13, "PreAuthAmt", "PreAuthAmt"),

  /**
   * The '<em><b>Total Auth Amt</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TOTAL_AUTH_AMT_VALUE
   * @generated
   * @ordered
   */
  TOTAL_AUTH_AMT(14, "TotalAuthAmt", "TotalAuthAmt"),

  /**
   * The '<em><b>Tax</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #TAX_VALUE
   * @generated
   * @ordered
   */
  TAX(15, "Tax", "Tax"),

  /**
   * The '<em><b>Beg Bal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BEG_BAL_VALUE
   * @generated
   * @ordered
   */
  BEG_BAL(16, "BegBal", "BegBal"),

  /**
   * The '<em><b>Ending Bal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ENDING_BAL_VALUE
   * @generated
   * @ordered
   */
  ENDING_BAL(17, "EndingBal", "EndingBal"),

  /**
   * The '<em><b>Avail Bal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #AVAIL_BAL_VALUE
   * @generated
   * @ordered
   */
  AVAIL_BAL(18, "AvailBal", "AvailBal"),

  /**
   * The '<em><b>Ledger Bal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #LEDGER_BAL_VALUE
   * @generated
   * @ordered
   */
  LEDGER_BAL(19, "LedgerBal", "LedgerBal"),

  /**
   * The '<em><b>Hold Bal</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HOLD_BAL_VALUE
   * @generated
   * @ordered
   */
  HOLD_BAL(20, "HoldBal", "HoldBal"),

  /**
   * The '<em><b>Orig Req Amt</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #ORIG_REQ_AMT_VALUE
   * @generated
   * @ordered
   */
  ORIG_REQ_AMT(21, "OrigReqAmt", "OrigReqAmt"),

  /**
   * The '<em><b>Open To Buy</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #OPEN_TO_BUY_VALUE
   * @generated
   * @ordered
   */
  OPEN_TO_BUY(23, "OpenToBuy", "OpenToBuy");

  /**
   * The '<em><b>Cashback</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Cashback</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CASHBACK
   * @model name="Cashback"
   * @generated
   * @ordered
   */
  public static final int CASHBACK_VALUE = 0;

  /**
   * The '<em><b>Surchrg</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Surchrg</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #SURCHRG
   * @model name="Surchrg"
   * @generated
   * @ordered
   */
  public static final int SURCHRG_VALUE = 1;

  /**
   * The '<em><b>Hltcare</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Hltcare</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HLTCARE
   * @model name="Hltcare"
   * @generated
   * @ordered
   */
  public static final int HLTCARE_VALUE = 2;

  /**
   * The '<em><b>Transit</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Transit</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TRANSIT
   * @model name="Transit"
   * @generated
   * @ordered
   */
  public static final int TRANSIT_VALUE = 3;

  /**
   * The '<em><b>RX</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>RX</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #RX
   * @model
   * @generated
   * @ordered
   */
  public static final int RX_VALUE = 4;

  /**
   * The '<em><b>Vision</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Vision</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #VISION
   * @model name="Vision"
   * @generated
   * @ordered
   */
  public static final int VISION_VALUE = 5;

  /**
   * The '<em><b>Clinical</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Clinical</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #CLINICAL
   * @model name="Clinical"
   * @generated
   * @ordered
   */
  public static final int CLINICAL_VALUE = 6;

  /**
   * The '<em><b>Dental</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Dental</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #DENTAL
   * @model name="Dental"
   * @generated
   * @ordered
   */
  public static final int DENTAL_VALUE = 7;

  /**
   * The '<em><b>Prdct1</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Prdct1</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRDCT1
   * @model name="Prdct1"
   * @generated
   * @ordered
   */
  public static final int PRDCT1_VALUE = 8;

  /**
   * The '<em><b>Prdct2</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Prdct2</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRDCT2
   * @model name="Prdct2"
   * @generated
   * @ordered
   */
  public static final int PRDCT2_VALUE = 9;

  /**
   * The '<em><b>Prdct3</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Prdct3</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRDCT3
   * @model name="Prdct3"
   * @generated
   * @ordered
   */
  public static final int PRDCT3_VALUE = 10;

  /**
   * The '<em><b>Prdct4</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Prdct4</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRDCT4
   * @model name="Prdct4"
   * @generated
   * @ordered
   */
  public static final int PRDCT4_VALUE = 11;

  /**
   * The '<em><b>First Auth Amt</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>First Auth Amt</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #FIRST_AUTH_AMT
   * @model name="FirstAuthAmt"
   * @generated
   * @ordered
   */
  public static final int FIRST_AUTH_AMT_VALUE = 12;

  /**
   * The '<em><b>Pre Auth Amt</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Pre Auth Amt</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #PRE_AUTH_AMT
   * @model name="PreAuthAmt"
   * @generated
   * @ordered
   */
  public static final int PRE_AUTH_AMT_VALUE = 13;

  /**
   * The '<em><b>Total Auth Amt</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Total Auth Amt</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TOTAL_AUTH_AMT
   * @model name="TotalAuthAmt"
   * @generated
   * @ordered
   */
  public static final int TOTAL_AUTH_AMT_VALUE = 14;

  /**
   * The '<em><b>Tax</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Tax</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #TAX
   * @model name="Tax"
   * @generated
   * @ordered
   */
  public static final int TAX_VALUE = 15;

  /**
   * The '<em><b>Beg Bal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Beg Bal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #BEG_BAL
   * @model name="BegBal"
   * @generated
   * @ordered
   */
  public static final int BEG_BAL_VALUE = 16;

  /**
   * The '<em><b>Ending Bal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Ending Bal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ENDING_BAL
   * @model name="EndingBal"
   * @generated
   * @ordered
   */
  public static final int ENDING_BAL_VALUE = 17;

  /**
   * The '<em><b>Avail Bal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Avail Bal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #AVAIL_BAL
   * @model name="AvailBal"
   * @generated
   * @ordered
   */
  public static final int AVAIL_BAL_VALUE = 18;

  /**
   * The '<em><b>Ledger Bal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Ledger Bal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #LEDGER_BAL
   * @model name="LedgerBal"
   * @generated
   * @ordered
   */
  public static final int LEDGER_BAL_VALUE = 19;

  /**
   * The '<em><b>Hold Bal</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Hold Bal</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HOLD_BAL
   * @model name="HoldBal"
   * @generated
   * @ordered
   */
  public static final int HOLD_BAL_VALUE = 20;

  /**
   * The '<em><b>Orig Req Amt</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Orig Req Amt</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #ORIG_REQ_AMT
   * @model name="OrigReqAmt"
   * @generated
   * @ordered
   */
  public static final int ORIG_REQ_AMT_VALUE = 21;

  /**
   * The '<em><b>Open To Buy</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>Open To Buy</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #OPEN_TO_BUY
   * @model name="OpenToBuy"
   * @generated
   * @ordered
   */
  public static final int OPEN_TO_BUY_VALUE = 23;

  /**
   * An array of all the '<em><b>Add Amt Type Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final AddAmtTypeType[] VALUES_ARRAY =
    new AddAmtTypeType[]
    {
      CASHBACK,
      SURCHRG,
      HLTCARE,
      TRANSIT,
      RX,
      VISION,
      CLINICAL,
      DENTAL,
      PRDCT1,
      PRDCT2,
      PRDCT3,
      PRDCT4,
      FIRST_AUTH_AMT,
      PRE_AUTH_AMT,
      TOTAL_AUTH_AMT,
      TAX,
      BEG_BAL,
      ENDING_BAL,
      AVAIL_BAL,
      LEDGER_BAL,
      HOLD_BAL,
      ORIG_REQ_AMT,
      OPEN_TO_BUY,
    };

  /**
   * A public read-only list of all the '<em><b>Add Amt Type Type</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<AddAmtTypeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Add Amt Type Type</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AddAmtTypeType get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      AddAmtTypeType result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Add Amt Type Type</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AddAmtTypeType getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      AddAmtTypeType result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Add Amt Type Type</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AddAmtTypeType get(int value)
  {
    switch (value)
    {
      case CASHBACK_VALUE: return CASHBACK;
      case SURCHRG_VALUE: return SURCHRG;
      case HLTCARE_VALUE: return HLTCARE;
      case TRANSIT_VALUE: return TRANSIT;
      case RX_VALUE: return RX;
      case VISION_VALUE: return VISION;
      case CLINICAL_VALUE: return CLINICAL;
      case DENTAL_VALUE: return DENTAL;
      case PRDCT1_VALUE: return PRDCT1;
      case PRDCT2_VALUE: return PRDCT2;
      case PRDCT3_VALUE: return PRDCT3;
      case PRDCT4_VALUE: return PRDCT4;
      case FIRST_AUTH_AMT_VALUE: return FIRST_AUTH_AMT;
      case PRE_AUTH_AMT_VALUE: return PRE_AUTH_AMT;
      case TOTAL_AUTH_AMT_VALUE: return TOTAL_AUTH_AMT;
      case TAX_VALUE: return TAX;
      case BEG_BAL_VALUE: return BEG_BAL;
      case ENDING_BAL_VALUE: return ENDING_BAL;
      case AVAIL_BAL_VALUE: return AVAIL_BAL;
      case LEDGER_BAL_VALUE: return LEDGER_BAL;
      case HOLD_BAL_VALUE: return HOLD_BAL;
      case ORIG_REQ_AMT_VALUE: return ORIG_REQ_AMT;
      case OPEN_TO_BUY_VALUE: return OPEN_TO_BUY;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private AddAmtTypeType(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral()
  {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return literal;
  }
  
} //AddAmtTypeType
