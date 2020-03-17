package com.haier.webservices.client.mdmTob2b.partner;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.8
 * 2019-10-12T14:54:29.974+08:00
 * Generated source version: 3.2.8
 *
 */
@WebService(targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease", name = "GeneralMDMDataRelease")
@XmlSeeAlso({ObjectFactory.class})
public interface GeneralMDMDataRelease {

    @WebMethod(action = "process")
    @RequestWrapper(localName = "process", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease", className = "com.haier.webservices.client.mdmTob2b.partner.Process")
    @ResponseWrapper(localName = "processResponse", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease", className = "com.haier.webservices.client.mdmTob2b.partner.ProcessResponse")
    public void process(
        @WebParam(name = "IN_SYS_NAME", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inSYSNAME,
        @WebParam(name = "IN_MASTER_TYPE", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inMASTERTYPE,
        @WebParam(name = "IN_TABLE_NAME", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inTABLENAME,
        @WebParam(name = "IN_STARTDATE", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inSTARTDATE,
        @WebParam(name = "IN_ENDDATE", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inENDDATE,
        @WebParam(name = "IN_PAGE", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inPAGE,
        @WebParam(name = "IN_BATCH_ID", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        java.lang.String inBATCHID,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_PAGE", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outPAGE,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_RESULT", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outRESULT,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_RETCODE", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outRETCODE,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_ALL_NUM", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outALLNUM,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_PAGE_CON", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outPAGECON,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_ALL_COUNT", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outALLCOUNT,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_RETMSG", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outRETMSG,
        @WebParam(mode = WebParam.Mode.OUT, name = "OUT_BATCH_ID", targetNamespace = "http://xmlns.oracle.com/Interface/GeneralMDMDataRelease/GeneralMDMDataRelease")
        javax.xml.ws.Holder<java.lang.String> outBATCHID
    );
}
