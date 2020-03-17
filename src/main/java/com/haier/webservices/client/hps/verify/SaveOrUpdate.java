
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOrUpdate", propOrder = {
    "arg0",
    "arg1"
})
public class SaveOrUpdate {

    protected VerifyBillSaveParam arg0;
    protected String arg1;

     
    public VerifyBillSaveParam getArg0() {
        return arg0;
    }

     
    public void setArg0(VerifyBillSaveParam value) {
        this.arg0 = value;
    }

     
    public String getArg1() {
        return arg1;
    }

     
    public void setArg1(String value) {
        this.arg1 = value;
    }

}
