package com.haier.webservices.client.mdm.searchcust2mdm;

public class MDMTableConditionDataNewProxy implements MDMTableConditionDataNew {
  private String _endpoint = null;
  private MDMTableConditionDataNew mDMTableConditionDataNew = null;
  
  public MDMTableConditionDataNewProxy() {
    _initMDMTableConditionDataNewProxy();
  }
  
  public MDMTableConditionDataNewProxy(String endpoint) {
    _endpoint = endpoint;
    _initMDMTableConditionDataNewProxy();
  }
  
  private void _initMDMTableConditionDataNewProxy() {
    try {
      mDMTableConditionDataNew = (new Mdmtableconditiondatanew_client_epLocator()).getMDMTableConditionDataNew_pt();
      if (mDMTableConditionDataNew != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mDMTableConditionDataNew)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mDMTableConditionDataNew)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mDMTableConditionDataNew != null)
      ((javax.xml.rpc.Stub)mDMTableConditionDataNew)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public MDMTableConditionDataNew getMDMTableConditionDataNew() {
    if (mDMTableConditionDataNew == null)
      _initMDMTableConditionDataNewProxy();
    return mDMTableConditionDataNew;
  }
  
  public void process(java.lang.String IN_SYS_NAME, java.lang.String IN_MASTER_TYPE, java.lang.String IN_TABLE_NAME, HAIERMDMFIELDS_VALUE_TYPE[] IN_FIELDS_VALUE_TABLE, javax.xml.rpc.holders.StringHolder OUT_RESULT, javax.xml.rpc.holders.StringHolder OUT_RETMSG, javax.xml.rpc.holders.StringHolder OUT_RETCODE) throws java.rmi.RemoteException{
    if (mDMTableConditionDataNew == null)
      _initMDMTableConditionDataNewProxy();
    mDMTableConditionDataNew.process(IN_SYS_NAME, IN_MASTER_TYPE, IN_TABLE_NAME, IN_FIELDS_VALUE_TABLE, OUT_RESULT, OUT_RETMSG, OUT_RETCODE);
  }
  
  
}