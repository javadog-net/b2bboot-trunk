/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 工程版信息视图Entity
 * @author hdx
 * @version 2019-05-29
 */
public class ViewQygBrown extends DataEntity<ViewQygBrown> {
	
	private static final long serialVersionUID = 1L;
	private String brownId;		// brown_id
	private String projectId;		// project_id
	private String projectCode;		// project_code
	private String projectName;		// project_name
	private String contractId;		// contract_id
	private String brownCode;		// brown_code
	private String productGroupId;		// product_group_id
	private String productGroupCode;		// product_group_code
	private String productGroupName;		// product_group_name
	private String dealerId;		// dealer_id
	private String dealerCode;		// dealer_code
	private String dealerName;		// dealer_name
	private String center;		// center
	private String type;		// type
	private String domainCode;		// domain_code
	private String openSystem;		// open_system
	private String brownCount;		// brown_count
	private Date expireTime;		// expire_time
	private String beValid;		// be_valid
	private String isCheck;		// is_check
	private Date effectTime;		// effect_time
	private String isSuccess;		// is_success
	private String rrsBeSuccess;		// rrs_be_success
	private Date rrsSuccessTime;		// rrs_success_time
	private String cdepcode;		// cdepcode
	private String caddress;		// caddress
	private String gvsBeSuccess;		// gvs_be_success
	private Date gvsTime;		// gvs_time
	private String gvsMessage;		// gvs_message
	private Date lastModifiedDate;		// 最后更新时间
	private String orderQuantitySum;		// 申请总数量
	private Date expireTimeBeforeApprove;		// 到期日期(审批通过后会将该字段更新到expire_time上)
	private String typeBeforeApprove;		// 类型(审批通过后会将该字段更新到type上)
	private String basePostId;		// 基础岗位ID
	private String basePostCode;		// 基础岗位编码
	private String basePostName;		// 基础岗位名称
	private String gmzt;		// 帐套
	private String infoSourse;		// 数据源
	private String cusAddress;		// 客户地址
	private String custContact;		// 联系人
	private String ctel;		// 电话
	private String cmobile;		// 移动电话
	private String cproName;		// 工程单位-R1甲方名称
	private String cproAddress;		// 工程单位地址-R项目地址
	private String cproContact;		// R2联系人
	private String cproTel;		// R2联系方式
	private String cproMobile;		// 移动电话2
	private String sysId;		// R2行业门类编码
	private String sysName;		// R2行业门类名称
	private String backWard;		// 政策兑现形式
	private String iallMoney;		// 整单金额
	private String billFlag;		// 单据类型
	private String createdById;		// 创建人ID
	private String lastModifiedById;		// 最后修改人ID
	private String deleted;		// 是否删除
	private String createdBy;		// 创建人
	private Date createdDate;		// 创建时间
	private String lastModifiedBy;		// 最后修改人名称
	private String creProId;		// 创建来源信息
	private String modProId;		// 修改来源信息
	private Date batchDate;		// 批处理时间

	private String centerName;
	private String effectTimeStr;		// effect_time
	private String effectTimeStart;		// effect_time
	private String effectTimeEnd;		// effect_time

	private String expireTimeStr;
	private String expireTimeStart;
	private String expireTimeEnd;

	public String getExpireTimeStr() {
		return expireTimeStr;
	}

	public void setExpireTimeStr(String expireTimeStr) {
		this.expireTimeStr = expireTimeStr;
	}

	public String getExpireTimeStart() {
		return expireTimeStart;
	}

	public void setExpireTimeStart(String expireTimeStart) {
		this.expireTimeStart = expireTimeStart;
	}

	public String getExpireTimeEnd() {
		return expireTimeEnd;
	}

