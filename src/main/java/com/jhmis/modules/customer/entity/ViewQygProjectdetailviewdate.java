/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.customer.entity;


import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * aEntity
 * @author a
 * @version 2019-10-29
 */
public class ViewQygProjectdetailviewdate extends DataEntity<ViewQygProjectdetailviewdate> {
	
	private static final long serialVersionUID = 1L;
	private String msgid;		// msgid
	private String projectname;		// projectname
	private String projectcode;		// projectcode
	private String companyname;		// companyname
	private String loginlevel;		// loginlevel
	private String funnelstage;		// funnelstage
	private String operatestatus;		// operatestatus
	private String operatename;		// operatename
	private String operatedescription;		// operatedescription
	private String companysize;		// companysize
	private String contact;		// contact
	private String contmobile;		// contmobile
	private String count;		// count
	private String bindtime;		// bindtime
	private String bindamount;		// bindamount
	private String indus;		// indus
	private String address;		// address
	private String orgid;		// orgid
	private String orgname;		// orgname
	private String orgidcharge;		// orgidcharge
	private String distributor;		// distributor
	private String orderstate;		// orderstate
	private String projectstate;		// projectstate
	private String losstime;		// losstime
	private String nodename;		// nodename
	private String nodestate;		// nodestate
	private String causeloss;		// causeloss
	private String cancelflag;		// cancelflag
	private String cancelreason;		// cancelreason
	private String cancelperson;		// cancelperson
	private String canceltime;		// canceltime
	private String creater;		// creater
	private String createdate;		// createdate
	private String lastupdater;		// lastupdater
	private String lastupdate;		// lastupdate
	private String allmoney;		// allmoney
	private String custcontact;		// custcontact
	private String isvalid;		// isvalid
	private String partyprjcompany;		// partyprjcompany
	private String tenderdate;		// tenderdate
	private String yjsigndate;		// yjsigndate
	private String bidprogress;		// bidprogress
	private String signdate;		// signdate
	private String constructionstate;		// constructionstate
	private String projecttype;		// projecttype
	private String resourceflag;		// resourceflag
	private String isnewcust;		// isnewcust
	private String isnewuser;		// isnewuser
	private String projectsourse;		// projectsourse
	private String isbidloss;		// isbidloss
	private String statusfx;		// statusfx
	private String bidlossmoney;		// bidlossmoney
	private String isfedbatch;		// isfedbatch
	private String usergroup;		// usergroup
	private String usertype;		// usertype
	private String expr1;		// expr1
	private String expr2;		// expr2
	private String iszlsp;		// iszlsp
	private String expr5;		// expr5
	private String expr6;		// expr6
	private String oldprojectcode;		// oldprojectcode
	private String r4savetime;		// r4savetime
	private String signprogress;		// signprogress
	
	public ViewQygProjectdetailviewdate() {
		super();
	}

	public ViewQygProjectdetailviewdate(String id){
		super(id);
	}

