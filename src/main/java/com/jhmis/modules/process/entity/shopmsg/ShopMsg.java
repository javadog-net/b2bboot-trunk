/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopmsg;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.process.entity.dispatcher.ShopMsgDispatcher;
import com.jhmis.modules.process.entity.shopmsgorder.ShopMsgCustcodeOrder;
import com.jhmis.modules.process.entity.shopmsgzykc.ShopMsgZykc;
import com.jhmis.modules.shop.entity.dealer.Dealer;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 发布需求相关Entity
 * @author hdx
 * @version 2019-09-03
 */
public class ShopMsg extends DataEntity<ShopMsg> {
	
	private static final long serialVersionUID = 1L;
	private String companyName;		// 公司
	private String companySize;		// 公司规模
	private String provinceId;		// 省份编码
	private String provinceName;		// 省份名称
	private String cityId;		// 城市编码
	private String cityName;		// 城市名称
	private String districId;		// 地区编码
	private String districName;		// 区域名称
	private String address;		// 省市区的名称
	private String addressDetail;		// 详细地址(hps必备）
	private String orgId;		// 工贸编码
	private String orgName;		// 工贸名称
	private String channel;		// 渠道来源
	private String connectName;		// 联系人
	private String mobile;		// 联系方式
	private String reserveMobile;		// 400中的预留手机号
	private String email;		// 邮箱
	private String qq;		// qq
	private String depart;		// 部门
	private String money;		// 预算（400 客服界面上有该选项）
	private String companyOrgCode;		// 统一社会信息编码
	private String companyOrgName;		// 统一信用认证完整公司名称
	private String brandName;		// 品牌
	private String category;		// 品类
	private String msgType;		// 需求分类（询价、代理、邀标、资料获取、品牌合作、文化交流）
	private String quotaInfo;		// 报价信息（400客服界面用到）
	private String projectCode;		// 项目编码
	private int count;//产品数量
	private String descp;		// 需求描述
	private String areaDetails;		// 详细地址	utf8
	private String isRead;		// 未读0  已读1
	private String haveUndertake;		// 是否有承接经销商 0 没有  1  有
	private String isDispatch;		// 是否派单
	private String franchiseId;		// 采购商ID
	private String msgFlag;		// 0 平台待审核；
	private String oldCode;		// 历史单号
	private String ptIspass;		// 平台审核是否通过
	private String ptChecker;		// 平台审核人
	private Date ptCheckDate;		// 平台审核时间
	private String ptDescp;		// 平台审核备注
	private String appIspass;		// 平台审核是否通过
	private String appChecker;		// APP 审核人
	private Date appCheckDate;		// app审核时间
	private String appCancleReason;		// 废弃原因
	private String appDescp;		// app审核原因
	private Date grabDate;		// 抢单时间
	private String custCode;		// 经销商编码
	private String isBack;		// 是否反馈，1是，0否
	private String operExchange;		// 合作交流内容
	private String managerNo;		// 指派人编号
	private String managerName;		// 指派人名称
	private String conFranchiseType;		// 商空采购商类型
	private Date remarksDate;		// 总监备注填写时间
	private String remarksPerson;		// 填写备注的总监编号
	private String sendmsg;		// 是否已成功发送短信，（1-已成功发送）
	private String unreadMsg;		// 客户未读信息条数
	private String proGroup;		// 产品组冗余字段
	private String proGroupCode; // 产品组对应冗余字段
	private String memo;		// 采购需求描述
	private String code; // 短信验证码（自注册时使用）
	private String registType; //是否已经注册
    private Date addTime;//添加时间
	private String msgNo;//需求单号

	//总监扩展字段
	private String quantity; //预计采购数量
	private String budget; //预计采购金额
	private String directorNo; //总监账号

	//时间between属性

	private String beginCreateDate;

	private String endCreateDate;

	//需求海尔接口人是否填写标识
    private boolean managerNoFlag;

    //是否是商空管理员
	private String isSkAdmin;

    //经销商订单
	private ShopMsgCustcodeOrder shopMsgCustcodeOrder;

	//派单信息
	private ShopMsgDispatcher shopMsgDispatcher;

