//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.22 at 11:25:06 AM CET 
//


package org.openforis.collect.earth.ipcc.serialize;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cltGrassland complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cltGrassland">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="guid" type="{http://ipcc2006.air.sk/IPCC2006Export}guid"/>
 *         &lt;element name="countryCode" type="{http://ipcc2006.air.sk/IPCC2006Export}countryCode"/>
 *         &lt;element name="customName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="managed" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="climateRegionId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="soilTypeId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="soilStatusId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="geoPlacementId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="nutrientTypeId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="improvedGrassland" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="vegetationTypeId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="mfLandUse" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mfTillage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mfInput" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="biomassStockHerb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="biomassStockWoody" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="carbonFractionHerb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="carbonFractionWoody" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ageClassId" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="ratioBgbAgbHerb" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="ratioBgbAgbWoody" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="agbGrowth" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="abandoned" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ageClassValue" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="soilCarbonStock" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cltGrassland", propOrder = {
    "id",
    "guid",
    "countryCode",
    "customName",
    "managed",
    "climateRegionId",
    "soilTypeId",
    "soilStatusId",
    "geoPlacementId",
    "nutrientTypeId",
    "improvedGrassland",
    "vegetationTypeId",
    "mfLandUse",
    "mfTillage",
    "mfInput",
    "biomassStockHerb",
    "biomassStockWoody",
    "carbonFractionHerb",
    "carbonFractionWoody",
    "ageClassId",
    "ratioBgbAgbHerb",
    "ratioBgbAgbWoody",
    "agbGrowth",
    "abandoned",
    "ageClassValue",
    "soilCarbonStock"
})
public class CltGrassland {

    @XmlElement(required = true)
    protected Integer id;
    @XmlElement(required = true)
    protected String guid;
    @XmlElement(required = true)
    protected String countryCode;
    @XmlElement(required = true)
    protected String customName;
    protected boolean managed;
    @XmlElement(required = true)
    protected Integer climateRegionId;
    @XmlElement(required = true)
    protected Integer soilTypeId;
    @XmlElement(required = true)
    protected Integer soilStatusId;
    @XmlElement(required = true)
    protected Integer geoPlacementId;
    @XmlElement(required = true)
    protected Integer nutrientTypeId;
    protected boolean improvedGrassland;
    @XmlElement(required = true)
    protected Integer vegetationTypeId;
    protected double mfLandUse;
    protected double mfTillage;
    protected double mfInput;
    protected double biomassStockHerb;
    protected double biomassStockWoody;
    protected double carbonFractionHerb;
    protected double carbonFractionWoody;
    @XmlElement(required = true)
    protected Integer ageClassId;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double ratioBgbAgbHerb;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double ratioBgbAgbWoody;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double agbGrowth;
    protected boolean abandoned;
    @XmlElement(required = true, nillable = true)
    protected Integer ageClassValue;
    @XmlElement(required = true, type = Double.class, nillable = true)
    protected Double soilCarbonStock;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

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
     * Gets the value of the customName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomName() {
        return customName;
    }

    /**
     * Sets the value of the customName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomName(String value) {
        this.customName = value;
    }

    /**
     * Gets the value of the managed property.
     * 
     */
    public boolean isManaged() {
        return managed;
    }

    /**
     * Sets the value of the managed property.
     * 
     */
    public void setManaged(boolean value) {
        this.managed = value;
    }

    /**
     * Gets the value of the climateRegionId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClimateRegionId() {
        return climateRegionId;
    }

    /**
     * Sets the value of the climateRegionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClimateRegionId(Integer value) {
        this.climateRegionId = value;
    }

    /**
     * Gets the value of the soilTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSoilTypeId() {
        return soilTypeId;
    }

    /**
     * Sets the value of the soilTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSoilTypeId(Integer value) {
        this.soilTypeId = value;
    }

    /**
     * Gets the value of the soilStatusId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSoilStatusId() {
        return soilStatusId;
    }

    /**
     * Sets the value of the soilStatusId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSoilStatusId(Integer value) {
        this.soilStatusId = value;
    }

    /**
     * Gets the value of the geoPlacementId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGeoPlacementId() {
        return geoPlacementId;
    }

    /**
     * Sets the value of the geoPlacementId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGeoPlacementId(Integer value) {
        this.geoPlacementId = value;
    }

    /**
     * Gets the value of the nutrientTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNutrientTypeId() {
        return nutrientTypeId;
    }

    /**
     * Sets the value of the nutrientTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNutrientTypeId(Integer value) {
        this.nutrientTypeId = value;
    }

    /**
     * Gets the value of the improvedGrassland property.
     * 
     */
    public boolean isImprovedGrassland() {
        return improvedGrassland;
    }

