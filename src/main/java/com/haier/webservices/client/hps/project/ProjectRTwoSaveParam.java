
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectRTwoSaveParam", propOrder = {
    "beSplitShipment",
    "beTemplate",
    "chancePoint",
    "createdById",
    "estimatedDeliveryAmount",
    "estimatedInstallationAmount",
    "firstContactName",
    "id",
    "industryCategory",
    "industryHomeCategory",
    "lastModifiedById",
    "lockUser",
    "phone",
    "position",
    "projectDevelopPlan",
    "projectFinancialStatus",
    "projectId",
    "projectSpecificLocation",
    "projectValueProposition",
    "userGroup"
})
public class ProjectRTwoSaveParam
    extends ToString
{

    protected Boolean beSplitShipment;
    protected Boolean beTemplate;
    protected String chancePoint;
    protected String createdById;
    protected BigDecimal estimatedDeliveryAmount;
    protected BigDecimal estimatedInstallationAmount;
    protected String firstContactName;
    protected String id;
    protected String industryCategory;
    protected String industryHomeCategory;
    protected String lastModifiedById;
    protected String lockUser;
    protected String phone;
    protected String position;
    protected String projectDevelopPlan;
    protected String projectFinancialStatus;
    protected String projectId;
    protected String projectSpecificLocation;
    protected String projectValueProposition;
    protected String userGroup;

     
    public Boolean isBeSplitShipment() {
        return beSplitShipment;
    }

     
    public void setBeSplitShipment(Boolean value) {
        this.beSplitShipment = value;
    }

     
    public Boolean isBeTemplate() {
        return beTemplate;
    }

     
    public void setBeTemplate(Boolean value) {
        this.beTemplate = value;
    }

     
    public String getChancePoint() {
        return chancePoint;
    }

     
    public void setChancePoint(String value) {
        this.chancePoint = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public BigDecimal getEstimatedDeliveryAmount() {
        return estimatedDeliveryAmount;
    }

     
    public void setEstimatedDeliveryAmount(BigDecimal value) {
        this.estimatedDeliveryAmount = value;
    }

     
    public BigDecimal getEstimatedInstallationAmount() {
        return estimatedInstallationAmount;
    }

     
    public void setEstimatedInstallationAmount(BigDecimal value) {
        this.estimatedInstallationAmount = value;
    }

     
    public String getFirstContactName() {
        return firstContactName;
    }

     
    public void setFirstContactName(String value) {
        this.firstContactName = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getIndustryCategory() {
        return industryCategory;
    }

     
    public void setIndustryCategory(String value) {
        this.industryCategory = value;
    }

     
    public String getIndustryHomeCategory() {
        return industryHomeCategory;
    }

     
    public void setIndustryHomeCategory(String value) {
        this.industryHomeCategory = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getLockUser() {
        return lockUser;
    }

     
    public void setLockUser(String value) {
        this.lockUser = value;
    }

     
    public String getPhone() {
        return phone;
    }

     
    public void setPhone(String value) {
        this.phone = value;
    }

     
    public String getPosition() {
        return position;
    }

     
    public void setPosition(String value) {
        this.position = value;
    }

     
    public String getProjectDevelopPlan() {
        return projectDevelopPlan;
    }

     
    public void setProjectDevelopPlan(String value) {
        this.projectDevelopPlan = value;
    }

     
    public String getProjectFinancialStatus() {
        return projectFinancialStatus;
    }

     
    public void setProjectFinancialStatus(String value) {
        this.projectFinancialStatus = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getProjectSpecificLocation() {
        return projectSpecificLocation;
    }

     
    public void setProjectSpecificLocation(String value) {
        this.projectSpecificLocation = value;
    }

     
    public String getProjectValueProposition() {
        return projectValueProposition;
    }

     
    public void setProjectValueProposition(String value) {
        this.projectValueProposition = value;
    }

     
    public String getUserGroup() {
        return userGroup;
    }

     
    public void setUserGroup(String value) {
        this.userGroup = value;
    }

}
