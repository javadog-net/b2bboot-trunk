
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "webserviceBrownQueryPagePara", propOrder = {
    "brownCode",
    "dealerCode",
    "maxExpireTime",
    "minExpireTime",
    "page",
    "projectName",
    "rows"
})
public class WebserviceBrownQueryPagePara {

    protected String brownCode;
    protected String dealerCode;
    protected String maxExpireTime;
    protected String minExpireTime;
    protected Integer page;
    protected String projectName;
    protected Integer rows;

     
    public String getBrownCode() {
        return brownCode;
    }

     
    public void setBrownCode(String value) {
        this.brownCode = value;
    }

     
    public String getDealerCode() {
        return dealerCode;
    }

     
    public void setDealerCode(String value) {
        this.dealerCode = value;
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

}
