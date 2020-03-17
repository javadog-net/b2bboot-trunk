
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryPage", propOrder = {
    "arg0"
})
public class QueryPage {

    protected WebserviceVerifyQueryPagePara arg0;

     
    public WebserviceVerifyQueryPagePara getArg0() {
        return arg0;
    }

     
    public void setArg0(WebserviceVerifyQueryPagePara value) {
        this.arg0 = value;
    }

}
