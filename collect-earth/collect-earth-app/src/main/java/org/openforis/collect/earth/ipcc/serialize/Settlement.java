//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.22 at 11:25:06 AM CET 
//


package org.openforis.collect.earth.ipcc.serialize;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for settlement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="settlement">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cltSettlement" type="{http://ipcc2006.air.sk/IPCC2006Export}cltSettlement" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "settlement", propOrder = {
    "cltSettlement"
})
public class Settlement {

    protected List<CltSettlement> cltSettlement;

    /**
     * Gets the value of the cltSettlement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cltSettlement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCltSettlement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CltSettlement }
     * 
     * 
     */
    public List<CltSettlement> getCltSettlement() {
        if (cltSettlement == null) {
            cltSettlement = new ArrayList<CltSettlement>();
        }
        return this.cltSettlement;
    }

}
