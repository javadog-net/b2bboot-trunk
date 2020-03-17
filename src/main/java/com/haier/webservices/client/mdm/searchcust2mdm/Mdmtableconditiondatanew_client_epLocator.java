/**
 * Mdmtableconditiondatanew_client_epLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.searchcust2mdm;

public class Mdmtableconditiondatanew_client_epLocator extends org.apache.axis.client.Service implements Mdmtableconditiondatanew_client_ep {

    public Mdmtableconditiondatanew_client_epLocator() {
    }


    public Mdmtableconditiondatanew_client_epLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public Mdmtableconditiondatanew_client_epLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MDMTableConditionDataNew_pt
    private java.lang.String MDMTableConditionDataNew_pt_address = "http://bpel.mdm.haier.com:7778/soa-infra/services/interface/MDMTableConditionDataNew/mdmtableconditiondatanew_client_ep";

    public java.lang.String getMDMTableConditionDataNew_ptAddress() {
        return MDMTableConditionDataNew_pt_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MDMTableConditionDataNew_ptWSDDServiceName = "MDMTableConditionDataNew_pt";

    public java.lang.String getMDMTableConditionDataNew_ptWSDDServiceName() {
        return MDMTableConditionDataNew_ptWSDDServiceName;
    }

    public void setMDMTableConditionDataNew_ptWSDDServiceName(java.lang.String name) {
        MDMTableConditionDataNew_ptWSDDServiceName = name;
    }

    public MDMTableConditionDataNew getMDMTableConditionDataNew_pt() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MDMTableConditionDataNew_pt_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMDMTableConditionDataNew_pt(endpoint);
    }

    public MDMTableConditionDataNew getMDMTableConditionDataNew_pt(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            MDMTableConditionDataNewBindingStub _stub = new MDMTableConditionDataNewBindingStub(portAddress, this);
            _stub.setPortName(getMDMTableConditionDataNew_ptWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMDMTableConditionDataNew_ptEndpointAddress(java.lang.String address) {
        MDMTableConditionDataNew_pt_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (MDMTableConditionDataNew.class.isAssignableFrom(serviceEndpointInterface)) {
                MDMTableConditionDataNewBindingStub _stub = new MDMTableConditionDataNewBindingStub(new java.net.URL(MDMTableConditionDataNew_pt_address), this);
                _stub.setPortName(getMDMTableConditionDataNew_ptWSDDServiceName());
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
        if ("MDMTableConditionDataNew_pt".equals(inputPortName)) {
            return getMDMTableConditionDataNew_pt();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "mdmtableconditiondatanew_client_ep");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "MDMTableConditionDataNew_pt"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MDMTableConditionDataNew_pt".equals(portName)) {
            setMDMTableConditionDataNew_ptEndpointAddress(address);
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
