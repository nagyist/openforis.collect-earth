//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.12.09 at 04:52:33 PM CET 
//


package org.openforis.collect.earth.ipcc.serialize;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Version" type="{http://ipcc2006.air.sk/IPCC2006Export}version"/>
 *         &lt;element name="inventoryYear" type="{http://www.w3.org/2001/XMLSchema}gYear"/>
 *         &lt;element name="countryCode" type="{http://ipcc2006.air.sk/IPCC2006Export}countryCode"/>
 *         &lt;element name="record" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="landTypes" type="{http://ipcc2006.air.sk/IPCC2006Export}landTypes"/>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "version",
    "inventoryYear",
    "countryCode",
    "record"
})
@XmlRootElement(name = "IPCC2006Export")
public class IPCC2006Export{

    @XmlElement(name = "Version", required = true)
    protected String version;
    @XmlElement(required = true)
    @XmlSchemaType(name = "gYear")
    protected Integer inventoryYear;
    @XmlElement(required = true)
    protected String countryCode;
    protected List<IPCC2006Export.Record> record;

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the inventoryYear property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public Integer getInventoryYear() {
        return inventoryYear;
    }

    /**
     * Sets the value of the inventoryYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInventoryYear(Integer value) {
        this.inventoryYear = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the record property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the record property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecord().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IPCC2006Export.Record }
     * 
     * 
     */
    public List<IPCC2006Export.Record> getRecord() {
        if (record == null) {
            record = new ArrayList<IPCC2006Export.Record>();
        }
        return this.record;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="landTypes" type="{http://ipcc2006.air.sk/IPCC2006Export}landTypes"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "landTypes"
    })
    public static class Record {

        protected LandTypes landTypes;

        /**
         * Gets the value of the landTypes property.
         * 
         * @return
         *     possible object is
         *     {@link LandTypes }
         *     
         */
        public LandTypes getLandTypes() {
            return landTypes;
        }

        /**
         * Sets the value of the landTypes property.
         * 
         * @param value
         *     allowed object is
         *     {@link LandTypes }
         *     
         */
        public void setLandTypes(LandTypes value) {
            this.landTypes = value;
        }

    }
    

}
