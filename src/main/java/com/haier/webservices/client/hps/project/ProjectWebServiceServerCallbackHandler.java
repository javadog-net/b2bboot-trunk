 
package com.haier.webservices.client.hps.project;


 
public abstract class ProjectWebServiceServerCallbackHandler {
    protected Object clientData;

     
    public ProjectWebServiceServerCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

     
    public ProjectWebServiceServerCallbackHandler() {
        this.clientData = null;
    }

     
    public Object getClientData() {
        return clientData;
    }

     
    public void receiveResultsaveProjectFromQYG(
        ProjectWebServiceServerStub.SaveProjectFromQYGResponseE result) {
    }

     
    public void receiveErrorsaveProjectFromQYG(java.lang.Exception e) {
    }

     
    public void receiveResultqueryProjectManagerFromHPS(
        ProjectWebServiceServerStub.QueryProjectManagerFromHPSResponseE result) {
    }

     
    public void receiveErrorqueryProjectManagerFromHPS(java.lang.Exception e) {
    }
}
