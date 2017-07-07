//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.24 at 10:55:05 AM CST 
//

package org.atdl4j.fixatdl.model.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Derived parameter type corresponding to the FIX "PriceOffset" type defined in the FIX specification.
 * <p>
 * <p>
 * Java class for PriceOffset_t complex type.
 * <p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <p>
 * <p>
 * <pre>
 * &lt;complexType name="PriceOffset_t">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.fixprotocol.org/FIXatdl-1-1/Core}Numeric_t">
 *       &lt;attribute name="minValue" type="{http://www.fixprotocol.org/FIXatdl-1-1/Core}PriceOffset" default="0" />
 *       &lt;attribute name="maxValue" type="{http://www.fixprotocol.org/FIXatdl-1-1/Core}PriceOffset" />
 *       &lt;attribute name="constValue" type="{http://www.fixprotocol.org/FIXatdl-1-1/Core}PriceOffset" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PriceOffset_t")
public class PriceOffsetT
        extends NumericT {

    @XmlAttribute
    protected BigDecimal minValue;
    @XmlAttribute
    protected BigDecimal maxValue;
    @XmlAttribute
    protected BigDecimal constValue;

    /**
     * Gets the value of the minValue property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getMinValue() {
        if (minValue == null) {
            return new BigDecimal("0");
        } else {
            return minValue;
        }
    }

    /**
     * Sets the value of the minValue property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setMinValue(BigDecimal value) {
        this.minValue = value;
    }

    /**
     * Gets the value of the maxValue property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the value of the maxValue property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setMaxValue(BigDecimal value) {
        this.maxValue = value;
    }

    /**
     * Gets the value of the constValue property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getConstValue() {
        return constValue;
    }

    /**
     * Sets the value of the constValue property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setConstValue(BigDecimal value) {
        this.constValue = value;
    }

    @Override
    public int getTag959() {
        return 9;
    }

}