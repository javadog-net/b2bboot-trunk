
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "basicOperatorRequest", propOrder = {
    "operator",
    "optSystem"
})
@XmlSeeAlso({
    VerifyBillSaveParam.class
})
public class BasicOperatorRequest
    extends ToString
{

    protected String operator;
    protected String optSystem;

     
    public String getOperator() {
        return operator;
    }

     
    public void setOperator(String value) {
        this.operator = value;
    }

     
    public String getOptSystem() {
        return optSystem;
    }

     
    public void setOptSystem(String value) {
        this.optSystem = value;
    }

}