	//自有库存表
	private ShopMsgZykc shopMsgZykc;

	//派单id
	private String dispatcherId;

    //经销商订单集合
	private List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder;

    //派单集合
	private List<ShopMsgDispatcher> listShopMsgDispatcher;

	//手机端全流程扩展字段
	private String nodetag;

	//400hic回传扩展字段
	private String isBackpass;

	//Dealer附属属性
	private Dealer dealer;

	//infcode整合数据遗留字段
	private String infcode;




	public String getInfcode() {
		return infcode;
	}

	public void setInfcode(String infcode) {
		this.infcode = infcode;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getIsBackpass() {
		return isBackpass;
	}

	public void setIsBackpass(String isBackpass) {
		this.isBackpass = isBackpass;
	}

	public String getIsSkAdmin() {
		return isSkAdmin;
	}

	public void setIsSkAdmin(String isSkAdmin) {
		this.isSkAdmin = isSkAdmin;
	}

	public String getDispatcherId() {
		return dispatcherId;
	}

	public void setDispatcherId(String dispatcherId) {
		this.dispatcherId = dispatcherId;
	}

	public String getNodetag() {
		return nodetag;
	}

	public ShopMsg setNodetag(String nodetag) {
		this.nodetag = nodetag;
		return this;
	}

	public List<ShopMsgCustcodeOrder> getListShopMsgCustcodeOrder() {
		return listShopMsgCustcodeOrder;
	}

	public void setListShopMsgCustcodeOrder(List<ShopMsgCustcodeOrder> listShopMsgCustcodeOrder) {
		this.listShopMsgCustcodeOrder = listShopMsgCustcodeOrder;
	}

	public List<ShopMsgDispatcher> getListShopMsgDispatcher() {
		return listShopMsgDispatcher;
	}

	public void setListShopMsgDispatcher(List<ShopMsgDispatcher> listShopMsgDispatcher) {
		this.listShopMsgDispatcher = listShopMsgDispatcher;
	}

	public boolean isManagerNoFlag() {
		return managerNoFlag;
	}

	public ShopMsgCustcodeOrder getShopMsgCustcodeOrder() {
		return shopMsgCustcodeOrder;
	}

	public void setShopMsgCustcodeOrder(ShopMsgCustcodeOrder shopMsgCustcodeOrder) {
		this.shopMsgCustcodeOrder = shopMsgCustcodeOrder;
	}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

	public ShopMsgDispatcher getShopMsgDispatcher() {
		return shopMsgDispatcher;
	}

	public void setShopMsgDispatcher(ShopMsgDispatcher shopMsgDispatcher) {
		this.shopMsgDispatcher = shopMsgDispatcher;
	}

	public boolean getManagerNoFlag() {
        return managerNoFlag;
    }

    public void setManagerNoFlag(boolean managerNoFlag) {
        this.managerNoFlag = managerNoFlag;
    }

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getDirectorNo() {
		return directorNo;
	}

	public void setDirectorNo(String directorNo) {
		this.directorNo = directorNo;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	private String isCheck; //审核状态

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getRegistType() {
		return registType;
	}

	public void setRegistType(String registType) {
		this.registType = registType;
	}

	public String getProGroupCode() {
		return proGroupCode;
	}

	public void setProGroupCode(String proGroupCode) {
		this.proGroupCode = proGroupCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ShopMsg() {
		super();
	}

	public ShopMsg(String id){
		super(id);
	}

	@ExcelField(title="公司", align=2, sort=1)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="公司规模", align=2, sort=2)
	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}
	
	@ExcelField(title="省份编码", dictType="", align=2, sort=3)
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	@ExcelField(title="省份名称", dictType="", align=2, sort=4)
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	@ExcelField(title="城市编码", dictType="", align=2, sort=5)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="城市名称", dictType="", align=2, sort=6)
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@ExcelField(title="地区编码", dictType="", align=2, sort=7)
	public String getDistricId() {
		return districId;
	}

	public void setDistricId(String districId) {
		this.districId = districId;
	}
	
