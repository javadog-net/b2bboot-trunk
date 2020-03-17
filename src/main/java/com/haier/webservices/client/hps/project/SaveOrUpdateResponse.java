
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveOrUpdateResponse", propOrder = {
    "_return"
})
public class SaveOrUpdateResponse {

    @XmlElement(name = "return")
    protected ResInfoVerifyParamInfo _return;

     
    public ResInfoVerifyParamInfo getReturn() {
        return _return;
    }

     
    public void setReturn(ResInfoVerifyParamInfo value) {
        this._return = value;
    }

}
