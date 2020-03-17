package com.jhmis.modules.tools.utils.ws;

/**
 * @Author：hdx
 * @Description: 从外部接口获取信息
 * @Date: Created in 15:24 2018/7/22
 * @Modified By
 */
public class External {
    /**
     * @description  获取销售样表信息
     * @method
     * @param
     * @return
     * @date:  15:34:09
     * @author:hdx
     */
    public static String getBatchSaleTemplate(String code){
        /*获取销售样表url*/
        String url = WebUtils.T_BATCHSALETEMPLATEQUERYMDM2BTB_CLIENT_EP;
        String soap = ReturnSoapStr.getBatchsaletemplateSoap(code);
        String res = WebUtils.returnWsResult(url,soap);
        return res;
    }

    /**
     * @description  获取MDM供应商信息
     * @method
     * @param
     * @return
     * @date:  15:34:09
     * @author:hdx
     */
    public static String getMDMCustomersList(String custCode,String custName){
        /*获取销售样表url*/
        String url = WebUtils.D_GETMDM_CUSTOMERSLIST;
        String soap = ReturnSoapStr.getMDMCustomersList(custCode,custName);
        String res = WebUtils.returnWsResult(url,soap);
        return res;
    }



}
