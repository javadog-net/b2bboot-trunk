/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 聊天好友表Entity
 * @author lvyangzhuo
 * @version 2018-11-22
 */
public class WxFriend extends DataEntity<WxFriend> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// purchaser_id
    private String userName;		// userName

	private String friendId;		// friend_id
	private String addPerson;		// add_person
	private Date addTime;		// add_time
	private String typeId;		// type_id
	private String nickName;		// nickName
	private String nameLetter;		// nameLetter
    private String status;  //0 是主动添加  1是被动添加

    private String avatar;//好友头像
    
    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNameLetter() {
		return nameLetter;
	}

	public void setNameLetter(String nameLetter) {
		this.nameLetter = nameLetter;
	}

	public WxFriend() {
		super();
	}

	public WxFriend(String id){
		super(id);
	}

	@ExcelField(title="friend_id", align=2, sort=2)
	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
	
	@ExcelField(title="add_person", align=2, sort=3)
	public String getAddPerson() {
		return addPerson;
	}

	public void setAddPerson(String addPerson) {
		this.addPerson = addPerson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="add_time", align=2, sort=4)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="type_id", align=2, sort=5)
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}