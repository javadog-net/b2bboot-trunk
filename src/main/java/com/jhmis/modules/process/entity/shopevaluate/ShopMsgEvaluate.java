/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopevaluate;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 评价相关Entity
 * @author hdx
 * @version 2019-11-06
 */
public class ShopMsgEvaluate extends DataEntity<ShopMsgEvaluate> {
	
	private static final long serialVersionUID = 1L;
	private String msgid;		// 需求Id
	private String nodestate;		// 所到节点状态
	private String nodename;		// 所到节点名称
	private String description;		// 描述
	private String skillGrade;		// 技能评分
	private String attitudeGrade;		// 态度评分
	private String subuserId;		// 提交人
	private String subusername;		// 提交人名称
	private Date addtime;		// 提交时间
	private String feedbackdesc;		// 客服反馈内容
	private String feedbackperson;		// 客服反馈人
	private Date feedbacktime;		// 客服反馈时间

	//扩展字段
	private String companyName;		// 公司
	private String orgId;		// 工贸编码
	private String orgName;		// 工贸名称
	private String connectName;		// 联系人
	private String mobile;		// 联系方式
	private String channel;		// 渠道来源

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public ShopMsgEvaluate() {
		super();
	}

	public ShopMsgEvaluate(String id){
		super(id);
	}

	@ExcelField(title="需求Id", align=2, sort=1)
	public String getMsgid() {
		return msgid;
	}

	public ShopMsgEvaluate setMsgid(String msgid) {
		this.msgid = msgid;
		return this;
	}
	
	@ExcelField(title="所到节点状态", align=2, sort=2)
	public String getNodestate() {
		return nodestate;
	}

	public ShopMsgEvaluate setNodestate(String nodestate) {
		this.nodestate = nodestate;
		return this;
	}
	
	@ExcelField(title="所到节点名称", align=2, sort=3)
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	@ExcelField(title="描述", align=2, sort=4)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@ExcelField(title="技能评分", align=2, sort=5)
	public String getSkillGrade() {
		return skillGrade;
	}

	public void setSkillGrade(String skillGrade) {
		this.skillGrade = skillGrade;
	}
	
	@ExcelField(title="态度评分", align=2, sort=6)
	public String getAttitudeGrade() {
		return attitudeGrade;
	}

	public void setAttitudeGrade(String attitudeGrade) {
		this.attitudeGrade = attitudeGrade;
	}
	
	@ExcelField(title="提交人", align=2, sort=7)
	public String getSubuserId() {
		return subuserId;
	}

	public void setSubuserId(String subuserId) {
		this.subuserId = subuserId;
	}
	
	@ExcelField(title="提交人名称", align=2, sort=8)
	public String getSubusername() {
		return subusername;
	}

	public void setSubusername(String subusername) {
		this.subusername = subusername;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="提交时间不能为空")
	@ExcelField(title="提交时间", align=2, sort=9)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@ExcelField(title="客服反馈内容", align=2, sort=10)
	public String getFeedbackdesc() {
		return feedbackdesc;
	}

	public void setFeedbackdesc(String feedbackdesc) {
		this.feedbackdesc = feedbackdesc;
	}
	
	@ExcelField(title="客服反馈人", align=2, sort=11)
	public String getFeedbackperson() {
		return feedbackperson;
	}

	public void setFeedbackperson(String feedbackperson) {
		this.feedbackperson = feedbackperson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="客服反馈时间", align=2, sort=12)
	public Date getFeedbacktime() {
		return feedbacktime;
	}

	public void setFeedbacktime(Date feedbacktime) {
		this.feedbacktime = feedbacktime;
	}
	
}