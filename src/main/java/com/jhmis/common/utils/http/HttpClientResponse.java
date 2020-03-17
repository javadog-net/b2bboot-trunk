package com.jhmis.common.utils.http;

import java.io.Serializable;
import java.util.Map;

/**
 * HTTP请求响应值对象
 * 
 * @author tity
 *
 */
public class HttpClientResponse<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> headers;
	private int statusCode;
	private T body;

	public HttpClientResponse() {
	}

	public HttpClientResponse(Map<String, String> headers, int statusCode, T body) {
		this.headers = headers;
		this.statusCode = statusCode;
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public T getBody() {
		return this.body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String toString() {
		return "RestResponse [headers=" + this.headers + ", statusCode=" + this.statusCode + ", body=" + this.body
				+ "]";
	}
	
}
