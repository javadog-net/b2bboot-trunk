package com.haier.webservices.client.hps.verify;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 
@WebService(targetNamespace = "http://verify.manage.hps.com/", name = "VerifyBillWebService")
@XmlSeeAlso({ObjectFactory.class})
public interface VerifyBillWebService {

    @WebMethod
    @RequestWrapper(localName = "getVerifyBillFromBrown", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.GetVerifyBillFromBrown")
    @ResponseWrapper(localName = "getVerifyBillFromBrownResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.GetVerifyBillFromBrownResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.verify.ResInfoVerifyInfo getVerifyBillFromBrown(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryPageForVerify", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.QueryPageForVerify")
    @ResponseWrapper(localName = "queryPageForVerifyResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.QueryPageForVerifyResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.verify.ResInfoBrown queryPageForVerify(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.verify.WebserviceBrownQueryPagePara arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryPage", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.QueryPage")
    @ResponseWrapper(localName = "queryPageResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.QueryPageResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.verify.ResInfoVerify queryPage(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.verify.WebserviceVerifyQueryPagePara arg0
    );

    @WebMethod
    @RequestWrapper(localName = "verifyInfo", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.VerifyInfo")
    @ResponseWrapper(localName = "verifyInfoResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.VerifyInfoResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.verify.ResInfoVerifyInfo verifyInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "saveOrUpdate", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.SaveOrUpdate")
    @ResponseWrapper(localName = "saveOrUpdateResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.verify.SaveOrUpdateResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.verify.ResInfoVerifyParamInfo saveOrUpdate(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.verify.VerifyBillSaveParam arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );
}
