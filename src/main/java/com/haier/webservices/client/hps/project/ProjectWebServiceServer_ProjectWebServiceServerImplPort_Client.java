
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

 
public final class ProjectWebServiceServer_ProjectWebServiceServerImplPort_Client {

    private static final QName SERVICE_NAME = new QName("http://project.funnel.api.manage.hps.com/", "ProjectWebServiceServer");

    private ProjectWebServiceServer_ProjectWebServiceServerImplPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = ProjectWebServiceServer_Service.WSDL_LOCATION;
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

        ProjectWebServiceServer_Service ss = new ProjectWebServiceServer_Service(wsdlURL, SERVICE_NAME);
        ProjectWebServiceServer port = ss.getProjectWebServiceServerImplPort();

        {
        System.out.println("Invoking saveProjectFromQYGNew...");
        java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> _saveProjectFromQYGNew_arg0 = null;
        java.lang.String _saveProjectFromQYGNew__return = port.saveProjectFromQYGNew(_saveProjectFromQYGNew_arg0);
        System.out.println("saveProjectFromQYGNew.result=" + _saveProjectFromQYGNew__return);


        }
        {
        System.out.println("Invoking lockUserList...");
        java.lang.String _lockUserList_arg0 = "";
        java.util.List<com.haier.webservices.client.hps.project.LockUserVO> _lockUserList__return = port.lockUserList(_lockUserList_arg0);
        System.out.println("lockUserList.result=" + _lockUserList__return);


        }
        {
        System.out.println("Invoking queryEnterpriseList...");
        com.haier.webservices.client.hps.project.EnterpriseOtherParam _queryEnterpriseList_arg0 = null;
        java.util.List<com.haier.webservices.client.hps.project.EnterpriseInfoVO> _queryEnterpriseList__return = port.queryEnterpriseList(_queryEnterpriseList_arg0);
        System.out.println("queryEnterpriseList.result=" + _queryEnterpriseList__return);


        }
        {
        System.out.println("Invoking saveProjectFromQYG...");
        java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> _saveProjectFromQYG_arg0 = null;
        java.lang.String _saveProjectFromQYG__return = port.saveProjectFromQYG(_saveProjectFromQYG_arg0);
        System.out.println("saveProjectFromQYG.result=" + _saveProjectFromQYG__return);


        }
        {
        System.out.println("Invoking queryProjectDetailList...");
        com.haier.webservices.client.hps.project.ProjectDetailOtherParam _queryProjectDetailList_arg0 = null;
        com.haier.webservices.client.hps.project.ProjectDetailInfoQYGVO _queryProjectDetailList__return = port.queryProjectDetailList(_queryProjectDetailList_arg0);
        System.out.println("queryProjectDetailList.result=" + _queryProjectDetailList__return);


        }
        {
        System.out.println("Invoking queryProjectManagerFromHPS...");
        com.haier.webservices.client.hps.project.ProjectROneSaveParam _queryProjectManagerFromHPS_arg0 = null;
        java.util.List<com.haier.webservices.client.hps.project.UserDTO> _queryProjectManagerFromHPS__return = port.queryProjectManagerFromHPS(_queryProjectManagerFromHPS_arg0);
        System.out.println("queryProjectManagerFromHPS.result=" + _queryProjectManagerFromHPS__return);


        }
        {
        System.out.println("Invoking updateProjectFromQYGNew...");
        java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> _updateProjectFromQYGNew_arg0 = null;
        java.lang.String _updateProjectFromQYGNew__return = port.updateProjectFromQYGNew(_updateProjectFromQYGNew_arg0);
        System.out.println("updateProjectFromQYGNew.result=" + _updateProjectFromQYGNew__return);


        }

        System.exit(0);
    }

}
