
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryPageForVerifyResponse", propOrder = {
    "_return"
})
public class QueryPageForVerifyResponse {

    @XmlElement(name = "return")
    protected ResInfoBrown _return;

     
    public ResInfoBrown getReturn() {
        return _return;
    }

     
    public void setReturn(ResInfoBrown value) {
        this._return = value;
    }

}