	@ExcelField(title="msgid", align=2, sort=0)
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	@ExcelField(title="projectname", align=2, sort=1)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="projectcode", align=2, sort=2)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="companyname", align=2, sort=3)
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@ExcelField(title="loginlevel", align=2, sort=4)
	public String getLoginlevel() {
		return loginlevel;
	}

	public void setLoginlevel(String loginlevel) {
		this.loginlevel = loginlevel;
	}
	
	@ExcelField(title="funnelstage", align=2, sort=5)
	public String getFunnelstage() {
		return funnelstage;
	}

	public void setFunnelstage(String funnelstage) {
		this.funnelstage = funnelstage;
	}
	
	@ExcelField(title="operatestatus", align=2, sort=6)
	public String getOperatestatus() {
		return operatestatus;
	}

	public void setOperatestatus(String operatestatus) {
		this.operatestatus = operatestatus;
	}
	
	@ExcelField(title="operatename", align=2, sort=7)
	public String getOperatename() {
		return operatename;
	}

	public void setOperatename(String operatename) {
		this.operatename = operatename;
	}
	
	@ExcelField(title="operatedescription", align=2, sort=8)
	public String getOperatedescription() {
		return operatedescription;
	}

	public void setOperatedescription(String operatedescription) {
		this.operatedescription = operatedescription;
	}
	
	@ExcelField(title="companysize", align=2, sort=9)
	public String getCompanysize() {
		return companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}
	
	@ExcelField(title="contact", align=2, sort=10)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@ExcelField(title="contmobile", align=2, sort=11)
	public String getContmobile() {
		return contmobile;
	}

	public void setContmobile(String contmobile) {
		this.contmobile = contmobile;
	}
	
	@ExcelField(title="count", align=2, sort=12)
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@ExcelField(title="bindtime", align=2, sort=13)
	public String getBindtime() {
		return bindtime;
	}

	public void setBindtime(String bindtime) {
		this.bindtime = bindtime;
	}
	
	@ExcelField(title="bindamount", align=2, sort=14)
	public String getBindamount() {
		return bindamount;
	}

	public void setBindamount(String bindamount) {
		this.bindamount = bindamount;
	}
	
	@ExcelField(title="indus", align=2, sort=15)
	public String getIndus() {
		return indus;
	}

	public void setIndus(String indus) {
		this.indus = indus;
	}
	
	@ExcelField(title="address", align=2, sort=16)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="orgid", align=2, sort=17)
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@ExcelField(title="orgname", align=2, sort=18)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	@ExcelField(title="orgidcharge", align=2, sort=19)
	public String getOrgidcharge() {
		return orgidcharge;
	}

	public void setOrgidcharge(String orgidcharge) {
		this.orgidcharge = orgidcharge;
	}
	
	@ExcelField(title="distributor", align=2, sort=20)
	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
	
	@ExcelField(title="orderstate", align=2, sort=21)
	public String getOrderstate() {
		return orderstate;
	}

	public void setOrderstate(String orderstate) {
		this.orderstate = orderstate;
	}
	
	@ExcelField(title="projectstate", align=2, sort=22)
	public String getProjectstate() {
		return projectstate;
	}

	public void setProjectstate(String projectstate) {
		this.projectstate = projectstate;
	}
	
	@ExcelField(title="losstime", align=2, sort=23)
	public String getLosstime() {
		return losstime;
	}

	public void setLosstime(String losstime) {
		this.losstime = losstime;
	}
	
	@ExcelField(title="nodename", align=2, sort=24)
	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	@ExcelField(title="nodestate", align=2, sort=25)
	public String getNodestate() {
		return nodestate;
	}

	public void setNodestate(String nodestate) {
		this.nodestate = nodestate;
	}
	
	@ExcelField(title="causeloss", align=2, sort=26)
	public String getCauseloss() {
		return causeloss;
	}

	public void setCauseloss(String causeloss) {
		this.causeloss = causeloss;
	}
	
	@ExcelField(title="cancelflag", align=2, sort=27)
	public String getCancelflag() {
		return cancelflag;
	}

	public void setCancelflag(String cancelflag) {
		this.cancelflag = cancelflag;
	}
	
	@ExcelField(title="cancelreason", align=2, sort=28)
	public String getCancelreason() {
		return cancelreason;
	}

	public void setCancelreason(String cancelreason) {
		this.cancelreason = cancelreason;
	}
	
	@ExcelField(title="cancelperson", align=2, sort=29)
	public String getCancelperson() {
		return cancelperson;
	}

	public void setCancelperson(String cancelperson) {
		this.cancelperson = cancelperson;
	}
	
	@ExcelField(title="canceltime", align=2, sort=30)
	public String getCanceltime() {
		return canceltime;
	}

	public void setCanceltime(String canceltime) {
		this.canceltime = canceltime;
	}
	
	@ExcelField(title="creater", align=2, sort=31)
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	@ExcelField(title="createdate", align=2, sort=32)
	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	@ExcelField(title="lastupdater", align=2, sort=33)
	public String getLastupdater() {
		return lastupdater;
	}

	public void setLastupdater(String lastupdater) {
		this.lastupdater = lastupdater;
	}
	
	@ExcelField(title="lastupdate", align=2, sort=34)
	public String getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}
	
	@ExcelField(title="allmoney", align=2, sort=35)
	public String getAllmoney() {
		return allmoney;
	}

	public void setAllmoney(String allmoney) {
		this.allmoney = allmoney;
	}
	
	@ExcelField(title="custcontact", align=2, sort=36)
	public String getCustcontact() {
		return custcontact;
	}

	public void setCustcontact(String custcontact) {
		this.custcontact = custcontact;
	}
	
	@ExcelField(title="isvalid", align=2, sort=37)
	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	
	@ExcelField(title="partyprjcompany", align=2, sort=38)
	public String getPartyprjcompany() {
		return partyprjcompany;
	}

	public void setPartyprjcompany(String partyprjcompany) {
		this.partyprjcompany = partyprjcompany;
	}
	
	@ExcelField(title="tenderdate", align=2, sort=39)
	public String getTenderdate() {
		return tenderdate;
	}

	public void setTenderdate(String tenderdate) {
		this.tenderdate = tenderdate;
	}
	
	@ExcelField(title="yjsigndate", align=2, sort=40)
	public String getYjsigndate() {
		return yjsigndate;
	}

	public void setYjsigndate(String yjsigndate) {
		this.yjsigndate = yjsigndate;
	}
	
	@ExcelField(title="bidprogress", align=2, sort=41)
	public String getBidprogress() {
		return bidprogress;
	}

	public void setBidprogress(String bidprogress) {
		this.bidprogress = bidprogress;
	}
	
	@ExcelField(title="signdate", align=2, sort=42)
	public String getSigndate() {
		return signdate;
	}

	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}
	
	@ExcelField(title="constructionstate", align=2, sort=43)
	public String getConstructionstate() {
		return constructionstate;
	}

	public void setConstructionstate(String constructionstate) {
		this.constructionstate = constructionstate;
	}
	
	@ExcelField(title="projecttype", align=2, sort=44)
	public String getProjecttype() {
		return projecttype;
	}

	public void setProjecttype(String projecttype) {
		this.projecttype = projecttype;
	}
	
	@ExcelField(title="resourceflag", align=2, sort=45)
	public String getResourceflag() {
		return resourceflag;
	}

	public void setResourceflag(String resourceflag) {
		this.resourceflag = resourceflag;
	}
	
	@ExcelField(title="isnewcust", align=2, sort=46)
	public String getIsnewcust() {
		return isnewcust;
	}

	public void setIsnewcust(String isnewcust) {
		this.isnewcust = isnewcust;
	}
	
	@ExcelField(title="isnewuser", align=2, sort=47)
	public String getIsnewuser() {
		return isnewuser;
	}

	public void setIsnewuser(String isnewuser) {
		this.isnewuser = isnewuser;
	}
	
	@ExcelField(title="projectsourse", align=2, sort=48)
	public String getProjectsourse() {
		return projectsourse;
	}

	public void setProjectsourse(String projectsourse) {
		this.projectsourse = projectsourse;
	}
	
	@ExcelField(title="isbidloss", align=2, sort=49)
	public String getIsbidloss() {
		return isbidloss;
	}

	public void setIsbidloss(String isbidloss) {
		this.isbidloss = isbidloss;
	}
	
	@ExcelField(title="statusfx", align=2, sort=50)
	public String getStatusfx() {
		return statusfx;
	}

	public void setStatusfx(String statusfx) {
		this.statusfx = statusfx;
	}
	
	@ExcelField(title="bidlossmoney", align=2, sort=51)
	public String getBidlossmoney() {
		return bidlossmoney;
	}

	public void setBidlossmoney(String bidlossmoney) {
		this.bidlossmoney = bidlossmoney;
	}
	
	@ExcelField(title="isfedbatch", align=2, sort=52)
	public String getIsfedbatch() {
		return isfedbatch;
	}

	public void setIsfedbatch(String isfedbatch) {
		this.isfedbatch = isfedbatch;
	}
	
	@ExcelField(title="usergroup", align=2, sort=53)
	public String getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	
	@ExcelField(title="usertype", align=2, sort=54)
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@ExcelField(title="expr1", align=2, sort=55)
	public String getExpr1() {
		return expr1;
	}

	public void setExpr1(String expr1) {
		this.expr1 = expr1;
	}
	
	@ExcelField(title="expr2", align=2, sort=56)
	public String getExpr2() {
		return expr2;
	}

	public void setExpr2(String expr2) {
		this.expr2 = expr2;
	}
	
	@ExcelField(title="iszlsp", align=2, sort=57)
	public String getIszlsp() {
		return iszlsp;
	}

	public void setIszlsp(String iszlsp) {
		this.iszlsp = iszlsp;
	}
	
	@ExcelField(title="expr5", align=2, sort=58)
	public String getExpr5() {
		return expr5;
	}

	public void setExpr5(String expr5) {
		this.expr5 = expr5;
	}
	
	@ExcelField(title="expr6", align=2, sort=59)
	public String getExpr6() {
		return expr6;
	}

	public void setExpr6(String expr6) {
		this.expr6 = expr6;
	}
	
	@ExcelField(title="oldprojectcode", align=2, sort=60)
	public String getOldprojectcode() {
		return oldprojectcode;
	}

	public void setOldprojectcode(String oldprojectcode) {
		this.oldprojectcode = oldprojectcode;
	}
	
	@ExcelField(title="r4savetime", align=2, sort=61)
	public String getR4savetime() {
		return r4savetime;
	}

	public void setR4savetime(String r4savetime) {
		this.r4savetime = r4savetime;
	}
	
	@ExcelField(title="signprogress", align=2, sort=62)
	public String getSignprogress() {
		return signprogress;
	}

	public void setSignprogress(String signprogress) {
		this.signprogress = signprogress;
	}
	
}