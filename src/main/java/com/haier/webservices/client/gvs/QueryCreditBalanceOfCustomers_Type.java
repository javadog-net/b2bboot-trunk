
package com.haier.webservices.client.gvs;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OMS_OUT" type="{http://www.example.org/QueryCreditBalanceOfCustomers/}OMS_OUT" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "omsout"
})
@XmlRootElement(name = "QueryCreditBalanceOfCustomers")
public class QueryCreditBalanceOfCustomers_Type {

    @XmlElement(name = "OMS_OUT")
    protected List<OMSOUT> omsout;

    /**
     * Gets the value of the omsout property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the omsout property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOMSOUT().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OMSOUT }
     *
     *
     */
    public List<OMSOUT> getOMSOUT() {
        if (omsout == null) {
            omsout = new ArrayList<OMSOUT>();
        }
        return this.omsout;
    }

}
