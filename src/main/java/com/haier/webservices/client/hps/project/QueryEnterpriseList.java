
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryEnterpriseList", propOrder = {
    "arg0"
})
public class QueryEnterpriseList {

    protected EnterpriseOtherParam arg0;

     
    public EnterpriseOtherParam getArg0() {
        return arg0;
    }

     
    public void setArg0(EnterpriseOtherParam value) {
        this.arg0 = value;
    }

}
