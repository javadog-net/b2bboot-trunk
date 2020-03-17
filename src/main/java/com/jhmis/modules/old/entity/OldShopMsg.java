/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.old.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * oldEntity
 * @author old
 * @version 2019-12-07
 */
public class OldShopMsg extends DataEntity<OldShopMsg> {
	
	private static final long serialVersionUID = 1L;
	private String provinceid;		// provinceid
	private String provincename;		// provincename
	private String cityid;		// cityid
	private String cityname;		// cityname
	private String orgid;		// 工贸编码
	private String orgname;		// orgname
	private String product;		// 产品组
	private String count;		// count
	private String money;		// 金额
	private Date createtime;		// 留言时间
	private String channel;		// 渠道来源
	private String companyname;		// 公司
	private String companysize;		// 公司规模
	private String keypersion;		// keypersion
	private String keymobile;		// keymobile
	private String reservemobile;		// reservemobile
	private String adress;		// adress
	private String indus;		// 行业
	private String infcode;		// 提交留言后返回的编号
	private String flag;		// 抢单使用的字段（是否分抢单 Y：是，N：否）
	private String custcode;		// 经销商编码(Flag=Y时,经销商编码不能为空)
	private String oldcode;		// 历史单号
	private String msgvalid;		// 留言是否有效 0 有效 1无效
	private String memberid;		// memberid
	private Date subtime;		// 提交时间
	private String quotainfo;		// 报价信息
	private String msgflag;		// 留言是否已提交 defalut 0  0 未提交 1已提交
	private String memo;		// 备注
	private Date approtime;		// approtime
	private String isqd;		// isqd
	private String qq;		// qq
	private String email;		// email
	private String ptchecker;		// 平台审核人
	private String ptispass;		// 平台审核是否通过
	private Date ptchecktime;		// 平台审核时间
	private String ptdescp;		// 平台审核备注
	private String isdispatch;		// 是否派单
	private String appchecker;		// APP 审核人
	private String appdescp;		// app审核原因
	private String appispass;		// 平台审核是否通过
	private String ordertype;		// 直单、客单
	private Date grabtime;		// 抢单时间
	private String mimgurl;		// 直单凭证
	private Date uploadtime;		// 直单凭证上传时间
	private String stracheck;		// 直单审核是否通过
	private String strachecker;		// 直单审核人
	private Date strachecktime;		// 直单审核时间
	private String msgisclose;		// 留言是否关闭 0 否 1是
	private String haveundertake;		// 是否有承接经销商 0 没有  1  有
	private String skmodel;		// 商空机型 (0小,1大,2全部)
	private String skproductd;		// 商空产品详细信息
	private String acgprojectid;		// 提交至ACG返回的项目信息
	private String projectcode;		// 项目编码
	private String depart;		// 部门
	private String smallprojectid;		// 小机的项目ID
	private String category;		// 需求分类（询价、代理、邀标、资料获取、品牌合作、文化交流）
	private String isback;		// 是否反馈，1是，0否
	private String dataurl;		// 资料地址
	private String unreadmsg;		// 未读消息条数
	private String cooperationandexchange;		// 合作交流
	private String nodetag;		// 节点状态
	private String isread;		// 未读0  已读1
	private String projectname;		// projectname
	private String managerno;		// 指派人编号
	private String managername;		// 指派人名称
	private String conditionermembertype;		// 商空会员类型
	private Date remarkstime;		// 总监备注填写时间
	private String remarksperson;		// 填写备注的总监编号
	private String hpsprovinceid;		// hps系统对应省份id
	private String hpscityid;		// hps系统对应城市id
	private String hpsdistrictid;		// hps系统对应地区id
	private String firstcompanyorgcode;		// 项目负责人编号
	private String firstcompanyorgname;		// 项目负责人名称
	private String areadetails;		// 详细地址	utf8
	private String sendmsg;		// 是否已成功发送短信，（1-已成功发送）
	
	public OldShopMsg() {
		super();
	}

	public OldShopMsg(String id){
		super(id);
	}

	@ExcelField(title="provinceid", align=2, sort=1)
	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	
	@ExcelField(title="provincename", align=2, sort=2)
	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	
	@ExcelField(title="cityid", align=2, sort=3)
	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	@ExcelField(title="cityname", align=2, sort=4)
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
	@ExcelField(title="工贸编码", align=2, sort=5)
	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	
	@ExcelField(title="orgname", align=2, sort=6)
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
	
