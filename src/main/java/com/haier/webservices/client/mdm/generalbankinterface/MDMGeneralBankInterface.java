/**
 * MDMGeneralBankInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.haier.webservices.client.mdm.generalbankinterface;

import com.haier.webservices.client.mdm.generalbankinterface.holders.ProcessResponseBankTableHolder;

public interface MDMGeneralBankInterface extends java.rmi.Remote {
    public void process(java.lang.String bankBranchCode, java.lang.String bankName, java.lang.String region, java.lang.String cityStreetRoom, java.lang.String bankCategory, ProcessResponseBankTableHolder bankTable, javax.xml.rpc.holders.StringHolder retCode, javax.xml.rpc.holders.StringHolder retMst) throws java.rmi.RemoteException;
}
