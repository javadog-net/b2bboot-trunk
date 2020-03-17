
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getVerifyBillFromBrown", propOrder = {
    "arg0"
})
public class GetVerifyBillFromBrown {

    protected String arg0;

     
    public String getArg0() {
        return arg0;
    }

     
    public void setArg0(String value) {
        this.arg0 = value;
    }

}
