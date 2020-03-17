package com.jhmis.api.weixin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.jhmis.core.web.BaseController;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.api.weixin
 * @Author: hdx
 * @CreateTime: 2020-02-11 23:01
 * @Description: weiXinService
 */
@Service
public class WeiXinService extends BaseController {
    /**
     * 初始化JSSDK配置信息
     * @param shareUrl
     * @return
     * @throws Exception
     */

    public  String JSSDK_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public  String JSSDK_GETTICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

  /*  public  String APPID = "wxced76e150a7244f0";

    public  String APPSECRET = "wxced76e150a7244f0";*/

    public  String APPID = "wxced76e150a7244f0";

    public  String APPSECRET = "9331ab4e6395cb2cdc00c81550153b14";


    public Map initJSSDKConfigInfo(String shareUrl) throws Exception {
        logger.info("initJSSDKConfigInfo()的 shareUrl" + shareUrl);
        String accessToken = getJSSDKAccessToken();
        logger.info("initJSSDKConfigInfo()的 accessToken" + accessToken);
        String jsapiTicket = getJSSDKJsapiTicket(accessToken);
        logger.info("initJSSDKConfigInfo()的 jsapiTicket" + jsapiTicket);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        logger.info("initJSSDKConfigInfo()的 timestamp" + timestamp);
        String nonceStr = UUID.randomUUID().toString();
        logger.info("initJSSDKConfigInfo()的 nonceStr" + nonceStr);
        String signature = buildJSSDKSignature(jsapiTicket,timestamp,nonceStr,shareUrl);
        logger.info("initJSSDKConfigInfo()的 signature" + signature);
        Map<String,String> map = new HashMap<String,String>();
        map.put("shareUrl", shareUrl);
        map.put("jsapi_ticket", jsapiTicket);
        map.put("nonceStr", nonceStr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        map.put("appid", APPID);
        return map;
    }

    public String getJSSDKAccessToken() {
        String token = null;
        String url = JSSDK_ACCESSTOKEN.replaceAll("APPID",
                APPID).replaceAll("APPSECRET",
                APPSECRET);
        logger.info("getJSSDKAccessToken()的 url" + url);
        String json = postRequestForWeiXinService(url);
        logger.info("getJSSDKAccessToken()的 json" + json);
        Map map = jsonToMap(json);
        if (map != null) {
            token = (String) map.get("access_token");
            logger.info("getJSSDKAccessToken()的 token" + token);
        }
        return token;
    }


    public String getJSSDKJsapiTicket(String token) {
        logger.info("getJSSDKJsapiTicket--token"+ token );
        String url = JSSDK_GETTICKET.replaceAll("ACCESS_TOKEN", token);
        logger.info("getJSSDKJsapiTicket--url"+ url );
        String json = postRequestForWeiXinService(url);
        logger.info("getJSSDKJsapiTicket--json"+ json );
        Map map = jsonToMap(json);
        String jsapi_ticket = null;
        if (map != null) {
            jsapi_ticket = (String) map.get("ticket");
            logger.info("getJSSDKJsapiTicket--jsapi_ticket"+ jsapi_ticket );
        }
        return jsapi_ticket;
    }



    /**
     * 构建分享链接的签名。
     * @param ticket
     * @param nonceStr
     * @param url
     * @return
     * @throws Exception
     */
    public static String buildJSSDKSignature(String ticket,String timestamp,String nonceStr ,String url) throws Exception {
        String orderedString = "jsapi_ticket=" + ticket
                + "&noncestr=" + nonceStr + "&timestamp=" + timestamp
                + "&url=" + url;

        return sha1(orderedString);
    }
    /**
     * sha1 加密JSSDK微信配置参数获取签名。
     *
     * @return
     */
    public static String sha1(String orderedString) throws Exception {
        String ciphertext = null;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(orderedString.getBytes());
        ciphertext = byteToStr(digest);
        return ciphertext.toLowerCase();
    }
    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }


    public String mapToJson(Map map){
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }
    private Map jsonToMap(String json) {
        Gson gons = new Gson();
        Map map = gons.fromJson(json, new TypeToken<Map>(){}.getType());
        return map;
    }

    private String postRequestForWeiXinService(String getAccessTokenUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> postForEntity = restTemplate.postForEntity(getAccessTokenUrl, null, String.class);
        String json = postForEntity.getBody();
        logger.info("postRequestForWeiXinService--json"+ json );
        return json;
    }


    private String getRequestForWeiXinService(String getUserInfoUrl) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> postForEntity = restTemplate.getForEntity(getUserInfoUrl.toString(), String.class);
        String json = postForEntity.getBody();
        logger.info("getRequestForWeiXinService--json"+ json );
        return json;
    }
}
