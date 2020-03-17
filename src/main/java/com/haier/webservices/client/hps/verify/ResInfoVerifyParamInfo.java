
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInfoVerifyParamInfo", propOrder = {
    "verifyBill"
})
public class ResInfoVerifyParamInfo
    extends ResInfo
{

    protected VerifyBillSaveParam verifyBill;

     
    public VerifyBillSaveParam getVerifyBill() {
        return verifyBill;
    }

     
    public void setVerifyBill(VerifyBillSaveParam value) {
        this.verifyBill = value;
    }

}
