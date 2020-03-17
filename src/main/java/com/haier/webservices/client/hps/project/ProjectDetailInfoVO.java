
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectDetailInfoVO", propOrder = {
    "createdDate",
    "loginLevel",
    "msgId",
    "nodename",
    "operateStatus",
    "operater",
    "projectCode",
    "projectCreaterName",
    "projectName",
    "projectState"
})
public class ProjectDetailInfoVO
    extends ToString
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected int loginLevel;
    protected String msgId;
    protected String nodename;
    protected String operateStatus;
    protected String operater;
    protected String projectCode;
    protected String projectCreaterName;
    protected String projectName;
    protected String projectState;

     
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

     
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

     
    public int getLoginLevel() {
        return loginLevel;
    }

     
    public void setLoginLevel(int value) {
        this.loginLevel = value;
    }

     
    public String getMsgId() {
        return msgId;
    }

     
    public void setMsgId(String value) {
        this.msgId = value;
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

     
    public String getOperater() {
        return operater;
    }

     
    public void setOperater(String value) {
        this.operater = value;
    }

     
    public String getProjectCode() {
        return projectCode;
    }

     
    public void setProjectCode(String value) {
        this.projectCode = value;
    }

     
    public String getProjectCreaterName() {
        return projectCreaterName;
    }

     
    public void setProjectCreaterName(String value) {
        this.projectCreaterName = value;
    }

     
    public String getProjectName() {
        return projectName;
    }

     
    public void setProjectName(String value) {
        this.projectName = value;
    }

     
    public String getProjectState() {
        return projectState;
    }

     
    public void setProjectState(String value) {
        this.projectState = value;
    }

}
