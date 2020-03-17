
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryProjectManagerFromHPS", propOrder = {
    "arg0"
})
public class QueryProjectManagerFromHPS {

    protected ProjectROneSaveParam arg0;

     
    public ProjectROneSaveParam getArg0() {
        return arg0;
    }

     
    public void setArg0(ProjectROneSaveParam value) {
        this.arg0 = value;
    }

}
