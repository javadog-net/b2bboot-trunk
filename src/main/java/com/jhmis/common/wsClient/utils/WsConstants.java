package com.jhmis.common.wsClient.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 15:04 2018/7/22
 * @Modified By
 */
public class WsConstants {
    //企业购取销售样表信息****测试地址
    //public static final String T_BATCHSALETEMPLATEQUERYMDM2BTB_CLIENT_EP = "http://10.135.16.46:10201/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep";

    //企业购取销售样表信息****生产地址
    public static final String T_BATCHSALETEMPLATEQUERYMDM2BTB_CLIENT_EP = "http://bpel.mdm.haier.com:7778/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep?WSDL";

    //从MDM获取供应商信息接口地址****生产地址
    public static final String D_GETMDM_CUSTOMERSLIST = "http://hpm.haier.net/haiergc/Service/APPQD_WebService.asmx";

    //从MDM获取供应商信息接口地址****测试地址
    //public static final String D_GETMDM_CUSTOMERSLIST = "http://10.135.12.95/haiergc_test/Service/APPQD_WebService.asmx";

    //MDM接口生产相关参数
    public static final String MDM_NAMESPACE = "http://IP/haiergc/Service/";
    public static final String MDM_WSDLURL = "http://hpm.haier.net/haiergc/Service/APPQD_WebService.asmx?wsdl";
    public static final String MDM_SERVICENAME = "APPQD_WebService";
    public static final String MDM_PORTNAME = "APPQD_WebServiceSoap12";
    public static final String MDM_OPERATIONNAME = "getMDM_CustomersList";
    public static final String MDM_RESPONSENAME = "getMDM_CustomersListResponse";

/*    //企业销售样表测试相关参数
    public static final String BST_NAMESPACE = "http://xmlns.oracle.com/Interface/BatchSaleTemplateQueryMDM2BTB";
    public static final String BST_WSDLURL = "http://10.135.16.46:10201/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep?wsdl";
    public static final String BST_SERVICENAME = "batchsaletemplatequerymdm2btb_client_ep";
    public static final String BST_PORTNAME = "BatchSaleTemplateQueryMDM2BTB_pt";
    public static final String BST_OPERATIONNAME = "process";
    public static final String BST_RESPONSENAME = "processResponse";*/


    //企业销售样表生产相关参数
    public static final String BST_NAMESPACE = "http://xmlns.oracle.com/Interface/BatchSaleTemplateQueryMDM2BTB";
    public static final String BST_WSDLURL = "http://bpel.mdm.haier.com:7778/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep?WSDL";
    public static final String BST_SERVICENAME = "batchsaletemplatequerymdm2btb_client_ep";
    public static final String BST_PORTNAME = "BatchSaleTemplateQueryMDM2BTB_pt";
    public static final String BST_OPERATIONNAME = "process";
    public static final String BST_RESPONSENAME = "processResponse";


}
