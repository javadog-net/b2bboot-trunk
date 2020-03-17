package com.haier.http.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 
 * <p>Title: CharsetHandler.java</p>
 * 
 * <p>Description: Http页面的编码</p>
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
public class CharsetHandler implements ResponseHandler<String> {
	private String charset;

	public CharsetHandler(String charset) {
		this.charset = charset;
	}

	public String handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(),
					statusLine.getReasonPhrase());
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			if (StringUtils.isEmpty(charset)) {
				charset="UTF-8";
			}
			if (!StringUtils.isBlank(charset)) {
				return EntityUtils.toString(entity, charset);
			} else {
				return EntityUtils.toString(entity);
			}
		} else {
			return null;
		}
	}
}