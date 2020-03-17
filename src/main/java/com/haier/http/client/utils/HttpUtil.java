package com.haier.http.client.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * 
 * <p>Title: HttpUtil.java</p>
 * 
 * <p>Description: Http工具类</p>
 * 
 * <p>Date: Jan 18, 2014</p>
 * 
 * <p>Time: 8:58:23 PM</p>
 * 
 * <p>Copyright: 2013</p>
 * 
 * <p>Company: b2b</p>
 * 
 * @author tity
 * @version 1.0
 * 
 * <p>============================================</p>
 * <p>Modification History
 * <p>Mender: </p>
 * <p>Date: </p>
 * <p>Reason: </p>
 * <p>============================================</p>
 */
public class HttpUtil {

	private static HttpClient HTTPCLIENT;
	private static Date lastCloseTime=null;//上次清除空闲连接时间
	public static String ERROR="cms_http_error:";//如果返回的结果带这个字符，说明方法执行有错误
	private static String[] IEBrowserSignals = { "MSIE", "Trident", "Edge" };
	
	/**
	 * 获取 httpclient对象
	 * @return
	 */
	public static HttpClient getHttpClient(){
		if (HTTPCLIENT==null) {
			HTTPCLIENT=new DefaultHttpClient(new ThreadSafeClientConnManager());
			HTTPCLIENT.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,60000);
			HTTPCLIENT.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);
		}
		//如果超过10分钟未清除空闲连接，则清除一次
		if (lastCloseTime==null 
				|| DateUtil.differ(lastCloseTime,new Date())>1000*60*10) {
			HTTPCLIENT.getConnectionManager().closeExpiredConnections();
			HTTPCLIENT.getConnectionManager().closeIdleConnections(10, TimeUnit.MINUTES);
			lastCloseTime=new Date();
		}
		return HTTPCLIENT;
	}
	/**
	 * 用get方式采集页面内容
	 * @param url
	 * @param charset
	 * @return
	 */
	public static String getUrlContent(String url, String charset)throws Exception {
		String content="";
		HttpGet httpget=null;
		try {
			httpget = new HttpGet(new URI(url));
			content = getHttpClient().execute(httpget, new CharsetHandler(charset));
		}finally{
			closeHttpGet(httpget);
		}
		return content;	
	}
	/**
	 * 关闭httpget链接
	 * @param httpGet
	 */
	public static void closeHttpGet(HttpGet httpget){
		if (httpget!=null) {
			httpget.abort();
			httpget.releaseConnection();
		}
	}

	/**
	 * 下载图片
	 * @param imgUrl
	 * @param contextPath
	 * @param uploadPath
	 * @return
	 */
	public static String downloadImg(String imgUrl, String contextPath, String uploadPath, ServletContext servletContext) {
		if (StringUtils.isNotEmpty(imgUrl) && StringUtils.isNotEmpty(uploadPath)) {
			HttpClient client = getHttpClient();
			String outFileName="";
			HttpGet httpget=null;
			try{
				httpget = new HttpGet(new URI(imgUrl));
				HttpResponse response = client.execute(httpget);
				InputStream is = null;
				OutputStream os = null;
				HttpEntity entity = null;
				entity = response.getEntity();
				is = entity.getContent();
				//创建目录
				File folder=new File(servletContext.getRealPath(uploadPath));
				if (!folder.exists()) {
					folder.mkdirs();
				}
				outFileName=uploadPath+ UUID.randomUUID()+imgUrl.substring(imgUrl.lastIndexOf("."));
				os = new FileOutputStream(servletContext.getRealPath(outFileName));
				IOUtils.copy(is, os);
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				closeHttpGet(httpget);
			}
			return contextPath+outFileName;
		}
		return "";
	}
	public static void main(String[] args) {
		//System.out.println(getUrlContent("http://www.baidu.com", "utf-8"));
	}
	
	public static boolean isMSBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		for (String signal : IEBrowserSignals) {
			if (userAgent.contains(signal))
				return true;
		}
		return false;
	}
	
}
