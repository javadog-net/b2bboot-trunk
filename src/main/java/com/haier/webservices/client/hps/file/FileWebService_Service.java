package com.haier.webservices.client.hps.file;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.8
 * 2019-05-13T20:34:15.521+08:00
 * Generated source version: 3.2.8
 *
 */
@WebServiceClient(name = "FileWebService",
                  wsdlLocation = "http://10.138.10.68:8090/soap/file?wsdl",
                  targetNamespace = "http://file.manage.hps.com/")
public class FileWebService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://file.manage.hps.com/", "FileWebService");
    public final static QName FileWebServiceImplPort = new QName("http://file.manage.hps.com/", "FileWebServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.138.10.68:8090/soap/file?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(FileWebService_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://10.138.10.68:8090/soap/file?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public FileWebService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public FileWebService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FileWebService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public FileWebService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public FileWebService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public FileWebService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns FileWebService
     */
    @WebEndpoint(name = "FileWebServiceImplPort")
    public FileWebService getFileWebServiceImplPort() {
        return super.getPort(FileWebServiceImplPort, FileWebService.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FileWebService
     */
    @WebEndpoint(name = "FileWebServiceImplPort")
    public FileWebService getFileWebServiceImplPort(WebServiceFeature... features) {
        return super.getPort(FileWebServiceImplPort, FileWebService.class, features);
    }

}
