/**
 * CreatePlCust2MDMBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.createcust2mdm;

public class CreatePlCust2MDMBindingStub extends org.apache.axis.client.Stub implements CreatePlCust2MDM {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("process");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CUSTOMERNUMBER"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_PARTNERFLAGSP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_PARTNERFLAGSH"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_PARTNERFLAGBP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_PARTNERFLAGPY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CUSTOMERTITLE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_ACCOUNTGROUP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CUSTOMERNAME"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_SEARCHTERMS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CUSTOMERCOUNTRY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_LANGUAGE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_REGION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CITY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_STREET"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CONTACTPERSON"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_PHONENUMBER1"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_VATREGNO"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_INDUSTRYCLASS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_TAXDOCTYPE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_LEGAL_PERSON"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_LEGAL_TYPE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_TRAINSTATION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_TELEPHONE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_BANKCOUNTRY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_BANKKEY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_ACCOUNTHOLDER"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_COMPANYCODE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_RECONACCOUNT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_SORTKEY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CUSTOMER_SUMMARY_GROUP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CURRENCY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CUSTOMER_PRICE_PROCEDURE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_ACCT_ASSGMT_GROUP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_TAX_CLASSIFICATION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_SHIP_CONDITION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_POD_RELATION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_SALES_REGION"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANACOUNTNAME"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANORGN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANORGNSHORT"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANACOUNTCODE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANCURRENCY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANTYPE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_FINANSTATE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_SYSNAME"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_CREATED_BY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "OPERATETYPE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "UPDATE_BASE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "UPDATE_BANK"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "UPDATE_EXTEND"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "UPDATE_SALE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "UPDATE_COMP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "UPDATE_FINANCE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IF_AFFIX"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_AFFIX_IP"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_AFFIX_USERNAME"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_AFFIX_PASSWORD"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_AFFIX_URL"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_AFFIX_FILENAME"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_PROVINCE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_CITY"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_AREA"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_TOWN"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_VILLAGE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_ROAD"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_HOUSE_NUMBER"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_LONGITUDE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_ADDRESS_LATITUDE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "in_SPECIFIC_ADDRESS"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "IN_INNER_COMPANYCODE"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "OUT_CUSTOMERCODE"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "RETCODE"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "RETMSG"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

    }

    public CreatePlCust2MDMBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public CreatePlCust2MDMBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public CreatePlCust2MDMBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public void process(java.lang.String IN_CUSTOMERNUMBER, java.lang.String IN_PARTNERFLAGSP, java.lang.String IN_PARTNERFLAGSH, java.lang.String IN_PARTNERFLAGBP, java.lang.String IN_PARTNERFLAGPY, java.lang.String IN_CUSTOMERTITLE, java.lang.String IN_ACCOUNTGROUP, java.lang.String IN_CUSTOMERNAME, java.lang.String IN_SEARCHTERMS, java.lang.String IN_CUSTOMERCOUNTRY, java.lang.String IN_LANGUAGE, java.lang.String IN_REGION, java.lang.String IN_CITY, java.lang.String IN_STREET, java.lang.String IN_CONTACTPERSON, java.lang.String IN_PHONENUMBER1, java.lang.String IN_VATREGNO, java.lang.String IN_INDUSTRYCLASS, java.lang.String IN_TAXDOCTYPE, java.lang.String IN_LEGAL_PERSON, java.lang.String IN_LEGAL_TYPE, java.lang.String IN_TRAINSTATION, java.lang.String IN_TELEPHONE, java.lang.String IN_BANKCOUNTRY, java.lang.String IN_BANKKEY, java.lang.String IN_ACCOUNTHOLDER, java.lang.String IN_COMPANYCODE, java.lang.String IN_RECONACCOUNT, java.lang.String IN_SORTKEY, java.lang.String IN_CUSTOMER_SUMMARY_GROUP, java.lang.String IN_CURRENCY, java.lang.String IN_CUSTOMER_PRICE_PROCEDURE, java.lang.String IN_ACCT_ASSGMT_GROUP, java.lang.String IN_TAX_CLASSIFICATION, java.lang.String IN_SHIP_CONDITION, java.lang.String IN_POD_RELATION, java.lang.String IN_SALES_REGION, java.lang.String IN_FINANACOUNTNAME, java.lang.String IN_FINANORGN, java.lang.String IN_FINANORGNSHORT, java.lang.String IN_FINANACOUNTCODE, java.lang.String IN_FINANCURRENCY, java.lang.String IN_FINANTYPE, java.lang.String IN_FINANSTATE, java.lang.String IN_SYSNAME, java.lang.String IN_CREATED_BY, java.lang.String OPERATETYPE, java.lang.String UPDATE_BASE, java.lang.String UPDATE_BANK, java.lang.String UPDATE_EXTEND, java.lang.String UPDATE_SALE, java.lang.String UPDATE_COMP, java.lang.String UPDATE_FINANCE, java.lang.String IF_AFFIX, java.lang.String IN_AFFIX_IP, java.lang.String IN_AFFIX_USERNAME, java.lang.String IN_AFFIX_PASSWORD, java.lang.String IN_AFFIX_URL, java.lang.String IN_AFFIX_FILENAME, java.lang.String in_ADDRESS_PROVINCE, java.lang.String in_ADDRESS_CITY, java.lang.String in_ADDRESS_AREA, java.lang.String in_ADDRESS_TOWN, java.lang.String in_ADDRESS_VILLAGE, java.lang.String in_ADDRESS_ROAD, java.lang.String in_ADDRESS_HOUSE_NUMBER, java.lang.String in_ADDRESS_LONGITUDE, java.lang.String in_ADDRESS_LATITUDE, java.lang.String in_SPECIFIC_ADDRESS, java.lang.String IN_INNER_COMPANYCODE, javax.xml.rpc.holders.StringHolder OUT_CUSTOMERCODE, javax.xml.rpc.holders.StringHolder RETCODE, javax.xml.rpc.holders.StringHolder RETMSG) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("process");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "process"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {IN_CUSTOMERNUMBER, IN_PARTNERFLAGSP, IN_PARTNERFLAGSH, IN_PARTNERFLAGBP, IN_PARTNERFLAGPY, IN_CUSTOMERTITLE, IN_ACCOUNTGROUP, IN_CUSTOMERNAME, IN_SEARCHTERMS, IN_CUSTOMERCOUNTRY, IN_LANGUAGE, IN_REGION, IN_CITY, IN_STREET, IN_CONTACTPERSON, IN_PHONENUMBER1, IN_VATREGNO, IN_INDUSTRYCLASS, IN_TAXDOCTYPE, IN_LEGAL_PERSON, IN_LEGAL_TYPE, IN_TRAINSTATION, IN_TELEPHONE, IN_BANKCOUNTRY, IN_BANKKEY, IN_ACCOUNTHOLDER, IN_COMPANYCODE, IN_RECONACCOUNT, IN_SORTKEY, IN_CUSTOMER_SUMMARY_GROUP, IN_CURRENCY, IN_CUSTOMER_PRICE_PROCEDURE, IN_ACCT_ASSGMT_GROUP, IN_TAX_CLASSIFICATION, IN_SHIP_CONDITION, IN_POD_RELATION, IN_SALES_REGION, IN_FINANACOUNTNAME, IN_FINANORGN, IN_FINANORGNSHORT, IN_FINANACOUNTCODE, IN_FINANCURRENCY, IN_FINANTYPE, IN_FINANSTATE, IN_SYSNAME, IN_CREATED_BY, OPERATETYPE, UPDATE_BASE, UPDATE_BANK, UPDATE_EXTEND, UPDATE_SALE, UPDATE_COMP, UPDATE_FINANCE, IF_AFFIX, IN_AFFIX_IP, IN_AFFIX_USERNAME, IN_AFFIX_PASSWORD, IN_AFFIX_URL, IN_AFFIX_FILENAME, in_ADDRESS_PROVINCE, in_ADDRESS_CITY, in_ADDRESS_AREA, in_ADDRESS_TOWN, in_ADDRESS_VILLAGE, in_ADDRESS_ROAD, in_ADDRESS_HOUSE_NUMBER, in_ADDRESS_LONGITUDE, in_ADDRESS_LATITUDE, in_SPECIFIC_ADDRESS, IN_INNER_COMPANYCODE});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                OUT_CUSTOMERCODE.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "OUT_CUSTOMERCODE"));
            } catch (java.lang.Exception _exception) {
                OUT_CUSTOMERCODE.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "OUT_CUSTOMERCODE")), java.lang.String.class);
            }
            try {
                RETCODE.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "RETCODE"));
            } catch (java.lang.Exception _exception) {
                RETCODE.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "RETCODE")), java.lang.String.class);
            }
            try {
                RETMSG.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "RETMSG"));
            } catch (java.lang.Exception _exception) {
                RETMSG.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("http://xmlns.oracle.com/interface_new/CreatePlCust2MDM/CreatePlCust2MDM", "RETMSG")), java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
