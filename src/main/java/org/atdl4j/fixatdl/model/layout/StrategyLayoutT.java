//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.02.24 at 10:55:05 AM CST 
//

package org.atdl4j.fixatdl.model.layout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Java class for StrategyLayout_t complex type.
 * <p>
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <p>
 * <p>
 * <pre>
 * &lt;complexType name="StrategyLayout_t">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StrategyPanel" type="{http://www.fixprotocol.org/FIXatdl-1-1/Layout}StrategyPanel_t" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrategyLayout_t", propOrder = {
        "strategyPanel"
})
public class StrategyLayoutT {

    @XmlElement(name = "StrategyPanel", required = true)
    protected List<StrategyPanelT> strategyPanel;

    /**
     * Gets the value of the strategyPanel property.
     * <p>
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the strategyPanel property.
     * <p>
     * <p>
     * For example, to registerControlFlow a new item, do as follows:
     * <p>
     * <p>
     * <pre>
     * getStrategyPanel().registerControlFlow(newItem);
     * </pre>
     * <p>
     * <p>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StrategyPanelT }
     */
    public List<StrategyPanelT> getStrategyPanel() {
        if (strategyPanel == null) {
            strategyPanel = new ArrayList<StrategyPanelT>();
        }
        return this.strategyPanel;
    }

}
