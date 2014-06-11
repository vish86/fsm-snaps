//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 02:31:56 PM EST 
//


package com.snaplogic.snaps.firstdata.gmf.toolkit.proxy;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AddAmtAcctTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AddAmtAcctTypeType">
 *   &lt;restriction base="{com/firstdata/Merchant/gmfV1.1}Max15Text">
 *     &lt;enumeration value="Unknown"/>
 *     &lt;enumeration value="Checking"/>
 *     &lt;enumeration value="Credit"/>
 *     &lt;enumeration value="CashBenefit"/>
 *     &lt;enumeration value="FoodBenefit"/>
 *     &lt;enumeration value="Prepaid"/>
 *     &lt;enumeration value="Savings"/>
 *     &lt;enumeration value="SpendingPower"/>
 *     &lt;enumeration value="Universal"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AddAmtAcctTypeType", namespace = "com/firstdata/Merchant/gmfV1.1")
@XmlEnum
public enum AddAmtAcctTypeType {

    @XmlEnumValue("Unknown")
    UNKNOWN("Unknown"),
    @XmlEnumValue("Checking")
    CHECKING("Checking"),
    @XmlEnumValue("Credit")
    CREDIT("Credit"),
    @XmlEnumValue("CashBenefit")
    CASH_BENEFIT("CashBenefit"),
    @XmlEnumValue("FoodBenefit")
    FOOD_BENEFIT("FoodBenefit"),
    @XmlEnumValue("Prepaid")
    PREPAID("Prepaid"),
    @XmlEnumValue("Savings")
    SAVINGS("Savings"),
    @XmlEnumValue("SpendingPower")
    SPENDING_POWER("SpendingPower"),
    @XmlEnumValue("Universal")
    UNIVERSAL("Universal");
    private final String value;

    AddAmtAcctTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AddAmtAcctTypeType fromValue(String v) {
        for (AddAmtAcctTypeType c: AddAmtAcctTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
