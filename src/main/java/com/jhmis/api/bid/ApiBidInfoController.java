/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.bid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.api.direct.ApiDirectOrderController;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.bid.entity.BidCheckRecord;
import com.jhmis.modules.bid.entity.BidInfo;
import com.jhmis.modules.bid.entity.BidSignup;
import com.jhmis.modules.bid.service.BidCheckRecordService;
import com.jhmis.modules.bid.service.BidInfoService;
import com.jhmis.modules.bid.service.BidSignupService;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.entity.dealer.DealerAccount;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.IndustryService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.shop.utils.DealerUtils;
import com.jhmis.modules.shop.utils.PurchaserUtils;
import com.jhmis.modules.sys.security.Principal;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxIndustry;
import com.jhmis.modules.wechat.service.WxIndustryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/bid/Info")
@Api(value = "ApiBidInfoController", description = "招采项目信息")
public class ApiBidInfoController {
	// 登录list页面type
	public static final String INDEX_INDEX = "index";
	public static final String INDEX_ISSUE = "issue";
	public static final String INDEX_SIGN = "sign";
	// 检索条件
	public static final String DATA_TRUE = "true";
	public static final String THREE_DAY = "1";
	public static final String SEVEN_DAY = "2";
	public static final String MONTH = "3";
	public static final String THREE_MONTH = "4";
	public static final String SIX_MONTH = "5";
	public static final String YEAR = "6";

	protected Logger logger = LoggerFactory.getLogger(ApiDirectOrderController.class);

	@Autowired
	private BidInfoService bidInfoService;
	@Autowired
	private BidSignupService bidSignupService;
	@Autowired
	private BidCheckRecordService bidCheckRecordService;
	@Autowired
	private WxIndustryService wxIndustryService;
	@Autowired
	private PurchaserService purchaserService;

