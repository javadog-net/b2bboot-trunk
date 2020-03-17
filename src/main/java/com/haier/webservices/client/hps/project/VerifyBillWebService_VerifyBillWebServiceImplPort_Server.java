
package com.haier.webservices.client.hps.project;

import javax.xml.ws.Endpoint;

 

public class VerifyBillWebService_VerifyBillWebServiceImplPort_Server{

    protected VerifyBillWebService_VerifyBillWebServiceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new VerifyBillWebServiceImplPortImpl();
        String address = "http://10.138.10.68:8090/soap/verify";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws java.lang.Exception {
        new VerifyBillWebService_VerifyBillWebServiceImplPort_Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
