/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopmsgorder;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;

/**
 * 经销商订单表Entity
 * @author hdx
 * @version 2019-09-26
 */
public class ShopMsgCustcodeOrder extends DataEntity<ShopMsgCustcodeOrder> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求ID
	private String orgId;		// 工贸编号
	private String orgName;		// 工贸名称
	private String companyName;		// 公司名称
	private String fromSource;		// 订单来源  抢单   派单
	private String cancelFlag;		// 订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)
	private String canceler;		// 废弃人
	private Date cancelDate;		// 废弃时间
	private String cancelReason;		// 废弃原因
	private String custName;		// 经销商名称
	private String custCode;		// 经销商编号
	private String address;		// 地址
	private String underTake;		// 承接方式
	private String memo;		// 备注信息
	private String totalCount;		// 预计订单金额
	private String isEnd;		// 交易是否结束 0 未结束  1 结束
	private String isBind;		// 是否中标 0 ,未中标，1中标，2 废标 10丢标
	private String timeoutFlag;		// 选择承接方式是否超时,0 未超时 1 超时
	private String timeoutReason;		// 超时原因
	private Date bindTime;		// 中标时间
	private String isInstall;		// 是否安装
	private String installPersion;		// 安装人
	private Date installDate;		// 安装时间
	private String isDeliver;		// 是否出库
	private String deliverNum;		// 出库/物流  单号
	private Date deliverTime;		// 出库时间/录入物流单号时间
	private String isSign;		// 是否签收
	private String signUrl;		// 上传签收凭证
	private Date signDate;		// 签收时间
	private String isSunburn;		// 是否晒单
	private String isBackpass;		// 是否已回传400   0默认   1已回传
	private String proGroup;		// 产品组冗余字段
	private String proGroupCode; // 产品组对应冗余字段

	private ShopMsg shopMsg;
	//扩展shopMsg
	private String provinceName;		// 省份名称
	private String cityName;		// 城市名称
	private String projectCode;		// 项目编码

	private String orderNo;        //订单编码，用于展示
	private String msgNo;          //订单编码，用于展示

	//是否是商空管理员
	private String isSkAdmin;

	public String getIsSkAdmin() {
		return isSkAdmin;
	}

	public void setIsSkAdmin(String isSkAdmin) {
		this.isSkAdmin = isSkAdmin;
	}

	public String getMsgNo() {
		return msgNo;
	}

	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	//时间between属性
	private Date beginCreateDate;
	private Date endCreateDate;

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public ShopMsg getShopMsg() {
		return shopMsg;
	}

	public void setShopMsg(ShopMsg shopMsg) {
		this.shopMsg = shopMsg;
	}

	public String getProGroup() {
		return proGroup;
	}

	public void setProGroup(String proGroup) {
		this.proGroup = proGroup;
	}

	public String getProGroupCode() {
		return proGroupCode;
	}

	public void setProGroupCode(String proGroupCode) {
		this.proGroupCode = proGroupCode;
	}

	public ShopMsgCustcodeOrder() {
		super();
	}

	public ShopMsgCustcodeOrder(String id){
		super(id);
	}

	@ExcelField(title="需求ID", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="工贸编号", align=2, sort=2)
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@ExcelField(title="工贸名称", align=2, sort=3)
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	@ExcelField(title="公司名称", align=2, sort=4)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="订单来源  抢单   派单", align=2, sort=5)
	public String getFromSource() {
		return fromSource;
	}

	public void setFromSource(String fromSource) {
		this.fromSource = fromSource;
	}
	
	@ExcelField(title="订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)", align=2, sort=6)
	public String getCancelFlag() {
		return cancelFlag;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}
	
	@ExcelField(title="废弃人", align=2, sort=7)
	public String getCanceler() {
		return canceler;
	}

	public void setCanceler(String canceler) {
		this.canceler = canceler;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="废弃时间", align=2, sort=8)
	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	
	@ExcelField(title="废弃原因", align=2, sort=9)
	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	
	@ExcelField(title="经销商名称", align=2, sort=10)
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	@ExcelField(title="经销商编号", align=2, sort=11)
	public String getCustCode() {
		return custCode;
	}

	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	
	@ExcelField(title="地址", align=2, sort=12)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="承接方式", align=2, sort=13)
	public String getUnderTake() {
		return underTake;
	}

	public void setUnderTake(String underTake) {
		this.underTake = underTake;
	}
	
	@ExcelField(title="备注信息", align=2, sort=14)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ExcelField(title="预计订单金额", align=2, sort=15)
	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	
	@ExcelField(title="交易是否结束 0 未结束  1 结束", align=2, sort=17)
	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
	
	@ExcelField(title="是否中标 0 ,未中标，1中标，2 废标 10丢标", align=2, sort=18)
	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}
	
	@ExcelField(title="选择承接方式是否超时,0 未超时 1 超时", align=2, sort=19)
	public String getTimeoutFlag() {
		return timeoutFlag;
	}

	public void setTimeoutFlag(String timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}
	
	@ExcelField(title="超时原因", align=2, sort=20)
	public String getTimeoutReason() {
		return timeoutReason;
	}

	public void setTimeoutReason(String timeoutReason) {
		this.timeoutReason = timeoutReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="中标时间", align=2, sort=21)
	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}
	
	@ExcelField(title="是否安装", align=2, sort=22)
	public String getIsInstall() {
		return isInstall;
	}

	public void setIsInstall(String isInstall) {
		this.isInstall = isInstall;
	}
	
	@ExcelField(title="安装人", align=2, sort=23)
	public String getInstallPersion() {
		return installPersion;
	}

	public void setInstallPersion(String installPersion) {
		this.installPersion = installPersion;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="安装时间", align=2, sort=24)
	public Date getInstallDate() {
		return installDate;
	}

	public void setInstallDate(Date installDate) {
		this.installDate = installDate;
	}
	
	@ExcelField(title="是否出库", align=2, sort=25)
	public String getIsDeliver() {
		return isDeliver;
	}

	public void setIsDeliver(String isDeliver) {
		this.isDeliver = isDeliver;
	}
	
	@ExcelField(title="出库/物流  单号", align=2, sort=26)
	public String getDeliverNum() {
		return deliverNum;
	}

	public void setDeliverNum(String deliverNum) {
		this.deliverNum = deliverNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出库时间/录入物流单号时间", align=2, sort=27)
	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}
	
	@ExcelField(title="是否签收", align=2, sort=28)
	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}
	
	@ExcelField(title="上传签收凭证", align=2, sort=29)
	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="签收时间", align=2, sort=30)
	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	@ExcelField(title="是否晒单", align=2, sort=31)
	public String getIsSunburn() {
		return isSunburn;
	}

	public void setIsSunburn(String isSunburn) {
		this.isSunburn = isSunburn;
	}
	
	@ExcelField(title="是否已回传400   0默认   1已回传", align=2, sort=32)
	public String getIsBackpass() {
		return isBackpass;
	}

	public void setIsBackpass(String isBackpass) {
		this.isBackpass = isBackpass;
	}
	
}