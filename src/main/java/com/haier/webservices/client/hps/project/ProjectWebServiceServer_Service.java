package com.haier.webservices.client.hps.project;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

 
@WebServiceClient(name = "ProjectWebServiceServer",
                  wsdlLocation = "http://10.138.10.68:8090/soap/project?wsdl",
                  targetNamespace = "http://project.funnel.api.manage.hps.com/")
public class ProjectWebServiceServer_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://project.funnel.api.manage.hps.com/", "ProjectWebServiceServer");
    public final static QName ProjectWebServiceServerImplPort = new QName("http://project.funnel.api.manage.hps.com/", "ProjectWebServiceServerImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://10.138.10.68:8090/soap/project?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(ProjectWebServiceServer_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://10.138.10.68:8090/soap/project?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ProjectWebServiceServer_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ProjectWebServiceServer_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ProjectWebServiceServer_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ProjectWebServiceServer_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ProjectWebServiceServer_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ProjectWebServiceServer_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




     
    @WebEndpoint(name = "ProjectWebServiceServerImplPort")
    public ProjectWebServiceServer getProjectWebServiceServerImplPort() {
        return super.getPort(ProjectWebServiceServerImplPort, ProjectWebServiceServer.class);
    }

     
    @WebEndpoint(name = "ProjectWebServiceServerImplPort")
    public ProjectWebServiceServer getProjectWebServiceServerImplPort(WebServiceFeature... features) {
        return super.getPort(ProjectWebServiceServerImplPort, ProjectWebServiceServer.class, features);
    }

}
