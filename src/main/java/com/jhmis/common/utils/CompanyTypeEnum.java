package com.jhmis.common.utils;
/**
 * 审核状态枚举
 * @author wangbn
 *
 */
public enum CompanyTypeEnum {
	NOSTRATEGY("0","散户"),
	STRATEGY("1","战略");
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	
	private CompanyTypeEnum (String key,String value){
		this.key=key;
		this.value=value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static String getValue(String key){
		
		for(CompanyTypeEnum se:CompanyTypeEnum.values()){
			if(key.equals(se.key)){
				return se.value;
			}
		}
		return "";
	}

}
