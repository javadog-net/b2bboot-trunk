package com.haier.webservices.client.hps.exception;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

 
@WebServiceClient(name = "BrownExceptionWebserviceImplService",
                  wsdlLocation = "http://10.138.10.68:8090/soap/createBrownException?wsdl",
                  targetNamespace = "http://api.webservice.hps.com/")
public class BrownExceptionWebserviceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://api.webservice.hps.com/", "BrownExceptionWebserviceImplService");
    public final static QName BrownExceptionWebserviceImplPort = new QName("http://api.webservice.hps.com/", "BrownExceptionWebserviceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.138.10.68:8090/soap/createBrownException?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(BrownExceptionWebserviceImplService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://10.138.10.68:8090/soap/createBrownException?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public BrownExceptionWebserviceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public BrownExceptionWebserviceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public BrownExceptionWebserviceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public BrownExceptionWebserviceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public BrownExceptionWebserviceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public BrownExceptionWebserviceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




     
    @WebEndpoint(name = "BrownExceptionWebserviceImplPort")
    public BrownExceptionWebservice getBrownExceptionWebserviceImplPort() {
        return super.getPort(BrownExceptionWebserviceImplPort, BrownExceptionWebservice.class);
    }

     
    @WebEndpoint(name = "BrownExceptionWebserviceImplPort")
    public BrownExceptionWebservice getBrownExceptionWebserviceImplPort(WebServiceFeature... features) {
        return super.getPort(BrownExceptionWebserviceImplPort, BrownExceptionWebservice.class, features);
    }

}
