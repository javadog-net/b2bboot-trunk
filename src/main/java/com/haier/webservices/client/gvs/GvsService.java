package com.haier.webservices.client.gvs;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：hdx
 * @Description: GVS余额查询接口
 * @Date: Created in 16:52 2019/3/29
 * @Modified By
 */
public class GvsService {
    //测试地址
    //private static final String GVSSERVICEURL = "http://10.138.40.168:8001/EAI/service/GVS/Customer/QueryCreditBalanceOfCustomers/QueryCreditBalanceOfCustomers?wsdl";
    //生产地址
    private static final String GVSSERVICEURL = "http://10.135.27.4:7001/EAI/service/GVS/Customer/QueryCreditBalanceOfCustomers/QueryCreditBalanceOfCustomers?wsdl";
    public static List<OMSIN> query(String KUNNR, String KKBER){
        //客户余额=KLIMK-（SKFOR+SSOBL+OEIKW+OLIKW+OFAKW）
        QueryCreditBalanceOfCustomers_Service ss = null;
        ss = new QueryCreditBalanceOfCustomers_Service();
        QueryCreditBalanceOfCustomers port=ss.getQueryCreditBalanceOfCustomersSOAP();
        List<OMSOUT> oos = new ArrayList<OMSOUT>();
        OMSOUT omsout = new OMSOUT();
        omsout.setKUNNR(KUNNR);
        omsout.setKKBER(KKBER);
        oos.add(omsout) ;
        List<OMSIN> listOMSIN = port.queryCreditBalanceOfCustomers(oos);
        return listOMSIN;
    }
}
