/**
 * Createplcust2Mdm_client_epLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.createcust2mdm;

public class Createplcust2Mdm_client_epLocator extends org.apache.axis.client.Service implements Createplcust2Mdm_client_ep {

    public Createplcust2Mdm_client_epLocator() {
    }


    public Createplcust2Mdm_client_epLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Createplcust2Mdm_client_epLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CreatePlCust2MDM_pt
    private java.lang.String CreatePlCust2MDM_pt_address = "http://10.135.16.46:10201/soa-infra/services/interface/CreatePlCust2MDM/createplcust2mdm_client_ep";

    public java.lang.String getCreatePlCust2MDM_ptAddress() {
        return CreatePlCust2MDM_pt_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CreatePlCust2MDM_ptWSDDServiceName = "CreatePlCust2MDM_pt";

    public java.lang.String getCreatePlCust2MDM_ptWSDDServiceName() {
        return CreatePlCust2MDM_ptWSDDServiceName;
    }

    public void setCreatePlCust2MDM_ptWSDDServiceName(java.lang.String name) {
        CreatePlCust2MDM_ptWSDDServiceName = name;
    }

    public CreatePlCust2MDM getCreatePlCust2MDM_pt() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CreatePlCust2MDM_pt_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCreatePlCust2MDM_pt(endpoint);
    }

    public CreatePlCust2MDM getCreatePlCust2MDM_pt(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            CreatePlCust2MDMBindingStub _stub = new CreatePlCust2MDMBindingStub(portAddress, this);
            _stub.setPortName(getCreatePlCust2MDM_ptWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCreatePlCust2MDM_ptEndpointAddress(java.lang.String address) {
        CreatePlCust2MDM_pt_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (CreatePlCust2MDM.class.isAssignableFrom(serviceEndpointInterface)) {
                CreatePlCust2MDMBindingStub _stub = new CreatePlCust2MDMBindingStub(new java.net.URL(CreatePlCust2MDM_pt_address), this);
                _stub.setPortName(getCreatePlCust2MDM_ptWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CreatePlCust2MDM_pt".equals(inputPortName)) {
            return getCreatePlCust2MDM_pt();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "createplcust2mdm_client_ep");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "CreatePlCust2MDM_pt"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CreatePlCust2MDM_pt".equals(portName)) {
            setCreatePlCust2MDM_ptEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
