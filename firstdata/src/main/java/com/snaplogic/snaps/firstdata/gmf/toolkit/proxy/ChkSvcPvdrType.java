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
 * <p>Java class for ChkSvcPvdrType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChkSvcPvdrType">
 *   &lt;restriction base="{com/firstdata/Merchant/gmfV1.1}Max12Text">
 *     &lt;enumeration value="TeleCheckECA"/>
 *     &lt;enumeration value="TeleCheck"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChkSvcPvdrType", namespace = "com/firstdata/Merchant/gmfV1.1")
@XmlEnum
public enum ChkSvcPvdrType {

    @XmlEnumValue("TeleCheckECA")
    TELE_CHECK_ECA("TeleCheckECA"),
    @XmlEnumValue("TeleCheck")
    TELE_CHECK("TeleCheck");
    private final String value;

    ChkSvcPvdrType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChkSvcPvdrType fromValue(String v) {
        for (ChkSvcPvdrType c: ChkSvcPvdrType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
