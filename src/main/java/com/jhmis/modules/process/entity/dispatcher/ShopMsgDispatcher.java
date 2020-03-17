/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.dispatcher;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 派单相关Entity
 * @author hdx
 * @version 2019-09-06
 */
public class ShopMsgDispatcher extends DataEntity<ShopMsgDispatcher> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求id
	private String dispaFlag;		// 是否已派单 0 否  1 是
	private Date dispaDate;		// 派单时间
	private String dispaUser;		// 派单人
	private String custcode;		// 承接经销商
	private String dispaDesc;		// 派单描述
	private String source;		// 未抢、派单
	private String isClosed;		// 是否关闭
	private String closer;		// 关闭人
	private Date closeDate;		// 关闭时间
	private Date createDate;		// 生成时间
	private String closeReason;		// 关闭原因
	private String dispaType;		// 派单区分(0是平台派单,1是总监派单)
	private String companyName;
	private String orgName;
	private String proGroup;
	private String channel;
	private String connectName;
	private String mobile;

	//是否是商空管理员
	private String isSkAdmin;

	public String getIsSkAdmin() {
		return isSkAdmin;
	}

	public void setIsSkAdmin(String isSkAdmin) {
		this.isSkAdmin = isSkAdmin;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getProGroup() {
		return proGroup;
	}

	public void setProGroup(String proGroup) {
		this.proGroup = proGroup;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public ShopMsgDispatcher() {
		super();
		this.setIdType(IDTYPE_UUID);
		this.setIsNewRecord(false);
	}

	public ShopMsgDispatcher(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="是否已派单 0 否  1 是", align=2, sort=2)
	public String getDispaFlag() {
		return dispaFlag;
	}

	public void setDispaFlag(String dispaFlag) {
		this.dispaFlag = dispaFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="派单时间", align=2, sort=3)
	public Date getDispaDate() {
		return dispaDate;
	}

	public void setDispaDate(Date dispaDate) {
		this.dispaDate = dispaDate;
	}
	
	@ExcelField(title="派单人", align=2, sort=4)
	public String getDispaUser() {
		return dispaUser;
	}

	public void setDispaUser(String dispaUser) {
		this.dispaUser = dispaUser;
	}
	
	@ExcelField(title="承接经销商", align=2, sort=5)
	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	@ExcelField(title="派单描述", align=2, sort=6)
	public String getDispaDesc() {
		return dispaDesc;
	}

	public void setDispaDesc(String dispaDesc) {
		this.dispaDesc = dispaDesc;
	}
	
	@ExcelField(title="未抢、派单", align=2, sort=8)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@ExcelField(title="是否关闭", align=2, sort=9)
	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
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
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	@ExcelField(title="关闭原因", align=2, sort=12)
	public String getCloseReason() {
		return closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}
	
	@ExcelField(title="派单区分(0是平台派单,1是总监派单)", align=2, sort=13)
	public String getDispaType() {
		return dispaType;
	}

	public void setDispaType(String dispaType) {
		this.dispaType = dispaType;
	}

	@Override
	public Date getCreateDate() {
		return createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}