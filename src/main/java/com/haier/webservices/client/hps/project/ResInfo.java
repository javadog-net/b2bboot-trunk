
package com.haier.webservices.client.hps.project;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "resInfo", propOrder = {
    "code",
    "msg"
})
@XmlSeeAlso({
    ResInfoVerifyInfo.class,
    ResInfoBrown.class,
    ResInfoVerify.class,
    ResInfoVerifyParamInfo.class
})
public class ResInfo {

    protected String code;
    protected String msg;

     
    public String getCode() {
        return code;
    }

     
    public void setCode(String value) {
        this.code = value;
    }

     
    public String getMsg() {
        return msg;
    }

     
    public void setMsg(String value) {
        this.msg = value;
    }

}
