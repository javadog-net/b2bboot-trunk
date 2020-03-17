
package com.haier.webservices.client.hps.exception;

 

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

 
public final class BrownExceptionWebservice_BrownExceptionWebserviceImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://api.webservice.hps.com/", "BrownExceptionWebserviceImplService");

    private BrownExceptionWebservice_BrownExceptionWebserviceImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = BrownExceptionWebserviceImplService.WSDL_LOCATION;
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

        BrownExceptionWebserviceImplService ss = new BrownExceptionWebserviceImplService(wsdlURL, SERVICE_NAME);
        BrownExceptionWebservice port = ss.getBrownExceptionWebserviceImplPort();

        {
        System.out.println("Invoking createBrownException...");
        com.haier.webservices.client.hps.exception.BrownExceptionSaveParam _createBrownException_arg0 = null;
        java.lang.String _createBrownException__return = port.createBrownException(_createBrownException_arg0);
        System.out.println("createBrownException.result=" + _createBrownException__return);


        }

        System.exit(0);
    }

}
