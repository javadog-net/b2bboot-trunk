
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryPageForVerify", propOrder = {
    "arg0"
})
public class QueryPageForVerify {

    protected WebserviceBrownQueryPagePara arg0;

     
    public WebserviceBrownQueryPagePara getArg0() {
        return arg0;
    }

     
    public void setArg0(WebserviceBrownQueryPagePara value) {
        this.arg0 = value;
    }

}
