
package com.haier.webservices.client.hps.project;

 

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 
public final class VerifyBillWebService_VerifyBillWebServiceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://verify.manage.hps.com/", "VerifyBillWebService");

    private VerifyBillWebService_VerifyBillWebServiceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = VerifyBillWebService_Service.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        VerifyBillWebService_Service ss = new VerifyBillWebService_Service(wsdlURL, SERVICE_NAME);
        VerifyBillWebService port = ss.getVerifyBillWebServiceImplPort();

        {
        System.out.println("Invoking getVerifyBillFromBrown...");
        java.lang.String _getVerifyBillFromBrown_arg0 = "";
        com.haier.webservices.client.hps.project.ResInfoVerifyInfo _getVerifyBillFromBrown__return = port.getVerifyBillFromBrown(_getVerifyBillFromBrown_arg0);
        System.out.println("getVerifyBillFromBrown.result=" + _getVerifyBillFromBrown__return);


        }
        {
        System.out.println("Invoking queryPageForVerify...");
        com.haier.webservices.client.hps.project.WebserviceBrownQueryPagePara _queryPageForVerify_arg0 = null;
        com.haier.webservices.client.hps.project.ResInfoBrown _queryPageForVerify__return = port.queryPageForVerify(_queryPageForVerify_arg0);
        System.out.println("queryPageForVerify.result=" + _queryPageForVerify__return);


        }
        {
        System.out.println("Invoking queryPage...");
        com.haier.webservices.client.hps.project.WebserviceVerifyQueryPagePara _queryPage_arg0 = null;
        com.haier.webservices.client.hps.project.ResInfoVerify _queryPage__return = port.queryPage(_queryPage_arg0);
        System.out.println("queryPage.result=" + _queryPage__return);


        }
        {
        System.out.println("Invoking verifyInfo...");
        java.lang.String _verifyInfo_arg0 = "";
        com.haier.webservices.client.hps.project.ResInfoVerifyInfo _verifyInfo__return = port.verifyInfo(_verifyInfo_arg0);
        System.out.println("verifyInfo.result=" + _verifyInfo__return);


        }
        {
        System.out.println("Invoking saveOrUpdate...");
        com.haier.webservices.client.hps.project.VerifyBillSaveParam _saveOrUpdate_arg0 = null;
        java.lang.String _saveOrUpdate_arg1 = "";
        com.haier.webservices.client.hps.project.ResInfoVerifyParamInfo _saveOrUpdate__return = port.saveOrUpdate(_saveOrUpdate_arg0, _saveOrUpdate_arg1);
        System.out.println("saveOrUpdate.result=" + _saveOrUpdate__return);


        }

        System.exit(0);
    }

}
