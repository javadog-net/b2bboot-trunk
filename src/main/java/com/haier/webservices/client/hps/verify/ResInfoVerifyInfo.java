
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInfoVerifyInfo", propOrder = {
    "verifyBill"
})
public class ResInfoVerifyInfo
    extends ResInfo
{

    protected VerifyBillVO verifyBill;

     
    public VerifyBillVO getVerifyBill() {
        return verifyBill;
    }

     
    public void setVerifyBill(VerifyBillVO value) {
        this.verifyBill = value;
    }

}
