/**
 * Mdmgeneralbankinterface_client_epLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.generalbankinterface;

public class Mdmgeneralbankinterface_client_epLocator extends org.apache.axis.client.Service implements Mdmgeneralbankinterface_client_ep {

    public Mdmgeneralbankinterface_client_epLocator() {
    }


    public Mdmgeneralbankinterface_client_epLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Mdmgeneralbankinterface_client_epLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MDMGeneralBankInterface_pt
    private java.lang.String MDMGeneralBankInterface_pt_address = "http://10.135.16.46:10201/soa-infra/services/interface/MDMGeneralBankInterface/mdmgeneralbankinterface_client_ep";

    public java.lang.String getMDMGeneralBankInterface_ptAddress() {
        return MDMGeneralBankInterface_pt_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MDMGeneralBankInterface_ptWSDDServiceName = "MDMGeneralBankInterface_pt";

    public java.lang.String getMDMGeneralBankInterface_ptWSDDServiceName() {
        return MDMGeneralBankInterface_ptWSDDServiceName;
    }

    public void setMDMGeneralBankInterface_ptWSDDServiceName(java.lang.String name) {
        MDMGeneralBankInterface_ptWSDDServiceName = name;
    }

    public MDMGeneralBankInterface getMDMGeneralBankInterface_pt() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MDMGeneralBankInterface_pt_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMDMGeneralBankInterface_pt(endpoint);
    }

    public MDMGeneralBankInterface getMDMGeneralBankInterface_pt(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            MDMGeneralBankInterfaceBindingStub _stub = new MDMGeneralBankInterfaceBindingStub(portAddress, this);
            _stub.setPortName(getMDMGeneralBankInterface_ptWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMDMGeneralBankInterface_ptEndpointAddress(java.lang.String address) {
        MDMGeneralBankInterface_pt_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (MDMGeneralBankInterface.class.isAssignableFrom(serviceEndpointInterface)) {
                MDMGeneralBankInterfaceBindingStub _stub = new MDMGeneralBankInterfaceBindingStub(new java.net.URL(MDMGeneralBankInterface_pt_address), this);
                _stub.setPortName(getMDMGeneralBankInterface_ptWSDDServiceName());
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
        if ("MDMGeneralBankInterface_pt".equals(inputPortName)) {
            return getMDMGeneralBankInterface_pt();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface", "mdmgeneralbankinterface_client_ep");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface", "MDMGeneralBankInterface_pt"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MDMGeneralBankInterface_pt".equals(portName)) {
            setMDMGeneralBankInterface_ptEndpointAddress(address);
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
