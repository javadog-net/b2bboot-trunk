
 

package com.haier.webservices.client.hps.verify;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 

@javax.jws.WebService(
                      serviceName = "VerifyBillWebService",
                      portName = "VerifyBillWebServiceImplPort",
                      targetNamespace = "http://verify.manage.hps.com/",
                      wsdlLocation = "http://10.138.111.55:8090/soap/verify?wsdl",
                      endpointInterface = "com.haier.webservices.client.hps.verify.VerifyBillWebService")

public class VerifyBillWebServiceImplPortImpl implements VerifyBillWebService {

    private static final Logger LOG = Logger.getLogger(VerifyBillWebServiceImplPortImpl.class.getName());

     
    public com.haier.webservices.client.hps.verify.ResInfoVerifyInfo getVerifyBillFromBrown(java.lang.String arg0) {
        LOG.info("Executing operation getVerifyBillFromBrown");
        System.out.println(arg0);
        try {
            com.haier.webservices.client.hps.verify.ResInfoVerifyInfo _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public com.haier.webservices.client.hps.verify.ResInfoBrown queryPageForVerify(com.haier.webservices.client.hps.verify.WebserviceBrownQueryPagePara arg0) {
        LOG.info("Executing operation queryPageForVerify");
        System.out.println(arg0);
        try {
            com.haier.webservices.client.hps.verify.ResInfoBrown _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public com.haier.webservices.client.hps.verify.ResInfoVerify queryPage(com.haier.webservices.client.hps.verify.WebserviceVerifyQueryPagePara arg0) {
        LOG.info("Executing operation queryPage");
        System.out.println(arg0);
        try {
            com.haier.webservices.client.hps.verify.ResInfoVerify _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public com.haier.webservices.client.hps.verify.ResInfoVerifyInfo verifyInfo(java.lang.String arg0) {
        LOG.info("Executing operation verifyInfo");
        System.out.println(arg0);
        try {
            com.haier.webservices.client.hps.verify.ResInfoVerifyInfo _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public com.haier.webservices.client.hps.verify.ResInfoVerifyParamInfo saveOrUpdate(com.haier.webservices.client.hps.verify.VerifyBillSaveParam arg0, java.lang.String arg1) {
        LOG.info("Executing operation saveOrUpdate");
        System.out.println(arg0);
        System.out.println(arg1);
        try {
            com.haier.webservices.client.hps.verify.ResInfoVerifyParamInfo _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
