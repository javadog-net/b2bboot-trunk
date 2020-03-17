
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryPageResponse", propOrder = {
    "_return"
})
public class QueryPageResponse {

    @XmlElement(name = "return")
    protected ResInfoVerify _return;

     
    public ResInfoVerify getReturn() {
        return _return;
    }

     
    public void setReturn(ResInfoVerify value) {
        this._return = value;
    }

}
