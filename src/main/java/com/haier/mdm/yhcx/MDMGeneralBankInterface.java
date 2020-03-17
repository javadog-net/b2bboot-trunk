package com.haier.mdm.yhcx;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2019-07-26T13:38:46.082+08:00
 * Generated source version: 3.3.2
 *
 */
@WebService(targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface", name = "MDMGeneralBankInterface")
@XmlSeeAlso({ObjectFactory.class})
public interface MDMGeneralBankInterface {

    @WebMethod(action = "process")
    @RequestWrapper(localName = "process", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface", className = "com.microfar.bank.Process")
    @ResponseWrapper(localName = "processResponse", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface", className = "com.microfar.bank.ProcessResponse")
    public void process(

            @WebParam(name = "bankBranchCode", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    String bankBranchCode,
            @WebParam(name = "bankName", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    String bankName,
            @WebParam(name = "region", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    String region,
            @WebParam(name = "cityStreetRoom", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    String cityStreetRoom,
            @WebParam(name = "bankCategory", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    String bankCategory,
            @WebParam(mode = WebParam.Mode.OUT, name = "bankTable", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    javax.xml.ws.Holder<ProcessResponse.BankTable> bankTable,
            @WebParam(mode = WebParam.Mode.OUT, name = "retCode", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    javax.xml.ws.Holder<String> retCode,
            @WebParam(mode = WebParam.Mode.OUT, name = "retMst", targetNamespace = "http://xmlns.oracle.com/Interface/MDMGeneralBankInterface/MDMGeneralBankInterface")
                    javax.xml.ws.Holder<String> retMst
    );
}
