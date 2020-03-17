package com.haier.webservices.client.hps.verify;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

 
@WebServiceClient(name = "VerifyBillWebService",
                  wsdlLocation = "http://10.138.111.55:8090/soap/verify?wsdl",
                  targetNamespace = "http://verify.manage.hps.com/")
public class VerifyBillWebService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://verify.manage.hps.com/", "VerifyBillWebService");
    public final static QName VerifyBillWebServiceImplPort = new QName("http://verify.manage.hps.com/", "VerifyBillWebServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.138.111.55:8090/soap/verify?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(VerifyBillWebService_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://10.138.111.55:8090/soap/verify?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public VerifyBillWebService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public VerifyBillWebService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public VerifyBillWebService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public VerifyBillWebService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public VerifyBillWebService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public VerifyBillWebService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




     
    @WebEndpoint(name = "VerifyBillWebServiceImplPort")
    public VerifyBillWebService getVerifyBillWebServiceImplPort() {
        return super.getPort(VerifyBillWebServiceImplPort, VerifyBillWebService.class);
    }

     
    @WebEndpoint(name = "VerifyBillWebServiceImplPort")
    public VerifyBillWebService getVerifyBillWebServiceImplPort(WebServiceFeature... features) {
        return super.getPort(VerifyBillWebServiceImplPort, VerifyBillWebService.class, features);
    }

}
