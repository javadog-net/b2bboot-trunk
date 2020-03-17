
package com.haier.webservices.client.acg;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.2.8
 * 2019-08-14T16:55:52.873+08:00
 * Generated source version: 3.2.8
 *
 */

public class AcgPushService_AcgPushServiceImplPort_Server{

    protected AcgPushService_AcgPushServiceImplPort_Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new AcgPushServiceImplPortImpl();
        String address = "http://localhost:8090/soap/acg";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws Exception {
        new AcgPushService_AcgPushServiceImplPort_Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
