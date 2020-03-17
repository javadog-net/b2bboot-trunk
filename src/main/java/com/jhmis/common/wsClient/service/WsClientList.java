package com.jhmis.common.wsClient.service;

import com.jhmis.common.utils.WebServiceClient;
import com.jhmis.common.wsClient.entity.BatchsaleTemplate;
import com.jhmis.common.wsClient.entity.MDMCustomer;
import com.jhmis.common.wsClient.utils.WsConstants;
import com.jhmis.common.wsClient.utils.WsUtils;
import org.dom4j.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WsClientList {

    public static List<MDMCustomer> getMDMCustomersList(String custCode,String custName){
        List<MDMCustomer> list = new ArrayList<>();
        WebServiceClient ws = new WebServiceClient(WsConstants.MDM_NAMESPACE,
                WsConstants.MDM_WSDLURL,
                WsConstants.MDM_SERVICENAME, WsConstants.MDM_PORTNAME,
                WsConstants.MDM_OPERATIONNAME, WsConstants.MDM_RESPONSENAME);
        HashMap<String, String> inMsg = new HashMap<String, String>();
        inMsg.put("custCode", custCode);
        inMsg.put("custName", custName);
        try {
            Document ret = ws.sendMessage(inMsg);
            //获取MDMCustomer 列表
            list = WsUtils.returnMDMCustomer(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static BatchsaleTemplate getBatchsaleTemplate(String materialCode){
        BatchsaleTemplate batchsaleTemplate = new BatchsaleTemplate();
        WebServiceClient ws = new WebServiceClient(WsConstants.BST_NAMESPACE,
                WsConstants.BST_WSDLURL,
                WsConstants.BST_SERVICENAME, WsConstants.BST_PORTNAME,
                WsConstants.BST_OPERATIONNAME, WsConstants.BST_RESPONSENAME);
        ws.setProtocol(WebServiceClient.SOAP_1_1_PROTOCOL);
        HashMap<String, String> inMsg = new HashMap<String, String>();
        inMsg.put("materialCode", materialCode);
        try {
            Document ret = ws.sendMessage(inMsg);
            //获取MDMCustomer 列表
            batchsaleTemplate = WsUtils.returnBatchsaleTemplate(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return batchsaleTemplate;
    }


    public static void getGvs(){
        BatchsaleTemplate batchsaleTemplate = new BatchsaleTemplate();
        WebServiceClient ws = new WebServiceClient("http://www.example.org/QueryCreditBalanceOfCustomers/",
                "http://10.135.27.4:7001/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=OMS_INT_HGVS_11",
                "QueryCreditBalanceOfCustomers", "QueryCreditBalanceOfCustomersSOAP",
                "QueryCreditBalanceOfCustomers", "QueryCreditBalanceOfCustomersResponse");
        ws.setProtocol(WebServiceClient.SOAP_1_1_PROTOCOL);
        HashMap<String, String> inMsg = new HashMap<String, String>();
        inMsg.put("KUNNR", "C200005495");
        inMsg.put("KKBER", "7210");

        try {
            Document ret = ws.sendMessage(inMsg);
            //获取MDMCustomer 列表
            batchsaleTemplate = WsUtils.returnBatchsaleTemplate(ret);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getGvs();
    }

}
