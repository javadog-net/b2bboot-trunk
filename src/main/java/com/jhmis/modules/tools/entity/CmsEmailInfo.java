/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.tools.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 邮件Entity
 * @author tc
 * @version 2019-09-03
 */
public class CmsEmailInfo extends DataEntity<CmsEmailInfo> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 邮件主题
	private String emailtype;		// 邮件类型（系统、营销）
	private Date createtime;		// 创建时间
	private String rectype;		// 收件人类型 （全部、经销商、采购商、按照地域、未激活、已激活）
	private String provinceid;		// provinceid
	private String provincename;		// 省份名称
	private String cityid;		// cityid
	private String cityname;		// 城市名称
	private String membertag;		// membertag
	private String content;		// content
	private String issendemail;		// 是否发送邮件 0 未发送 1 发送
	private Date sendtime;		// 发送时间
	private String sender;		// 发件人
	private String sendtype;		// 1群发，0单发
	private String email;		// 邮箱
	
	public CmsEmailInfo() {
		super();
	}

	public CmsEmailInfo(String id){
		super(id);
	}

	@ExcelField(title="邮件主题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="邮件类型（系统、营销）", align=2, sort=2)
	public String getEmailtype() {
		return emailtype;
	}

	public void setEmailtype(String emailtype) {
		this.emailtype = emailtype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	@ExcelField(title="创建时间", align=2, sort=3)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@ExcelField(title="收件人类型 （全部、经销商、采购商、按照地域、未激活、已激活）", align=2, sort=4)
	public String getRectype() {
		return rectype;
	}

	public void setRectype(String rectype) {
		this.rectype = rectype;
	}
	
	@ExcelField(title="provinceid", align=2, sort=5)
	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	
	@ExcelField(title="省份名称", align=2, sort=6)
	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	
	@ExcelField(title="cityid", align=2, sort=7)
	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	@ExcelField(title="城市名称", align=2, sort=8)
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
	@ExcelField(title="membertag", align=2, sort=9)
	public String getMembertag() {
		return membertag;
	}

	public void setMembertag(String membertag) {
		this.membertag = membertag;
	}
	
	@ExcelField(title="content", align=2, sort=10)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="是否发送邮件 0 未发送 1 发送", align=2, sort=11)
	public String getIssendemail() {
		return issendemail;
	}

	public void setIssendemail(String issendemail) {
		this.issendemail = issendemail;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="发送时间", align=2, sort=12)
	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}
	
	@ExcelField(title="发件人", align=2, sort=13)
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	@ExcelField(title="1群发，0单发", align=2, sort=14)
	public String getSendtype() {
		return sendtype;
	}

	public void setSendtype(String sendtype) {
		this.sendtype = sendtype;
	}
	
	@ExcelField(title="邮箱", align=2, sort=15)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}