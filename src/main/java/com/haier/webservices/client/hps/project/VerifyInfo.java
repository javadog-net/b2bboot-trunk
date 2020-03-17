
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyInfo", propOrder = {
    "arg0"
})
public class VerifyInfo {

    protected String arg0;

     
    public String getArg0() {
        return arg0;
    }

     
    public void setArg0(String value) {
        this.arg0 = value;
    }

}
