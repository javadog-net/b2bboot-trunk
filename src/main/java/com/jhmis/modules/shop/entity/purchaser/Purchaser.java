package com.jhmis.modules.shop.entity.purchaser;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jhmis.common.utils.AccountLevelEnum;
import com.jhmis.common.utils.AuditStateEnum;
import com.jhmis.common.utils.CompanyTypeEnum;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.core.persistence.DataEntity;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 采购商管理Entity
 * @author tity
 * @version 2018-07-22
 */
public class Purchaser extends DataEntity<Purchaser> {
	
	private static final long serialVersionUID = 1L;
	private String companyCode;		// 公司代码
	private String companyName;		// 公司名称
	private String companyNum;		// 公司人数
	private String provinceId;		// 省id
	private String cityId;		// 城市ID
	private String districtId;		// 区ID
	private String areaInfo;		// 省市区汉字
	private String detailAddress;		// 详细地址
	private String industryId;		// 行业ID
	private String contacts;		// 联系人
	private String companyTel;		// 电话
    private String mobile;          //手机
	private String email;		// 邮箱
	private String companyNature;		// 公司性质
    private String logoUrl;            //公司logo
	private String invitationCode;		// 邀请码
	private String auditState;		// 采购商审核状态  0 未审核  1 审核通过 2 审核不通过
	private Date auditTime;		// 审核时间 
	private String auditor;		// 审核人
	private String auditDesc;		// 审核意见
	
	private double creditLine;  //信用额度

    private String industryName;		// 行业

	private String licenceUrl;		// 营业执照(三证合一)
	private String bankName;		// 开户行名称
	private String bankAccount;		// 开户行账号
	private String bankAddress;		// 开户行地址

	private String depart;   //部门

	private String channel;  //渠道
	
	private String invitationOper;  //邀请人
	
	private String purchaseType; //直采类型  
	
	private String companyType;// 采购商类型(0散户1战略)
	
	private String isAdmin;//是否主账号(主账号  0 否 1  是)
	
	private String bankKey;//银行编号
	
	
	private String loginNum;//88码
	@Transient
	private String loginName;//用户名
	@Transient
	private String auditStateName;//审核状态名称
	@Transient
	private String companyTypeName;//采购商类型
	@Transient
	private String isAdminName;//是否主账号
	@Transient
	private String cityName;//城市名称
	@Transient
	private String vatregno;//增值税登记号
	@Transient
	private String creditLineStr;  //信用额度
	@Transient
	private String regPhone;//开票电话
	@Transient
	private String regAddr;//注册地址
	
	public String getRegAddr() {
		return regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getRegPhone() {
		return regPhone;
	}

	public void setRegPhone(String regPhone) {
		this.regPhone = regPhone;
	}

	public String getCreditLineStr() {
		return creditLineStr;
	}

	public void setCreditLineStr(String creditLineStr) {
		this.creditLineStr = creditLineStr;
	}

	public String getBankKey() {
		return bankKey;
	}

	public void setBankKey(String bankKey) {
		this.bankKey = bankKey;
	}

	public String getVatregno() {
		return vatregno;
	}

	public void setVatregno(String vatregno) {
		this.vatregno = vatregno;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getAuditStateName() {
		return auditStateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setAuditStateName(String auditState) {
		if(StringUtils.isNotEmpty(auditState)){
			this.auditStateName = AuditStateEnum.getValue(auditState);
		}else{
			this.auditStateName = "";
		}
	}

	public String getCompanyTypeName() {
		if(StringUtils.isNotEmpty(companyType)){
			return CompanyTypeEnum.getValue(companyType);
		}
		return companyTypeName;
	}

	public void setCompanyTypeName(String companyType) {
		if(StringUtils.isNotEmpty(companyType)){
			this.companyTypeName = CompanyTypeEnum.getValue(companyType);
		}else{
			this.companyTypeName ="";
		}
	}

	public String getIsAdminName() {
		if(StringUtils.isNotEmpty(isAdmin)){
			return AccountLevelEnum.getValue(isAdmin);
		}
		return isAdminName;
	}

	public void setIsAdminName(String isAdmin) {
		if(StringUtils.isNotEmpty(isAdmin)){
			this.isAdminName = AccountLevelEnum.getValue(isAdmin);
		}else{
			this.isAdminName = "";
		}
	}

	public String getInvitationOper() {
		return invitationOper;
	}

	public void setInvitationOper(String invitationOper) {
		this.invitationOper = invitationOper;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLicenceUrl() {
		return licenceUrl;
	}

	public void setLicenceUrl(String licenceUrl) {
		this.licenceUrl = licenceUrl;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public Purchaser() {
		super();
	}

	public Purchaser(String id){
		super(id);
	}

	public double getCreditLine() {
		return creditLine;
	}

	public void setCreditLine(double creditLine) {
		this.creditLine = creditLine;
	}

	@ExcelField(title="公司代码", align=2, sort=1)
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	@ExcelField(title="公司名称", align=2, sort=2)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="公司人数", align=2, sort=3)
	public String getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(String companyNum) {
		this.companyNum = companyNum;
	}
	
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	
	@ExcelField(title="所在地区", align=2, sort=4)
	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}
	
	@ExcelField(title="详细地址", align=2, sort=5)
	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

    @ExcelField(title="行业", align=2, sort=6)
    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    @ExcelField(title="公司电话", align=2, sort=8)
    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

	@ExcelField(title="联系人", align=2, sort=7)
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

    @ExcelField(title="手机", align=2, sort=7)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Email(message="邮箱必须为合法邮箱")
	@ExcelField(title="邮箱", align=2, sort=9)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="公司性质", align=2, sort=10)
	public String getCompanyNature() {
		return companyNature;
	}

	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}
	
	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	
	@ExcelField(title="审核状态", dictType="audit_state", align=2, sort=11)
	public String getAuditState() {
		return auditState;
	}

	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="审核时间 ", align=2, sort=12)
	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
	@ExcelField(title="审核人", align=2, sort=13)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@ExcelField(title="审核意见", align=2, sort=14)
	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }


}