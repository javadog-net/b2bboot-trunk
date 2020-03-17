/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.jhmis.core.persistence.DataEntity;
import com.jhmis.common.utils.excel.annotation.ExcelField;

/**
 * 招投标Entity
 * @author tc
 * @version 2019-07-19
 */
public class BidInfoDetail extends DataEntity<BidInfoDetail> {
	
	private static final long serialVersionUID = 1L;
	private String projectName;		// 项目名称
	private String companyName;		// 项目所属公司的名称
	private String companyType;		// 项目所属公司的类型
	private String projectIndustry;		// 项目所属的行业
	private String projectStatus;		// 项目状态
	private String projectContent;		// 项目的内容
	private String projectBudget;		// 项目的预算
	private String projectProperties;		// 项目的性质
	private Date startTime;		// 开工时间
	private Date stopTime;		// 竣工时间
	private String buildArea;		// 建筑面积
	private String redecorateCondition;		// 装修情况
	private String redecorateStand;		// 装修标准
	private String belongArea;		// 所属地区
	private String provinceId;		// 省份id
	private String cityId;		// 城市id
	private String projectNeedProduct;		// 项目所需要的产品
	private String projectAddress;		// 项目的地址
	private String addUser; //项目发布人
	private Date addTime;		// 项目发布的时间
	private int projectVisitNum;//项目的浏览量
	private String projectIndustryName;
	private String signUpTab;//当前用户是否已经报名的标识

	public String getSignUpTab() {
		return signUpTab;
	}

	public void setSignUpTab(String signUpTab) {
		this.signUpTab = signUpTab;
	}

	public String getProjectIndustryName() {
		return projectIndustryName;
	}

	public void setProjectIndustryName(String projectIndustryName) {
		this.projectIndustryName = projectIndustryName;
	}

	public int getProjectVisitNum() {
		return projectVisitNum;
	}

	public void setProjectVisitNum(int projectVisitNum) {
		this.projectVisitNum = projectVisitNum;
	}

	private String timeType; // 时间筛选
	private String sevenDay; // 三天
	private String threeDay; //七天
	private String month; // 一个月
	private String threeMonth; // 三个月
	private String sixMonth; // 半年
	private String year; // 一年
	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getSevenDay() {
		return sevenDay;
	}

	public void setSevenDay(String sevenDay) {
		this.sevenDay = sevenDay;
	}

	public String getThreeDay() {
		return threeDay;
	}

