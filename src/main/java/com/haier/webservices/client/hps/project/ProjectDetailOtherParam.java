
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectDetailOtherParam", propOrder = {
    "createBeginDate",
    "createEndDate",
    "cusCode",
    "nodename",
    "operateStatus",
    "pageNo",
    "pageSize",
    "projectName"
})
public class ProjectDetailOtherParam
    extends ToString
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createBeginDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createEndDate;
    protected String cusCode;
    protected String nodename;
    protected String operateStatus;
    protected String pageNo;
    protected String pageSize;
    protected String projectName;

     
    public XMLGregorianCalendar getCreateBeginDate() {
        return createBeginDate;
    }

     
    public void setCreateBeginDate(XMLGregorianCalendar value) {
        this.createBeginDate = value;
    }

     
    public XMLGregorianCalendar getCreateEndDate() {
        return createEndDate;
    }

     
    public void setCreateEndDate(XMLGregorianCalendar value) {
        this.createEndDate = value;
    }

     
    public String getCusCode() {
        return cusCode;
    }

     
    public void setCusCode(String value) {
        this.cusCode = value;
    }

     
    public String getNodename() {
        return nodename;
    }

     
    public void setNodename(String value) {
        this.nodename = value;
    }

     
    public String getOperateStatus() {
        return operateStatus;
    }

     
    public void setOperateStatus(String value) {
        this.operateStatus = value;
    }

     
    public String getPageNo() {
        return pageNo;
    }

     
    public void setPageNo(String value) {
        this.pageNo = value;
    }

     
    public String getPageSize() {
        return pageSize;
    }

     
    public void setPageSize(String value) {
        this.pageSize = value;
    }

     
    public String getProjectName() {
        return projectName;
    }

     
    public void setProjectName(String value) {
        this.projectName = value;
    }

}
