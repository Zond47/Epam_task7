//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB)
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.26 at 01:39:03 AM EEST 
//


package com.epam.rd.java.basic.practice7.entity;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrencyType.
 */
@XmlType(name = "CurrencyType")
@XmlEnum
public enum CurrencyType {

    USD,
    UAH;

    public String value() {
        return name();
    }

    public static CurrencyType fromValue(String v) {
        return valueOf(v);
    }

}
