/**
 * MDMTableConditionDataNew.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.searchcust2mdm;

public interface MDMTableConditionDataNew extends java.rmi.Remote {
    public void process(java.lang.String IN_SYS_NAME, java.lang.String IN_MASTER_TYPE, java.lang.String IN_TABLE_NAME, HAIERMDMFIELDS_VALUE_TYPE[] IN_FIELDS_VALUE_TABLE, javax.xml.rpc.holders.StringHolder OUT_RESULT, javax.xml.rpc.holders.StringHolder OUT_RETMSG, javax.xml.rpc.holders.StringHolder OUT_RETCODE) throws java.rmi.RemoteException;
}
