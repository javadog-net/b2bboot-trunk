
package com.haier.webservices.client.hps.trans;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.webservices.client.hps.trans package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TransCode_QNAME = new QName("http://hps.server.webservices.haier.com/", "transCode");
    private final static QName _TransCodeResponse_QNAME = new QName("http://hps.server.webservices.haier.com/", "transCodeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.webservices.client.hps.trans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AjaxJson }
     * 
     */
    public AjaxJson createAjaxJson() {
        return new AjaxJson();
    }

    /**
     * Create an instance of {@link AjaxJson.Body }
     * 
     */
    public AjaxJson.Body createAjaxJsonBody() {
        return new AjaxJson.Body();
    }

    /**
     * Create an instance of {@link TransCode }
     * 
     */
    public TransCode createTransCode() {
        return new TransCode();
    }

    /**
     * Create an instance of {@link TransCodeResponse }
     * 
     */
    public TransCodeResponse createTransCodeResponse() {
        return new TransCodeResponse();
    }

    /**
     * Create an instance of {@link AjaxJson.Body.Entry }
     * 
     */
    public AjaxJson.Body.Entry createAjaxJsonBodyEntry() {
        return new AjaxJson.Body.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hps.server.webservices.haier.com/", name = "transCode")
    public JAXBElement<TransCode> createTransCode(TransCode value) {
        return new JAXBElement<TransCode>(_TransCode_QNAME, TransCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hps.server.webservices.haier.com/", name = "transCodeResponse")
    public JAXBElement<TransCodeResponse> createTransCodeResponse(TransCodeResponse value) {
        return new JAXBElement<TransCodeResponse>(_TransCodeResponse_QNAME, TransCodeResponse.class, null, value);
    }

}