	/**
	 * 招采项目列表
	 * 
	 * @return
	 */
	@ApiOperation(notes = "InfoList", httpMethod = "GET", value = "招采列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "type", value = "列表类型 首页:index, 发布页:issue 报名页:sign", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "projectName", value = "项目名称", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "industry", value = "行业编号", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "provinceId", value = "省", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "cityId", value = "市", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "timeType", value = "时间筛选条件 三天:1,七天:2,半个月:3,一个月:4,半年:5,一年:6", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "pageNo", value = "页码", required = false, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, paramType = "query", dataType = "int") })

	@RequestMapping("InfoList")
	public AjaxJson InfoList(HttpServletRequest request, BidInfo bidInfo, HttpServletResponse response) {
		String type = request.getParameter("type");
		String projectName = request.getParameter("projectName");

		logger.info("*_*_*_*_*_*_*_*_*_* ApiBidInfoController  /api/bid/Info 招采信息列表-- 参数: TYPE:" + type
				+ ",projectName:" + projectName + "----industry:" + bidInfo.getIndustry() + "--provinceId:"
				+ bidInfo.getProvinceId() + "-cityId:" + bidInfo.getCityId() + "-timeType:" + bidInfo.getTimeType()
				+ "------------------------接口开始*_*_*_*_*_*_*_*_*_*");
		Page<BidInfo> bid = new Page<BidInfo>(request, response);
		List<BidInfo> infoList = null;
		List<BidInfo> infoLists = null;
		Page<BidInfo> page = new Page<BidInfo>(request, response);

		// 首页
		if (type.equals(INDEX_INDEX)) {
			// Map<String, Object> map = new HashMap<>();
			logger.info("*_*_*_*_*_*_*_*_*_*   获取主页招采信息列表----------接口开始*_*_*_*_*_*_*_*_*_*");
			if (bidInfo.getTimeType() != null) {
				if (bidInfo.getTimeType().equals(THREE_DAY)) {// 3天的数据
					bidInfo.setThreeDay(DATA_TRUE);
				} else if (bidInfo.getTimeType().equals(SEVEN_DAY)) {// 7天的数据
					bidInfo.setSevenDay(DATA_TRUE);
				} else if (bidInfo.getTimeType().equals(MONTH)) {// 1个月的数据
					bidInfo.setMonth(DATA_TRUE);
				} else if (bidInfo.getTimeType().equals(THREE_MONTH)) {// 3个
																		// 月的数据
					bidInfo.setThreeMonth(DATA_TRUE);
				} else if (bidInfo.getTimeType().equals(SIX_MONTH)) {// 6个月的数据
					bidInfo.setSixMonth(DATA_TRUE);
				} else if (bidInfo.getTimeType().equals(YEAR)) {// 1年的数据
					bidInfo.setYear(DATA_TRUE);
				}
			}
			// 审核通过的
			bidInfo.setStatus(Constants.BID_CHECK_PASS);
			// 有效状态的
			bidInfo.setIsValid(Constants.BID_VALID_YES);
			page.setOrderBy("add_time desc");
			bid = bidInfoService.findPage(page, bidInfo);
			// 发布
		} else if (type.equals(INDEX_ISSUE)) {
			logger.info("*_*_*_*_*_*_*_*_*_*   获取当前用户发布的招采信息列表----------接口开始*_*_*_*_*_*_*_*_*_*");
			// 获取当前登录用户
			Principal principal = UserUtils.getPrincipal();
			// 根据当前登录用户名获取采购商信息
			PurchaserAccount Account = PurchaserUtils.getByLoginName(principal.getLoginName());
			if (projectName != null) {
				bidInfo.setProjectName(projectName);
			}
			// 根据采购商ID获取当前用户发布信息
			bidInfo.setAddUser(Account.getPurchaserId());
			bid = bidInfoService.findPage(page, bidInfo);
			// 报名
		} else if (type.equals(INDEX_SIGN)) {
			logger.info("*_*_*_*_*_*_*_*_*_*   获取当前用户报名的招采信息列表----------接口开始*_*_*_*_*_*_*_*_*_*");
			// 获取当前登录用户
			Principal principal = UserUtils.getPrincipal();
			// 根据-当前登录用户名获取采购商信息
			PurchaserAccount Account = PurchaserUtils.getByLoginName(principal.getLoginName());
			BidSignup sugnup = new BidSignup();
			int num = bid.getPageSize();
			sugnup.setPurchaserAccountId(Account.getPurchaserId());
			List<BidSignup> sugnupList = bidSignupService.findList(sugnup);
			infoList = new ArrayList<BidInfo>();
			for (int i = 0;i<num;i++) {
				if(sugnupList.size()>i) {
				BidSignup sign = sugnupList.get(i);
				BidInfo info = bidInfoService.get(sign.getBidInfoId());
				if (info != null) {
					infoList.add(info);
				}
			}
			}
			// 条件筛选
			if (projectName != null) {
				List<BidInfo> inlist = new ArrayList<BidInfo>();
				for (BidInfo in : infoList) {
					if (in.getProjectName().contains(projectName)) {
						inlist.add(in);
					}
				}
				bid.setCount(inlist.size());
				bid.setList(inlist);
			} else {
				bid.setCount(infoList.size());
				bid.setList(infoList);
			}
		}
		if (bid.getList().size() == 0) {
			logger.info("*_*_*_*_*_*_*_*_*_* 获取招采信息列表  ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		// 行业转换
		infoLists = bid.getList();
		for (BidInfo in : infoLists) {
			WxIndustry industry = wxIndustryService.find(in.getIndustry());
			if (industry != null) {
				in.setIndustry(industry.getName());
			}
		}
		return AjaxJson.layuiTable(bid);
	}

	/**
	 * 获取行业地区
	 * 
	 * @return
	 */
	@ApiOperation(notes = "getScreening", httpMethod = "GET", value = "获取行业地区")

	@RequestMapping("getScreening")
	public AjaxJson getScreening() {
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/InfoParticular  获取招采信息详情----------接口开始*_*_*_*_*_*_*_*_*_*");
		Map<String, Object> map = new HashMap<>();
		WxIndustry wxIndustry = new WxIndustry();
		List<WxIndustry> in = wxIndustryService.findList(wxIndustry);
		if (in == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiBidInfoController  /api/bid/Info/InfoParticular  获取招采信息详情 ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		map.put("industryList", in);
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/InfoParticular  获取招采信息详情----------接口结束*_*_*_*_*_*_*_*_*_*");
		return AjaxJson.ok(map);
	}

	/**
	 * 项目详情
	 * 
	 * @return
	 */
	@ApiOperation(notes = "InfoParticular", httpMethod = "GET", value = "项目详情")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "项目主键", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("InfoParticular")
	public AjaxJson InfoParticular(BidInfo bidInfo) {
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/InfoParticular  获取招采信息详情----------接口开始*_*_*_*_*_*_*_*_*_*");
		Map<String, Object> map = new HashMap<>();
		List<BidSignup> signupList  = null;
		Principal principal = UserUtils.getPrincipal();
		logger.info("*_*_*_*_*_**_* 根据当前登录用户名获取采购商信息----------*_*_*_*_*_*_*_*_*_*");
		// 根据当前登录用户名获取采购商信息
		PurchaserAccount Account = PurchaserUtils.getByLoginName(principal.getLoginName());
		logger.info("*_*_*_*_*_**_* 根据ID获取项目信息增加访问热量----------*_*_*_*_*_*_*_*_*_*");
		try {
		BidInfo bid = bidInfoService.get(bidInfo.getId());
		if (bid != null) {
			bid.setVisitNum(bid.getVisitNum() + 1);
			bidInfoService.save(bid);
		}
		// 根据行业ID 获取行业
		WxIndustry stry = wxIndustryService.find(bid.getIndustry());
		if (stry != null) {
			bid.setIndustry(stry.getName());
		}
		map.put("bidInfo", bid);
		logger.info("*_*_*_*_*_**_* 个根据招采ID 获取所有审核信息----------*_*_*_*_*_*_*_*_*_*");
		BidCheckRecord bidCheckRecord = new BidCheckRecord();
		bidCheckRecord.setBidInfoId(bidInfo.getId());
		List<BidCheckRecord> checkList = bidCheckRecordService.findList(bidCheckRecord);
		map.put("checkList", checkList);

		logger.info("*_*_*_*_*_**_* 当前人是否已在当前项目报名----------*_*_*_*_*_*_*_*_*_*");
		// 当前人是否已在当前项目报名
		BidSignup sugnup = new BidSignup();
		sugnup.setPurchaserAccountId(Account.getPurchaserId());
		sugnup.setBidInfoId(bidInfo.getId());
		signupList = bidSignupService.findList(sugnup);
		}catch (Exception e) {
			logger.info(
					"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/saveInfo 错误信息："+e);
		}
		if (signupList.size() > 0) {
			map.put("isSignup", true);
		} else {
			map.put("isSignup", false);
		}
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/InfoParticular  获取招采信息详情----------接口结束*_*_*_*_*_*_*_*_*_*");
		return AjaxJson.ok(map);
	}

	/**
	 * 获取报名信息
	 * 
	 * @return
	 */
	@ApiOperation(notes = "getSignParticular", httpMethod = "GET", value = "获取报名信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bidInfoId", value = "项目主键", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("getSignParticular")
	public AjaxJson getSignParticular(BidSignup bidSignup) {
		Principal principal = UserUtils.getPrincipal();
		logger.info("*_*_*_*_*_**_* 根据项目ID及采购商id匹配报名信息----------*_*_*_*_*_*_*_*_*_*");
		// 根据当前登录用户名获取采购商信息
		PurchaserAccount Account = PurchaserUtils.getByLoginName(principal.getLoginName());
		bidSignup.setPurchaserId(Account.getPurchaserId());
		List<BidSignup> bidSignups = bidSignupService.getSign(bidSignup);
		if (bidSignups == null) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_*  getSignParticular 根据项目ID及采购商id匹配报名信息 ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		return AjaxJson.ok(bidSignups);
	}
	/**
	 * 获取报名人员列表
	 * 
	 * @return
	 */
	@ApiOperation(notes = "getSignUserList", httpMethod = "GET", value = "获取报名信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyName", value = "公司名称", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "signupConcat", value = "联系人", required = false, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "bidInfoId", value = "项目主键", required = true, paramType = "query", dataType = "String") })
	@RequestMapping("getSignUserList")
	public AjaxJson getSignUserList( HttpServletRequest request,HttpServletResponse response, BidSignup bidSignup) {
		Page<BidSignup> page = bidSignupService.findPage(new Page<BidSignup>(request, response), bidSignup); 
		if (page.getList().size()<1) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_*  getSignUserList 获取报名信息 ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		return AjaxJson.layuiTable(page);
	}
	/**
	 * （项目列表换一换）根据热度查询项目列表
	 * 
	 * @return
	 */
	@ApiOperation(notes = "getVisitProjectList", httpMethod = "GET", value = "（项目列表换一换）根据热度查询项目列表")
	@RequestMapping("getVisitProjectList")
	public AjaxJson getVisitProjectList(BidInfo bidInfo) {
		logger.info("*_*_*_*_*_**_* 根据热度查询项目列表----------*_*_*_*_*_*_*_*_*_*");
		// 审核通过的
		bidInfo.setStatus(Constants.BID_CHECK_PASS);
		// 有效状态的
		bidInfo.setIsValid(Constants.BID_VALID_YES);
		List<BidInfo> infoList = bidInfoService.getVisitProjectList();
		if (infoList == null) {
			logger.info("*_*_*_*_*_*_*_*_*_*  getVisitProjectList 根据热度查询项目列表----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		return AjaxJson.ok(infoList);
	}

	/**
	 * 获取当前登录采购商信息
	 * 
	 * @return
	 */
	@ApiOperation(notes = "getParticular", httpMethod = "GET", value = "获取当前登录采购商信息")
	@RequestMapping("getParticular")
	public AjaxJson getParticular() {
		Principal principal = UserUtils.getPrincipal();
		logger.info("*_*_*_*_*_**_* 获取当前登录采购商信息----------*_*_*_*_*_*_*_*_*_*");
		// 根据当前登录用户名获取采购商信息
		PurchaserAccount Account = PurchaserUtils.getByLoginName(principal.getLoginName());
		Purchaser purchaser = purchaserService.get(Account.getPurchaserId());
		if (purchaser == null) {
			logger.info("*_*_*_*_*_*_*_*_*_*  getParticular 获取当前登录采购商信息 ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		return AjaxJson.ok(Account);
	}

	/**
	 * 新建或修改项目
	 * 
	 * @return
	 */
	@ApiOperation(notes = "saveInfo", httpMethod = "POST", value = "新建或修改项目")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "projectLogo", value = "项目logo", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "industry", value = "行业ID", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "cityId", value = "城市id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "provinceId", value = "省份id", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "areaInfo", value = "所属地区中文", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "projectName", value = "项目名称", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "projectBudget", value = "项目预算", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "projectContent", value = "项目内容", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "projectIntro", value = "项目介绍", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "applicationConditions", value = "报名单位资格要求", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "bidConcatPhone", value = "联系人电话", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "bidConcat", value = "联系人", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "bidConcatEmail", value = "联系人邮箱", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "bidConcatAddress", value = "联系人地址", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "id", value = "项目Id", required = false, paramType = "query", dataType = "String") })
	@RequestMapping("saveInfo")
	public AjaxJson saveInfo(String companyName, String projectLogo, String industry, String cityId, String provinceId,
			String projectName, String projectBudget, String projectIntro, String applicationConditions,
			String bidConcatPhone, String bidConcat, String bidConcatEmail, String bidConcatAddress, String id,
			String areaInfo, String projectContent, String addUser) {
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/saveInfo  新建或修改项目----------接口开始*_*_*_*_*_*_*_*_*_*");

		logger.info("*_*_*_*_*_**_* 保存项目信息参数--companyName:" + companyName + ",--projectLogo:" + projectLogo
				+ ",--industry:" + industry + "-cityId:" + cityId + ",--projectContent:" + projectContent
				+ ",-projectIntro:" + projectIntro + ",--applicationConditions:" + applicationConditions
				+ ",-bidConcatPhone:" + bidConcatPhone + ",--bidConcat:" + bidConcat + ",-bidConcatEmail:"
				+ bidConcatEmail + ",--provinceId:" + provinceId + ",-areaInfo:" + areaInfo + ",--bidConcatAddress:"
				+ bidConcatAddress + ",-id:" + id + ",-------projectName:" + projectName + "-----,projectBudget:"
				+ projectBudget + "--------接口开始*_*_*_*_*_*_*_*_*_*");
		// 新增或编辑表单保存
		// 来源为用户新建
		BidInfo bidInfo = new BidInfo();
		if (StringUtils.isBlank(companyName)) {
			return AjaxJson.fail("公司名称不能为空");

		}

		if (StringUtils.isBlank(projectName)) {
			return AjaxJson.fail("项目名称不能为空");

		}
		if (StringUtils.isBlank(projectLogo)) {
			return AjaxJson.fail("项目logo不能为空");

		}
		if (StringUtils.isBlank(industry)) {
			return AjaxJson.fail("行业不能为空");

		}
		if (StringUtils.isBlank(provinceId)) {
			return AjaxJson.fail("所属地区不能为空");
		}

		if (StringUtils.isBlank(projectBudget)) {
			return AjaxJson.fail("项目预算不能为空");
		}

		if (StringUtils.isBlank(projectContent)) {
			return AjaxJson.fail("项目内容不能为空");
		}

		if (StringUtils.isBlank(projectIntro)) {
			return AjaxJson.fail("项目介绍不能为空");
		}
		if (StringUtils.isBlank(applicationConditions)) {
			return AjaxJson.fail("报名单位资格要求");
		}

		if (StringUtils.isBlank(bidConcatPhone)) {
			return AjaxJson.fail("联系电话不能为空");
		}

		if (StringUtils.isBlank(bidConcat)) {
			return AjaxJson.fail("联系人不能为空");
		}

		if (StringUtils.isBlank(bidConcatEmail)) {
			return AjaxJson.fail("联系人邮箱不能为空");
		}

		if (StringUtils.isBlank(bidConcatAddress)) {
			return AjaxJson.fail("地址不能为空");
		}
		if (!StringUtils.isBlank(id)) {
			bidInfo.setId(id);
		}
		try {
			// 根据当前登录用户名获取采购商信息
			Principal principal = UserUtils.getPrincipal();
			PurchaserAccount Account =PurchaserUtils.getByLoginName(principal.getLoginName());
			bidInfo.setAddUser(Account.getPurchaserId());
			bidInfo.setSource("3");// 用户新建
			bidInfo.setStatus("0");// 初始状态待审核
			bidInfo.setIsValid("0");// 初始无效
			bidInfo.setCityId(cityId);// 市编
			bidInfo.setProvinceId(provinceId);// 省编
			bidInfo.setAreaInfo(areaInfo);// 所属地区
			bidInfo.setApplicationConditions(applicationConditions);// 报名要求
			bidInfo.setBidConcat(bidConcat);// 联系人
			bidInfo.setBidConcatAddress(bidConcatAddress);// 地址
			bidInfo.setCompanyName(companyName);// 公司名称
			bidInfo.setBidConcatEmail(bidConcatEmail);// 联系人邮箱
			bidInfo.setBidConcatPhone(bidConcatPhone);// 联系人电话
			bidInfo.setIndustry(industry);// 行业
			bidInfo.setProjectBudget(projectBudget);// 预算
			bidInfo.setProjectContent(projectContent);// 项目内容
			bidInfo.setProjectIntro(projectIntro);// 项目介绍
			bidInfo.setProjectName(projectName);// 项目名称
			bidInfo.setProjectLogo(projectLogo);// logo地址
			bidInfoService.save(bidInfo);// 保存
		} catch (Exception e) {
			logger.info(
					"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/saveInfo 错误信息："+e);
			return AjaxJson.fail("保存失败");

		}
		return AjaxJson.ok("保存成功");
	}

	/**
	 * 新增报名信息保存
	 * 
	 * @return
	 */
	@ApiOperation(notes = "saveSignup", httpMethod = "POST", value = "报名信息保存")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "companyName", value = "公司名称", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "projectName", value = "项目名称", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "signupConcat", value = "报名联系人", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "purchaserAccountId", value = "报名联系人ID", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "signupConcatEmail", value = "联系人邮箱", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "signupConcatMobile", value = "联系人电话", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "signupConcatJob", value = "联系人职务", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "message", value = "留言", required = true, paramType = "form", dataType = "String"),
			@ApiImplicitParam(name = "bidInfoId", value = "项目Id", required = true, paramType = "form", dataType = "String") })
	@RequestMapping("saveSignup")
	public AjaxJson saveSignup(String companyName, String projectName, String signupConcat, String purchaserId,
			String signupConcatEmail, String signupConcatMobile, String signupConcatJob, String message,
			String bidInfoId) {
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/saveSignup  报名信息保存----------接口开始*_*_*_*_*_*_*_*_*_*");
		logger.info("*_*_*_*_*_**_* 保存报名信息参数--companyName:" + companyName + ",--projectName:" + projectName
				+ ",--signupConcat:" + signupConcat + ",-purchaserAccountId:" + purchaserId + ",--signupConcatEmail:"
				+ signupConcatEmail + ",-signupConcatMobile:" + signupConcatMobile + ",-------message:" + message
				+ ",-----bidInfoId:" + bidInfoId + "--------接口开始*_*_*_*_*_*_*_*_*_*");
		// 新增或编辑表单保存
		if (StringUtils.isBlank(companyName)) {
			return AjaxJson.fail("公司名称不能为空");
		}
		if (StringUtils.isBlank(projectName)) {
			return AjaxJson.fail("项目名称不能为空");

		}
		if (StringUtils.isBlank(signupConcat)) {
			return AjaxJson.fail("报名联系人不能为空");

		}
		if (StringUtils.isBlank(signupConcatEmail)) {
			return AjaxJson.fail("联系人邮箱不能为空");

		}
		if (StringUtils.isBlank(signupConcatMobile)) {
			return AjaxJson.fail("联系人电话不能为空");

		}
		if (StringUtils.isBlank(signupConcatJob)) {
			return AjaxJson.fail("联系人职务不能为空");

		}
		if (StringUtils.isBlank(message)) {
			return AjaxJson.fail("留言不能为空");
		}
		if (StringUtils.isBlank(bidInfoId)) {
			return AjaxJson.fail("项目Id不能为空");
		}
		try {
			BidSignup bidSignup = new BidSignup();
			bidSignup.setCompanyName(companyName);
			bidSignup.setProjectName(projectName);
			bidSignup.setSignupConcat(signupConcatJob);
			bidSignup.setPurchaserAccountId(purchaserId);
			bidSignup.setSignupConcatEmail(signupConcatEmail);
			bidSignup.setSignupConcatMobile(signupConcatMobile);
			bidSignup.setSignupConcatJob(signupConcatJob);
			bidSignup.setMessage(message);
			bidSignup.setBidInfoId(bidInfoId);
			bidSignupService.save(bidSignup);// 保存
		} catch (Exception e) {
			System.out.print(e);
			logger.info(
					"*_*_*_*_*_**_* ApiBidInfoController  /api/bid/Info/saveSignup 错误信息："+e);
			return AjaxJson.fail("保存失败");

		}
		return AjaxJson.ok("保存成功");
	}

}