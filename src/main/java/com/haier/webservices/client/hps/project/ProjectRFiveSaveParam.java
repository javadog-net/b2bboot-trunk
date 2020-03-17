
package com.haier.webservices.client.hps.project;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectRFiveSaveParam", propOrder = {
    "beWinBid",
    "centerPoolProportion",
    "createdById",
    "estimatedTimeDelivery",
    "estimatedTimeSigning",
    "id",
    "lastModifiedById",
    "projectId",
    "projectManagerCode",
    "projectManagerId",
    "projectManagerName",
    "winBidMessage",
    "winBidTime"
})
public class ProjectRFiveSaveParam
    extends ToString
{

    protected Boolean beWinBid;
    protected BigDecimal centerPoolProportion;
    protected String createdById;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedTimeDelivery;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar estimatedTimeSigning;
    protected String id;
    protected String lastModifiedById;
    protected String projectId;
    protected String projectManagerCode;
    protected String projectManagerId;
    protected String projectManagerName;
    protected String winBidMessage;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar winBidTime;

     
    public Boolean isBeWinBid() {
        return beWinBid;
    }

     
    public void setBeWinBid(Boolean value) {
        this.beWinBid = value;
    }

     
    public BigDecimal getCenterPoolProportion() {
        return centerPoolProportion;
    }

     
    public void setCenterPoolProportion(BigDecimal value) {
        this.centerPoolProportion = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public XMLGregorianCalendar getEstimatedTimeDelivery() {
        return estimatedTimeDelivery;
    }

     
    public void setEstimatedTimeDelivery(XMLGregorianCalendar value) {
        this.estimatedTimeDelivery = value;
    }

     
    public XMLGregorianCalendar getEstimatedTimeSigning() {
        return estimatedTimeSigning;
    }

     
    public void setEstimatedTimeSigning(XMLGregorianCalendar value) {
        this.estimatedTimeSigning = value;
    }

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getLastModifiedById() {
        return lastModifiedById;
    }

     
    public void setLastModifiedById(String value) {
        this.lastModifiedById = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public String getProjectManagerCode() {
        return projectManagerCode;
    }

     
    public void setProjectManagerCode(String value) {
        this.projectManagerCode = value;
    }

     
    public String getProjectManagerId() {
        return projectManagerId;
    }

     
    public void setProjectManagerId(String value) {
        this.projectManagerId = value;
    }

     
    public String getProjectManagerName() {
        return projectManagerName;
    }

     
    public void setProjectManagerName(String value) {
        this.projectManagerName = value;
    }

     
    public String getWinBidMessage() {
        return winBidMessage;
    }

     
    public void setWinBidMessage(String value) {
        this.winBidMessage = value;
    }

     
    public XMLGregorianCalendar getWinBidTime() {
        return winBidTime;
    }

     
    public void setWinBidTime(XMLGregorianCalendar value) {
        this.winBidTime = value;
    }

}
