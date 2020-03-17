
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInfoVerify", propOrder = {
    "verifyPage"
})
public class ResInfoVerify
    extends ResInfo
{

    protected PageableVo verifyPage;

     
    public PageableVo getVerifyPage() {
        return verifyPage;
    }

     
    public void setVerifyPage(PageableVo value) {
        this.verifyPage = value;
    }

}
