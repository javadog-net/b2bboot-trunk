
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lockUserListResponse", propOrder = {
    "_return"
})
public class LockUserListResponse {

    @XmlElement(name = "return")
    protected List<LockUserVO> _return;

     
    public List<LockUserVO> getReturn() {
        if (_return == null) {
            _return = new ArrayList<LockUserVO>();
        }
        return this._return;
    }

}
