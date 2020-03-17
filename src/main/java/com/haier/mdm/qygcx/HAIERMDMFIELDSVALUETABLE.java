
package com.haier.mdm.qygcx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>HAIERMDM.FIELDS_VALUE_TABLE complex type   Java  ࡣ
 * 
 * <p>        ָ
 * 
 * <pre>
 * &lt;complexType name="HAIERMDM.FIELDS_VALUE_TABLE"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IN_FIELDS_VALUE_TABLE_ITEM" type="{http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew}HAIERMDM.FIELDS_VALUE_TYPE" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HAIERMDM.FIELDS_VALUE_TABLE", propOrder = {
    "infieldsvaluetableitem"
})
public class HAIERMDMFIELDSVALUETABLE {

    @XmlElement(name = "IN_FIELDS_VALUE_TABLE_ITEM", nillable = true)
    protected List<HAIERMDMFIELDSVALUETYPE> infieldsvaluetableitem;

    /**
     * Gets the value of the infieldsvaluetableitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infieldsvaluetableitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getINFIELDSVALUETABLEITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HAIERMDMFIELDSVALUETYPE }
     * 
     * 
     */
    public List<HAIERMDMFIELDSVALUETYPE> getINFIELDSVALUETABLEITEM() {
        if (infieldsvaluetableitem == null) {
            infieldsvaluetableitem = new ArrayList<HAIERMDMFIELDSVALUETYPE>();
        }
        return this.infieldsvaluetableitem;
    }

}
