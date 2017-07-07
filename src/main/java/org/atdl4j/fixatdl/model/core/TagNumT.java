//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.24 at 10:55:05 AM CST 
//

package org.atdl4j.fixatdl.model.core;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

/**
 * Derived parameter type corresponding to the FIX "TagNum" type defined in the FIX specification.
 * <p>
 * <p>
 * Java class for TagNum_t complex type.
 * <p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <p>
 * <p>
 * <pre>
 * &lt;complexType name="TagNum_t">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.fixprotocol.org/FIXatdl-1-1/Core}Parameter_t">
 *       &lt;attribute name="constValue" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TagNum_t")
public class TagNumT
        extends ParameterT {

    @XmlAttribute
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger constValue;

    /**
     * Gets the value of the constValue property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public BigInteger getConstValue() {
        return constValue;
    }

    /**
     * Sets the value of the constValue property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setConstValue(BigInteger value) {
        this.constValue = value;
    }

    @Override
    public int getTag959() {
        return 5;
    }

}
