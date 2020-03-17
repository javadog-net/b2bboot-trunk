package com.haier.webservices.client.acg;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.8
 * 2019-08-14T16:55:52.876+08:00
 * Generated source version: 3.2.8
 *
 */
@WebServiceClient(name = "AcgPushServiceImplService",
                  wsdlLocation = "http://localhost:8090/soap/acg?wsdl",
                  targetNamespace = "http://impl.acg.server.webservices.haier.com/")
public class AcgPushServiceImplService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.acg.server.webservices.haier.com/", "AcgPushServiceImplService");
    public final static QName AcgPushServiceImplPort = new QName("http://impl.acg.server.webservices.haier.com/", "AcgPushServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8090/soap/acg?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AcgPushServiceImplService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://localhost:8090/soap/acg?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AcgPushServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AcgPushServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AcgPushServiceImplService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public AcgPushServiceImplService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public AcgPushServiceImplService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public AcgPushServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns AcgPushService
     */
    @WebEndpoint(name = "AcgPushServiceImplPort")
    public AcgPushService getAcgPushServiceImplPort() {
        return super.getPort(AcgPushServiceImplPort, AcgPushService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AcgPushService
     */
    @WebEndpoint(name = "AcgPushServiceImplPort")
    public AcgPushService getAcgPushServiceImplPort(WebServiceFeature... features) {
        return super.getPort(AcgPushServiceImplPort, AcgPushService.class, features);
    }

}