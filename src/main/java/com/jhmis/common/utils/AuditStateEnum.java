package com.jhmis.common.utils;
/**
 * 审核状态枚举
 * @author wangbn
 *
 */
public enum AuditStateEnum {
	WAIT("0","未审核"),
	PASS("1","已审核"),
	NOPASS("2","审核不通过");
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	
	private AuditStateEnum (String key,String value){
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
		
		for(AuditStateEnum se:AuditStateEnum.values()){
			if(key.equals(se.key)){
				return se.value;
			}
		}
		return "";
	}

}
