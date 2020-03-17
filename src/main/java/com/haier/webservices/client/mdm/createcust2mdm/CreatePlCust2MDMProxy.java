package com.haier.webservices.client.mdm.createcust2mdm;

public class CreatePlCust2MDMProxy implements CreatePlCust2MDM {
  private String _endpoint = null;
  private CreatePlCust2MDM createPlCust2MDM = null;
  
  public CreatePlCust2MDMProxy() {
    _initCreatePlCust2MDMProxy();
  }
  
  public CreatePlCust2MDMProxy(String endpoint) {
    _endpoint = endpoint;
    _initCreatePlCust2MDMProxy();
  }
  
  private void _initCreatePlCust2MDMProxy() {
    try {
      createPlCust2MDM = (new Createplcust2Mdm_client_epLocator()).getCreatePlCust2MDM_pt();
      if (createPlCust2MDM != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)createPlCust2MDM)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)createPlCust2MDM)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (createPlCust2MDM != null)
      ((javax.xml.rpc.Stub)createPlCust2MDM)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public CreatePlCust2MDM getCreatePlCust2MDM() {
    if (createPlCust2MDM == null)
      _initCreatePlCust2MDMProxy();
    return createPlCust2MDM;
  }
  
  public void process(java.lang.String IN_CUSTOMERNUMBER, java.lang.String IN_PARTNERFLAGSP, java.lang.String IN_PARTNERFLAGSH, java.lang.String IN_PARTNERFLAGBP, java.lang.String IN_PARTNERFLAGPY, java.lang.String IN_CUSTOMERTITLE, java.lang.String IN_ACCOUNTGROUP, java.lang.String IN_CUSTOMERNAME, java.lang.String IN_SEARCHTERMS, java.lang.String IN_CUSTOMERCOUNTRY, java.lang.String IN_LANGUAGE, java.lang.String IN_REGION, java.lang.String IN_CITY, java.lang.String IN_STREET, java.lang.String IN_CONTACTPERSON, java.lang.String IN_PHONENUMBER1, java.lang.String IN_VATREGNO, java.lang.String IN_INDUSTRYCLASS, java.lang.String IN_TAXDOCTYPE, java.lang.String IN_LEGAL_PERSON, java.lang.String IN_LEGAL_TYPE, java.lang.String IN_TRAINSTATION, java.lang.String IN_TELEPHONE, java.lang.String IN_BANKCOUNTRY, java.lang.String IN_BANKKEY, java.lang.String IN_ACCOUNTHOLDER, java.lang.String IN_COMPANYCODE, java.lang.String IN_RECONACCOUNT, java.lang.String IN_SORTKEY, java.lang.String IN_CUSTOMER_SUMMARY_GROUP, java.lang.String IN_CURRENCY, java.lang.String IN_CUSTOMER_PRICE_PROCEDURE, java.lang.String IN_ACCT_ASSGMT_GROUP, java.lang.String IN_TAX_CLASSIFICATION, java.lang.String IN_SHIP_CONDITION, java.lang.String IN_POD_RELATION, java.lang.String IN_SALES_REGION, java.lang.String IN_FINANACOUNTNAME, java.lang.String IN_FINANORGN, java.lang.String IN_FINANORGNSHORT, java.lang.String IN_FINANACOUNTCODE, java.lang.String IN_FINANCURRENCY, java.lang.String IN_FINANTYPE, java.lang.String IN_FINANSTATE, java.lang.String IN_SYSNAME, java.lang.String IN_CREATED_BY, java.lang.String OPERATETYPE, java.lang.String UPDATE_BASE, java.lang.String UPDATE_BANK, java.lang.String UPDATE_EXTEND, java.lang.String UPDATE_SALE, java.lang.String UPDATE_COMP, java.lang.String UPDATE_FINANCE, java.lang.String IF_AFFIX, java.lang.String IN_AFFIX_IP, java.lang.String IN_AFFIX_USERNAME, java.lang.String IN_AFFIX_PASSWORD, java.lang.String IN_AFFIX_URL, java.lang.String IN_AFFIX_FILENAME, java.lang.String in_ADDRESS_PROVINCE, java.lang.String in_ADDRESS_CITY, java.lang.String in_ADDRESS_AREA, java.lang.String in_ADDRESS_TOWN, java.lang.String in_ADDRESS_VILLAGE, java.lang.String in_ADDRESS_ROAD, java.lang.String in_ADDRESS_HOUSE_NUMBER, java.lang.String in_ADDRESS_LONGITUDE, java.lang.String in_ADDRESS_LATITUDE, java.lang.String in_SPECIFIC_ADDRESS, java.lang.String IN_INNER_COMPANYCODE, javax.xml.rpc.holders.StringHolder OUT_CUSTOMERCODE, javax.xml.rpc.holders.StringHolder RETCODE, javax.xml.rpc.holders.StringHolder RETMSG) throws java.rmi.RemoteException{
    if (createPlCust2MDM == null)
      _initCreatePlCust2MDMProxy();
    createPlCust2MDM.process(IN_CUSTOMERNUMBER, IN_PARTNERFLAGSP, IN_PARTNERFLAGSH, IN_PARTNERFLAGBP, IN_PARTNERFLAGPY, IN_CUSTOMERTITLE, IN_ACCOUNTGROUP, IN_CUSTOMERNAME, IN_SEARCHTERMS, IN_CUSTOMERCOUNTRY, IN_LANGUAGE, IN_REGION, IN_CITY, IN_STREET, IN_CONTACTPERSON, IN_PHONENUMBER1, IN_VATREGNO, IN_INDUSTRYCLASS, IN_TAXDOCTYPE, IN_LEGAL_PERSON, IN_LEGAL_TYPE, IN_TRAINSTATION, IN_TELEPHONE, IN_BANKCOUNTRY, IN_BANKKEY, IN_ACCOUNTHOLDER, IN_COMPANYCODE, IN_RECONACCOUNT, IN_SORTKEY, IN_CUSTOMER_SUMMARY_GROUP, IN_CURRENCY, IN_CUSTOMER_PRICE_PROCEDURE, IN_ACCT_ASSGMT_GROUP, IN_TAX_CLASSIFICATION, IN_SHIP_CONDITION, IN_POD_RELATION, IN_SALES_REGION, IN_FINANACOUNTNAME, IN_FINANORGN, IN_FINANORGNSHORT, IN_FINANACOUNTCODE, IN_FINANCURRENCY, IN_FINANTYPE, IN_FINANSTATE, IN_SYSNAME, IN_CREATED_BY, OPERATETYPE, UPDATE_BASE, UPDATE_BANK, UPDATE_EXTEND, UPDATE_SALE, UPDATE_COMP, UPDATE_FINANCE, IF_AFFIX, IN_AFFIX_IP, IN_AFFIX_USERNAME, IN_AFFIX_PASSWORD, IN_AFFIX_URL, IN_AFFIX_FILENAME, in_ADDRESS_PROVINCE, in_ADDRESS_CITY, in_ADDRESS_AREA, in_ADDRESS_TOWN, in_ADDRESS_VILLAGE, in_ADDRESS_ROAD, in_ADDRESS_HOUSE_NUMBER, in_ADDRESS_LONGITUDE, in_ADDRESS_LATITUDE, in_SPECIFIC_ADDRESS, IN_INNER_COMPANYCODE, OUT_CUSTOMERCODE, RETCODE, RETMSG);
  }
  
  
}