package com.haier.mdm.qygcx;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2019-07-26T11:45:46.623+08:00
 * Generated source version: 3.3.2
 *
 */
@WebServiceClient(name = "mdmtableconditiondatanew_client_ep",
                  wsdlLocation = "http://bpel.mdm.haier.com:7778/soa-infra/services/interface/MDMTableConditionDataNew/mdmtableconditiondatanew_client_ep?WSDL",
                  targetNamespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew")
public class MdmtableconditiondatanewClientEp extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "mdmtableconditiondatanew_client_ep");
    public final static QName MDMTableConditionDataNewPt = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "MDMTableConditionDataNew_pt");
    static {
        URL url = null;
        try {
            url = new URL("http://bpel.mdm.haier.com:7778/soa-infra/services/interface/MDMTableConditionDataNew/mdmtableconditiondatanew_client_ep?WSDL");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(MdmtableconditiondatanewClientEp.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://bpel.mdm.haier.com:7778/soa-infra/services/interface/MDMTableConditionDataNew/mdmtableconditiondatanew_client_ep?WSDL");
        }
        WSDL_LOCATION = url;
    }

    public MdmtableconditiondatanewClientEp(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MdmtableconditiondatanewClientEp(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MdmtableconditiondatanewClientEp() {
        super(WSDL_LOCATION, SERVICE);
    }

    public MdmtableconditiondatanewClientEp(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public MdmtableconditiondatanewClientEp(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public MdmtableconditiondatanewClientEp(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns MDMTableConditionDataNew
     */
    @WebEndpoint(name = "MDMTableConditionDataNew_pt")
    public MDMTableConditionDataNew getMDMTableConditionDataNewPt() {
        return super.getPort(MDMTableConditionDataNewPt, MDMTableConditionDataNew.class);
    }

    /**
     *
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MDMTableConditionDataNew
     */
    @WebEndpoint(name = "MDMTableConditionDataNew_pt")
    public MDMTableConditionDataNew getMDMTableConditionDataNewPt(WebServiceFeature... features) {
        return super.getPort(MDMTableConditionDataNewPt, MDMTableConditionDataNew.class, features);
    }

}
