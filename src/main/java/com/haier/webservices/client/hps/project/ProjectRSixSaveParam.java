
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectRSixSaveParam", propOrder = {
    "createdById",
    "dataMessage",
    "id",
    "lastModifiedById",
    "projectId",
    "projectManagerCode",
    "projectManagerId",
    "projectManagerName"
})
public class ProjectRSixSaveParam
    extends ToString
{

    protected String createdById;
    protected String dataMessage;
    protected String id;
    protected String lastModifiedById;
    protected String projectId;
    protected String projectManagerCode;
    protected String projectManagerId;
    protected String projectManagerName;

     
    public String getCreatedById() {
        return createdById;
    }

     
    public void setCreatedById(String value) {
        this.createdById = value;
    }

     
    public String getDataMessage() {
        return dataMessage;
    }

     
    public void setDataMessage(String value) {
        this.dataMessage = value;
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

}
