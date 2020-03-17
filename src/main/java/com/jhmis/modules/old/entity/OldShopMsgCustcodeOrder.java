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
 * @version 2019-12-05
 */
public class OldShopMsgCustcodeOrder extends DataEntity<OldShopMsgCustcodeOrder> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// code
	private String msgid;		// msgid
	private String product;		// 产品信息
	private String orgid;		// 工贸编号
	private String orgname;		// 工贸名称
	private String companyname;		// 公司名称
	private String fromsource;		// 订单来源  抢单   派单
	private String cancelfalg;		// 订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)
	private String canceler;		// 废弃人
	private Date canceltime;		// 废弃时间
	private String cancelreason;		// 废弃原因
	private String custname;		// 经销商名称
	private String custcode;		// 经销商编号
	private String address;		// 地址
	private String undertake;		// 承接方式
	private String memo;		// 备注信息
	private String totalcount;		// 预计订单金额
	private Date addtime;		// 生成订单时间
	private String isend;		// 交易是否结束 0 未结束  1 结束
	private String isbind;		// 是否中标 0 ,未中标，1中标，2 废标 10丢标
	private String timeoutflag;		// 选择承接方式是否超时,0 未超时 1 超时
	private String timeoutreason;		// 超时原因
	private Date bindtime;		// 中标时间
	private String infcode;		// 提交留言后返回的编号
	private String isinstall;		// 是否安装
	private String installpersion;		// 安装人
	private Date installtime;		// 安装时间
	private String isdeliver;		// 是否出库
	private String delivernum;		// 出库/物流  单号
	private Date delivertime;		// 出库时间/录入物流单号时间
	private String issign;		// 是否签收
	private String signurl;		// 上传签收凭证
	private Date signtime;		// 签收时间
	private String issunburn;		// 是否晒单
	private String isbackpass;		// 是否已回传400   0默认   1已回传
	private String orderstatus;		// 直单客单区分标识
	
	public OldShopMsgCustcodeOrder() {
		super();
	}

	public OldShopMsgCustcodeOrder(String id){
		super(id);
	}

	@ExcelField(title="code", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="msgid", align=2, sort=2)
	public String getMsgid() {
		return msgid;
	}

	public OldShopMsgCustcodeOrder setMsgid(String msgid) {
		this.msgid = msgid;
		return this;
	}
	
	@ExcelField(title="产品信息", align=2, sort=3)
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	@ExcelField(title="工贸编号", align=2, sort=4)
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@ExcelField(title="工贸名称", align=2, sort=5)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	@ExcelField(title="公司名称", align=2, sort=6)
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@ExcelField(title="订单来源  抢单   派单", align=2, sort=7)
	public String getFromsource() {
		return fromsource;
	}

	public void setFromsource(String fromsource) {
		this.fromsource = fromsource;
	}
	
	@ExcelField(title="订单是否废弃订单是否废弃(0未处理，1跟进中,2零售关闭，3工程关闭)", align=2, sort=8)
	public String getCancelfalg() {
		return cancelfalg;
	}

	public void setCancelfalg(String cancelfalg) {
		this.cancelfalg = cancelfalg;
	}
	
	@ExcelField(title="废弃人", align=2, sort=9)
	public String getCanceler() {
		return canceler;
	}

	public void setCanceler(String canceler) {
		this.canceler = canceler;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="废弃时间", align=2, sort=10)
	public Date getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}
	
	@ExcelField(title="废弃原因", align=2, sort=11)
	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
	
	@ExcelField(title="经销商名称", align=2, sort=12)
	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}
	
	@ExcelField(title="经销商编号", align=2, sort=13)
	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	@ExcelField(title="地址", align=2, sort=14)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="承接方式", align=2, sort=15)
	public String getUndertake() {
		return undertake;
	}

	public void setUndertake(String undertake) {
		this.undertake = undertake;
	}
	
	@ExcelField(title="备注信息", align=2, sort=16)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ExcelField(title="预计订单金额", align=2, sort=17)
	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="生成订单时间", align=2, sort=18)
	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	@ExcelField(title="交易是否结束 0 未结束  1 结束", align=2, sort=19)
	public String getIsend() {
		return isend;
	}

	public void setIsend(String isend) {
		this.isend = isend;
	}
	
	@ExcelField(title="是否中标 0 ,未中标，1中标，2 废标 10丢标", align=2, sort=20)
	public String getIsbind() {
		return isbind;
	}

	public void setIsbind(String isbind) {
		this.isbind = isbind;
	}
	
	@ExcelField(title="选择承接方式是否超时,0 未超时 1 超时", align=2, sort=21)
	public String getTimeoutflag() {
		return timeoutflag;
	}

	public void setTimeoutflag(String timeoutflag) {
		this.timeoutflag = timeoutflag;
	}
	
	@ExcelField(title="超时原因", align=2, sort=22)
	public String getTimeoutreason() {
		return timeoutreason;
	}

	public void setTimeoutreason(String timeoutreason) {
		this.timeoutreason = timeoutreason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="中标时间", align=2, sort=23)
	public Date getBindtime() {
		return bindtime;
	}

	public void setBindtime(Date bindtime) {
		this.bindtime = bindtime;
	}
	
	@ExcelField(title="提交留言后返回的编号", align=2, sort=24)
	public String getInfcode() {
		return infcode;
	}

	public void setInfcode(String infcode) {
		this.infcode = infcode;
	}
	
	@ExcelField(title="是否安装", align=2, sort=25)
	public String getIsinstall() {
		return isinstall;
	}

	public void setIsinstall(String isinstall) {
		this.isinstall = isinstall;
	}
	
	@ExcelField(title="安装人", align=2, sort=26)
	public String getInstallpersion() {
		return installpersion;
	}

	public void setInstallpersion(String installpersion) {
		this.installpersion = installpersion;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="安装时间", align=2, sort=27)
	public Date getInstalltime() {
		return installtime;
	}

	public void setInstalltime(Date installtime) {
		this.installtime = installtime;
	}
	
	@ExcelField(title="是否出库", align=2, sort=28)
	public String getIsdeliver() {
		return isdeliver;
	}

	public void setIsdeliver(String isdeliver) {
		this.isdeliver = isdeliver;
	}
	
	@ExcelField(title="出库/物流  单号", align=2, sort=29)
	public String getDelivernum() {
		return delivernum;
	}

	public void setDelivernum(String delivernum) {
		this.delivernum = delivernum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出库时间/录入物流单号时间", align=2, sort=30)
	public Date getDelivertime() {
		return delivertime;
	}

	public void setDelivertime(Date delivertime) {
		this.delivertime = delivertime;
	}
	
	@ExcelField(title="是否签收", align=2, sort=31)
	public String getIssign() {
		return issign;
	}

	public void setIssign(String issign) {
		this.issign = issign;
	}
	
	@ExcelField(title="上传签收凭证", align=2, sort=32)
	public String getSignurl() {
		return signurl;
	}

	public void setSignurl(String signurl) {
		this.signurl = signurl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="签收时间", align=2, sort=33)
	public Date getSigntime() {
		return signtime;
	}

	public void setSigntime(Date signtime) {
		this.signtime = signtime;
	}
	
	@ExcelField(title="是否晒单", align=2, sort=34)
	public String getIssunburn() {
		return issunburn;
	}

	public void setIssunburn(String issunburn) {
		this.issunburn = issunburn;
	}
	
	@ExcelField(title="是否已回传400   0默认   1已回传", align=2, sort=35)
	public String getIsbackpass() {
		return isbackpass;
	}

	public void setIsbackpass(String isbackpass) {
		this.isbackpass = isbackpass;
	}
	
	@ExcelField(title="直单客单区分标识", align=2, sort=36)
	public String getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
}