    /**
     * Sets the value of the improvedGrassland property.
     * 
     */
    public void setImprovedGrassland(boolean value) {
        this.improvedGrassland = value;
    }

    /**
     * Gets the value of the vegetationTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVegetationTypeId() {
        return vegetationTypeId;
    }

    /**
     * Sets the value of the vegetationTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVegetationTypeId(Integer value) {
        this.vegetationTypeId = value;
    }

    /**
     * Gets the value of the mfLandUse property.
     * 
     */
    public double getMfLandUse() {
        return mfLandUse;
    }

    /**
     * Sets the value of the mfLandUse property.
     * 
     */
    public void setMfLandUse(double value) {
        this.mfLandUse = value;
    }

    /**
     * Gets the value of the mfTillage property.
     * 
     */
    public double getMfTillage() {
        return mfTillage;
    }

    /**
     * Sets the value of the mfTillage property.
     * 
     */
    public void setMfTillage(double value) {
        this.mfTillage = value;
    }

    /**
     * Gets the value of the mfInput property.
     * 
     */
    public double getMfInput() {
        return mfInput;
    }

    /**
     * Sets the value of the mfInput property.
     * 
     */
    public void setMfInput(double value) {
        this.mfInput = value;
    }

    /**
     * Gets the value of the biomassStockHerb property.
     * 
     */
    public double getBiomassStockHerb() {
        return biomassStockHerb;
    }

    /**
     * Sets the value of the biomassStockHerb property.
     * 
     */
    public void setBiomassStockHerb(double value) {
        this.biomassStockHerb = value;
    }

    /**
     * Gets the value of the biomassStockWoody property.
     * 
     */
    public double getBiomassStockWoody() {
        return biomassStockWoody;
    }

    /**
     * Sets the value of the biomassStockWoody property.
     * 
     */
    public void setBiomassStockWoody(double value) {
        this.biomassStockWoody = value;
    }

    /**
     * Gets the value of the carbonFractionHerb property.
     * 
     */
    public double getCarbonFractionHerb() {
        return carbonFractionHerb;
    }

    /**
     * Sets the value of the carbonFractionHerb property.
     * 
     */
    public void setCarbonFractionHerb(double value) {
        this.carbonFractionHerb = value;
    }

    /**
     * Gets the value of the carbonFractionWoody property.
     * 
     */
    public double getCarbonFractionWoody() {
        return carbonFractionWoody;
    }

    /**
     * Sets the value of the carbonFractionWoody property.
     * 
     */
    public void setCarbonFractionWoody(double value) {
        this.carbonFractionWoody = value;
    }

    /**
     * Gets the value of the ageClassId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAgeClassId() {
        return ageClassId;
    }

    /**
     * Sets the value of the ageClassId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAgeClassId(Integer value) {
        this.ageClassId = value;
    }

    /**
     * Gets the value of the ratioBgbAgbHerb property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRatioBgbAgbHerb() {
        return ratioBgbAgbHerb;
    }

    /**
     * Sets the value of the ratioBgbAgbHerb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatioBgbAgbHerb(Double value) {
        this.ratioBgbAgbHerb = value;
    }

    /**
     * Gets the value of the ratioBgbAgbWoody property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRatioBgbAgbWoody() {
        return ratioBgbAgbWoody;
    }

    /**
     * Sets the value of the ratioBgbAgbWoody property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRatioBgbAgbWoody(Double value) {
        this.ratioBgbAgbWoody = value;
    }

    /**
     * Gets the value of the agbGrowth property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAgbGrowth() {
        return agbGrowth;
    }

    /**
     * Sets the value of the agbGrowth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAgbGrowth(Double value) {
        this.agbGrowth = value;
    }

    /**
     * Gets the value of the abandoned property.
     * 
     */
    public boolean isAbandoned() {
        return abandoned;
    }

    /**
     * Sets the value of the abandoned property.
     * 
     */
    public void setAbandoned(boolean value) {
        this.abandoned = value;
    }

    /**
     * Gets the value of the ageClassValue property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAgeClassValue() {
        return ageClassValue;
    }

    /**
     * Sets the value of the ageClassValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAgeClassValue(Integer value) {
        this.ageClassValue = value;
    }

    /**
     * Gets the value of the soilCarbonStock property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSoilCarbonStock() {
        return soilCarbonStock;
    }

    /**
     * Sets the value of the soilCarbonStock property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSoilCarbonStock(Double value) {
        this.soilCarbonStock = value;
    }

}
