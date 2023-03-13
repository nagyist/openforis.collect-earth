//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.12.09 at 04:52:33 PM CET 
//


package org.openforis.collect.earth.ipcc.serialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openforis.collect.earth.ipcc.RegionColumn;


/**
 * <p>Java class for lrtRegion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lrtRegion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="guid" type="{http://ipcc2006.air.sk/IPCC2006Export}guid"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="area" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="approachId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="landCategories" type="{http://ipcc2006.air.sk/IPCC2006Export}lrtLandCategories"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lrtRegion", propOrder = {
    "guid",
    "name",
    "area",
    "approachId",
    "remark",
    "landCategories"
})
public class LrtRegion {

    @XmlElement(required = true)
    protected String guid;
    @XmlElement(required = true)
    protected String name;
    protected double area;
    @XmlElement(required = true)
    protected Integer approachId;
    @XmlElement(required = true, nillable = true)
    protected String remark;
    @XmlElement(required = true)
    protected LrtLandCategories landCategories;
    @XmlTransient
    RegionColumn stratifyByRegionColumn;
    
    /**
     * Gets the value of the guid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Sets the value of the guid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuid(String value) {
        this.guid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the area property.
     * 
     */
    public double getArea() {
        return area;
    }

    /**
     * Sets the value of the area property.
     * 
     */
    public void setArea(double value) {
        this.area = value;
    }

    /**
     * Gets the value of the approachId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApproachId() {
        return approachId;
    }

    /**
     * Sets the value of the approachId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApproachId(Integer value) {
        this.approachId = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the landCategories property.
     * 
     * @return
     *     possible object is
     *     {@link LrtLandCategories }
     *     
     */
    public LrtLandCategories getLandCategories() {
    	if( landCategories == null ) {
    		landCategories = new LrtLandCategories();
    	}
        return landCategories;
    }

    /**
     * Sets the value of the landCategories property.
     * 
     * @param value
     *     allowed object is
     *     {@link LrtLandCategories }
     *     
     */
    public void setLandCategories(LrtLandCategories value) {
        this.landCategories = value;
    }

	public RegionColumn getStratifyByRegionColumn() {
		return stratifyByRegionColumn;
	}

	public void setStratifyByRegionColumn(RegionColumn stratifyByRegionColumn) {
		this.stratifyByRegionColumn = stratifyByRegionColumn;
	}

}
