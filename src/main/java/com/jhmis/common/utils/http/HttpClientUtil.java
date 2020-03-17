package com.jhmis.common.utils.http;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhmis.core.mapper.JsonMapper;

/**
 * Http客户端
 * 使用连接池，支持https
 * @author tity
 *
 */
public class HttpClientUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
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
    
    public static CloseableHttpClient getHttpClient(){
        if(defClient == null){
            synchronized (syncLock){
                	defClient =HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
            }
        }
        //System.out.println("now client pool "+connMgr.getTotalStats().toString());
        return defClient;
    }
    
	/**
	 * 请求类型
	 */
	public static final class REQUEST_METHOD {
		public static final String GET = "GET";
		public static final String POST = "POST";
		public static final String HEAD = "HEAD";
		public static final String OPTIONS = "OPTIONS";
		public static final String PATCH = "PATCH";
		public static final String PUT = "PUT";
		public static final String DELETE = "DELETE";
	}

	/**
	 * 处理返回字符串类型的请求
	 * @param httpClientRequest
	 * @return
	 */
	public static HttpClientResponse<String> execute(HttpClientRequest httpClientRequest) {
		return execute(httpClientRequest,String.class);
	}
	/**
	 * 处理请求
	 * @param httpClientRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> HttpClientResponse<T> execute(HttpClientRequest httpClientRequest,Class<T> t) {
		HttpClientResponse<T> httpClientResponse = new HttpClientResponse<T>();
		//根据是否ssl创建https或者http客户端
		//CloseableHttpClient httpclient = httpClientRequest.getUseSSL() == true ?getHttpsClient():HttpClients.createDefault();
		CloseableHttpClient httpclient = getHttpClient();
		try {
			//处理路径变量
			String uri = processPathVariables(httpClientRequest.getUri(),httpClientRequest.getPathVariablesMap());
			RequestBuilder requestBuilder = RequestBuilder.create(httpClientRequest.getRequestMethod()).setUri(new URI(uri));
			
			//处理头部参数
			Map<String, String> headMap = httpClientRequest.getHeadMap();
			if (headMap != null && !headMap.isEmpty()) {
				for (Map.Entry<String, String> entry : headMap.entrySet()) {
					requestBuilder.addHeader(entry.getKey(), entry.getValue());
				}
				//输出头部参数
				logger.info("header: "+headMap);
			}
			
			//处理JSON或者XML
			Object jsonObject = httpClientRequest.getJsonEntityData();
			if (jsonObject != null) {
				String jsonBody = (jsonObject instanceof String) ? (String) jsonObject : JsonMapper.toJsonString(jsonObject);
				HttpEntity httpEntity = new StringEntity(jsonBody, ContentType.create("application/json", Consts.UTF_8));
				requestBuilder.setEntity(httpEntity);
			} else{
				//处理参数
				Map<String, String> paramMap = httpClientRequest.getParamMap();
				if(httpClientRequest.getIsMultipart()){
					//文件上传
					MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
					// mode 和 charset组合解决上传文件名中文乱码问题
					multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).setCharset(Charset.forName("UTF-8"));
					ContentType textContent = ContentType.create("text/plain", MIME.UTF8_CHARSET);
					if (paramMap != null && !paramMap.isEmpty()) {
						for (Map.Entry<String, String> entry : paramMap.entrySet()) {
							multipartEntityBuilder.addTextBody(entry.getKey(), entry.getValue(), textContent);
						}
					}
					Map<String, File> fileMap = httpClientRequest.getFileMap();
					if (fileMap != null && !fileMap.isEmpty()) {
						for (Map.Entry<String, File> entry : fileMap.entrySet()) {
							FileBody fileBody = new FileBody(entry.getValue());
							multipartEntityBuilder.addPart(entry.getKey(), fileBody);
						}
					}
					HttpEntity httpEntity = multipartEntityBuilder.build();
					requestBuilder.setEntity(httpEntity);
				} else {
					//普通请求
					if (paramMap != null && !paramMap.isEmpty()) {
						for (Map.Entry<String, String> entry : paramMap.entrySet()) {
							requestBuilder.addParameter(entry.getKey(), entry.getValue());
						}
					}
				}
			}
			
			//生成请求
			HttpUriRequest httpUriRequest = requestBuilder.build();
			
			//输出url字符串调试
			//System.out.println("url:"+httpUriRequest.getURI().toString());
			CloseableHttpResponse httpResponse = null;
			try {
				httpResponse = httpclient.execute(httpUriRequest);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				System.out.println("执行状态码 : " + statusCode);
				httpClientResponse.setStatusCode(statusCode);
				
				Map<String, String> resHeaders = new HashMap<String, String>();
				for (Header header : httpResponse.getAllHeaders()) {
					resHeaders.put(header.getName(), header.getValue());
				}
				httpClientResponse.setHeaders(resHeaders);
				
				HttpEntity entity = httpResponse.getEntity();
				//非200的返回也返回内容
				//if (statusCode == HttpStatus.SC_OK) {  
					String body = entity != null ? EntityUtils.toString(entity, "utf-8") : null;
					if (t.equals(String.class)) {
						httpClientResponse.setBody((T) body);
					} else {
						//转化为对象
						T bodyObject = (T) JsonMapper.fromJsonString(body, t);
						httpClientResponse.setBody(bodyObject);
					}
				//}
				if (entity != null) {
					EntityUtils.consume(entity);
				}
			} finally {
				if (httpResponse != null) {
					httpResponse.close();
				}
			}
		} catch (Exception e) {
			//屏蔽异常
			e.printStackTrace();
		} finally {
			//不关闭client，关闭后丢失了连接池
			/*try {
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}*/
		}
		return httpClientResponse;
	}

	/**
	 * json对象post
	 * @param uri
	 * @param pathVariables
	 * @param header
	 * @param obj
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> post(String uri, Map<String, String> pathVariables,Map<String, String> header,Object obj,Class<T> t) {
		HttpClientRequest httpClientRequest = new HttpClientRequest(uri);
		httpClientRequest.setPathVariablesMap(pathVariables);
		httpClientRequest.setHeadMap(header);
		httpClientRequest.setJsonEntityData(obj);
		return HttpClientUtil.execute(httpClientRequest,t);
	}
	
	/**
	 * json对象post
	 * @param uri
	 * @param header
	 * @param obj
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> post(String uri, Map<String, String> header,Object obj,Class<T> t) {
		return post(uri,null,header,obj,t);
	}
	
	/**
	 * json对象post
	 * @param uri
	 * @param obj
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> post(String uri, Object obj,Class<T> t) {
		return post(uri,null,null,obj,t);
	}
	
	/**
	 * map表单post
	 * @param uri
	 * @param pathVariables
	 * @param param
	 * @param header
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> post(String uri, Map<String, String> pathVariables, Map<String, String> param, Map<String, String> header,Class<T> t) {
		HttpClientRequest httpClientRequest = new HttpClientRequest(uri,param);
		httpClientRequest.setPathVariablesMap(pathVariables);
		httpClientRequest.setHeadMap(header);
		return HttpClientUtil.execute(httpClientRequest,t);
	}
	
	/**
	 * map表单post
	 * @param uri
	 * @param param
	 * @param header
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> post(String uri, Map<String, String> param, Map<String, String> header,Class<T> t) {
		return post(uri,null,param,header,t);
	}
	
	/**
	 * map表单post
	 * @param uri
	 * @param param
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> post(String uri, Map<String, String> param, Class<T> t) {
		return post(uri,null,param,null,t);
	}

	/**
	 * get
	 * @param uri
	 * @param pathVariables
	 * @param param
	 * @param header
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> get(String uri, Map<String, String> pathVariables, Map<String, String> param, Map<String, String> header,Class<T> t) {
		HttpClientRequest httpClientRequest = new HttpClientRequest(uri,param,REQUEST_METHOD.GET);
		httpClientRequest.setPathVariablesMap(pathVariables);
		httpClientRequest.setHeadMap(header);
		return HttpClientUtil.execute(httpClientRequest,t);
	}
	
	/**
	 * get
	 * @param uri
	 * @param param
	 * @param header
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> get(String uri, Map<String, String> param, Map<String, String> header,Class<T> t) {
		return get(uri,null,param,header,t);
	}
	
	/**
	 * map表单post
	 * @param uri
	 * @param param
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> get(String uri, Map<String, String> param, Class<T> t) {
		return get(uri,null,param,null,t);
	}
	
	/**
	 * json对象put
	 * @param uri
	 * @param pathVariables
	 * @param header
	 * @param obj
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> put(String uri, Map<String, String> pathVariables,Map<String, String> header,Object obj,Class<T> t) {
		HttpClientRequest httpClientRequest = new HttpClientRequest(uri,REQUEST_METHOD.PUT);
		httpClientRequest.setPathVariablesMap(pathVariables);
		httpClientRequest.setHeadMap(header);
		httpClientRequest.setJsonEntityData(obj);
		return HttpClientUtil.execute(httpClientRequest,t);
	}
	
	/**
	 * json对象put
	 * @param uri
	 * @param header
	 * @param obj
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> put(String uri, Map<String, String> header,Object obj,Class<T> t) {
		return put(uri,null,header,obj,t);
	}
	
	/**
	 * json对象put
	 * @param uri
	 * @param obj
	 * @param t
	 * @return
	 */
	public static <T> HttpClientResponse<T> put(String uri, Object obj,Class<T> t) {
		return put(uri,null,null,obj,t);
	}	
	/**
	 * 获取httpsclient
	 * @return
	 */
	@SuppressWarnings("unused")
	private static CloseableHttpClient getHttpsClient() {
        return HttpClients.custom()
                .setSSLSocketFactory(createSSLConnSocketFactory())
                .setConnectionManager(connMgr)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

	/**
	 * 创建信任SSLConnectionSocketFactory
	 * @return
	 */
	
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        try {
        	/*
        	//以前的写法
        	SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {  
                //信任所有  
                public boolean isTrusted(X509Certificate[] chain,  
                                String authType) throws CertificateException {  
                    return true;  
                }  
            }).build();
             */
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                    .build();
            return new SSLConnectionSocketFactory(sslcontext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 处理url中的路径变量
     * @param uri
     * @param pathVariables
     * @return
     */
    public static String processPathVariables(String uri, Map<String, String> pathVariables) {
		if ((pathVariables == null) || (pathVariables.size() == 0)) {
			return uri;
		}

		String result = uri;

		for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
			result = StringUtils.replace(result, "{" + ((String) entry.getKey()) + "}", (String) entry.getValue());
		}

		return result;
	}
    
    /**
     * 基本路径和uri路径组装
     * @param baseUrl
     * @param paths
     * @return
     */
    public static String buildPath(String baseUrl, String[] paths) {
		StringBuilder sb = new StringBuilder(baseUrl);
		for (String p : paths) {
			sb.append("/").append(p);
		}

		return sb.toString();
	}
    /**
     * 基本路径和uri路径组装
     * @param baseUrl
     * @param paths
     * @return
     */
    public static String buildPath(String baseUrl, String uri) {
		return baseUrl.concat(uri);
	}
}