	@ExcelField(title="产品组", align=2, sort=7)
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
	@ExcelField(title="count", align=2, sort=8)
	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
	@ExcelField(title="金额", align=2, sort=9)
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="留言时间不能为空")
	@ExcelField(title="留言时间", align=2, sort=10)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@ExcelField(title="渠道来源", align=2, sort=11)
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@ExcelField(title="公司", align=2, sort=12)
	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	
	@ExcelField(title="公司规模", align=2, sort=13)
	public String getCompanysize() {
		return companysize;
	}

	public void setCompanysize(String companysize) {
		this.companysize = companysize;
	}
	
	@ExcelField(title="keypersion", align=2, sort=14)
	public String getKeypersion() {
		return keypersion;
	}

	public void setKeypersion(String keypersion) {
		this.keypersion = keypersion;
	}
	
	@ExcelField(title="keymobile", align=2, sort=15)
	public String getKeymobile() {
		return keymobile;
	}

	public void setKeymobile(String keymobile) {
		this.keymobile = keymobile;
	}
	
	@ExcelField(title="reservemobile", align=2, sort=16)
	public String getReservemobile() {
		return reservemobile;
	}

	public void setReservemobile(String reservemobile) {
		this.reservemobile = reservemobile;
	}
	
	@ExcelField(title="adress", align=2, sort=17)
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	@ExcelField(title="行业", align=2, sort=18)
	public String getIndus() {
		return indus;
	}

	public void setIndus(String indus) {
		this.indus = indus;
	}
	
	@ExcelField(title="提交留言后返回的编号", align=2, sort=19)
	public String getInfcode() {
		return infcode;
	}

	public void setInfcode(String infcode) {
		this.infcode = infcode;
	}
	
	@ExcelField(title="抢单使用的字段（是否分抢单 Y：是，N：否）", align=2, sort=20)
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@ExcelField(title="经销商编码(Flag=Y时,经销商编码不能为空)", align=2, sort=21)
	public String getCustcode() {
		return custcode;
	}

	public void setCustcode(String custcode) {
		this.custcode = custcode;
	}
	
	@ExcelField(title="历史单号", align=2, sort=22)
	public String getOldcode() {
		return oldcode;
	}

	public void setOldcode(String oldcode) {
		this.oldcode = oldcode;
	}
	
	@ExcelField(title="留言是否有效 0 有效 1无效", align=2, sort=23)
	public String getMsgvalid() {
		return msgvalid;
	}

	public void setMsgvalid(String msgvalid) {
		this.msgvalid = msgvalid;
	}
	
	@ExcelField(title="memberid", align=2, sort=24)
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="提交时间", align=2, sort=25)
	public Date getSubtime() {
		return subtime;
	}

	public void setSubtime(Date subtime) {
		this.subtime = subtime;
	}
	
	@ExcelField(title="报价信息", align=2, sort=26)
	public String getQuotainfo() {
		return quotainfo;
	}

	public void setQuotainfo(String quotainfo) {
		this.quotainfo = quotainfo;
	}
	
	@ExcelField(title="留言是否已提交 defalut 0  0 未提交 1已提交", align=2, sort=27)
	public String getMsgflag() {
		return msgflag;
	}

	public void setMsgflag(String msgflag) {
		this.msgflag = msgflag;
	}
	
	@ExcelField(title="备注", align=2, sort=28)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="approtime", align=2, sort=29)
	public Date getApprotime() {
		return approtime;
	}

	public void setApprotime(Date approtime) {
		this.approtime = approtime;
	}
	
	@ExcelField(title="isqd", align=2, sort=30)
	public String getIsqd() {
		return isqd;
	}

	public void setIsqd(String isqd) {
		this.isqd = isqd;
	}
	
	@ExcelField(title="qq", align=2, sort=31)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@ExcelField(title="email", align=2, sort=32)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@ExcelField(title="平台审核人", align=2, sort=33)
	public String getPtchecker() {
		return ptchecker;
	}

	public void setPtchecker(String ptchecker) {
		this.ptchecker = ptchecker;
	}
	
	@ExcelField(title="平台审核是否通过", align=2, sort=34)
	public String getPtispass() {
		return ptispass;
	}

	public void setPtispass(String ptispass) {
		this.ptispass = ptispass;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="平台审核时间", align=2, sort=35)
	public Date getPtchecktime() {
		return ptchecktime;
	}

	public void setPtchecktime(Date ptchecktime) {
		this.ptchecktime = ptchecktime;
	}
	
	@ExcelField(title="平台审核备注", align=2, sort=36)
	public String getPtdescp() {
		return ptdescp;
	}

	public void setPtdescp(String ptdescp) {
		this.ptdescp = ptdescp;
	}
	
	@ExcelField(title="是否派单", align=2, sort=37)
	public String getIsdispatch() {
		return isdispatch;
	}

	public void setIsdispatch(String isdispatch) {
		this.isdispatch = isdispatch;
	}
	
	@ExcelField(title="APP 审核人", align=2, sort=38)
	public String getAppchecker() {
		return appchecker;
	}

	public void setAppchecker(String appchecker) {
		this.appchecker = appchecker;
	}
	
	@ExcelField(title="app审核原因", align=2, sort=39)
	public String getAppdescp() {
		return appdescp;
	}

	public void setAppdescp(String appdescp) {
		this.appdescp = appdescp;
	}
	
	@ExcelField(title="平台审核是否通过", align=2, sort=40)
	public String getAppispass() {
		return appispass;
	}

	public void setAppispass(String appispass) {
		this.appispass = appispass;
	}
	
	@ExcelField(title="直单、客单", align=2, sort=41)
	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="抢单时间", align=2, sort=42)
	public Date getGrabtime() {
		return grabtime;
	}

	public void setGrabtime(Date grabtime) {
		this.grabtime = grabtime;
	}
	
	@ExcelField(title="直单凭证", align=2, sort=43)
	public String getMimgurl() {
		return mimgurl;
	}

	public void setMimgurl(String mimgurl) {
		this.mimgurl = mimgurl;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="直单凭证上传时间", align=2, sort=44)
	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	@ExcelField(title="直单审核是否通过", align=2, sort=45)
	public String getStracheck() {
		return stracheck;
	}

	public void setStracheck(String stracheck) {
		this.stracheck = stracheck;
	}
	
	@ExcelField(title="直单审核人", align=2, sort=46)
	public String getStrachecker() {
		return strachecker;
	}

	public void setStrachecker(String strachecker) {
		this.strachecker = strachecker;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="直单审核时间", align=2, sort=47)
	public Date getStrachecktime() {
		return strachecktime;
	}

	public void setStrachecktime(Date strachecktime) {
		this.strachecktime = strachecktime;
	}
	
	@ExcelField(title="留言是否关闭 0 否 1是", align=2, sort=48)
	public String getMsgisclose() {
		return msgisclose;
	}

	public void setMsgisclose(String msgisclose) {
		this.msgisclose = msgisclose;
	}
	
	@ExcelField(title="是否有承接经销商 0 没有  1  有", align=2, sort=49)
	public String getHaveundertake() {
		return haveundertake;
	}

	public void setHaveundertake(String haveundertake) {
		this.haveundertake = haveundertake;
	}
	
	@ExcelField(title="商空机型 (0小,1大,2全部)", align=2, sort=50)
	public String getSkmodel() {
		return skmodel;
	}

	public void setSkmodel(String skmodel) {
		this.skmodel = skmodel;
	}
	
	@ExcelField(title="商空产品详细信息", align=2, sort=51)
	public String getSkproductd() {
		return skproductd;
	}

	public void setSkproductd(String skproductd) {
		this.skproductd = skproductd;
	}
	
	@ExcelField(title="提交至ACG返回的项目信息", align=2, sort=52)
	public String getAcgprojectid() {
		return acgprojectid;
	}

	public void setAcgprojectid(String acgprojectid) {
		this.acgprojectid = acgprojectid;
	}
	
	@ExcelField(title="项目编码", align=2, sort=53)
	public String getProjectcode() {
		return projectcode;
	}

	public void setProjectcode(String projectcode) {
		this.projectcode = projectcode;
	}
	
	@ExcelField(title="部门", align=2, sort=54)
	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}
	
	@ExcelField(title="小机的项目ID", align=2, sort=55)
	public String getSmallprojectid() {
		return smallprojectid;
	}

	public void setSmallprojectid(String smallprojectid) {
		this.smallprojectid = smallprojectid;
	}
	
	@ExcelField(title="需求分类（询价、代理、邀标、资料获取、品牌合作、文化交流）", align=2, sort=56)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@ExcelField(title="是否反馈，1是，0否", align=2, sort=57)
	public String getIsback() {
		return isback;
	}

	public void setIsback(String isback) {
		this.isback = isback;
	}
	
	@ExcelField(title="资料地址", align=2, sort=58)
	public String getDataurl() {
		return dataurl;
	}

	public void setDataurl(String dataurl) {
		this.dataurl = dataurl;
	}
	
	@ExcelField(title="未读消息条数", align=2, sort=59)
	public String getUnreadmsg() {
		return unreadmsg;
	}

	public void setUnreadmsg(String unreadmsg) {
		this.unreadmsg = unreadmsg;
	}
	
	@ExcelField(title="合作交流", align=2, sort=60)
	public String getCooperationandexchange() {
		return cooperationandexchange;
	}

	public void setCooperationandexchange(String cooperationandexchange) {
		this.cooperationandexchange = cooperationandexchange;
	}
	
	@ExcelField(title="节点状态", align=2, sort=61)
	public String getNodetag() {
		return nodetag;
	}

	public void setNodetag(String nodetag) {
		this.nodetag = nodetag;
	}
	
	@ExcelField(title="未读0  已读1", align=2, sort=62)
	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}
	
	@ExcelField(title="projectname", align=2, sort=63)
	public String getProjectname() {
		return projectname;
	}

	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	@ExcelField(title="指派人编号", align=2, sort=64)
	public String getManagerno() {
		return managerno;
	}

	public void setManagerno(String managerno) {
		this.managerno = managerno;
	}
	
	@ExcelField(title="指派人名称", align=2, sort=65)
	public String getManagername() {
		return managername;
	}

	public void setManagername(String managername) {
		this.managername = managername;
	}
	
	@ExcelField(title="商空会员类型", align=2, sort=66)
	public String getConditionermembertype() {
		return conditionermembertype;
	}

	public void setConditionermembertype(String conditionermembertype) {
		this.conditionermembertype = conditionermembertype;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="总监备注填写时间", align=2, sort=68)
	public Date getRemarkstime() {
		return remarkstime;
	}

	public void setRemarkstime(Date remarkstime) {
		this.remarkstime = remarkstime;
	}
	
	@ExcelField(title="填写备注的总监编号", align=2, sort=69)
	public String getRemarksperson() {
		return remarksperson;
	}

	public void setRemarksperson(String remarksperson) {
		this.remarksperson = remarksperson;
	}
	
	@ExcelField(title="hps系统对应省份id", align=2, sort=70)
	public String getHpsprovinceid() {
		return hpsprovinceid;
	}

	public void setHpsprovinceid(String hpsprovinceid) {
		this.hpsprovinceid = hpsprovinceid;
	}
	
	@ExcelField(title="hps系统对应城市id", align=2, sort=71)
	public String getHpscityid() {
		return hpscityid;
	}

	public void setHpscityid(String hpscityid) {
		this.hpscityid = hpscityid;
	}
	
	@ExcelField(title="hps系统对应地区id", align=2, sort=72)
	public String getHpsdistrictid() {
		return hpsdistrictid;
	}

	public void setHpsdistrictid(String hpsdistrictid) {
		this.hpsdistrictid = hpsdistrictid;
	}
	
	@ExcelField(title="项目负责人编号", align=2, sort=73)
	public String getFirstcompanyorgcode() {
		return firstcompanyorgcode;
	}

	public void setFirstcompanyorgcode(String firstcompanyorgcode) {
		this.firstcompanyorgcode = firstcompanyorgcode;
	}
	
	@ExcelField(title="项目负责人名称", align=2, sort=74)
	public String getFirstcompanyorgname() {
		return firstcompanyorgname;
	}

	public void setFirstcompanyorgname(String firstcompanyorgname) {
		this.firstcompanyorgname = firstcompanyorgname;
	}
	
	@ExcelField(title="详细地址	utf8", align=2, sort=75)
	public String getAreadetails() {
		return areadetails;
	}

	public void setAreadetails(String areadetails) {
		this.areadetails = areadetails;
	}
	
	@ExcelField(title="是否已成功发送短信，（1-已成功发送）", align=2, sort=76)
	public String getSendmsg() {
		return sendmsg;
	}

	public void setSendmsg(String sendmsg) {
		this.sendmsg = sendmsg;
	}
	
}