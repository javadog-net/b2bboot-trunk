
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyInfoResponse", propOrder = {
    "_return"
})
public class VerifyInfoResponse {

    @XmlElement(name = "return")
    protected ResInfoVerifyInfo _return;

     
    public ResInfoVerifyInfo getReturn() {
        return _return;
    }

     
    public void setReturn(ResInfoVerifyInfo value) {
        this._return = value;
    }

}
