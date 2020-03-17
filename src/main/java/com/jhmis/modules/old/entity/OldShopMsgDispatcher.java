/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * oldEntity
 * @author old
 * @version 2019-11-29
 */
public class OldShopMsgDispatcher extends DataEntity<OldShopMsgDispatcher> {
	
	private static final long serialVersionUID = 1L;
	private String msgid;		// 需求id
	private String dispaflag;		// 是否已派单 0 否  1 是
	private Date dispatime;		// 派单时间
	private String dispauser;		// 派单人
	private String custcode;		// 承接经销商
	private String dispadesc;		// 派单描述
	private Date addtime;		// 进入派单时间
	private String source;		// 未抢、派单
	private String isclosed;		// 是否关闭
	private String closer;		// 关闭人
	private Date closetime;		// 关闭时间
	private String closereason;		// 关闭原因
	private String dispatype;		// 派单区分(null是平台派单,1是总监派单)
	
	public OldShopMsgDispatcher() {
		super();
	}

	public OldShopMsgDispatcher(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	@ExcelField(title="是否已派单 0 否  1 是", align=2, sort=2)
	public String getDispaflag() {
		return dispaflag;
	}

	public void setDispaflag(String dispaflag) {
		this.dispaflag = dispaflag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="派单时间", align=2, sort=3)
	public Date getDispatime() {
		return dispatime;
	}

	public void setDispatime(Date dispatime) {
		this.dispatime = dispatime;
	}
	
	@ExcelField(title="派单人", align=2, sort=4)
	public String getDispauser() {
		return dispauser;
	}

	public void setDispauser(String dispauser) {
		this.dispauser = dispauser;
	}
	
	@ExcelField(title="承接经销商", align=2, sort=5)
	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	@ExcelField(title="派单描述", align=2, sort=6)
	public String getDispadesc() {
		return dispadesc;
	}

	public void setDispadesc(String dispadesc) {
		this.dispadesc = dispadesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="进入派单时间", align=2, sort=7)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@ExcelField(title="未抢、派单", align=2, sort=8)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@ExcelField(title="是否关闭", align=2, sort=9)
	public String getIsclosed() {
		return isclosed;
	}

	public void setIsclosed(String isclosed) {
		this.isclosed = isclosed;
	}
	
	@ExcelField(title="关闭人", align=2, sort=10)
	public String getCloser() {
		return closer;
	}

	public void setCloser(String closer) {
		this.closer = closer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="关闭时间", align=2, sort=11)
	public Date getClosetime() {
		return closetime;
	}

	public void setClosetime(Date closetime) {
		this.closetime = closetime;
	}
	
	@ExcelField(title="关闭原因", align=2, sort=12)
	public String getClosereason() {
		return closereason;
	}

	public void setClosereason(String closereason) {
		this.closereason = closereason;
	}
	
	@ExcelField(title="派单区分(null是平台派单,1是总监派单)", align=2, sort=13)
	public String getDispatype() {
		return dispatype;
	}

	public void setDispatype(String dispatype) {
		this.dispatype = dispatype;
	}
	
}