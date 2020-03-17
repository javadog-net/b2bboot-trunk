package com.haier.webservices.client.mdm.generalbankinterface;

import com.haier.webservices.client.mdm.generalbankinterface.holders.ProcessResponseBankTableHolder;

public class MDMGeneralBankInterfaceProxy implements MDMGeneralBankInterface {
  private String _endpoint = null;
  private MDMGeneralBankInterface mDMGeneralBankInterface = null;
  
  public MDMGeneralBankInterfaceProxy() {
    _initMDMGeneralBankInterfaceProxy();
  }
  
  public MDMGeneralBankInterfaceProxy(String endpoint) {
    _endpoint = endpoint;
    _initMDMGeneralBankInterfaceProxy();
  }
  
  private void _initMDMGeneralBankInterfaceProxy() {
    try {
      mDMGeneralBankInterface = (new Mdmgeneralbankinterface_client_epLocator()).getMDMGeneralBankInterface_pt();
      if (mDMGeneralBankInterface != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mDMGeneralBankInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mDMGeneralBankInterface)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mDMGeneralBankInterface != null)
      ((javax.xml.rpc.Stub)mDMGeneralBankInterface)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public MDMGeneralBankInterface getMDMGeneralBankInterface() {
    if (mDMGeneralBankInterface == null)
      _initMDMGeneralBankInterfaceProxy();
    return mDMGeneralBankInterface;
  }
  
  public void process(java.lang.String bankBranchCode, java.lang.String bankName, java.lang.String region, java.lang.String cityStreetRoom, java.lang.String bankCategory, ProcessResponseBankTableHolder bankTable, javax.xml.rpc.holders.StringHolder retCode, javax.xml.rpc.holders.StringHolder retMst) throws java.rmi.RemoteException{
    if (mDMGeneralBankInterface == null)
      _initMDMGeneralBankInterfaceProxy();
    mDMGeneralBankInterface.process(bankBranchCode, bankName, region, cityStreetRoom, bankCategory, bankTable, retCode, retMst);
  }
  
  
}