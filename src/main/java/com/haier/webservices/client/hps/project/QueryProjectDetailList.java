
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryProjectDetailList", propOrder = {
    "arg0"
})
public class QueryProjectDetailList {

    protected ProjectDetailOtherParam arg0;

     
    public ProjectDetailOtherParam getArg0() {
        return arg0;
    }

     
    public void setArg0(ProjectDetailOtherParam value) {
        this.arg0 = value;
    }

}
