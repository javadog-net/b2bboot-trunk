package com.jhmis.modules.shop.entity.dealer;

import lombok.Data;
import org.apache.cxf.annotations.DataBinding;
import org.apache.yetus.audience.InterfaceAudience;

import java.util.Date;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.modules.shop.entity.dealer
 * @Author: hdx
 * @CreateTime: 2019-08-14 16:21
 * @Description: Acg推送用户实体类
 */
@Data
public class DealerForAcg {
    private static final long serialVersionUID = 1L;
    private String id;

    Date createDate;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String companyCode;		// 供应商编码
    private String companyName;		// 供应商名称
    private String companyNum;		// 公司人数
    private String contacts;		// 公司联系人
    private String tel;		// 公司联系人电话
    private String mobile;		// 手机
    private String email;		// 邮箱
    private String zipCode;		// 邮编
    private String provinceId;		// 省
    private String cityId;		// 市
    private String districtId;		// 区
    private String areaInfo;		// 所在地区
    private String detailAddress;		// 详细地址
    private String channelName;		// 渠道名称
    private String taxCode;		// 税码
    private String kjtAccount;		// 快捷通账号
    private String electronicUrl;		// 营业执照电子版
    private String idCardUrl;		// 身份证电子版
    private String legalPersonName;		// 法人姓名
    private String legalPersonIdCard;		// 法人身份号
    private String companyTel;		// 公司电话
    private String electronicLicense;		// 开户许可证电子版
    private String logoUrl;		// 企业logo地址
    private String gmId;		// 所属工贸
    private String isClosed;		// 是否关闭
    private String isStore;		// 是否开店
    private String isSelf;		// 是否自营
    private String undertakeArea;		// 承接区域
    private String auditState;		// 审核状态
    private Date auditTime;		// 审核时间
    private String auditor;		// 审核人
    private String auditDesc;		// 审核意见
    //acg传入密码
    private String acgPassword;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getKjtAccount() {
        return kjtAccount;
    }

    public void setKjtAccount(String kjtAccount) {
        this.kjtAccount = kjtAccount;
    }

    public String getElectronicUrl() {
        return electronicUrl;
    }

    public void setElectronicUrl(String electronicUrl) {
        this.electronicUrl = electronicUrl;
    }

    public String getIdCardUrl() {
        return idCardUrl;
    }

    public void setIdCardUrl(String idCardUrl) {
        this.idCardUrl = idCardUrl;
    }

    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getLegalPersonIdCard() {
        return legalPersonIdCard;
    }

    public void setLegalPersonIdCard(String legalPersonIdCard) {
        this.legalPersonIdCard = legalPersonIdCard;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getElectronicLicense() {
        return electronicLicense;
    }

    public void setElectronicLicense(String electronicLicense) {
        this.electronicLicense = electronicLicense;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getGmId() {
        return gmId;
    }

    public void setGmId(String gmId) {
        this.gmId = gmId;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public String getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf;
    }

    public String getUndertakeArea() {
        return undertakeArea;
    }

    public void setUndertakeArea(String undertakeArea) {
        this.undertakeArea = undertakeArea;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public String getAcgPassword() {
        return acgPassword;
    }

    public void setAcgPassword(String acgPassword) {
        this.acgPassword = acgPassword;
    }

    @Override
    public String toString() {
        return "DealerForAcg{" +
                "companyCode='" + companyCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyNum='" + companyNum + '\'' +
                ", contacts='" + contacts + '\'' +
                ", tel='" + tel + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", areaInfo='" + areaInfo + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", channelName='" + channelName + '\'' +
                ", taxCode='" + taxCode + '\'' +
                ", kjtAccount='" + kjtAccount + '\'' +
                ", electronicUrl='" + electronicUrl + '\'' +
                ", idCardUrl='" + idCardUrl + '\'' +
                ", legalPersonName='" + legalPersonName + '\'' +
                ", legalPersonIdCard='" + legalPersonIdCard + '\'' +
                ", companyTel='" + companyTel + '\'' +
                ", electronicLicense='" + electronicLicense + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", gmId='" + gmId + '\'' +
                ", isClosed='" + isClosed + '\'' +
                ", isStore='" + isStore + '\'' +
                ", isSelf='" + isSelf + '\'' +
                ", undertakeArea='" + undertakeArea + '\'' +
                ", auditState='" + auditState + '\'' +
                ", auditTime=" + auditTime +
                ", auditor='" + auditor + '\'' +
                ", auditDesc='" + auditDesc + '\'' +
                ", acgPassword='" + acgPassword + '\'' +
                '}';
    }
}
