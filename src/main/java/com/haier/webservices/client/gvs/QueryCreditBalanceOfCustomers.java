package com.haier.webservices.client.gvs;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.0
 * 2019-04-03T10:50:12.333+08:00
 * Generated source version: 3.2.0
 * 
 */
@WebService(targetNamespace = "http://www.example.org/QueryCreditBalanceOfCustomers/", name = "QueryCreditBalanceOfCustomers")
@XmlSeeAlso({ObjectFactory.class})
public interface QueryCreditBalanceOfCustomers {

    @WebMethod(operationName = "QueryCreditBalanceOfCustomers", action = "http://www.example.org/QueryCreditBalanceOfCustomers/QueryCreditBalanceOfCustomers")
    @RequestWrapper(localName = "QueryCreditBalanceOfCustomers", targetNamespace = "http://www.example.org/QueryCreditBalanceOfCustomers/", className = "com.rrs.dispenser.report.wsdl.fxst.QueryCreditBalanceOfCustomers.QueryCreditBalanceOfCustomers_Type")
    @ResponseWrapper(localName = "QueryCreditBalanceOfCustomersResponse", targetNamespace = "http://www.example.org/QueryCreditBalanceOfCustomers/", className = "com.rrs.dispenser.report.wsdl.fxst.QueryCreditBalanceOfCustomers.QueryCreditBalanceOfCustomersResponse")
    @WebResult(name = "out", targetNamespace = "")
    public java.util.List<OMSIN> queryCreditBalanceOfCustomers(
            @WebParam(name = "OMS_OUT", targetNamespace = "")
                    java.util.List<OMSOUT> omsOUT
    );
}