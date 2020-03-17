
package com.haier.webservices.client.hps.verify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInfoBrown", propOrder = {
    "brownPage"
})
public class ResInfoBrown
    extends ResInfo
{

    protected PageableVo brownPage;

     
    public PageableVo getBrownPage() {
        return brownPage;
    }

     
    public void setBrownPage(PageableVo value) {
        this.brownPage = value;
    }

}
