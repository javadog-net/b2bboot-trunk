
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryProjectDetailListResponse", propOrder = {
    "_return"
})
public class QueryProjectDetailListResponse {

    @XmlElement(name = "return")
    protected ProjectDetailInfoQYGVO _return;

     
    public ProjectDetailInfoQYGVO getReturn() {
        return _return;
    }

     
    public void setReturn(ProjectDetailInfoQYGVO value) {
        this._return = value;
    }

}