	public void setThreeDay(String threeDay) {
		this.threeDay = threeDay;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getThreeMonth() {
		return threeMonth;
	}

	public void setThreeMonth(String threeMonth) {
		this.threeMonth = threeMonth;
	}

	public String getSixMonth() {
		return sixMonth;
	}

	public void setSixMonth(String sixMonth) {
		this.sixMonth = sixMonth;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	private String bidConcat1;		// 项目所属公司联系人1
	private String bidConcat1Position;		// 项目所属公司联系人1职位
	private String bidConcat1Tel;		// 项目所属公司联系人1固话
	private String bidConcat1Phone;		// 项目所属公司联系人1手机号
	private String bidConcat2;		// 项目所属公司联系人2
	private String bidConcat2Position;		// 项目所属公司联系人2职位
	private String bidConcat2Tel;		// 项目所属公司联系人2固话
	private String bidConcat2Phone;		// 项目所属公司联系人2手机号
	private String bidConcat3;		// 项目所属公司联系人3
	private String bidConcat3Position;		// 项目所属公司联系人3职位
	private String bidConcat3Tel;		// 项目所属公司联系人3固话
	private String bidConcat3Phone;		// 项目所属公司联系人3手机号
	private String bidDesignConcat1;		// 项目设计公司联系人1
	private String bidDesignConcatPositon;		// 项目设计公司联系人1职位
	private String bidDesignConcat1Tel;		// 项目设计公司联系人1固话
	private String bidDesignConcat1Phone;		// 项目设计公司联系人1手机
	private String bidDesignConcat2;		// 项目设计公司联系人2
	private String bidDesignConcat2Position;		// 项目设计公司联系人2职位
	private String bidDesignConcat2Tel;		// 项目设计公司联系人2固话
	private String bidDesignConcat2Phone;		// 项目设计公司联系人2手机号
	private String bidBuildConcat1;		// 项目施工公司联系人1
	private String bidBuildConcat1Positon;		// 项目施工公司联系人1职位
	private String bidBuildConcat1Tel;		// 项目施工公司联系人1固话
	private String bidBuildConcat1Phone;		// 项目施工公司联系人1手机号
	private String status;		// 该订单的状态(0待审核,1审核通过,2审核拒绝)
	
	public BidInfoDetail() {
		super();
	}

	public BidInfoDetail(String id){
		super(id);
	}

	@ExcelField(title="项目名称(必填)", align=2, sort=1,mustWrist=1)
	@NotNull(message="项目名称不能为空")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@ExcelField(title="项目所属公司的名称(必填)", align=2, sort=2,mustWrist=1)
	@NotNull(message="项目所属公司名称不能为空")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@ExcelField(title="项目所属公司的类型", align=2, sort=3,DATA_LIST={"政府","商业","教育","军队"})
	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	
	@ExcelField(title="项目所属的行业", align=2, sort=4,
			DATA_LIST={
				"金融业",
				"公共管理、社会保障和社会组织",
				"制造业",
				"农、林、牧、副、渔业 ",
				"居民服务、修理和其他服务业",
				"水利、环境和公共设施管理业",
				"批发和零售业",
				"国际组织",
				"租赁和商务服务业",
				"公寓",
				"采矿业",
				"房地产业",
				"科学研究和技术服务业",
				"文化、体育和娱乐业",
				"建筑业",
				"卫生和社会工作",
				"信息传输、软件和信息技术服务业",
				"住宿和餐饮业",
				"电力、热力、燃气及水生产和供",
				"教育",
				"交通运输、仓储和邮政业",
				"金属制品、机械和设备修理业  ",
				"其他" }
			)
	public String getProjectIndustry() {
		return projectIndustry;
	}

	public void setProjectIndustry(String projectIndustry) {
		this.projectIndustry = projectIndustry;
	}
	
	@ExcelField(title="项目状态(必填)", align=2, sort=5,mustWrist=1,
			DATA_LIST={"在建","筹建","完工"}
	)
	@NotNull(message="项目状态不能为空")
	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	
	@ExcelField(title="项目的内容(必填)", align=2, sort=6,mustWrist=1)
	@NotNull(message="项目内容不能为空")
	public String getProjectContent() {
		return projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}
	
	@ExcelField(title="项目的预算(必填 格式：如 100000)", align=2, sort=7,mustWrist=1)
	@NotNull(message="项目预算不能为空")
	public String getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(String projectBudget) {
		this.projectBudget = projectBudget;
	}
	
	@ExcelField(title="项目的性质", align=2, sort=8,DATA_LIST={"新建","改扩建"})
	public String getProjectProperties() {
		return projectProperties;
	}

	public void setProjectProperties(String projectProperties) {
		this.projectProperties = projectProperties;
	}
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="开工时间(必填.例如：2019-07-24 00:00:00)", align=2, sort=9,mustWrist=1)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="竣工时间(必填.例如：2019-07-24 00:00:00)", align=2, sort=10,mustWrist=1)
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	
	@ExcelField(title="建筑面积", align=2, sort=11)
	public String getBuildArea() {
		return buildArea;
	}

	public void setBuildArea(String buildArea) {
		this.buildArea = buildArea;
	}
	
	@ExcelField(title="装修情况", align=2, sort=12)
	public String getRedecorateCondition() {
		return redecorateCondition;
	}

	public void setRedecorateCondition(String redecorateCondition) {
		this.redecorateCondition = redecorateCondition;
	}
	
	@ExcelField(title="装修标准", align=2, sort=13)
	public String getRedecorateStand() {
		return redecorateStand;
	}

	public void setRedecorateStand(String redecorateStand) {
		this.redecorateStand = redecorateStand;
	}
	