	@ExcelField(title="区域名称", dictType="", align=2, sort=8)
	public String getDistricName() {
		return districName;
	}

	public void setDistricName(String districName) {
		this.districName = districName;
	}
	
	@ExcelField(title="省市区的名称", align=2, sort=9)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="详细地址(hps必备）", align=2, sort=10)
	public String getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	
	@ExcelField(title="工贸编码", align=2, sort=11)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@ExcelField(title="工贸名称", align=2, sort=12)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@ExcelField(title="渠道来源", align=2, sort=13)
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@ExcelField(title="联系人", align=2, sort=14)
	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
	
	@ExcelField(title="联系方式", align=2, sort=15)
	public String getMobile() {
		return mobile;
	}

	public ShopMsg setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}
	
	@ExcelField(title="400中的预留手机号", align=2, sort=16)
	public String getReserveMobile() {
		return reserveMobile;
	}

	public void setReserveMobile(String reserveMobile) {
		this.reserveMobile = reserveMobile;
	}
	
	@ExcelField(title="邮箱", align=2, sort=17)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="qq", align=2, sort=18)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@ExcelField(title="部门", align=2, sort=19)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="预算（400 客服界面上有该选项）", align=2, sort=20)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@ExcelField(title="统一社会信息编码", align=2, sort=22)
	public String getCompanyOrgCode() {
		return companyOrgCode;
	}

	public void setCompanyOrgCode(String companyOrgCode) {
		this.companyOrgCode = companyOrgCode;
	}
	
	@ExcelField(title="统一信用认证完整公司名称", align=2, sort=23)
	public String getCompanyOrgName() {
		return companyOrgName;
	}

	public void setCompanyOrgName(String companyOrgName) {
		this.companyOrgName = companyOrgName;
	}
	
	@ExcelField(title="品牌", align=2, sort=24)
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	@ExcelField(title="品类", align=2, sort=25)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@ExcelField(title="需求分类（询价、代理、邀标、资料获取、品牌合作、文化交流）", dictType="", align=2, sort=26)
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@ExcelField(title="报价信息（400客服界面用到）", align=2, sort=27)
	public String getQuotaInfo() {
		return quotaInfo;
	}

	public void setQuotaInfo(String quotaInfo) {
		this.quotaInfo = quotaInfo;
	}
	
	@ExcelField(title="项目编码", align=2, sort=28)
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@ExcelField(title="需求描述", align=2, sort=29)
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}
	
	@ExcelField(title="详细地址	utf8", align=2, sort=30)
	public String getAreaDetails() {
		return areaDetails;
	}

	public void setAreaDetails(String areaDetails) {
		this.areaDetails = areaDetails;
	}
	
	@ExcelField(title="未读0  已读1", align=2, sort=31)
	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
	@ExcelField(title="是否有承接经销商 0 没有  1  有", align=2, sort=32)
	public String getHaveUndertake() {
		return haveUndertake;
	}

	public void setHaveUndertake(String haveUndertake) {
		this.haveUndertake = haveUndertake;
	}
	
	@ExcelField(title="是否派单", align=2, sort=33)
	public String getIsDispatch() {
		return isDispatch;
	}

	public void setIsDispatch(String isDispatch) {
		this.isDispatch = isDispatch;
	}
	
	@ExcelField(title="采购商ID", align=2, sort=34)
	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}
	
	@ExcelField(title="0 平台待审核；", dictType="", align=2, sort=35)
	public String getMsgFlag() {
		return msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}
	
	@ExcelField(title="历史单号", align=2, sort=36)
	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}
	
	@ExcelField(title="平台审核是否通过", align=2, sort=37)
	public String getPtIspass() {
		return ptIspass;
	}

	public void setPtIspass(String ptIspass) {
		this.ptIspass = ptIspass;
	}
	
	@ExcelField(title="平台审核人", align=2, sort=38)
	public String getPtChecker() {
		return ptChecker;
	}

	public void setPtChecker(String ptChecker) {
		this.ptChecker = ptChecker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="平台审核时间", align=2, sort=39)
	public Date getPtCheckDate() {
		return ptCheckDate;
	}

	public void setPtCheckDate(Date ptCheckDate) {
		this.ptCheckDate = ptCheckDate;
	}
	
	@ExcelField(title="平台审核备注", align=2, sort=40)
	public String getPtDescp() {
		return ptDescp;
	}

	public void setPtDescp(String ptDescp) {
		this.ptDescp = ptDescp;
	}
	
	@ExcelField(title="平台审核是否通过", align=2, sort=41)
	public String getAppIspass() {
		return appIspass;
	}

	public void setAppIspass(String appIspass) {
		this.appIspass = appIspass;
	}
	
	@ExcelField(title="APP 审核人", align=2, sort=42)
	public String getAppChecker() {
		return appChecker;
	}

	public void setAppChecker(String appChecker) {
		this.appChecker = appChecker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="app审核时间", align=2, sort=43)
	public Date getAppCheckDate() {
		return appCheckDate;
	}

	public void setAppCheckDate(Date appCheckDate) {
		this.appCheckDate = appCheckDate;
	}
	
	@ExcelField(title="废弃原因", align=2, sort=44)
	public String getAppCancleReason() {
		return appCancleReason;
	}

	public void setAppCancleReason(String appCancleReason) {
		this.appCancleReason = appCancleReason;
	}
	
	@ExcelField(title="app审核原因", align=2, sort=45)
	public String getAppDescp() {
		return appDescp;
	}

	public void setAppDescp(String appDescp) {
		this.appDescp = appDescp;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="抢单时间", align=2, sort=46)
	public Date getGrabDate() {
		return grabDate;
	}

	public void setGrabDate(Date grabDate) {
		this.grabDate = grabDate;
	}
	
	@ExcelField(title="经销商编码", align=2, sort=47)
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	@ExcelField(title="是否反馈，1是，0否", dictType="", align=2, sort=48)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}
	
	@ExcelField(title="合作交流内容", align=2, sort=49)
	public String getOperExchange() {
		return operExchange;
	}

	public void setOperExchange(String operExchange) {
		this.operExchange = operExchange;
	}
	
	@ExcelField(title="指派人编号", align=2, sort=50)
	public String getManagerNo() {
		return managerNo;
	}

	public void setManagerNo(String managerNo) {
		this.managerNo = managerNo;
	}
	
	@ExcelField(title="指派人名称", align=2, sort=51)
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	@ExcelField(title="商空采购商类型", align=2, sort=52)
	public String getConFranchiseType() {
		return conFranchiseType;
	}

	public void setConFranchiseType(String conFranchiseType) {
		this.conFranchiseType = conFranchiseType;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="总监备注填写时间", align=2, sort=54)
	public Date getRemarksDate() {
		return remarksDate;
	}

	public void setRemarksDate(Date remarksDate) {
		this.remarksDate = remarksDate;
	}
	
	@ExcelField(title="填写备注的总监编号", align=2, sort=55)
	public String getRemarksPerson() {
		return remarksPerson;
	}

	public void setRemarksPerson(String remarksPerson) {
		this.remarksPerson = remarksPerson;
	}
	
	@ExcelField(title="是否已成功发送短信，（1-已成功发送）", align=2, sort=56)
	public String getSendmsg() {
		return sendmsg;
	}

	public void setSendmsg(String sendmsg) {
		this.sendmsg = sendmsg;
	}
	
	@ExcelField(title="客户未读信息条数", align=2, sort=57)
	public String getUnreadMsg() {
		return unreadMsg;
	}

	public void setUnreadMsg(String unreadMsg) {
		this.unreadMsg = unreadMsg;
	}
	
	@ExcelField(title="产品组冗余字段", align=2, sort=58)
	public String getProGroup() {
		return proGroup;
	}

	public void setProGroup(String proGroup) {
		this.proGroup = proGroup;
	}
	
	@ExcelField(title="采购需求描述", align=2, sort=59)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public ShopMsgZykc getShopMsgZykc() {
		return shopMsgZykc;
	}

	public void setShopMsgZykc(ShopMsgZykc shopMsgZykc) {
		this.shopMsgZykc = shopMsgZykc;
	}
}