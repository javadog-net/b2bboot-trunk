
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basicDto", propOrder = {
    "batchDate",
    "creProId",
    "createdBy",
    "createdDate",
    "deleted",
    "lastModifiedBy",
    "lastModifiedDate",
    "modProId"
})
@XmlSeeAlso({
    StrategyProductDTO.class,
    UserDTO.class,
    BasePostTagContentDTO.class
})
public abstract class BasicDto
    extends ToString
{

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar batchDate;
    protected String creProId;
    protected String createdBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected Boolean deleted;
    protected String lastModifiedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    protected String modProId;

     
    public XMLGregorianCalendar getBatchDate() {
        return batchDate;
    }

     
    public void setBatchDate(XMLGregorianCalendar value) {
        this.batchDate = value;
    }

     
    public String getCreProId() {
        return creProId;
    }

     
    public void setCreProId(String value) {
        this.creProId = value;
    }

     
    public String getCreatedBy() {
        return createdBy;
    }

     
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

     
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

     
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

     
    public Boolean isDeleted() {
        return deleted;
    }

     
    public void setDeleted(Boolean value) {
        this.deleted = value;
    }

     
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

     
    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }

     
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

     
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

     
    public String getModProId() {
        return modProId;
    }

     
    public void setModProId(String value) {
        this.modProId = value;
    }

}
