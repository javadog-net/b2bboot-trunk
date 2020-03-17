/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 客单漏斗项目Entity
 * @author hdx
 * @version 2019-04-16
 */
public class CustomerProjectInfo extends DataEntity<CustomerProjectInfo> {
	
	private static final long serialVersionUID = 1L;
	private String msgId;		// 商机ID
	private String projectcode;		// 项目编码
	private String projectname;		// 项目信息
	private String companyname;		// 甲方公司名称
	private String companysize;		// 公司规模
	private String contact;		// 甲方联系人
	private String contmobile;		// 甲方联系方式
	private String product;		// 产品组，产品组是一个List
	private String count;		// 购买数量
	private Date bindtime;		// 中标时间
	private String bindamount;		// 中标金额,项目中标后报价单的金额
	private String indus;		// 所在行业,行业门类，传递名称（汉字）
	private String address;		// 项目地址
	private String orgid;		// 工贸编码
	private String orgname;		// 工贸名称
	private String orgidcharge;		// 工贸责任人
	private String distributor;		// 承接经销商
	private String orderstate;		// 订单状态
	private String projectstate;		// 项目状态
	private Date losstime;		// 丢标时间
	private String nodename;		// 节点名称 R1、R4、R6
	private String nodestate;		// 节点状态
	private String causeloss;		// 丢标原因
	private String channel;		// 渠道来源
	private String descp;		// 备注
	private String infcode;		// 接口编码
	private String cancelflag;		// 是否作废  0：作废
	private String cancelreason;		// 作废原因
	private String cancelperson;		// 作废人
	private Date canceltime;		// 作废操作时间
	private String bz1;		// 预留字段1
	private String bz2;		// 预留字段2
	private String creater;		// 创建人编码
	private Date createdate;		// 创建时间
	private String lastupdater;		// 最后修改人编码
	private Date lastupdate;		// 最后修改时间
	private String allmoney;		// 预计总金额
	private String custcontact;		// 承接商联系人
	private String isvalid;		// 1是否有效,默认为1
	private String partyprjcompany;		// 甲方总公司战略签约进展
	private Date tenderdate;		// 预计招标时间
	private Date yjsigndate;		// 预计签约时间
	private String bidprogress;		// 投标进展
	private Date signdate;		// 签约日期
	private String constructionstate;		// 施工状态
	private String projecttype;		// 项目类型  C690，CSK (产品组为BA,BB,BD)传CSK;其他传C690
	private String resourceflag;		// 是否是形象工程：1:是,0:不是
	private String isnewcust;		// 是否新客户，根据是否复购判断？是的，目前可以为空（经销商）
	private String isnewuser;		// 是否新客户，（甲方）
	private String projectsourse;		// 项目来源，现在项目来源分为工程平台自建和企业
	private String isbidloss;		// 是否丢标0中标；1丢标
	private String statusfx;		// 丢标状态分析
	private String bidlossmoney;		// 丢标项目的报价单总额
	private String isfedbatch;		// 是否分批供货 1：是，0：否
	private String usergroup;		// 用户群
	private String usertype;		// 用户群类型
	private String expr1;		// 工程类型(太阳能工程类型)需求中有太阳能（FC）的必填
	private String expr2;		// 变更内容
	private String iszlsp;		// 是否是战略项目   1：是，0:否
	private String expr5;		// 战略项目工贸承担比例 战略项目=1，必填
	private String expr6;		// 战略项目总部承担比例 战略项目=1，必填
	private String oldprojectcode;		// 原项目编码（项目变更时必须传原项目编码）
	private Date r4savetime;		// R4节点第一次保存时间
	private String signprogress;		// 签约进展
	private String projectsSource;		// 来源

	public String getProjectsSource() {
		return projectsSource;
	}

	public void setProjectsSource(String projectsSource) {
		this.projectsSource = projectsSource;
	}

	public CustomerProjectInfo() {
		super();
	}

	public CustomerProjectInfo(String id){
		super(id);
	}

