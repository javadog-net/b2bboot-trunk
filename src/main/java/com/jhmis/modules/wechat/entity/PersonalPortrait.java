package com.jhmis.modules.wechat.entity;

public class PersonalPortrait {
	private static final long serialVersionUID = 1L;
	String id;
	String imgUrl;
	String userMobile;
	String nickName;
	
	
	public PersonalPortrait() {
		super();
	}
	
	public PersonalPortrait(String id) {
		super();
		this.id = id;
	}
	
	
	
	public PersonalPortrait(String id, String imgUrl, String userMobile, String nickName) {
		super();
		this.id = id;
		this.imgUrl = imgUrl;
		this.userMobile = userMobile;
		this.nickName = nickName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	
	
}

