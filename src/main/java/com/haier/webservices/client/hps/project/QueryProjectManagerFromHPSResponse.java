
package com.haier.webservices.client.hps.project;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "queryProjectManagerFromHPSResponse", propOrder = {
    "_return"
})
public class QueryProjectManagerFromHPSResponse {

    @XmlElement(name = "return")
    protected List<UserDTO> _return;

     
    public List<UserDTO> getReturn() {
        if (_return == null) {
            _return = new ArrayList<UserDTO>();
        }
        return this._return;
    }

}
