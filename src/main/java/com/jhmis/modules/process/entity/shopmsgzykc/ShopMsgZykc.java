/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.process.entity.shopmsgzykc;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;
import com.jhmis.modules.process.entity.shopmsg.ShopMsg;

/**
 * 零售相关Entity
 * @author hdx
 * @version 2019-10-11
 */
public class ShopMsgZykc extends DataEntity<ShopMsgZykc> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 需求id
	private String companyName;		// 提需求公司名称
	private String cancelType;		// 废弃类型 0 未废弃 1 用户废弃   2 经销商废弃 
	private String cancelReson;		// 废弃原因
	private String cancelDesc;		// 废弃描述
	private Date cancleDate;		// 废弃时间
	private String custcode;		// 经销商编码
	private String tradeCount;		// 交易金额
	private String isCheck;		// 平台审核是否通过 0 是 1否
	private String checker;		// 平台审核人
	private Date checkDate;		// 平台审核时间
	private String imageUrl;		// 上传凭证
	private String isClosed;		// 1 关闭
	private String orderId;		// 经销商订单id

	//补充的经销商订单字段
	private String isBind;

	//需求相关实体
	private ShopMsg shopMsg;

	public ShopMsg getShopMsg() {
		return shopMsg;
	}

	public void setShopMsg(ShopMsg shopMsg) {
		this.shopMsg = shopMsg;
	}

	public String getIsBind() {
		return isBind;
	}

	public void setIsBind(String isBind) {
		this.isBind = isBind;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public ShopMsgZykc() {
		super();
	}

	public ShopMsgZykc(String id){
		super(id);
	}

	@ExcelField(title="需求id", align=2, sort=1)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="提需求公司名称", align=2, sort=3)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="废弃类型 0 未废弃 1 用户废弃   2 经销商废弃 ", align=2, sort=4)
	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}
	
	@ExcelField(title="废弃原因", align=2, sort=5)
	public String getCancelReson() {
		return cancelReson;
	}

	public void setCancelReson(String cancelReson) {
		this.cancelReson = cancelReson;
	}
	
	@ExcelField(title="废弃描述", align=2, sort=6)
	public String getCancelDesc() {
		return cancelDesc;
	}

	public void setCancelDesc(String cancelDesc) {
		this.cancelDesc = cancelDesc;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="废弃时间", align=2, sort=7)
	public Date getCancleDate() {
		return cancleDate;
	}

	public void setCancleDate(Date cancleDate) {
		this.cancleDate = cancleDate;
	}
	
	@ExcelField(title="经销商编码", align=2, sort=8)
	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	@ExcelField(title="交易金额", align=2, sort=9)
	public String getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(String tradeCount) {
		this.tradeCount = tradeCount;
	}
	
	@ExcelField(title="平台审核是否通过 0 是 1否", align=2, sort=10)
	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}
	
	@ExcelField(title="平台审核人", align=2, sort=11)
	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="平台审核时间", align=2, sort=12)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@ExcelField(title="上传凭证", align=2, sort=13)
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@ExcelField(title="1 关闭", align=2, sort=14)
	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
}