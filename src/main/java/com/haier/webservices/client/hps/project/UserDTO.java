
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userDTO", propOrder = {
    "activeBeginTime",
    "activeEndTime",
    "beLocked",
    "beSystemManage",
    "code",
    "createdById",
    "deptName",
    "deptNo",
    "domainCode",
    "domainName",
    "email",
    "fax",
    "firstLineCode",
    "firstLineName",
    "id",
    "imgPath",
    "isManage",
    "lastLoginTime",
    "lastModifiedById",
    "lockReason",
    "mobile",
    "name",
    "openid",
    "organizationCode",
    "organizationName",
    "phone",
    "registTime",
    "remark",
    "secondLineCode",
    "secondLineName",
    "sex",
    "standardPostCode",
    "standardPostName",
    "tagInfo"
})
public class UserDTO
    extends BasicDto
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar activeBeginTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar activeEndTime;
    protected Boolean beLocked;
    protected Boolean beSystemManage;
    protected String code;
    protected String createdById;
    protected String deptName;
    protected String deptNo;
    protected String domainCode;
    protected String domainName;
    protected String email;
    protected String fax;
    protected String firstLineCode;
    protected String firstLineName;
    protected String id;
    protected String imgPath;
    protected String isManage;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastLoginTime;
    protected String lastModifiedById;
    protected String lockReason;
    protected String mobile;
    protected String name;
    protected String openid;
    protected String organizationCode;
    protected String organizationName;
    protected String phone;
    protected String registTime;
    protected String remark;
    protected String secondLineCode;
    protected String secondLineName;
    protected String sex;
    protected String standardPostCode;
    protected String standardPostName;
    @XmlElement(nillable = true)
    protected List<BasePostTagContentDTO> tagInfo;

     
    public XMLGregorianCalendar getActiveBeginTime() {
        return activeBeginTime;
    }

     
    public void setActiveBeginTime(XMLGregorianCalendar value) {
        this.activeBeginTime = value;
    }

     
    public XMLGregorianCalendar getActiveEndTime() {
        return activeEndTime;
    }

     
    public void setActiveEndTime(XMLGregorianCalendar value) {
        this.activeEndTime = value;
    }

     
    public Boolean isBeLocked() {
        return beLocked;
    }

     
    public void setBeLocked(Boolean value) {
        this.beLocked = value;
    }

     
    public Boolean isBeSystemManage() {
        return beSystemManage;
    }

     
    public void setBeSystemManage(Boolean value) {
        this.beSystemManage = value;
    }

     
    public String getCode() {
        return code;
    }

     
    public void setCode(String value) {
        this.code = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public String getDeptName() {
        return deptName;
    }

     
    public void setDeptName(String value) {
        this.deptName = value;
    }

     
    public String getDeptNo() {
        return deptNo;
    }

     
    public void setDeptNo(String value) {
        this.deptNo = value;
    }

     
    public String getDomainCode() {
        return domainCode;
    }

     
    public void setDomainCode(String value) {
        this.domainCode = value;
    }

     
    public String getDomainName() {
        return domainName;
    }

     
    public void setDomainName(String value) {
        this.domainName = value;
    }

     
    public String getEmail() {
        return email;
    }

     
    public void setEmail(String value) {
        this.email = value;
    }

     
    public String getFax() {
        return fax;
    }

     
    public void setFax(String value) {
        this.fax = value;
    }

     
    public String getFirstLineCode() {
        return firstLineCode;
    }

     
    public void setFirstLineCode(String value) {
        this.firstLineCode = value;
    }

     
    public String getFirstLineName() {
        return firstLineName;
    }

     
    public void setFirstLineName(String value) {
        this.firstLineName = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getImgPath() {
        return imgPath;
    }

     
    public void setImgPath(String value) {
        this.imgPath = value;
    }

     
    public String getIsManage() {
        return isManage;
    }

     
    public void setIsManage(String value) {
        this.isManage = value;
    }

     
    public XMLGregorianCalendar getLastLoginTime() {
        return lastLoginTime;
    }

     
    public void setLastLoginTime(XMLGregorianCalendar value) {
        this.lastLoginTime = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getLockReason() {
        return lockReason;
    }

     
    public void setLockReason(String value) {
        this.lockReason = value;
    }

     
    public String getMobile() {
        return mobile;
    }

     
    public void setMobile(String value) {
        this.mobile = value;
    }

     
    public String getName() {
        return name;
    }

     
    public void setName(String value) {
        this.name = value;
    }

     
    public String getOpenid() {
        return openid;
    }

     
    public void setOpenid(String value) {
        this.openid = value;
    }

     
    public String getOrganizationCode() {
        return organizationCode;
    }

     
    public void setOrganizationCode(String value) {
        this.organizationCode = value;
    }

     
    public String getOrganizationName() {
        return organizationName;
    }

     
    public void setOrganizationName(String value) {
        this.organizationName = value;
    }

     
    public String getPhone() {
        return phone;
    }

     
    public void setPhone(String value) {
        this.phone = value;
    }

     
    public String getRegistTime() {
        return registTime;
    }

     
    public void setRegistTime(String value) {
        this.registTime = value;
    }

     
    public String getRemark() {
        return remark;
    }

     
    public void setRemark(String value) {
        this.remark = value;
    }

     
    public String getSecondLineCode() {
        return secondLineCode;
    }

     
    public void setSecondLineCode(String value) {
        this.secondLineCode = value;
    }

     
    public String getSecondLineName() {
        return secondLineName;
    }

     
    public void setSecondLineName(String value) {
        this.secondLineName = value;
    }

     
    public String getSex() {
        return sex;
    }

     
    public void setSex(String value) {
        this.sex = value;
    }

     
    public String getStandardPostCode() {
        return standardPostCode;
    }

     
    public void setStandardPostCode(String value) {
        this.standardPostCode = value;
    }

     
    public String getStandardPostName() {
        return standardPostName;
    }

     
    public void setStandardPostName(String value) {
        this.standardPostName = value;
    }

     
    public List<BasePostTagContentDTO> getTagInfo() {
        if (tagInfo == null) {
            tagInfo = new ArrayList<BasePostTagContentDTO>();
        }
        return this.tagInfo;
    }

}
