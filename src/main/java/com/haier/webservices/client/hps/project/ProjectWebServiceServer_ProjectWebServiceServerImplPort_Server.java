
package com.haier.webservices.client.hps.project;

import javax.xml.ws.Endpoint;

 

public class ProjectWebServiceServer_ProjectWebServiceServerImplPort_Server{

    protected ProjectWebServiceServer_ProjectWebServiceServerImplPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new ProjectWebServiceServerImplPortImpl();
        String address = "http://10.138.10.68:8090/soap/project";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws java.lang.Exception {
        new ProjectWebServiceServer_ProjectWebServiceServerImplPort_Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
