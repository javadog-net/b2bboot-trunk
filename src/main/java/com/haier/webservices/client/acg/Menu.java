
package com.haier.webservices.client.acg;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>menu complex type?? Java ??
 * 
 * <p>????????????????????????????????
 * 
 * <pre>
 * &lt;complexType name="menu"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}dataEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="children" type="{http://acg.server.webservices.haier.com/}menu" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataRuleList" type="{http://acg.server.webservices.haier.com/}dataRule" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="hasChildren" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="href" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="icon" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="isShow" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="parent" type="{http://acg.server.webservices.haier.com/}menu" minOccurs="0"/&gt;
 *         &lt;element name="parentIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="permission" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sort" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="target" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="userId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "menu", propOrder = {
    "children",
    "dataRuleList",
    "hasChildren",
    "href",
    "icon",
    "isShow",
    "name",
    "parent",
    "parentIds",
    "permission",
    "sort",
    "target",
    "type",
    "userId"
})
public class Menu
    extends DataEntity
{

    @XmlElement(nillable = true)
    protected List<Menu> children;
    @XmlElement(nillable = true)
    protected List<DataRule> dataRuleList;
    protected boolean hasChildren;
    protected String href;
    protected String icon;
    protected String isShow;
    protected String name;
    protected Menu parent;
    protected String parentIds;
    protected String permission;
    protected Integer sort;
    protected String target;
    protected String type;
    protected String userId;

    /**
     * Gets the value of the children property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the children property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildren().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Menu }
     * 
     * 
     */
    public List<Menu> getChildren() {
        if (children == null) {
            children = new ArrayList<Menu>();
        }
        return this.children;
    }

    /**
     * Gets the value of the dataRuleList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataRuleList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataRuleList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataRule }
     * 
     * 
     */
    public List<DataRule> getDataRuleList() {
        if (dataRuleList == null) {
            dataRuleList = new ArrayList<DataRule>();
        }
        return this.dataRuleList;
    }

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
     * ???href????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHref() {
        return href;
    }

    /**
     * ????href????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHref(String value) {
        this.href = value;
    }

    /**
     * ???icon????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcon() {
        return icon;
    }

    /**
     * ????icon????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcon(String value) {
        this.icon = value;
    }

    /**
     * ???isShow????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsShow() {
        return isShow;
    }

    /**
     * ????isShow????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsShow(String value) {
        this.isShow = value;
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
     *     {@link Menu }
     *     
     */
    public Menu getParent() {
        return parent;
    }

    /**
     * ????parent????????
     * 
     * @param value
     *     allowed object is
     *     {@link Menu }
     *     
     */
    public void setParent(Menu value) {
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
     * ???permission????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPermission() {
        return permission;
    }

    /**
     * ????permission????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPermission(String value) {
        this.permission = value;
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

    /**
     * ???target????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarget() {
        return target;
    }

    /**
     * ????target????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * ???type????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * ????type????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * ???userId????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ????userId????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

}
