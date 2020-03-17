package com.jhmis.common.utils;
/**
 * 审核状态枚举
 * @author wangbn
 *
 */
public enum AccountLevelEnum {
	NOMASTER("0","子账号"),
	MASTER("1","主账号");
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	
	private AccountLevelEnum (String key,String value){
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
		
		for(AccountLevelEnum se:AccountLevelEnum.values()){
			if(key.equals(se.key)){
				return se.value;
			}
		}
		return "";
	}

}
