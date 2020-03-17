package com.haier.webservices.client.hps.project;

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
    @RequestWrapper(localName = "getVerifyBillFromBrown", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.GetVerifyBillFromBrown")
    @ResponseWrapper(localName = "getVerifyBillFromBrownResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.GetVerifyBillFromBrownResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.project.ResInfoVerifyInfo getVerifyBillFromBrown(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryPageForVerify", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryPageForVerify")
    @ResponseWrapper(localName = "queryPageForVerifyResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryPageForVerifyResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.project.ResInfoBrown queryPageForVerify(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.project.WebserviceBrownQueryPagePara arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryPage", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryPage")
    @ResponseWrapper(localName = "queryPageResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryPageResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.project.ResInfoVerify queryPage(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.project.WebserviceVerifyQueryPagePara arg0
    );

    @WebMethod
    @RequestWrapper(localName = "verifyInfo", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.VerifyInfo")
    @ResponseWrapper(localName = "verifyInfoResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.VerifyInfoResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.project.ResInfoVerifyInfo verifyInfo(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "saveOrUpdate", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.SaveOrUpdate")
    @ResponseWrapper(localName = "saveOrUpdateResponse", targetNamespace = "http://verify.manage.hps.com/", className = "com.haier.webservices.client.hps.project.SaveOrUpdateResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.project.ResInfoVerifyParamInfo saveOrUpdate(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.project.VerifyBillSaveParam arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );
}
