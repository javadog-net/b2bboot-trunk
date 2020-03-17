
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "projectSKMaglevSaveParam", propOrder = {
    "id",
    "maglevId",
    "name",
    "parentname",
    "projectId",
    "quantity"
})
public class ProjectSKMaglevSaveParam
    extends ToString
{

    protected String id;
    protected String maglevId;
    protected String name;
    protected String parentname;
    protected String projectId;
    protected int quantity;

     
    public String getId() {
        return id;
    }

     
    public void setId(String value) {
        this.id = value;
    }

     
    public String getMaglevId() {
        return maglevId;
    }

     
    public void setMaglevId(String value) {
        this.maglevId = value;
    }

     
    public String getName() {
        return name;
    }

     
    public void setName(String value) {
        this.name = value;
    }

     
    public String getParentname() {
        return parentname;
    }

     
    public void setParentname(String value) {
        this.parentname = value;
    }

     
    public String getProjectId() {
        return projectId;
    }

     
    public void setProjectId(String value) {
        this.projectId = value;
    }

     
    public int getQuantity() {
        return quantity;
    }

     
    public void setQuantity(int value) {
        this.quantity = value;
    }

}
