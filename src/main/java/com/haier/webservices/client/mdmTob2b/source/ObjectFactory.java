
package com.haier.webservices.client.mdmTob2b.source;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.haier.webservices.client.mdmTob2b.source package. 
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

    private final static QName _ProcessINSYSNAME_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "IN_SYS_NAME");
    private final static QName _ProcessINMASTERTYPE_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "IN_MASTER_TYPE");
    private final static QName _ProcessINTABLENAME_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "IN_TABLE_NAME");
    private final static QName _ProcessINFIELDSVALUETABLE_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "IN_FIELDS_VALUE_TABLE");
    private final static QName _ProcessResponseOUTRESULT_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "OUT_RESULT");
    private final static QName _ProcessResponseOUTRETMSG_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "OUT_RETMSG");
    private final static QName _ProcessResponseOUTRETCODE_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "OUT_RETCODE");
    private final static QName _HAIERMDMFIELDSVALUETYPEFIELDNAME_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "FIELD_NAME");
    private final static QName _HAIERMDMFIELDSVALUETYPEFIELDVALUE_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "FIELD_VALUE");
    private final static QName _HAIERMDMFIELDSVALUETYPEFIELDQUERYTYPE_QNAME = new QName("http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", "FIELD_QUERY_TYPE");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.haier.webservices.client.mdmTob2b.source
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Process }
     * 
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link HAIERMDMFIELDSVALUETABLE }
     * 
     */
    public HAIERMDMFIELDSVALUETABLE createHAIERMDMFIELDSVALUETABLE() {
        return new HAIERMDMFIELDSVALUETABLE();
    }

    /**
     * Create an instance of {@link ProcessResponse }
     * 
     */
    public ProcessResponse createProcessResponse() {
        return new ProcessResponse();
    }

    /**
     * Create an instance of {@link HAIERMDMFIELDSVALUETYPE }
     * 
     */
    public HAIERMDMFIELDSVALUETYPE createHAIERMDMFIELDSVALUETYPE() {
        return new HAIERMDMFIELDSVALUETYPE();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "IN_SYS_NAME", scope = Process.class)
    public JAXBElement<String> createProcessINSYSNAME(String value) {
        return new JAXBElement<String>(_ProcessINSYSNAME_QNAME, String.class, Process.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "IN_MASTER_TYPE", scope = Process.class)
    public JAXBElement<String> createProcessINMASTERTYPE(String value) {
        return new JAXBElement<String>(_ProcessINMASTERTYPE_QNAME, String.class, Process.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "IN_TABLE_NAME", scope = Process.class)
    public JAXBElement<String> createProcessINTABLENAME(String value) {
        return new JAXBElement<String>(_ProcessINTABLENAME_QNAME, String.class, Process.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HAIERMDMFIELDSVALUETABLE }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "IN_FIELDS_VALUE_TABLE", scope = Process.class)
    public JAXBElement<HAIERMDMFIELDSVALUETABLE> createProcessINFIELDSVALUETABLE(HAIERMDMFIELDSVALUETABLE value) {
        return new JAXBElement<HAIERMDMFIELDSVALUETABLE>(_ProcessINFIELDSVALUETABLE_QNAME, HAIERMDMFIELDSVALUETABLE.class, Process.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "OUT_RESULT", scope = ProcessResponse.class)
    public JAXBElement<String> createProcessResponseOUTRESULT(String value) {
        return new JAXBElement<String>(_ProcessResponseOUTRESULT_QNAME, String.class, ProcessResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "OUT_RETMSG", scope = ProcessResponse.class)
    public JAXBElement<String> createProcessResponseOUTRETMSG(String value) {
        return new JAXBElement<String>(_ProcessResponseOUTRETMSG_QNAME, String.class, ProcessResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "OUT_RETCODE", scope = ProcessResponse.class)
    public JAXBElement<String> createProcessResponseOUTRETCODE(String value) {
        return new JAXBElement<String>(_ProcessResponseOUTRETCODE_QNAME, String.class, ProcessResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "FIELD_NAME", scope = HAIERMDMFIELDSVALUETYPE.class)
    public JAXBElement<String> createHAIERMDMFIELDSVALUETYPEFIELDNAME(String value) {
        return new JAXBElement<String>(_HAIERMDMFIELDSVALUETYPEFIELDNAME_QNAME, String.class, HAIERMDMFIELDSVALUETYPE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "FIELD_VALUE", scope = HAIERMDMFIELDSVALUETYPE.class)
    public JAXBElement<String> createHAIERMDMFIELDSVALUETYPEFIELDVALUE(String value) {
        return new JAXBElement<String>(_HAIERMDMFIELDSVALUETYPEFIELDVALUE_QNAME, String.class, HAIERMDMFIELDSVALUETYPE.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/Interface/MDMTableConditionDataNew/MDMTableConditionDataNew", name = "FIELD_QUERY_TYPE", scope = HAIERMDMFIELDSVALUETYPE.class)
    public JAXBElement<String> createHAIERMDMFIELDSVALUETYPEFIELDQUERYTYPE(String value) {
        return new JAXBElement<String>(_HAIERMDMFIELDSVALUETYPEFIELDQUERYTYPE_QNAME, String.class, HAIERMDMFIELDSVALUETYPE.class, value);
    }

}
