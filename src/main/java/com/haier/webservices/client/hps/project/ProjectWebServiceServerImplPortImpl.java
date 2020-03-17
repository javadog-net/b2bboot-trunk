
 

package com.haier.webservices.client.hps.project;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 

@javax.jws.WebService(
                      serviceName = "ProjectWebServiceServer",
                      portName = "ProjectWebServiceServerImplPort",
                      targetNamespace = "http://project.funnel.api.manage.hps.com/",
                      wsdlLocation = "http://10.138.10.68:8090/soap/project?wsdl",
                      endpointInterface = "com.haier.webservices.client.hps.project.ProjectWebServiceServer")

public class ProjectWebServiceServerImplPortImpl implements ProjectWebServiceServer {

    private static final Logger LOG = Logger.getLogger(ProjectWebServiceServerImplPortImpl.class.getName());

     
    public java.lang.String saveProjectFromQYGNew(java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> arg0) {
        LOG.info("Executing operation saveProjectFromQYGNew");
        System.out.println(arg0);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public java.util.List<com.haier.webservices.client.hps.project.LockUserVO> lockUserList(java.lang.String arg0) {
        LOG.info("Executing operation lockUserList");
        System.out.println(arg0);
        try {
            java.util.List<com.haier.webservices.client.hps.project.LockUserVO> _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public java.util.List<com.haier.webservices.client.hps.project.EnterpriseInfoVO> queryEnterpriseList(com.haier.webservices.client.hps.project.EnterpriseOtherParam arg0) {
        LOG.info("Executing operation queryEnterpriseList");
        System.out.println(arg0);
        try {
            java.util.List<com.haier.webservices.client.hps.project.EnterpriseInfoVO> _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public java.lang.String saveProjectFromQYG(java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> arg0) {
        LOG.info("Executing operation saveProjectFromQYG");
        System.out.println(arg0);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public com.haier.webservices.client.hps.project.ProjectDetailInfoQYGVO queryProjectDetailList(com.haier.webservices.client.hps.project.ProjectDetailOtherParam arg0) {
        LOG.info("Executing operation queryProjectDetailList");
        System.out.println(arg0);
        try {
            com.haier.webservices.client.hps.project.ProjectDetailInfoQYGVO _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public java.util.List<com.haier.webservices.client.hps.project.UserDTO> queryProjectManagerFromHPS(com.haier.webservices.client.hps.project.ProjectROneSaveParam arg0) {
        LOG.info("Executing operation queryProjectManagerFromHPS");
        System.out.println(arg0);
        try {
            java.util.List<com.haier.webservices.client.hps.project.UserDTO> _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

     
    public java.lang.String updateProjectFromQYGNew(java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> arg0) {
        LOG.info("Executing operation updateProjectFromQYGNew");
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