	public void setExpireTimeEnd(String expireTimeEnd) {
		this.expireTimeEnd = expireTimeEnd;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getEffectTimeStr() {
		return effectTimeStr;
	}

	public void setEffectTimeStr(String effectTimeStr) {
		this.effectTimeStr = effectTimeStr;
	}

	public String getEffectTimeStart() {
		return effectTimeStart;
	}

	public void setEffectTimeStart(String effectTimeStart) {
		this.effectTimeStart = effectTimeStart;
	}

	public String getEffectTimeEnd() {
		return effectTimeEnd;
	}

	public void setEffectTimeEnd(String effectTimeEnd) {
		this.effectTimeEnd = effectTimeEnd;
	}

	public ViewQygBrown() {
		super();
	}

	public ViewQygBrown(String id){
		super(id);
	}

	@ExcelField(title="brown_id", align=2, sort=0)
	public String getBrownId() {
		return brownId;
	}

	public void setBrownId(String brownId) {
		this.brownId = brownId;
	}
	
	@ExcelField(title="project_id", align=2, sort=1)
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@ExcelField(title="project_code", align=2, sort=2)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="project_name", align=2, sort=3)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="contract_id", align=2, sort=4)
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	
	@ExcelField(title="brown_code", align=2, sort=5)
	public String getBrownCode() {
		return brownCode;
	}

	public void setBrownCode(String brownCode) {
		this.brownCode = brownCode;
	}
	
	@ExcelField(title="product_group_id", align=2, sort=6)
	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}
	
	@ExcelField(title="product_group_code", align=2, sort=7)
	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	@ExcelField(title="product_group_name", align=2, sort=8)
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	
	@ExcelField(title="dealer_id", align=2, sort=9)
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	
	@ExcelField(title="dealer_code", align=2, sort=10)
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	@ExcelField(title="dealer_name", align=2, sort=11)
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
	@ExcelField(title="center", align=2, sort=12)
	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}
	
	@ExcelField(title="type", align=2, sort=13)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@ExcelField(title="domain_code", align=2, sort=14)
	public String getDomainCode() {
		return domainCode;
	}

	public void setDomainCode(String domainCode) {
		this.domainCode = domainCode;
	}
	
	@ExcelField(title="open_system", align=2, sort=15)
	public String getOpenSystem() {
		return openSystem;
	}

	public void setOpenSystem(String openSystem) {
		this.openSystem = openSystem;
	}
	
	@ExcelField(title="brown_count", align=2, sort=16)
	public String getBrownCount() {
		return brownCount;
	}

	public void setBrownCount(String brownCount) {
		this.brownCount = brownCount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="expire_time", align=2, sort=17)
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@ExcelField(title="be_valid", align=2, sort=18)
	public String getBeValid() {
		return beValid;
	}

	public void setBeValid(String beValid) {
		this.beValid = beValid;
	}
	
	@ExcelField(title="is_check", align=2, sort=19)
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="effect_time", align=2, sort=20)
	public Date getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Date effectTime) {
		this.effectTime = effectTime;
	}
	
	@ExcelField(title="is_success", align=2, sort=21)
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@ExcelField(title="rrs_be_success", align=2, sort=22)
	public String getRrsBeSuccess() {
		return rrsBeSuccess;
	}

	public void setRrsBeSuccess(String rrsBeSuccess) {
		this.rrsBeSuccess = rrsBeSuccess;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="rrs_success_time", align=2, sort=23)
	public Date getRrsSuccessTime() {
		return rrsSuccessTime;
	}

	public void setRrsSuccessTime(Date rrsSuccessTime) {
		this.rrsSuccessTime = rrsSuccessTime;
	}
	
	@ExcelField(title="cdepcode", align=2, sort=24)
	public String getCdepcode() {
		return cdepcode;
	}

	public void setCdepcode(String cdepcode) {
		this.cdepcode = cdepcode;
	}
	
	@ExcelField(title="caddress", align=2, sort=25)
	public String getCaddress() {
		return caddress;
	}

	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	
	@ExcelField(title="gvs_be_success", align=2, sort=26)
	public String getGvsBeSuccess() {
		return gvsBeSuccess;
	}

	public void setGvsBeSuccess(String gvsBeSuccess) {
		this.gvsBeSuccess = gvsBeSuccess;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="gvs_time", align=2, sort=27)
	public Date getGvsTime() {
		return gvsTime;
	}

	public void setGvsTime(Date gvsTime) {
		this.gvsTime = gvsTime;
	}
	
	@ExcelField(title="gvs_message", align=2, sort=28)
	public String getGvsMessage() {
		return gvsMessage;
	}

	public void setGvsMessage(String gvsMessage) {
		this.gvsMessage = gvsMessage;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后更新时间", align=2, sort=29)
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	@ExcelField(title="申请总数量", align=2, sort=30)
	public String getOrderQuantitySum() {
		return orderQuantitySum;
	}

	public void setOrderQuantitySum(String orderQuantitySum) {
		this.orderQuantitySum = orderQuantitySum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="到期日期(审批通过后会将该字段更新到expire_time上)", align=2, sort=31)
	public Date getExpireTimeBeforeApprove() {
		return expireTimeBeforeApprove;
	}

	public void setExpireTimeBeforeApprove(Date expireTimeBeforeApprove) {
		this.expireTimeBeforeApprove = expireTimeBeforeApprove;
	}
	
	@ExcelField(title="类型(审批通过后会将该字段更新到type上)", align=2, sort=32)
	public String getTypeBeforeApprove() {
		return typeBeforeApprove;
	}

	public void setTypeBeforeApprove(String typeBeforeApprove) {
		this.typeBeforeApprove = typeBeforeApprove;
	}
	
	@ExcelField(title="基础岗位ID", align=2, sort=33)
	public String getBasePostId() {
		return basePostId;
	}

	public void setBasePostId(String basePostId) {
		this.basePostId = basePostId;
	}
	
	@ExcelField(title="基础岗位编码", align=2, sort=34)
	public String getBasePostCode() {
		return basePostCode;
	}

	public void setBasePostCode(String basePostCode) {
		this.basePostCode = basePostCode;
	}
	
	@ExcelField(title="基础岗位名称", align=2, sort=35)
	public String getBasePostName() {
		return basePostName;
	}

	public void setBasePostName(String basePostName) {
		this.basePostName = basePostName;
	}
	
	@ExcelField(title="帐套", align=2, sort=36)
	public String getGmzt() {
		return gmzt;
	}

	public void setGmzt(String gmzt) {
		this.gmzt = gmzt;
	}
	
	@ExcelField(title="数据源", align=2, sort=37)
	public String getInfoSourse() {
		return infoSourse;
	}

	public void setInfoSourse(String infoSourse) {
		this.infoSourse = infoSourse;
	}
	
	@ExcelField(title="客户地址", align=2, sort=38)
	public String getCusAddress() {
		return cusAddress;
	}

	public void setCusAddress(String cusAddress) {
		this.cusAddress = cusAddress;
	}
	
	@ExcelField(title="联系人", align=2, sort=39)
	public String getCustContact() {
		return custContact;
	}

	public void setCustContact(String custContact) {
		this.custContact = custContact;
	}
	
	@ExcelField(title="电话", align=2, sort=40)
	public String getCtel() {
		return ctel;
	}

	public void setCtel(String ctel) {
		this.ctel = ctel;
	}
	
	@ExcelField(title="移动电话", align=2, sort=41)
	public String getCmobile() {
		return cmobile;
	}

	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}
	
	@ExcelField(title="工程单位-R1甲方名称", align=2, sort=42)
	public String getCproName() {
		return cproName;
	}

	public void setCproName(String cproName) {
		this.cproName = cproName;
	}
	
	@ExcelField(title="工程单位地址-R项目地址", align=2, sort=43)
	public String getCproAddress() {
		return cproAddress;
	}

	public void setCproAddress(String cproAddress) {
		this.cproAddress = cproAddress;
	}
	
	@ExcelField(title="R2联系人", align=2, sort=44)
	public String getCproContact() {
		return cproContact;
	}

	public void setCproContact(String cproContact) {
		this.cproContact = cproContact;
	}
	
	@ExcelField(title="R2联系方式", align=2, sort=45)
	public String getCproTel() {
		return cproTel;
	}

	public void setCproTel(String cproTel) {
		this.cproTel = cproTel;
	}
	
	@ExcelField(title="移动电话2", align=2, sort=46)
	public String getCproMobile() {
		return cproMobile;
	}

	public void setCproMobile(String cproMobile) {
		this.cproMobile = cproMobile;
	}
	
	@ExcelField(title="R2行业门类编码", align=2, sort=47)
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	
	@ExcelField(title="R2行业门类名称", align=2, sort=48)
	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	@ExcelField(title="政策兑现形式", align=2, sort=49)
	public String getBackWard() {
		return backWard;
	}

	public void setBackWard(String backWard) {
		this.backWard = backWard;
	}
	
	@ExcelField(title="整单金额", align=2, sort=50)
	public String getIallMoney() {
		return iallMoney;
	}

	public void setIallMoney(String iallMoney) {
		this.iallMoney = iallMoney;
	}
	
	@ExcelField(title="单据类型", align=2, sort=51)
	public String getBillFlag() {
		return billFlag;
	}

	public void setBillFlag(String billFlag) {
		this.billFlag = billFlag;
	}
	
	@ExcelField(title="创建人ID", align=2, sort=52)
	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById;
	}
	
	@ExcelField(title="最后修改人ID", align=2, sort=53)
	public String getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(String lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}
	
	@ExcelField(title="是否删除", align=2, sort=54)
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@ExcelField(title="创建人", align=2, sort=55)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=56)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@ExcelField(title="最后修改人名称", align=2, sort=57)
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
	@ExcelField(title="创建来源信息", align=2, sort=58)
	public String getCreProId() {
		return creProId;
	}

	public void setCreProId(String creProId) {
		this.creProId = creProId;
	}
	
	@ExcelField(title="修改来源信息", align=2, sort=59)
	public String getModProId() {
		return modProId;
	}

	public void setModProId(String modProId) {
		this.modProId = modProId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="批处理时间", align=2, sort=60)
	public Date getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(Date batchDate) {
		this.batchDate = batchDate;
	}
	
}