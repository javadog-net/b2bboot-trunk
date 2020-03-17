
package com.haier.mdm.yhcx;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.microfar.bank package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.microfar.bank
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ProcessResponse }
     * 
     */
    public ProcessResponse createProcessResponse() {
        return new ProcessResponse();
    }

    /**
     * Create an instance of {@link ProcessResponse.BankTable }
     * 
     */
    public ProcessResponse.BankTable createProcessResponseBankTable() {
        return new ProcessResponse.BankTable();
    }

    /**
     * Create an instance of {@link Process }
     * 
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link ProcessResponse.BankTable.BankItem }
     * 
     */
    public ProcessResponse.BankTable.BankItem createProcessResponseBankTableBankItem() {
        return new ProcessResponse.BankTable.BankItem();
    }

}
