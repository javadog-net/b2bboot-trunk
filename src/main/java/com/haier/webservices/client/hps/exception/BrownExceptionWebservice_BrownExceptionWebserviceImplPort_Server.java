
package com.haier.webservices.client.hps.exception;

import javax.xml.ws.Endpoint;

 

public class BrownExceptionWebservice_BrownExceptionWebserviceImplPort_Server{

    protected BrownExceptionWebservice_BrownExceptionWebserviceImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new BrownExceptionWebserviceImplPortImpl();
        String address = "http://10.138.10.68:8090/soap/createBrownException";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws java.lang.Exception {
        new BrownExceptionWebservice_BrownExceptionWebserviceImplPort_Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
