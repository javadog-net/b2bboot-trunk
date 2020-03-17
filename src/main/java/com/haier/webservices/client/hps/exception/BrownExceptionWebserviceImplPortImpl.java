
 

package com.haier.webservices.client.hps.exception;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 

@javax.jws.WebService(
                      serviceName = "BrownExceptionWebserviceImplService",
                      portName = "BrownExceptionWebserviceImplPort",
                      targetNamespace = "http://api.webservice.hps.com/",
                      wsdlLocation = "http://10.138.10.68:8090/soap/createBrownException?wsdl",
                      endpointInterface = "com.haier.webservices.client.hps.exception.BrownExceptionWebservice")

public class BrownExceptionWebserviceImplPortImpl implements BrownExceptionWebservice {

    private static final Logger LOG = Logger.getLogger(BrownExceptionWebserviceImplPortImpl.class.getName());

     
    public java.lang.String createBrownException(com.haier.webservices.client.hps.exception.BrownExceptionSaveParam arg0) {
        LOG.info("Executing operation createBrownException");
        System.out.println(arg0);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
