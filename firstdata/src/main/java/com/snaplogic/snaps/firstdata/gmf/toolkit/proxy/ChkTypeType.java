//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 02:31:56 PM EST 
//


package com.snaplogic.snaps.firstdata.gmf.toolkit.proxy;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChkTypeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ChkTypeType">
 *   &lt;restriction base="{com/firstdata/Merchant/gmfV1.1}Len1Text">
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="C"/>
 *     &lt;enumeration value="B"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ChkTypeType", namespace = "com/firstdata/Merchant/gmfV1.1")
@XmlEnum
public enum ChkTypeType {

    P,
    C,
    B;

    public String value() {
        return name();
    }

    public static ChkTypeType fromValue(String v) {
        return valueOf(v);
    }

}