	@ExcelField(title="商机ID", align=2, sort=0)
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	
	@ExcelField(title="项目编码", align=2, sort=1)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="项目信息", align=2, sort=2)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="甲方公司名称", align=2, sort=3)
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@ExcelField(title="公司规模", align=2, sort=4)
	public String getCompanysize() {
		return companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}
	
	@ExcelField(title="甲方联系人", align=2, sort=5)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@ExcelField(title="甲方联系方式", align=2, sort=6)
	public String getContmobile() {
		return contmobile;
	}

	public void setContmobile(String contmobile) {
		this.contmobile = contmobile;
	}
	
	@ExcelField(title="产品组，产品组是一个List", align=2, sort=7)
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	@ExcelField(title="购买数量", align=2, sort=8)
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="中标时间", align=2, sort=9)
	public Date getBindtime() {
		return bindtime;
	}

	public void setBindtime(Date bindtime) {
		this.bindtime = bindtime;
	}
	
	@ExcelField(title="中标金额,项目中标后报价单的金额", align=2, sort=10)
	public String getBindamount() {
		return bindamount;
	}

	public void setBindamount(String bindamount) {
		this.bindamount = bindamount;
	}
	
	@ExcelField(title="所在行业,行业门类，传递名称（汉字）", align=2, sort=11)
	public String getIndus() {
		return indus;
	}

	public void setIndus(String indus) {
		this.indus = indus;
	}
	
	@ExcelField(title="项目地址", align=2, sort=12)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="工贸编码", align=2, sort=13)
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@ExcelField(title="工贸名称", align=2, sort=14)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	@ExcelField(title="工贸责任人", align=2, sort=15)
	public String getOrgidcharge() {
		return orgidcharge;
	}

	public void setOrgidcharge(String orgidcharge) {
		this.orgidcharge = orgidcharge;
	}
	
	@ExcelField(title="承接经销商", align=2, sort=16)
	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	
	@ExcelField(title="订单状态", align=2, sort=17)
	public String getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}
	
	@ExcelField(title="项目状态", align=2, sort=18)
	public String getProjectstate() {
		return projectstate;
	}

	public void setProjectstate(String projectstate) {
		this.projectstate = projectstate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="丢标时间", align=2, sort=19)
	public Date getLosstime() {
		return losstime;
	}

	public void setLosstime(Date losstime) {
		this.losstime = losstime;
	}
	
	@ExcelField(title="节点名称 R1、R4、R6", align=2, sort=20)
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	@ExcelField(title="节点状态", align=2, sort=21)
	public String getNodestate() {
		return nodestate;
	}

	public void setNodestate(String nodestate) {
		this.nodestate = nodestate;
	}
	
	@ExcelField(title="丢标原因", align=2, sort=22)
	public String getCauseloss() {
		return causeloss;
	}

	public void setCauseloss(String causeloss) {
		this.causeloss = causeloss;
	}
	
	@ExcelField(title="渠道来源", align=2, sort=23)
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@ExcelField(title="备注", align=2, sort=24)
	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}
	
	@ExcelField(title="接口编码", align=2, sort=25)
	public String getInfcode() {
		return infcode;
	}

	public void setInfcode(String infcode) {
		this.infcode = infcode;
	}
	
	@ExcelField(title="是否作废  0：作废", align=2, sort=26)
	public String getCancelflag() {
		return cancelflag;
	}

	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}
	
	@ExcelField(title="作废原因", align=2, sort=27)
	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
	
	@ExcelField(title="作废人", align=2, sort=28)
	public String getCancelperson() {
		return cancelperson;
	}

	public void setCancelperson(String cancelperson) {
		this.cancelperson = cancelperson;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="作废操作时间", align=2, sort=29)
	public Date getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(Date canceltime) {
		this.canceltime = canceltime;
	}
	
	@ExcelField(title="预留字段1", align=2, sort=30)
	public String getBz1() {
		return bz1;
	}

	public void setBz1(String bz1) {
		this.bz1 = bz1;
	}
	
	@ExcelField(title="预留字段2", align=2, sort=31)
	public String getBz2() {
		return bz2;
	}

	public void setBz2(String bz2) {
		this.bz2 = bz2;
	}
	
	@ExcelField(title="创建人编码", align=2, sort=32)
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="创建时间", align=2, sort=33)
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@ExcelField(title="最后修改人编码", align=2, sort=34)
	public String getLastupdater() {
		return lastupdater;
	}

	public void setLastupdater(String lastupdater) {
		this.lastupdater = lastupdater;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后修改时间", align=2, sort=35)
	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}
	
	@ExcelField(title="预计总金额", align=2, sort=36)
	public String getAllmoney() {
		return allmoney;
	}

	public void setAllmoney(String allmoney) {
		this.allmoney = allmoney;
	}
	
	@ExcelField(title="承接商联系人", align=2, sort=37)
	public String getCustcontact() {
		return custcontact;
	}

	public void setCustcontact(String custcontact) {
		this.custcontact = custcontact;
	}
	
	@ExcelField(title="1是否有效,默认为1", align=2, sort=38)
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@ExcelField(title="甲方总公司战略签约进展", align=2, sort=39)
	public String getPartyprjcompany() {
		return partyprjcompany;
	}

	public void setPartyprjcompany(String partyprjcompany) {
		this.partyprjcompany = partyprjcompany;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="预计招标时间", align=2, sort=40)
	public Date getTenderdate() {
		return tenderdate;
	}

	public void setTenderdate(Date tenderdate) {
		this.tenderdate = tenderdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="预计签约时间", align=2, sort=41)
	public Date getYjsigndate() {
		return yjsigndate;
	}

	public void setYjsigndate(Date yjsigndate) {
		this.yjsigndate = yjsigndate;
	}
	
	@ExcelField(title="投标进展", align=2, sort=42)
	public String getBidprogress() {
		return bidprogress;
	}

	public void setBidprogress(String bidprogress) {
		this.bidprogress = bidprogress;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="签约日期", align=2, sort=43)
	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}
	
	@ExcelField(title="施工状态", align=2, sort=44)
	public String getConstructionstate() {
		return constructionstate;
	}

	public void setConstructionstate(String constructionstate) {
		this.constructionstate = constructionstate;
	}
	
	@ExcelField(title="项目类型  C690，CSK (产品组为BA,BB,BD)传CSK;其他传C690", align=2, sort=45)
	public String getProjecttype() {
		return projecttype;
	}

	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
	
	@ExcelField(title="是否是形象工程：1:是,0:不是", align=2, sort=46)
	public String getResourceflag() {
		return resourceflag;
	}

	public void setResourceflag(String resourceflag) {
		this.resourceflag = resourceflag;
	}
	
	@ExcelField(title="是否新客户，根据是否复购判断？是的，目前可以为空（经销商）", align=2, sort=47)
	public String getIsnewcust() {
		return isnewcust;
	}

	public void setIsnewcust(String isnewcust) {
		this.isnewcust = isnewcust;
	}
	
	@ExcelField(title="是否新客户，（甲方）", align=2, sort=48)
	public String getIsnewuser() {
		return isnewuser;
	}

	public void setIsnewuser(String isnewuser) {
		this.isnewuser = isnewuser;
	}
	
	@ExcelField(title="项目来源，现在项目来源分为工程平台自建和企业", align=2, sort=49)
	public String getProjectsourse() {
		return projectsourse;
	}

	public void setProjectsourse(String projectsourse) {
		this.projectsourse = projectsourse;
	}
	
	@ExcelField(title="是否丢标0中标；1丢标", align=2, sort=50)
	public String getIsbidloss() {
		return isbidloss;
	}

	public void setIsbidloss(String isbidloss) {
		this.isbidloss = isbidloss;
	}
	
	@ExcelField(title="丢标状态分析", align=2, sort=51)
	public String getStatusfx() {
		return statusfx;
	}

	public void setStatusfx(String statusfx) {
		this.statusfx = statusfx;
	}
	
	@ExcelField(title="丢标项目的报价单总额", align=2, sort=52)
	public String getBidlossmoney() {
		return bidlossmoney;
	}

	public void setBidlossmoney(String bidlossmoney) {
		this.bidlossmoney = bidlossmoney;
	}
	
	@ExcelField(title="是否分批供货 1：是，0：否", align=2, sort=53)
	public String getIsfedbatch() {
		return isfedbatch;
	}

	public void setIsfedbatch(String isfedbatch) {
		this.isfedbatch = isfedbatch;
	}
	
	@ExcelField(title="用户群", align=2, sort=54)
	public String getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	
	@ExcelField(title="用户群类型", align=2, sort=55)
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@ExcelField(title="工程类型(太阳能工程类型)需求中有太阳能（FC）的必填", align=2, sort=56)
	public String getExpr1() {
		return expr1;
	}

	public void setExpr1(String expr1) {
		this.expr1 = expr1;
	}
	
	@ExcelField(title="变更内容", align=2, sort=57)
	public String getExpr2() {
		return expr2;
	}

	public void setExpr2(String expr2) {
		this.expr2 = expr2;
	}
	
	@ExcelField(title="是否是战略项目   1：是，0:否", align=2, sort=58)
	public String getIszlsp() {
		return iszlsp;
	}

	public void setIszlsp(String iszlsp) {
		this.iszlsp = iszlsp;
	}
	
	@ExcelField(title="战略项目工贸承担比例 战略项目=1，必填", align=2, sort=59)
	public String getExpr5() {
		return expr5;
	}

	public void setExpr5(String expr5) {
		this.expr5 = expr5;
	}
	
	@ExcelField(title="战略项目总部承担比例 战略项目=1，必填", align=2, sort=60)
	public String getExpr6() {
		return expr6;
	}

	public void setExpr6(String expr6) {
		this.expr6 = expr6;
	}
	
	@ExcelField(title="原项目编码（项目变更时必须传原项目编码）", align=2, sort=61)
	public String getOldprojectcode() {
		return oldprojectcode;
	}

	public void setOldprojectcode(String oldprojectcode) {
		this.oldprojectcode = oldprojectcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="R4节点第一次保存时间", align=2, sort=62)
	public Date getR4savetime() {
		return r4savetime;
	}

	public void setR4savetime(Date r4savetime) {
		this.r4savetime = r4savetime;
	}
	
	@ExcelField(title="签约进展", align=2, sort=63)
	public String getSignprogress() {
		return signprogress;
	}

	public void setSignprogress(String signprogress) {
		this.signprogress = signprogress;
	}
	
}