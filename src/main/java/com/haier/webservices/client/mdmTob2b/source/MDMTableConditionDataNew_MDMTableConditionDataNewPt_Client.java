
package com.haier.webservices.client.mdmTob2b.source;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.8
 * 2020-02-24T11:56:47.714+08:00
 * Generated source version: 3.2.8
 *
 */
public final class MDMTableConditionDataNew_MDMTableConditionDataNewPt_Client {

    private static final QName SERVICE_NAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "mdmtableconditiondatanew_client_ep");

    private MDMTableConditionDataNew_MDMTableConditionDataNewPt_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = MdmtableconditiondatanewClientEp.WSDL_LOCATION;
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

        MdmtableconditiondatanewClientEp ss = new MdmtableconditiondatanewClientEp(wsdlURL, SERVICE_NAME);
        MDMTableConditionDataNew port = ss.getMDMTableConditionDataNewPt();

        {
        System.out.println("Invoking process...");
        java.lang.String _process_inSYSNAME = "";
        java.lang.String _process_inMASTERTYPE = "";
        java.lang.String _process_inTABLENAME = "";
        com.haier.webservices.client.mdmTob2b.source.HAIERMDMFIELDSVALUETABLE _process_inFIELDSVALUETABLE = null;
        javax.xml.ws.Holder<java.lang.String> _process_outRESULT = new javax.xml.ws.Holder<java.lang.String>();
        javax.xml.ws.Holder<java.lang.String> _process_outRETMSG = new javax.xml.ws.Holder<java.lang.String>();
        javax.xml.ws.Holder<java.lang.String> _process_outRETCODE = new javax.xml.ws.Holder<java.lang.String>();
        port.process(_process_inSYSNAME, _process_inMASTERTYPE, _process_inTABLENAME, _process_inFIELDSVALUETABLE, _process_outRESULT, _process_outRETMSG, _process_outRETCODE);

        System.out.println("process._process_outRESULT=" + _process_outRESULT.value);
        System.out.println("process._process_outRETMSG=" + _process_outRETMSG.value);
        System.out.println("process._process_outRETCODE=" + _process_outRETCODE.value);

        }

        System.exit(0);
    }

}
