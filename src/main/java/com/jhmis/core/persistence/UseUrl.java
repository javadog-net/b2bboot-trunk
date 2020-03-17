package com.jhmis.core.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="UseUrl")
public class UseUrl {
	
	public static String baseUrl;
	
	public static String urlone;
	
	public String getBaseUrl() {
		return baseUrl;
	}
	public String getUrlone(){
		return urlone;
	}
	
	@Value("${baseUrl}")
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	@Value("${urlone}")
	public void setUrlone(String urlone){
		this.urlone = urlone;
	}

	

}
