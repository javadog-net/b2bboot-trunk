/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 发送短信Entity
 * @author tc
 * @version 2019-09-04
 */
public class CmsShortMsg extends DataEntity<CmsShortMsg> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String msgtype;		// 短信分类
	private String creater;		// 创建人
	private String areaname;		// 省份
	private String membertype;		// 会员类型
	private Date createtime;		// 创建时间
	private String isshortmsg;		// 0 否，1 是
	private Date sendtime;		// 发送时间
	private String sender;		// 发件人
	private String sendtype;		// 1 群发，0单发
	private String mobilephone;		// 手机号
	
	public CmsShortMsg() {
		super();
	}

	public CmsShortMsg(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="短信分类", dictType="", align=2, sort=3)
	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	@ExcelField(title="创建人", align=2, sort=4)
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@ExcelField(title="省份", align=2, sort=5)
	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	
	@ExcelField(title="会员类型", dictType="", align=2, sort=6)
	public String getMembertype() {
		return membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=7)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@ExcelField(title="0 否，1 是", align=2, sort=8)
	public String getIsshortmsg() {
		return isshortmsg;
	}

	public void setIsshortmsg(String isshortmsg) {
		this.isshortmsg = isshortmsg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发送时间", align=2, sort=9)
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	@ExcelField(title="发件人", align=2, sort=10)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@ExcelField(title="1 群发，0单发", align=2, sort=11)
	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	
	@ExcelField(title="手机号", align=2, sort=12)
	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	
}