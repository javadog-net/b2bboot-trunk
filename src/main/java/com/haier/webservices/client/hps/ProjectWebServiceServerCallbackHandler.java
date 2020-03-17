/**
 * ProjectWebServiceServerCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package com.haier.webservices.client.hps;


/**
 *  ProjectWebServiceServerCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class ProjectWebServiceServerCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public ProjectWebServiceServerCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public ProjectWebServiceServerCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for saveProjectFromQYGNew method
     * override this method for handling normal response from saveProjectFromQYGNew operation
     */
    public void receiveResultsaveProjectFromQYGNew(
    		com.haier.webservices.client.hps.ProjectWebServiceServerStub.SaveProjectFromQYGNewResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveProjectFromQYGNew operation
     */
    public void receiveErrorsaveProjectFromQYGNew(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for queryProjectManagerFromHPS method
     * override this method for handling normal response from queryProjectManagerFromHPS operation
     */
    public void receiveResultqueryProjectManagerFromHPS(
    		com.haier.webservices.client.hps.ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from queryProjectManagerFromHPS operation
     */
    public void receiveErrorqueryProjectManagerFromHPS(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for lockUserList method
     * override this method for handling normal response from lockUserList operation
     */
    public void receiveResultlockUserList(
    		com.haier.webservices.client.hps.ProjectWebServiceServerStub.LockUserListResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from lockUserList operation
     */
    public void receiveErrorlockUserList(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for saveProjectFromQYG method
     * override this method for handling normal response from saveProjectFromQYG operation
     */
    public void receiveResultsaveProjectFromQYG(
    		com.haier.webservices.client.hps.ProjectWebServiceServerStub.SaveProjectFromQYGResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from saveProjectFromQYG operation
     */
    public void receiveErrorsaveProjectFromQYG(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for queryEnterpriseList method
     * override this method for handling normal response from queryEnterpriseList operation
     */
    public void receiveResultqueryEnterpriseList(
    		com.haier.webservices.client.hps.ProjectWebServiceServerStub.QueryEnterpriseListResponseE result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from queryEnterpriseList operation
     */
    public void receiveErrorqueryEnterpriseList(java.lang.Exception e) {
    }
}
