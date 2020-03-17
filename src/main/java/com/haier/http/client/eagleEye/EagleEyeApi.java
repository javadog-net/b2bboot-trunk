package com.haier.http.client.eagleEye;

import com.haier.http.client.utils.CharsetHandler;
import com.haier.http.client.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.haier.http.client.utils.HttpUtil.closeHttpGet;

/**
 * //调取鹰眼接口
 * @param
 * @return
 */
public class EagleEyeApi {

    //鹰眼接口相关密钥
    public static final String API_GATEWAY_AUTH_URL = "http://apigw.haier.net/dtsqxbapi/APIService/v2/enterprise/searchListPaging?keyword=KEYWORD&skip=SKIP";
    public static final String API_GATEWAY_AUTH_APP_ID_LABEL = "api_gateway_auth_app_id";
    public static final String API_GATEWAY_AUTH_APP_CIPHERKEY_LABEL = "api_gateway_auth_app_password";
    public static final String API_GATEWAY_AUTH_APP_ID = "1a0e65d2-e02e-48d1-aa99-fbd3a4472dee";
    public static final String API_GATEWAY_AUTH_APP_CIPHERKEY = "3cdeD5ScdsVc";

    public static void main(String[] args) {
        eagleEyeApi("海尔","1");
    }
    public static Map<String,Object> eagleEyeApi(String companyName, String skip){
        Map<String,Object> map = new HashMap<>();
        //调用成功与否标识
        Boolean flag = false;
        //结果返回
        String result = "";
        //提示信息
        String msg = "";
        //初始化httpget参数
        HttpGet httpget=null;
        //httpClient回调数据
        String content="";
        //验证信息
        if(StringUtils.isEmpty(companyName) && StringUtils.isEmpty(skip)){
            map.put("flag",flag);
            map.put("result","");
            map.put("msg","参数不能为空");
            return map;
        }
        //鹰眼接口路劲
        String url = EagleEyeApi.API_GATEWAY_AUTH_URL;
        //拼接url
        url=url.replace("KEYWORD", companyName);
        url=url.replace("SKIP", skip);
        try {
            try {
                httpget = new HttpGet(new URI(url));
                httpget.setHeader(EagleEyeApi.API_GATEWAY_AUTH_APP_ID_LABEL,EagleEyeApi.API_GATEWAY_AUTH_APP_ID);
                httpget.setHeader(EagleEyeApi.API_GATEWAY_AUTH_APP_CIPHERKEY_LABEL,EagleEyeApi.API_GATEWAY_AUTH_APP_CIPHERKEY);
                result = HttpUtil.getHttpClient().execute(httpget, new CharsetHandler("utf-8"));
                map.put("flag",true);
                map.put("result",result);
                map.put("msg","接口调用成功");
            } catch (Exception e) {
                map.put("flag",false);
                map.put("result",result);
                map.put("msg","接口调用失败，原因=" + e.getMessage());
                e.printStackTrace();
                return map;
            }
        }finally{
            closeHttpGet(httpget);
        }
        return map;
    }
}
