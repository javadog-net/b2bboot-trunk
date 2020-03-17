/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.api.bid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhmis.api.direct.ApiDirectOrderController;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.core.persistence.Page;
import com.jhmis.modules.bid.entity.BidCheckRecord;
import com.jhmis.modules.bid.entity.BidInfoDetail;
import com.jhmis.modules.bid.entity.BidSignup;
import com.jhmis.modules.bid.service.BidCheckRecordService;
import com.jhmis.modules.bid.service.BidInfoDetailService;
import com.jhmis.modules.bid.service.BidSignupService;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
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
@RequestMapping(value = "/api/bid/InfoDetail")
@Api(value = "ApiBidInfoDetailController", description = "招采项目信息")
public class ApiBidlInfoDetaiController {
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
	private BidInfoDetailService bidInfoDetailService;
	@Autowired
	private BidSignupService bidSignupService;
	@Autowired
	private WxIndustryService wxIndustryService;
	@Autowired
	private PurchaserService purchaserService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

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
	public AjaxJson InfoList(HttpServletRequest request, BidInfoDetail bidInfoDetail, HttpServletResponse response) {
		String type = request.getParameter("type");
		String projectName = request.getParameter("projectName");

		logger.info("*_*_*_*_*_*_*_*_*_* ApiBidInfoDetailController  /api/bid/InfoDetail 招采信息列表-- 参数: TYPE:" + type
				+ ",projectName:" + projectName + "----industry:" + bidInfoDetail.getProjectIndustry() + "--provinceId:"
				+ bidInfoDetail.getProvinceId() + "-cityId:" + bidInfoDetail.getCityId() + "-timeType:"
				+ bidInfoDetail.getTimeType() + "------------------------接口开始*_*_*_*_*_*_*_*_*_*");
		Page<BidInfoDetail> biddetail = new Page<BidInfoDetail>(request, response);
		List<BidInfoDetail> infoList = null;
		List<BidInfoDetail> infoLists = null;
		Page<BidInfoDetail> page = new Page<BidInfoDetail>(request, response);
		page.setOrderBy("add_time desc");
		// 首页
		if (type.equals(INDEX_INDEX)) {
			// Map<String, Object> map = new HashMap<>();
			logger.info("*_*_*_*_*_*_*_*_*_*   获取主页招采信息列表----------接口开始*_*_*_*_*_*_*_*_*_*");
			if (bidInfoDetail.getTimeType() != null) {
				if (bidInfoDetail.getTimeType().equals(THREE_DAY)) {// 3天的数据
					bidInfoDetail.setThreeDay(DATA_TRUE);
				} else if (bidInfoDetail.getTimeType().equals(SEVEN_DAY)) {// 7天的数据
					bidInfoDetail.setSevenDay(DATA_TRUE);
				} else if (bidInfoDetail.getTimeType().equals(MONTH)) {// 1个月的数据
					bidInfoDetail.setMonth(DATA_TRUE);
				} else if (bidInfoDetail.getTimeType().equals(THREE_MONTH)) {// 3个
					// 月的数据
					bidInfoDetail.setThreeMonth(DATA_TRUE);
				} else if (bidInfoDetail.getTimeType().equals(SIX_MONTH)) {// 6个月的数据
					bidInfoDetail.setSixMonth(DATA_TRUE);
				} else if (bidInfoDetail.getTimeType().equals(YEAR)) {// 1年的数据
					bidInfoDetail.setYear(DATA_TRUE);
				}
			}
			// 审核通过的
			bidInfoDetail.setStatus(Constants.BID_CHECK_PASS);

			biddetail = bidInfoDetailService.findPage(page, bidInfoDetail);
			// 发布
		} else if (type.equals(INDEX_ISSUE)) {
			logger.info("*_*_*_*_*_*_*_*_*_*   获取当前用户发布的招采信息列表----------接口开始*_*_*_*_*_*_*_*_*_*");
			// 获取当前登录用户
			// 根据当前登录用户名获取采购商信息
			PurchaserAccount Account = PurchaserUtils.getPurchaserAccount();
			if (projectName != null) {
				bidInfoDetail.setProjectName(projectName);
			}
			if(Account!=null){
                // 根据采购商ID获取当前用户发布信息
                bidInfoDetail.setAddUser(Account.getPurchaserId());
            }
			biddetail = bidInfoDetailService.findPage(page, bidInfoDetail);
			// 报名
		} else if (type.equals(INDEX_SIGN)) {
			logger.info("*_*_*_*_*_*_*_*_*_*   获取当前用户报名的招采信息列表----------接口开始*_*_*_*_*_*_*_*_*_*");
			// PurchaserAccount purchaserAccount =
			// PurchaserUtils.getPurchaserAccount();
			// 根据-当前登录用户名获取采购商信息
			PurchaserAccount Account = PurchaserUtils.getPurchaserAccount();
			BidSignup sugnup = new BidSignup();
			int num = biddetail.getPageSize();
			if(null != Account) {
                sugnup.setPurchaserAccountId(Account.getPurchaserId());
            }
			List<BidSignup> sugnupList = bidSignupService.findList(sugnup);
			infoList = new ArrayList<BidInfoDetail>();
			for (int i = 0; i < num; i++) {
				if (sugnupList.size() > i) {
					BidSignup sign = sugnupList.get(i);
					BidInfoDetail info = bidInfoDetailService.get(sign.getBidInfoId());
					if (info != null) {
						infoList.add(info);
					}
				}
			}
			// 条件筛选
			if (projectName != null) {
				List<BidInfoDetail> inlist = new ArrayList<BidInfoDetail>();
				for (BidInfoDetail in : infoList) {
					if (in.getProjectName().contains(projectName)) {
						inlist.add(in);
					}
				}
				biddetail.setCount(inlist.size());
				biddetail.setList(inlist);
			} else {
				biddetail.setCount(infoList.size());
				biddetail.setList(infoList);
			}
		}
		if (biddetail.getList().size() == 0) {
			logger.info("*_*_*_*_*_*_*_*_*_* 获取招采信息列表  ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		infoLists = biddetail.getList();
        PurchaserAccount Account = PurchaserUtils.getPurchaserAccount();
		if (Account != null) {
			BidSignup sugnup = new BidSignup();

			sugnup.setPurchaserAccountId(Account.getPurchaserId());
			List<BidSignup> sugnupList = bidSignupService.findList(sugnup);
			
			// set进当前用户的已参与标识
			try {
				for (BidInfoDetail in : infoLists) {
					if (sugnupList != null && sugnupList.size() > 0) {
						for (BidSignup sign : sugnupList) {
							if (in.getId().equals(sign.getBidInfoId())) {
								in.setSignUpTab("1");
							}
						}
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
           //行业的装换
			for (BidInfoDetail in : infoLists) {
				WxIndustry industry = wxIndustryService.find(in.getProjectIndustry());
				if (industry != null) {
					in.setProjectIndustryName(industry.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return AjaxJson.layuiTable(biddetail);
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
				"*_*_*_*_*_**_* ApiBidInfoDetailController  /api/bid/InfoDetail/InfoParticular  获取招采信息详情----------接口开始*_*_*_*_*_*_*_*_*_*");
		Map<String, Object> map = new HashMap<>();
		WxIndustry wxIndustry = new WxIndustry();
		List<WxIndustry> in = wxIndustryService.findList(wxIndustry);
		if (in == null || in.size() == 0) {
			logger.info(
					"*_*_*_*_*_*_*_*_*_* ApiBidInfoDetailController  /api/bid/InfoDetail/InfoParticular  获取招采信息详情 ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		map.put("industryList", in);
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoDetailController  /api/bid/InfoDetail/InfoParticular  获取招采信息详情----------接口结束*_*_*_*_*_*_*_*_*_*");
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
	public AjaxJson InfoParticular(BidInfoDetail bidInfoDetail) {
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoDetailController  /api/bid/InfoDetail/InfoParticular  获取招采信息详情----------接口开始*_*_*_*_*_*_*_*_*_*");
		Map<String, Object> map = new HashMap<>();
		List<BidSignup> signupList = null;

		logger.info("*_*_*_*_*_**_* 根据ID获取项目信息增加访问热量----------*_*_*_*_*_*_*_*_*_*");
		try {
			BidInfoDetail biddetail = bidInfoDetailService.get(bidInfoDetail.getId());
			// 热度

			if (biddetail != null) {
				biddetail.setProjectVisitNum(biddetail.getProjectVisitNum() + 1);
				bidInfoDetailService.save(biddetail);
			}
			if (biddetail != null) {
				// 根据行业ID 获取行业
				WxIndustry stry = wxIndustryService.find(biddetail.getProjectIndustry());
				if (stry != null) {
					biddetail.setProjectIndustryName(stry.getName());
				}
			}
			map.put("bidInfo", biddetail);
			/*
			 * logger.
			 * info("*_*_*_*_*_**_* 个根据招采ID 获取所有审核信息----------*_*_*_*_*_*_*_*_*_*"
			 * ); BidCheckRecord bidCheckRecord = new BidCheckRecord();
			 * bidCheckRecord.setBidInfoId(bidInfoDetail.getId());
			 * List<BidCheckRecord> checkList =
			 * bidCheckRecordService.findList(bidCheckRecord);
			 * map.put("checkList", checkList);
			 */
            PurchaserAccount Account = PurchaserUtils.getPurchaserAccount();
			if (Account != null) {
				logger.info("*_*_*_*_*_**_* 根据当前登录用户名获取采购商信息----------*_*_*_*_*_*_*_*_*_*");
				// 根据当前登录用户名获取采购商信息
				logger.info("*_*_*_*_*_**_* 当前人是否已在当前项目报名----------*_*_*_*_*_*_*_*_*_*");
				// 当前人是否已在当前项目报名
				BidSignup sugnup = new BidSignup();
				sugnup.setPurchaserAccountId(Account.getPurchaserId());
				sugnup.setBidInfoId(bidInfoDetail.getId());
				signupList = bidSignupService.findList(sugnup);
			}
		} catch (Exception e) {
			logger.info("*_*_*_*_*_**_* ApiBidInfoDetailController  /api/bid/InfoDetail/saveInfo 错误信息：" + e);
		}
		if (signupList != null && signupList.size() > 0) {
			map.put("isSignup", true);
		} else {
			map.put("isSignup", false);
		}
		logger.info(
				"*_*_*_*_*_**_* ApiBidInfoDetailController  /api/bid/InfoDetail/InfoParticular  获取招采信息详情----------接口结束*_*_*_*_*_*_*_*_*_*");
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
		logger.info("*_*_*_*_*_**_* 根据项目ID及采购商id匹配报名信息----------*_*_*_*_*_*_*_*_*_*");
		// 根据当前登录用户名获取采购商信息
		PurchaserAccount Account = PurchaserUtils.getPurchaserAccount();
		if(null == Account){
		    return AjaxJson.fail("登录信息有误，请重新登录！");
        }
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
	public AjaxJson getSignUserList(HttpServletRequest request, HttpServletResponse response, BidSignup bidSignup) {
		Page<BidSignup> page = bidSignupService.findPage(new Page<BidSignup>(request, response), bidSignup);
		if (page.getList() == null || page.getList().size() < 1) {
			logger.info("*_*_*_*_*_*_*_*_*_*  getSignUserList 获取报名信息 ----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		return AjaxJson.layuiTable(page);
	}

	/**
	 * （项目列表换一换）根据热度倒序查询项目列表 至多查询到前15条数据，只是在前台进行至多三次的展示
	 * 
	 * @return
	 */
	@ApiOperation(notes = "getVisitProjectList", httpMethod = "GET", value = "（项目列表换一换）根据热度查询项目列表")
	@RequestMapping("getVisitProjectList")
	public AjaxJson getVisitProjectList(BidInfoDetail bidInfoDetail) {
		logger.info("*_*_*_*_*_**_* 根据热度倒序查询项目列表----------*_*_*_*_*_*_*_*_*_*" + bidInfoDetail);
		// 状态是1 审核通过的 方可查询到
		List<BidInfoDetail> infoList = bidInfoDetailService.getVisitProjectList();
		if (infoList == null || infoList.size() <= 0) {
			logger.info("*_*_*_*_*_*_*_*_*_*  getVisitProjectList 根据热点、查询项目列表----------   未查到数据*_*_*_*_*_*_*_*_*_*");
			return AjaxJson.fail("未查到数据");
		}
		for (BidInfoDetail in : infoList) {
			WxIndustry industry = wxIndustryService.find(in.getProjectIndustry());
			if (industry != null) {
				in.setProjectIndustryName(industry.getName());
			}
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
        PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
        if(null == purchaserAccount){
            return AjaxJson.fail("登录信息有误！");
        }
		return AjaxJson.ok(purchaserAccount);
	}

	/**
	 * @Title: saveInfo
	 * @Description: TODO 用户个人保存招投标信息
	 * @param bidInfoDetail
	 * @return
	 * @return AjaxJson
	 * @author tc
	 * @date 2019年7月24日下午8:52:35
	 */
	@RequestMapping("saveInfo")
	public AjaxJson saveInfo(BidInfoDetail bidInfoDetail) {
		logger.info("*_**_* ApiBidInfoDetailController ->InfoDetail/saveInfo用户个人 保存招投标" + bidInfoDetail.toString());
		try {

			// 根据当前登录用户名获取采购商信息
            PurchaserAccount purchaserAccount = PurchaserUtils.getPurchaserAccount();
            if(null == purchaserAccount){
                return AjaxJson.fail("登录信息有误，请重新登录！");
            }
			if (StringUtils.isBlank(bidInfoDetail.getProjectName())) {
				return AjaxJson.fail("项目名称不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getCompanyName())) {
				return AjaxJson.fail("项目所属公司的名称不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getProjectIndustry())) {
				return AjaxJson.fail("项目所属行业不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getProjectStatus())) {
				return AjaxJson.fail("项目的状态不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getProjectContent())) {
				return AjaxJson.fail("项目的内容不能为空");
			}
			if (bidInfoDetail.getStartTime() == null) {
				return AjaxJson.fail("项目开工时间不能为空");
			}
			if (bidInfoDetail.getStopTime() == null) {
				return AjaxJson.fail("项目竣工时间不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getBelongArea())) {
				return AjaxJson.fail("项目所属地区不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getProjectNeedProduct())) {
				return AjaxJson.fail("项目所需产品不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getBidConcat1())) {
				return AjaxJson.fail("项目所属公司联系人不能为空");
			}
			if (StringUtils.isBlank(bidInfoDetail.getBidConcat1Phone())) {
				return AjaxJson.fail("项目所属公司联系人手机号不能为空");
			}
			bidInfoDetail.setStatus("0");
			bidInfoDetail.setAddTime(new Date());
			bidInfoDetail.setAddUser(purchaserAccount.getPurchaserId());
			bidInfoDetail.setProjectVisitNum(0);
			bidInfoDetailService.save(bidInfoDetail);

		} catch (Exception e) {
			logger.info("*_*_*_*_*_**_* ApiBidInfoController ->saveInfo 错误信息：" + e);
			return AjaxJson.fail("保存失败");

		}
		return AjaxJson.ok("保存成功");
	}

	/**
	 * 新增报名信息保存
	 * 
	 * @returnf
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
		logger.info("*_*_*_*_*_**_* ApiBidInfoDetailController  ->saveSignup  报名信息保存----------接口开始*_*_*_*_*_*_*_*_*_*");
		/*
		 * logger.info("*_*_*_*_*_**_* 保存报名信息参数--companyName:" + companyName +
		 * ",--projectName:" + projectName + ",--signupConcat:" + signupConcat +
		 * ",-purchaserAccountId:" + purchaserId + ",--signupConcatEmail:" +
		 * signupConcatEmail + ",-signupConcatMobile:" + signupConcatMobile +
		 * ",-------message:" + message + ",-----bidInfoId:" + bidInfoId +
		 * ",,signupConcatJob"+signupConcatJob+
		 * "+--------接口开始*_*_*_*_*_*_*_*_*_*");
		 */
		logger.info("*_*_*_*_*_**_* 保存报名信息参数" + ",--signupConcat:" + signupConcat + ",,signupConcatJob"
				+ signupConcatJob + "+--------接口开始*_*_*_*_*_*_*_*_*_*");
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
			bidSignup.setBidInfoId(bidInfoId);
			bidSignup.setPurchaserAccountId(purchaserId);

			List<BidSignup> list = bidSignupService.findList(bidSignup);
			if (list != null && list.size() > 0) {
				return AjaxJson.fail("您已报名，请在-我的报名-中进行查看。");
			}
			bidSignup.setCompanyName(companyName);
			bidSignup.setProjectName(projectName);
			bidSignup.setSignupConcat(signupConcat);
			bidSignup.setSignupConcatEmail(signupConcatEmail);
			bidSignup.setSignupConcatMobile(signupConcatMobile);
			bidSignup.setSignupConcatJob(signupConcatJob);
			bidSignup.setMessage(message);
			logger.info("savesignup " + bidSignup.toString());
			logger.info("*_*_*_*_*_**_* 保存报名信息参数" + ",--signupConcat:" + bidSignup.getSignupConcat()
					+ ",,signupConcatJob" + bidSignup.getSignupConcatJob() + "+--------接口开始*_*_*_*_*_*_*_*_*_*");
			bidSignupService.save(bidSignup);// 保存
		} catch (Exception e) {
			logger.info("*_*_*_*_*_**_* ApiBidInfoDetailController  ->saveSignup 错误信息：" + e);
			return AjaxJson.fail("保存失败");
		}
		return AjaxJson.ok("保存成功");
	}

}