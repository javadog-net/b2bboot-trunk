
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basePostTagContentDTO", propOrder = {
    "basePostCode",
    "basePostId",
    "createdById",
    "id",
    "lastModifiedById",
    "standardPostCode",
    "standardPostId",
    "tagCode",
    "tagContentDesc",
    "tagContentId",
    "tagContentName",
    "tagContentType",
    "tagId"
})
public class BasePostTagContentDTO
    extends BasicDto
{

    protected String basePostCode;
    protected String basePostId;
    protected String createdById;
    protected String id;
    protected String lastModifiedById;
    protected String standardPostCode;
    protected String standardPostId;
    protected String tagCode;
    protected String tagContentDesc;
    protected String tagContentId;
    protected String tagContentName;
    protected String tagContentType;
    protected String tagId;

     
    public String getBasePostCode() {
        return basePostCode;
    }

     
    public void setBasePostCode(String value) {
        this.basePostCode = value;
    }

     
    public String getBasePostId() {
        return basePostId;
    }

     
    public void setBasePostId(String value) {
        this.basePostId = value;
    }

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
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

     
    public String getStandardPostCode() {
        return standardPostCode;
    }

     
    public void setStandardPostCode(String value) {
        this.standardPostCode = value;
    }

     
    public String getStandardPostId() {
        return standardPostId;
    }

     
    public void setStandardPostId(String value) {
        this.standardPostId = value;
    }

     
    public String getTagCode() {
        return tagCode;
    }

     
    public void setTagCode(String value) {
        this.tagCode = value;
    }

     
    public String getTagContentDesc() {
        return tagContentDesc;
    }

     
    public void setTagContentDesc(String value) {
        this.tagContentDesc = value;
    }

     
    public String getTagContentId() {
        return tagContentId;
    }

     
    public void setTagContentId(String value) {
        this.tagContentId = value;
    }

     
    public String getTagContentName() {
        return tagContentName;
    }

     
    public void setTagContentName(String value) {
        this.tagContentName = value;
    }

     
    public String getTagContentType() {
        return tagContentType;
    }

     
    public void setTagContentType(String value) {
        this.tagContentType = value;
    }

     
    public String getTagId() {
        return tagId;
    }

     
    public void setTagId(String value) {
        this.tagId = value;
    }

}
