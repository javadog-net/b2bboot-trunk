
package com.haier.webservices.client.hps.exception;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createBrownException", propOrder = {
    "arg0"
})
public class CreateBrownException {

    protected BrownExceptionSaveParam arg0;

     
    public BrownExceptionSaveParam getArg0() {
        return arg0;
    }

     
    public void setArg0(BrownExceptionSaveParam value) {
        this.arg0 = value;
    }

}
