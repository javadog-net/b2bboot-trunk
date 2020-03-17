
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lockUserVO", propOrder = {
    "code",
    "id",
    "sysId",
    "userGroup",
    "userName",
    "userType"
})
public class LockUserVO
    extends ToString
{

    protected String code;
    protected String id;
    protected String sysId;
    protected String userGroup;
    protected String userName;
    protected String userType;

     
    public String getCode() {
        return code;
    }

     
    public void setCode(String value) {
        this.code = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getSysId() {
        return sysId;
    }

     
    public void setSysId(String value) {
        this.sysId = value;
    }

     
    public String getUserGroup() {
        return userGroup;
    }

     
    public void setUserGroup(String value) {
        this.userGroup = value;
    }

     
    public String getUserName() {
        return userName;
    }

     
    public void setUserName(String value) {
        this.userName = value;
    }

     
    public String getUserType() {
        return userType;
    }

     
    public void setUserType(String value) {
        this.userType = value;
    }

}
