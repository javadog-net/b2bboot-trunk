package com.jhmis.common.utils.http;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP请求值对象
 * 
 * @author tity
 *
 */
public class HttpClientRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 编码
	 */
	private Charset charset;
	
	/**
	 * 请求路径地址
	 */
	private String uri;
	
	/**
	 * 请求类型
	 */
	private String requestMethod;
	
	/**
	 * 使用使用https,默认不使用
	 */
	private boolean useSSL = false;
	
	/**
	 * 请求路径参数
	 */
	private Map<String, String> pathVariablesMap;
	
	/**
	 * 请求参数
	 */
	private Map<String, String> paramMap;
	
	/**
	 * 头对象
	 */
	private Map<String, String> headMap;
	
	/**
	 * 请求上传文件参数参数
	 */
	private Map<String, File> fileMap;

	/**
	 * 请求JSON实体数据
	 */
	private Object JsonEntityData;

	/**
	 * 构造
	 */
	public HttpClientRequest(){
		this.charset = Charset.forName("UTF-8");
		setRequestMethod(HttpClientUtil.REQUEST_METHOD.POST);
	}
	
	/**
	 * 构造
	 */
	public HttpClientRequest(String pUrl){
		this.charset = Charset.forName("UTF-8");
		setUri(pUrl);
		setRequestMethod(HttpClientUtil.REQUEST_METHOD.POST);
	}
	
	
	/**
	 * 构造
	 * 
	 * @param pUrl
	 * @param pParamMap
	 */
	public HttpClientRequest(String pUrl, Map<String, String> paramMap){
		this.charset = Charset.forName("UTF-8");
		setUri(pUrl);
		setParamMap(paramMap);
		setRequestMethod(HttpClientUtil.REQUEST_METHOD.POST);
	}
	
	/**
	 * 构造
	 * 
	 * @param pUrl
	 * @param pParamMap
	 */
	public HttpClientRequest(String pUrl, Map<String, String> paramMap, String requestMethod){
		this.charset = Charset.forName("UTF-8");
		setUri(pUrl);
		setParamMap(paramMap);
		setRequestMethod(requestMethod);
	}
	/**
	 * 构造
	 * @param uri
	 * @param requestMethod
	 */
	public HttpClientRequest(String uri, String requestMethod) {
		this.charset = Charset.forName("UTF-8");
		this.uri = uri;
		this.requestMethod = requestMethod;
	}

	/**
	 * 快捷设置头信息
	 * 
	 * @param type
	 * @param value
	 */
	public void addHeader(String type, String value){
		if (headMap == null) {
			headMap = new HashMap<String, String>();
		}
		headMap.put(type, value);
	}
	
	/**
	 * 快捷设置路径变量
	 * 
	 * @param type
	 * @param value
	 */
	public void addPathVariables(String type, String value){
		if (pathVariablesMap == null) {
			pathVariablesMap = new HashMap<String, String>();
		}
		pathVariablesMap.put(type, value);
	}
	
	/**
	 * 快捷设置参数
	 * 
	 * @param type
	 * @param value
	 */
	public void addparam(String type, String value){
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		paramMap.put(type, value);
	}
	
	/**
	 * 设置Json实体数据
	 * 
	 * @param jsonEntityData
	 */
	public void setJsonEntityData(Object jsonEntityData) {
		//直接提交Json实体
		addHeader("Content-type", "application/json");
		//将参数Map设为null，这种情况下paramMap是无用的
		setParamMap(null);
		JsonEntityData = jsonEntityData;
	}

	public Charset getCharset() {
		return charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean getUseSSL() {
		return useSSL;
	}

	public void setUseSSL(boolean useSSL) {
		this.useSSL = useSSL;
	}

	public Map<String, String> getPathVariablesMap() {
		return pathVariablesMap;
	}

	public void setPathVariablesMap(Map<String, String> pathVariablesMap) {
		this.pathVariablesMap = pathVariablesMap;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public Map<String, File> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, File> fileMap) {
		this.fileMap = fileMap;
	}

	public Map<String, String> getHeadMap() {
		return headMap;
	}

	public void setHeadMap(Map<String, String> headMap) {
		this.headMap = headMap;
	}

	public Object getJsonEntityData() {
		return JsonEntityData;
	}

	public boolean getIsMultipart() {
		return (fileMap != null && !fileMap.isEmpty());
	}
	
	
}
