package com.haier.webservices.client.gvs;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：hdx
 * @Description:
 * @Date: Created in 16:29 2019/3/21
 * @Modified By
 */
public class Main {
    public static void main(String[] args) {
        //客户余额=KLIMK-（SKFOR+SSOBL+OEIKW+OLIKW+OFAKW）
        QueryCreditBalanceOfCustomers_Service ss = null;
        ss = new QueryCreditBalanceOfCustomers_Service();
        QueryCreditBalanceOfCustomers port=ss.getQueryCreditBalanceOfCustomersSOAP();
        List<OMSOUT> oos = new ArrayList<OMSOUT>();
        OMSOUT omsout = new OMSOUT();
        omsout.setKUNNR("8800046011");
        omsout.setKKBER("3200");
        oos.add(omsout) ;
        List<OMSIN> a = port.queryCreditBalanceOfCustomers(oos);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
