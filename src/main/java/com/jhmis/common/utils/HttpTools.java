package com.jhmis.common.utils;

import com.jhmis.core.web.BaseController;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpTools extends BaseController {
	private static HttpClient httpClient = new DefaultHttpClient();
	public static String httpGet(String url, Map<String, String> requestParams) {
		HttpGet httpGet = null;
		String content = "";
		try {
			// 参数设置
			StringBuilder builder = new StringBuilder(url);
			builder.append("?");
			for (Map.Entry<String, String> entry : requestParams.entrySet()) {
				builder.append((String) entry.getKey());
				builder.append("=");
				builder.append((String) entry.getValue());
				builder.append("&");
			}
			String tmpUrl = builder.toString();
			tmpUrl = tmpUrl.substring(0, tmpUrl.length() - 1);
			httpGet = new HttpGet(tmpUrl);
			HttpResponse response = httpClient.execute(httpGet);
			// reponse header
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
			}
			// 网页内容
			HttpEntity httpEntity = response.getEntity();
			content = EntityUtils.toString(httpEntity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpGet != null) {
				httpGet.abort();
			}
		}
		return content;
	}
	public static String httpPost(String url, Map<String, String> requestParams, String urlEncode) {

		HttpPost httpPost = null;
		String content = "";
		try {
			// 参数设置
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : requestParams.entrySet()) {
				params.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
			}

			httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params, urlEncode));
			HttpResponse response = httpClient.execute(httpPost);
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
			}
			// 网页内容
			HttpEntity httpEntity = response.getEntity();
			content = EntityUtils.toString(httpEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
		}
		return content;
	}
}
