
package com.haier.webservices.client.hps.exception;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


 
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateBrownException_QNAME = new QName("http://api.webservice.hps.com/", "createBrownException");
    private final static QName _CreateBrownExceptionResponse_QNAME = new QName("http://api.webservice.hps.com/", "createBrownExceptionResponse");

     
    public ObjectFactory() {
    }

     
    public CreateBrownException createCreateBrownException() {
        return new CreateBrownException();
    }

     
    public CreateBrownExceptionResponse createCreateBrownExceptionResponse() {
        return new CreateBrownExceptionResponse();
    }

     
    public BrownExceptionSaveParam createBrownExceptionSaveParam() {
        return new BrownExceptionSaveParam();
    }

     
    public ExceptionProductSaveParam createExceptionProductSaveParam() {
        return new ExceptionProductSaveParam();
    }

     
    @XmlElementDecl(namespace = "http://api.webservice.hps.com/", name = "createBrownException")
    public JAXBElement<CreateBrownException> createCreateBrownException(CreateBrownException value) {
        return new JAXBElement<CreateBrownException>(_CreateBrownException_QNAME, CreateBrownException.class, null, value);
    }

     
    @XmlElementDecl(namespace = "http://api.webservice.hps.com/", name = "createBrownExceptionResponse")
    public JAXBElement<CreateBrownExceptionResponse> createCreateBrownExceptionResponse(CreateBrownExceptionResponse value) {
        return new JAXBElement<CreateBrownExceptionResponse>(_CreateBrownExceptionResponse_QNAME, CreateBrownExceptionResponse.class, null, value);
    }

}
