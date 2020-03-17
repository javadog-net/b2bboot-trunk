
package com.haier.webservices.client.acg;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>role complex type?? Java ??
 * 
 * <p>??????????????????????е?????????
 * 
 * <pre>
 * &lt;complexType name="role"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://acg.server.webservices.haier.com/}dataEntity"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="dataRuleIdList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dataRuleIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dataRuleList" type="{http://acg.server.webservices.haier.com/}dataRule" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="enname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="menuIdList" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="menuIds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="menuList" type="{http://acg.server.webservices.haier.com/}menu" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="office" type="{http://acg.server.webservices.haier.com/}office" minOccurs="0"/&gt;
 *         &lt;element name="oldEnname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="oldName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="roleType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sysData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="useable" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="user" type="{http://acg.server.webservices.haier.com/}user" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "role", propOrder = {
    "dataRuleIdList",
    "dataRuleIds",
    "dataRuleList",
    "enname",
    "menuIdList",
    "menuIds",
    "menuList",
    "name",
    "office",
    "oldEnname",
    "oldName",
    "roleType",
    "sysData",
    "useable",
    "user"
})
public class Role
    extends DataEntity
{

    @XmlElement(nillable = true)
    protected List<String> dataRuleIdList;
    protected String dataRuleIds;
    @XmlElement(nillable = true)
    protected List<DataRule> dataRuleList;
    protected String enname;
    @XmlElement(nillable = true)
    protected List<String> menuIdList;
    protected String menuIds;
    @XmlElement(nillable = true)
    protected List<Menu> menuList;
    protected String name;
    protected Office office;
    protected String oldEnname;
    protected String oldName;
    protected String roleType;
    protected String sysData;
    protected String useable;
    protected User user;

    /**
     * Gets the value of the dataRuleIdList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataRuleIdList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataRuleIdList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataRuleIdList() {
        if (dataRuleIdList == null) {
            dataRuleIdList = new ArrayList<String>();
        }
        return this.dataRuleIdList;
    }

    /**
     * ???dataRuleIds????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataRuleIds() {
        return dataRuleIds;
    }

    /**
     * ????dataRuleIds????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataRuleIds(String value) {
        this.dataRuleIds = value;
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
     * ???enname????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnname() {
        return enname;
    }

    /**
     * ????enname????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnname(String value) {
        this.enname = value;
    }

    /**
     * Gets the value of the menuIdList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuIdList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuIdList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getMenuIdList() {
        if (menuIdList == null) {
            menuIdList = new ArrayList<String>();
        }
        return this.menuIdList;
    }

    /**
     * ???menuIds????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMenuIds() {
        return menuIds;
    }

    /**
     * ????menuIds????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMenuIds(String value) {
        this.menuIds = value;
    }

    /**
     * Gets the value of the menuList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the menuList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMenuList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Menu }
     * 
     * 
     */
    public List<Menu> getMenuList() {
        if (menuList == null) {
            menuList = new ArrayList<Menu>();
        }
        return this.menuList;
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
     * ???office????????
     * 
     * @return
     *     possible object is
     *     {@link Office }
     *     
     */
    public Office getOffice() {
        return office;
    }

    /**
     * ????office????????
     * 
     * @param value
     *     allowed object is
     *     {@link Office }
     *     
     */
    public void setOffice(Office value) {
        this.office = value;
    }

    /**
     * ???oldEnname????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldEnname() {
        return oldEnname;
    }

    /**
     * ????oldEnname????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldEnname(String value) {
        this.oldEnname = value;
    }

    /**
     * ???oldName????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * ????oldName????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldName(String value) {
        this.oldName = value;
    }

    /**
     * ???roleType????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * ????roleType????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoleType(String value) {
        this.roleType = value;
    }

    /**
     * ???sysData????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysData() {
        return sysData;
    }

    /**
     * ????sysData????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysData(String value) {
        this.sysData = value;
    }

    /**
     * ???useable????????
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseable() {
        return useable;
    }

    /**
     * ????useable????????
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseable(String value) {
        this.useable = value;
    }

    /**
     * ???user????????
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * ????user????????
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

}
