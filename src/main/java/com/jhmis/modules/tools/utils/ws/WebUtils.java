package com.jhmis.modules.tools.utils.ws;

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
public class WebUtils {
    //企业购取销售样表信息****测试地址
    public static final String T_BATCHSALETEMPLATEQUERYMDM2BTB_CLIENT_EP = "http://10.135.16.46:10201/soa-infra/services/interface/BatchSaleTemplateQueryMDM2BTB/batchsaletemplatequerymdm2btb_client_ep";

    //从MDM获取供应商信息接口地址****生产地址
    public static final String D_GETMDM_CUSTOMERSLIST = "http://hpm.haier.net/haiergc/Service/APPQD_WebService.asmx";

    //从MDM获取供应商信息接口地址****测试地址
    public static final String T_GETMDM_CUSTOMERSLIST = "http://10.135.12.95/haiergc_test/Service/APPQD_WebService.asmx";

    //获取soap接口返回值
    public static String returnWsResult(String url,String soap){
        URL wsUrl = null;
        HttpURLConnection conn = null;
        InputStream is = null;
        String s = "";
        try {
            wsUrl = new URL(url);
            conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(soap.getBytes());
            is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while((len = is.read(b)) != -1){
                String ss = new String(b,0,len,"UTF-8");
                s += ss;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
