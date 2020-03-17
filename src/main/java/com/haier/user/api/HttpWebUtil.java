package com.haier.user.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.haier.user.api.dto.ExecuteResult;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * 调用海尔用户中心接口，取得返回信息
 */
public class HttpWebUtil {
    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(HttpWebUtil.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static CloseableHttpClient defClient;
    //连接和请求超时时间默认30秒，以毫秒为单位
    private static final int MAX_CONNECT_TIMEOUT = 30000;
    private static final int MAX_SOCKET_TIMEOUT = 30000;
    private static final int MAX_CONNECT_REQUEST_TIMEOUT = 30000;
    private static final int MAX_TOTAL = 800;
    private static final int Max_PerRoute = 200;
    private final static Object syncLock = new Object();
    static {
        SSLConnectionSocketFactory sslsf = createSSLConnSocketFactory();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .build();
        connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

        // connecting pools
        //connMgr = new PoolingHttpClientConnectionManager();

        //pool size
        connMgr.setMaxTotal(MAX_TOTAL);
        connMgr.setDefaultMaxPerRoute(Max_PerRoute);
        RequestConfig.Builder configBuilder = RequestConfig.custom();

        // timeout unit : milliseconds
        configBuilder.setConnectTimeout(MAX_CONNECT_TIMEOUT);
        configBuilder.setSocketTimeout(MAX_SOCKET_TIMEOUT);
        configBuilder.setConnectionRequestTimeout(MAX_CONNECT_REQUEST_TIMEOUT);

        // check availability
        //configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    public static ExecuteResult<String> httpRequest(String requestUrl, Map<String,Object> params){
        return httpRequest(requestUrl, params, String.class);
    }

	public static <T> ExecuteResult<T> httpRequest(String requestUrl, Map<String,Object> params, Class<T> clazz) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                ExecuteResult<T> result = new ExecuteResult<T>();
                result.addErrorMessage("接口异常："+statusCode);
                return result;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                ExecuteResult<T> result = new ExecuteResult<T>();
                result.addErrorMessage("接口异常：未返回内容");
                return result;
            }
            
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(httpStr);
        logger.debug(httpStr);
        ExecuteResult<T> result = new ExecuteResult<T>();
        try{
           result = JSONObject.parseObject(httpStr, new TypeReference<ExecuteResult<T>>(clazz){});
        }catch(Exception e){
            result.addErrorMessage("json解析异常");
        }
        return result;
    }
	
	public static String QixinhttpRequest(String requestUrl, Map<String,Object> params) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(httpStr);
        logger.debug(httpStr);
        return httpStr;
    }
	
	public static String QixinhttpRequestHeader(String requestUrl, Map<String,Object> params, Map<String,String> headers) {
        CloseableHttpClient httpClient = getHttpClient();
        
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {      	
        	
            
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            //httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            String paramsL = EntityUtils.toString(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            HttpGet httpGet = new HttpGet(requestUrl+"?"+ paramsL);
            httpGet.setConfig(requestConfig);
            for(Map.Entry<String,String> head : headers.entrySet()){
            	httpGet.addHeader(head.getKey(),head.getValue());
            }
            /*httpGet.addHeader("api_gateway_auth_app_id", "1a0e65d2-e02e-48d1-aa99-fbd3a4472dee");
            httpGet.addHeader("api_gateway_auth_app_password", "3cdeD5ScdsVc");*/
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(httpStr);
        logger.debug(httpStr);
        return httpStr;
    }
	
	public static String ProcuctsHttpRequestHeader(String requestUrl, String keyWords) {
    	 cn.hutool.json.JSONObject param = JSONUtil.createObj();
    	 param.put("keyWords", keyWords);
        Map<String,String> map = new HashMap<String,String>();
        map.put("Content-Type", "application/json;charset=utf-8");
    	String  httpStr = HttpRequest.post(requestUrl).addHeaders(map).body(param).execute().body();
        return httpStr;
    }
	
/*	public static <T> ExecuteResult<T> httpRequest(String requestUrl, Map<String,Object> params, Map<String,String> headers, Class<T> clazz) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(requestUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("utf-8")));
            
            Iterator headerIterator = headers.entrySet().iterator();          //循环增加header
            while(headerIterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) headerIterator.next();  
                httpPost.addHeader(elem.getKey(),elem.getValue());
            }
            
            response = httpClient.execute(httpPost);
            System.out.println(response);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                ExecuteResult<T> result = new ExecuteResult<T>();
                result.addErrorMessage("接口异常："+statusCode);
                return result;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                ExecuteResult<T> result = new ExecuteResult<T>();
                result.addErrorMessage("接口异常：未返回内容");
                return result;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(httpStr);
        logger.debug(httpStr);
        ExecuteResult<T> result = new ExecuteResult<T>();
        try{
           result = JSONObject.parseObject(httpStr, new TypeReference<ExecuteResult<T>>(clazz){});
        }catch(Exception e){
            result.addErrorMessage("json解析异常");
        }
        System.out.println(result);
        return result;
    }*/

    public static CloseableHttpClient getHttpClient(){
        if(defClient == null){
            synchronized (syncLock){
                defClient =HttpClients.custom()
                        .setConnectionManager(connMgr)
                        .setDefaultRequestConfig(requestConfig)
                        .build();
            }
        }
        //System.out.println("now client pool "+connMgr.getTotalStats().toString());
        return defClient;
    }

    /**
     * 创建SSL安全连接
     *
     * @return
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(TrustAllStrategy.INSTANCE).build();
            sslsf = new SSLConnectionSocketFactory(sslContext);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }
}


