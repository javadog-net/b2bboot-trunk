
package com.haier.webservices.client.acg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>treeEntity complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="treeEntity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}dataEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="hasChildren" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parent" type="{http://www.w3.org/2001/XMLSchema}anyType" minOccurs="0"/&gt;
 *         &lt;element name="parentIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "treeEntity", propOrder = {
    "hasChildren",
    "name",
    "parent",
    "parentIds",
    "sort"
})
@XmlSeeAlso({
    Office.class,
    Area.class
})
public abstract class TreeEntity
    extends DataEntity
{

    protected boolean hasChildren;
    protected String name;
    protected Object parent;
    protected String parentIds;
    protected Integer sort;

    /**
     * ???hasChildren????????
     * 
     */
    public boolean isHasChildren() {
        return hasChildren;
    }

    /**
     * ????hasChildren????????
     * 
     */
    public void setHasChildren(boolean value) {
        this.hasChildren = value;
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
     * ???parent????????
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getParent() {
        return parent;
    }

    /**
     * ????parent????????
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setParent(Object value) {
        this.parent = value;
    }

    /**
     * ???parentIds????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * ????parentIds????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentIds(String value) {
        this.parentIds = value;
    }

    /**
     * ???sort????????
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * ????sort????????
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSort(Integer value) {
        this.sort = value;
    }

}
