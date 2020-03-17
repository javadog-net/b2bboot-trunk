package com.jhmis.modules.tools.utils.ws;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 15:53 2018/7/22
 * @Modified By
 */
public class ReturnSoapStr {
    public static String getBatchsaletemplateSoap(String materialCode){
        String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:bat=\"http://xmlns.oracle.com/Interface/BatchSaleTemplateQueryMDM2BTB\">" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <bat:process>\n" +
                "         <bat:materialCode>"+ materialCode +"</bat:materialCode>\n" +
                "      </bat:process>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soap;
    }
    public static String getMDMCustomersList(String custCode, String custName){
        String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://IP/haiergc/Service/\">" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "     <ser:getMDM_CustomersList>\n" +
                "         <ser:custCode>"+custCode+"</ser:custCode>\n" +
                "         <ser:custName>"+custName+"</ser:custName>\n" +
                "     </ser:getMDM_CustomersList>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return soap;
    }



}
