
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "webserviceVerifyQueryPagePara", propOrder = {
    "appeal",
    "cbillCode",
    "center",
    "custCode",
    "maxExpireTime",
    "minExpireTime",
    "page",
    "projectName",
    "rows",
    "verifyStatus"
})
public class WebserviceVerifyQueryPagePara {

    protected String appeal;
    protected String cbillCode;
    protected String center;
    protected String custCode;
    protected String maxExpireTime;
    protected String minExpireTime;
    protected Integer page;
    protected String projectName;
    protected Integer rows;
    protected String verifyStatus;

     
    public String getAppeal() {
        return appeal;
    }

     
    public void setAppeal(String value) {
        this.appeal = value;
    }

     
    public String getCbillCode() {
        return cbillCode;
    }

     
    public void setCbillCode(String value) {
        this.cbillCode = value;
    }

     
    public String getCenter() {
        return center;
    }

     
    public void setCenter(String value) {
        this.center = value;
    }

     
    public String getCustCode() {
        return custCode;
    }

     
    public void setCustCode(String value) {
        this.custCode = value;
    }

     
    public String getMaxExpireTime() {
        return maxExpireTime;
    }

     
    public void setMaxExpireTime(String value) {
        this.maxExpireTime = value;
    }

     
    public String getMinExpireTime() {
        return minExpireTime;
    }

     
    public void setMinExpireTime(String value) {
        this.minExpireTime = value;
    }

     
    public Integer getPage() {
        return page;
    }

     
    public void setPage(Integer value) {
        this.page = value;
    }

     
    public String getProjectName() {
        return projectName;
    }

     
    public void setProjectName(String value) {
        this.projectName = value;
    }

     
    public Integer getRows() {
        return rows;
    }

     
    public void setRows(Integer value) {
        this.rows = value;
    }

     
    public String getVerifyStatus() {
        return verifyStatus;
    }

     
    public void setVerifyStatus(String value) {
        this.verifyStatus = value;
    }

}
