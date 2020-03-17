
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>dataRule complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="dataRule"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}dataEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="className" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="express" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="field" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="menuId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sqlSegment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataRule", propOrder = {
    "className",
    "express",
    "field",
    "menuId",
    "name",
    "sqlSegment",
    "value"
})
public class DataRule
    extends DataEntity
{

    protected String className;
    protected String express;
    protected String field;
    protected String menuId;
    protected String name;
    protected String sqlSegment;
    protected String value;

    /**
     * ???className????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * ????className????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
    }

    /**
     * ???express????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpress() {
        return express;
    }

    /**
     * ????express????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpress(String value) {
        this.express = value;
    }

    /**
     * ???field????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField() {
        return field;
    }

    /**
     * ????field????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField(String value) {
        this.field = value;
    }

    /**
     * ???menuId????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * ????menuId????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuId(String value) {
        this.menuId = value;
    }

    /**
     * ???name????????
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
     * ????name????????
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
     * ???sqlSegment????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSqlSegment() {
        return sqlSegment;
    }

    /**
     * ????sqlSegment????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSqlSegment(String value) {
        this.sqlSegment = value;
    }

    /**
     * ???value????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * ????value????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
