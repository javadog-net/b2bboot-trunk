
package com.haier.webservices.client.gvs;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rrs.dispenser.report.wsdl.fxst.QueryCreditBalanceOfCustomers package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rrs.dispenser.report.wsdl.fxst.QueryCreditBalanceOfCustomers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryCreditBalanceOfCustomers_Type }
     * 
     */
    public QueryCreditBalanceOfCustomers_Type createQueryCreditBalanceOfCustomers_Type() {
        return new QueryCreditBalanceOfCustomers_Type();
    }

    /**
     * Create an instance of {@link OMSOUT }
     *
     */
    public OMSOUT createOMSOUT() {
        return new OMSOUT();
    }

    /**
     * Create an instance of {@link QueryCreditBalanceOfCustomersResponse }
     *
     */
    public QueryCreditBalanceOfCustomersResponse createQueryCreditBalanceOfCustomersResponse() {
        return new QueryCreditBalanceOfCustomersResponse();
    }

    /**
     * Create an instance of {@link OMSIN }
     * 
     */
    public OMSIN createOMSIN() {
        return new OMSIN();
    }

}
