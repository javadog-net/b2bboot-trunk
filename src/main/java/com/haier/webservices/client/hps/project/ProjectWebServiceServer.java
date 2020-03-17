package com.haier.webservices.client.hps.project;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 
@WebService(targetNamespace = "http://project.funnel.api.manage.hps.com/", name = "ProjectWebServiceServer")
@XmlSeeAlso({ObjectFactory.class})
public interface ProjectWebServiceServer {

    @WebMethod
    @RequestWrapper(localName = "saveProjectFromQYGNew", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.SaveProjectFromQYGNew")
    @ResponseWrapper(localName = "saveProjectFromQYGNewResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.SaveProjectFromQYGNewResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String saveProjectFromQYGNew(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> arg0
    );

    @WebMethod
    @RequestWrapper(localName = "lockUserList", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.LockUserList")
    @ResponseWrapper(localName = "lockUserListResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.LockUserListResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<com.haier.webservices.client.hps.project.LockUserVO> lockUserList(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryEnterpriseList", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryEnterpriseList")
    @ResponseWrapper(localName = "queryEnterpriseListResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryEnterpriseListResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<com.haier.webservices.client.hps.project.EnterpriseInfoVO> queryEnterpriseList(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.project.EnterpriseOtherParam arg0
    );

    @WebMethod
    @RequestWrapper(localName = "saveProjectFromQYG", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.SaveProjectFromQYG")
    @ResponseWrapper(localName = "saveProjectFromQYGResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.SaveProjectFromQYGResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String saveProjectFromQYG(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryProjectDetailList", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryProjectDetailList")
    @ResponseWrapper(localName = "queryProjectDetailListResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryProjectDetailListResponse")
    @WebResult(name = "return", targetNamespace = "")
    public com.haier.webservices.client.hps.project.ProjectDetailInfoQYGVO queryProjectDetailList(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.project.ProjectDetailOtherParam arg0
    );

    @WebMethod
    @RequestWrapper(localName = "queryProjectManagerFromHPS", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryProjectManagerFromHPS")
    @ResponseWrapper(localName = "queryProjectManagerFromHPSResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.QueryProjectManagerFromHPSResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.util.List<com.haier.webservices.client.hps.project.UserDTO> queryProjectManagerFromHPS(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.project.ProjectROneSaveParam arg0
    );

    @WebMethod
    @RequestWrapper(localName = "updateProjectFromQYGNew", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.UpdateProjectFromQYGNew")
    @ResponseWrapper(localName = "updateProjectFromQYGNewResponse", targetNamespace = "http://project.funnel.api.manage.hps.com/", className = "com.haier.webservices.client.hps.project.UpdateProjectFromQYGNewResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String updateProjectFromQYGNew(
        @WebParam(name = "arg0", targetNamespace = "")
        java.util.List<com.haier.webservices.client.hps.project.ProjectSaveParam> arg0
    );
}
