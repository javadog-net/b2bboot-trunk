/**
 * SendMesServiceSingleCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.4  Built on : Oct 21, 2016 (10:47:34 BST)
 */
package com.haier.b2b.api.wsClient;


/**
 *  SendMesServiceSingleCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SendMesServiceSingleCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SendMesServiceSingleCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SendMesServiceSingleCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for sendMesServiceSingle method
     * override this method for handling normal response from sendMesServiceSingle operation
     */
    public void receiveResultsendMesServiceSingle(
        com.haier.b2b.api.wsClient.SendMesServiceSingleStub.SendMesServiceSingleResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sendMesServiceSingle operation
     */
    public void receiveErrorsendMesServiceSingle(java.lang.Exception e) {
    }
}