	@ExcelField(title="所属地区(必填  如： 山东省/青岛市/崂山区)", align=2, sort=14,mustWrist=1)
	@NotNull(message="项目所属地区不能为空")
	public String getBelongArea() {
		return belongArea;
	}

	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}
	
	//@ExcelField(title="省份id", align=2, sort=15)
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
   //	@ExcelField(title="城市id", align=2, sort=16)
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	@ExcelField(title="项目所需要的产品(必填)", align=2, sort=15,mustWrist=1)
	@NotNull(message="项目所需产品不能为空")
	public String getProjectNeedProduct() {
		return projectNeedProduct;
	}

	public void setProjectNeedProduct(String projectNeedProduct) {
		this.projectNeedProduct = projectNeedProduct;
	}
	
	@ExcelField(title="项目的地址", align=2, sort=16)
	public String getProjectAddress() {
		return projectAddress;
	}

	public void setProjectAddress(String projectAddress) {
		this.projectAddress = projectAddress;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="项目发布的时间(不需填写，以导入时间为准)", align=2, sort=17)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	@ExcelField(title="项目所属公司联系人1(必填)", align=2, sort=18,mustWrist=1)
	@NotNull(message="项目所属公司主联系人不能为空")
	public String getBidConcat1() {
		return bidConcat1;
	}

	public void setBidConcat1(String bidConcat1) {
		this.bidConcat1 = bidConcat1;
	}
	
	@ExcelField(title="项目所属公司联系人1职位", align=2, sort=19)
	public String getBidConcat1Position() {
		return bidConcat1Position;
	}

	public void setBidConcat1Position(String bidConcat1Position) {
		this.bidConcat1Position = bidConcat1Position;
	}
	
	@ExcelField(title="项目所属公司联系人1固话", align=2, sort=20)
	public String getBidConcat1Tel() {
		return bidConcat1Tel;
	}

	public void setBidConcat1Tel(String bidConcat1Tel) {
		this.bidConcat1Tel = bidConcat1Tel;
	}
	
	@ExcelField(title="项目所属公司联系人1手机号(必填)", align=2, sort=21,mustWrist=1)
	@NotNull(message="项目所属公司主联系人手机号不能为空")
	public String getBidConcat1Phone() {
		return bidConcat1Phone;
	}

	public void setBidConcat1Phone(String bidConcat1Phone) {
		this.bidConcat1Phone = bidConcat1Phone;
	}
	
	@ExcelField(title="项目所属公司联系人2", align=2, sort=22)
	public String getBidConcat2() {
		return bidConcat2;
	}

	public void setBidConcat2(String bidConcat2) {
		this.bidConcat2 = bidConcat2;
	}
	
	@ExcelField(title="项目所属公司联系人2职位", align=2, sort=23)
	public String getBidConcat2Position() {
		return bidConcat2Position;
	}

	public void setBidConcat2Position(String bidConcat2Position) {
		this.bidConcat2Position = bidConcat2Position;
	}
	
	@ExcelField(title="项目所属公司联系人2固话", align=2, sort=24)
	public String getBidConcat2Tel() {
		return bidConcat2Tel;
	}

	public void setBidConcat2Tel(String bidConcat2Tel) {
		this.bidConcat2Tel = bidConcat2Tel;
	}
	
	@ExcelField(title="项目所属公司联系人2手机号", align=2, sort=25)
	public String getBidConcat2Phone() {
		return bidConcat2Phone;
	}

	public void setBidConcat2Phone(String bidConcat2Phone) {
		this.bidConcat2Phone = bidConcat2Phone;
	}
	
	@ExcelField(title="项目所属公司联系人3", align=2, sort=26)
	public String getBidConcat3() {
		return bidConcat3;
	}

	public void setBidConcat3(String bidConcat3) {
		this.bidConcat3 = bidConcat3;
	}
	
	@ExcelField(title="项目所属公司联系人3职位", align=2, sort=27)
	public String getBidConcat3Position() {
		return bidConcat3Position;
	}

	public void setBidConcat3Position(String bidConcat3Position) {
		this.bidConcat3Position = bidConcat3Position;
	}
	
	@ExcelField(title="项目所属公司联系人3固话", align=2, sort=28)
	public String getBidConcat3Tel() {
		return bidConcat3Tel;
	}

	public void setBidConcat3Tel(String bidConcat3Tel) {
		this.bidConcat3Tel = bidConcat3Tel;
	}
	
	@ExcelField(title="项目所属公司联系人3手机号", align=2, sort=29)
	public String getBidConcat3Phone() {
		return bidConcat3Phone;
	}

	public void setBidConcat3Phone(String bidConcat3Phone) {
		this.bidConcat3Phone = bidConcat3Phone;
	}
	
	@ExcelField(title="项目设计公司联系人1", align=2, sort=30)
	public String getBidDesignConcat1() {
		return bidDesignConcat1;
	}

	public void setBidDesignConcat1(String bidDesignConcat1) {
		this.bidDesignConcat1 = bidDesignConcat1;
	}
	
	@ExcelField(title="项目设计公司联系人1职位", align=2, sort=31)
	public String getBidDesignConcatPositon() {
		return bidDesignConcatPositon;
	}

	public void setBidDesignConcatPositon(String bidDesignConcatPositon) {
		this.bidDesignConcatPositon = bidDesignConcatPositon;
	}
	
	@ExcelField(title="项目设计公司联系人1固话", align=2, sort=32)
	public String getBidDesignConcat1Tel() {
		return bidDesignConcat1Tel;
	}

	public void setBidDesignConcat1Tel(String bidDesignConcat1Tel) {
		this.bidDesignConcat1Tel = bidDesignConcat1Tel;
	}
	
	@ExcelField(title="项目设计公司联系人1手机", align=2, sort=33)
	public String getBidDesignConcat1Phone() {
		return bidDesignConcat1Phone;
	}

	public void setBidDesignConcat1Phone(String bidDesignConcat1Phone) {
		this.bidDesignConcat1Phone = bidDesignConcat1Phone;
	}
	
	@ExcelField(title="项目设计公司联系人2", align=2, sort=34)
	public String getBidDesignConcat2() {
		return bidDesignConcat2;
	}

	public void setBidDesignConcat2(String bidDesignConcat2) {
		this.bidDesignConcat2 = bidDesignConcat2;
	}
	
	@ExcelField(title="项目设计公司联系人2职位", align=2, sort=35)
	public String getBidDesignConcat2Position() {
		return bidDesignConcat2Position;
	}

	public void setBidDesignConcat2Position(String bidDesignConcat2Position) {
		this.bidDesignConcat2Position = bidDesignConcat2Position;
	}
	
	@ExcelField(title="项目设计公司联系人2固话", align=2, sort=36)
	public String getBidDesignConcat2Tel() {
		return bidDesignConcat2Tel;
	}

	public void setBidDesignConcat2Tel(String bidDesignConcat2Tel) {
		this.bidDesignConcat2Tel = bidDesignConcat2Tel;
	}
	
	@ExcelField(title="项目设计公司联系人2手机号", align=2, sort=37)
	public String getBidDesignConcat2Phone() {
		return bidDesignConcat2Phone;
	}

	public void setBidDesignConcat2Phone(String bidDesignConcat2Phone) {
		this.bidDesignConcat2Phone = bidDesignConcat2Phone;
	}
	
	@ExcelField(title="项目施工公司联系人1", align=2, sort=38)
	public String getBidBuildConcat1() {
		return bidBuildConcat1;
	}

	public void setBidBuildConcat1(String bidBuildConcat1) {
		this.bidBuildConcat1 = bidBuildConcat1;
	}
	
	@ExcelField(title="项目施工公司联系人1职位", align=2, sort=39)
	public String getBidBuildConcat1Positon() {
		return bidBuildConcat1Positon;
	}

	public void setBidBuildConcat1Positon(String bidBuildConcat1Positon) {
		this.bidBuildConcat1Positon = bidBuildConcat1Positon;
	}
	
	@ExcelField(title="项目施工公司联系人1固话", align=2, sort=40)
	public String getBidBuildConcat1Tel() {
		return bidBuildConcat1Tel;
	}

	public void setBidBuildConcat1Tel(String bidBuildConcat1Tel) {
		this.bidBuildConcat1Tel = bidBuildConcat1Tel;
	}
	
	@ExcelField(title="项目施工公司联系人1手机号", align=2, sort=41)
	public String getBidBuildConcat1Phone() {
		return bidBuildConcat1Phone;
	}

	public void setBidBuildConcat1Phone(String bidBuildConcat1Phone) {
		this.bidBuildConcat1Phone = bidBuildConcat1Phone;
	}
	
	//@ExcelField(title="该订单的状态(0待审核,1审核通过,2审核拒绝)", align=2, sort=44)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}