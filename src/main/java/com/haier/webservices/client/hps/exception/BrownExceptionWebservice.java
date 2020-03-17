package com.haier.webservices.client.hps.exception;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

 
@WebService(targetNamespace = "http://api.webservice.hps.com/", name = "BrownExceptionWebservice")
@XmlSeeAlso({ObjectFactory.class})
public interface BrownExceptionWebservice {

    @WebMethod
    @RequestWrapper(localName = "createBrownException", targetNamespace = "http://api.webservice.hps.com/", className = "com.haier.webservices.client.hps.exception.CreateBrownException")
    @ResponseWrapper(localName = "createBrownExceptionResponse", targetNamespace = "http://api.webservice.hps.com/", className = "com.haier.webservices.client.hps.exception.CreateBrownExceptionResponse")
    @WebResult(name = "return", targetNamespace = "")
    public java.lang.String createBrownException(
        @WebParam(name = "arg0", targetNamespace = "")
        com.haier.webservices.client.hps.exception.BrownExceptionSaveParam arg0
    );
}
