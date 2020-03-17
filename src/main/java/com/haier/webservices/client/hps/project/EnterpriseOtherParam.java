
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "enterpriseOtherParam", propOrder = {
    "keyword"
})
public class EnterpriseOtherParam
    extends ToString
{

    protected String keyword;

     
    public String getKeyword() {
        return keyword;
    }

     
    public void setKeyword(String value) {
        this.keyword = value;
    }

}
