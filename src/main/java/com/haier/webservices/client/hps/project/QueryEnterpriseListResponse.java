
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryEnterpriseListResponse", propOrder = {
    "_return"
})
public class QueryEnterpriseListResponse {

    @XmlElement(name = "return")
    protected List<EnterpriseInfoVO> _return;

     
    public List<EnterpriseInfoVO> getReturn() {
        if (_return == null) {
            _return = new ArrayList<EnterpriseInfoVO>();
        }
        return this._return;
    }

}
