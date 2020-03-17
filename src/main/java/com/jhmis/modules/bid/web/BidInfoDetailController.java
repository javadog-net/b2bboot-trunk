/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.bid.entity.BidCheckRecord;
import com.jhmis.modules.bid.entity.BidInfoDetail;
import com.jhmis.modules.bid.service.BidCheckRecordService;
import com.jhmis.modules.bid.service.BidInfoDetailService;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.sys.security.Principal;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxIndustry;
import com.jhmis.modules.wechat.service.WxIndustryService;

/**
 * 招投标Controller
 * 
 * @author tc
 * @version 2019-07-19
 */
@Controller
@RequestMapping(value = "${adminPath}/bid/bidInfoDetail")
public class BidInfoDetailController extends BaseController {

	@Autowired
	private BidInfoDetailService bidInfoDetailService;
	@Autowired
	private WxIndustryService wxIndustryService;
	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private BidCheckRecordService bidCheckRecordService;

	@ModelAttribute
	public BidInfoDetail get(@RequestParam(required = false) String id) {
		BidInfoDetail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bidInfoDetailService.get(id);
		}
		if (entity == null) {
			entity = new BidInfoDetail();
		}
		return entity;
	}

	/**
	 * 招投标列表页面
	 */
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/bid/bidInfoDetailList";
	}

	/**
	 * 招投标列表数据
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BidInfoDetail bidInfoDetail, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<BidInfoDetail> pa=new Page<BidInfoDetail>(request, response);
		pa.setOrderBy("add_time desc");
		Page<BidInfoDetail> page = bidInfoDetailService.findPage(pa,
				bidInfoDetail);
		List<BidInfoDetail> info = page.getList();
		for (BidInfoDetail in : info) {
			WxIndustry stry = wxIndustryService.find(in.getProjectIndustry());
			if (stry != null) {
				in.setProjectIndustry(stry.getName());
			}
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑招投标表单页面
	 */

	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, BidInfoDetail bidInfoDetail, Model model,
			RedirectAttributes redirectAttributes) {
		Principal principal = UserUtils.getPrincipal();
		Purchaser purchaser = new Purchaser();
		try {

			PurchaserAccount entity = purchaserAccountService.getByName(principal.getLoginName());
			if (entity != null) {
				purchaser = purchaserService.get(entity.getPurchaserId());
			}

			model.addAttribute("purchaserAccount", entity);
			model.addAttribute("purchaser", purchaser);
			// 行业已经在前台写死了。
			/*
			 * WxIndustry wxIndustry = new WxIndustry(); List<WxIndustry>
			 * industryList = wxIndustryService.findList(wxIndustry);
			 * model.addAttribute("industryList", industryList);
			 */
			model.addAttribute("bidInfoDetail", bidInfoDetail);
			if (StringUtils.isBlank(bidInfoDetail.getId())) {// 如果ID是空为添加
				model.addAttribute("isAdd", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/bid/bidInfoDetailForm";

	}

	@RequestMapping(value = "particular")
	public String particular(HttpServletRequest request, BidInfoDetail bidInfoDetail, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			String yulan = request.getParameter("preview");
			if (yulan != null && yulan.equals("1")) {
				model.addAttribute("preview", 1);
			} else {
				model.addAttribute("preview", 0);
			}
			// 根据行业ID 获取行业
			WxIndustry stry = wxIndustryService.find(bidInfoDetail.getProjectIndustry());
			if (stry != null) {
				bidInfoDetail.setProjectIndustry(stry.getName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BidCheckRecord bidCheckRecord = new BidCheckRecord();
			bidCheckRecord.setBidInfoId(bidInfoDetail.getId());
			List<BidCheckRecord> recordList = bidCheckRecordService.findList(bidCheckRecord);
			if (recordList != null) {
				for (BidCheckRecord bid : recordList) {
					if (bid.getCheckStatus().equals(Constants.BID_CHECK_PASS)) {
						bid.setCheckStatus("审核通过");
					} else if (bid.getCheckStatus().equals(Constants.BID_CHECK_FALSE)) {
						bid.setCheckStatus("审核拒绝");

					}
					String time = sdf.format(bid.getCheckTime());
					bid.setCheckDate(time);
				}
				model.addAttribute("recordList", recordList);
			}
			model.addAttribute("bidInfoDetail", bidInfoDetail);
			if (StringUtils.isBlank(bidInfoDetail.getId())) {// 如果ID是空为添加
				model.addAttribute("isAdd", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/bid/bidInfoParticular";
		// return "purchaser/view/user/login";

	}

	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request, BidInfoDetail bidInfoDetail, Model model,
			RedirectAttributes redirectAttributes) {
		Principal principal = UserUtils.getPrincipal();
		Purchaser purchaser = new Purchaser();
		try {
			PurchaserAccount entity = purchaserAccountService.getByName(principal.getLoginName());
			if (entity != null) {
				purchaser = purchaserService.get(entity.getPurchaserId());
			}

			model.addAttribute("purchaserAccount", entity);
			model.addAttribute("purchaser", purchaser);
			/*
			 * // 读取行业信息 List<Industry> industryList =
			 * industryService.findList(new Industry());
			 */
			WxIndustry wxIndustry = new WxIndustry();
			List<WxIndustry> industryList = wxIndustryService.findList(wxIndustry);
			model.addAttribute("industryList", industryList);
			model.addAttribute("bidInfoDetail", bidInfoDetail);
			if (StringUtils.isBlank(bidInfoDetail.getId())) {// 如果ID是空为添加
				model.addAttribute("isAdd", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/bid/bidInfoEdit";

	}

	@ResponseBody
	@RequestMapping(value = "updateValid")
	public AjaxJson updateValid(HttpServletRequest request, String ids, String valid, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		// 新增或编辑表单保存
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			bidInfoDetailService.updateValid(id, valid);// 保存
		}
		j.setMsg("操作成功");
		return j;
	}
	
	/**
	 * 保存招投标
	 */
	@RequiresPermissions(value = { "bid:bidInfoDetail:add", "bid:bidInfoDetail:list" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request, BidInfoDetail bidInfoDetail, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		Principal principal = UserUtils.getPrincipal();
		if (principal != null) {
			bidInfoDetail.setAddUser(principal.getLoginName());
		}
		if (!beanValidator(model, bidInfoDetail)) {
			return form(request, bidInfoDetail, model, redirectAttributes);
		}
		bidInfoDetail.setAddTime(new Date());
		// 新增或编辑表单保存
		bidInfoDetailService.save(bidInfoDetail);// 保存
		addMessage(redirectAttributes, "保存招投标成功");
		return "redirect:" + Global.getAdminPath() + "/bid/bidInfoDetail/?repage";
	}

	/**
	 * 删除招投标
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BidInfoDetail bidInfoDetail, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		bidInfoDetailService.delete(bidInfoDetail);
		j.setMsg("删除招投标成功");
		return j;
	}

	/**
	 * 批量删除招投标
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			bidInfoDetailService.delete(bidInfoDetailService.get(id));
		}
		j.setMsg("删除招投标成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(BidInfoDetail bidInfoDetail, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "招投标" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<BidInfoDetail> page = bidInfoDetailService.findPage(new Page<BidInfoDetail>(request, response, -1),
					bidInfoDetail);
			new ExportExcel("招投标", BidInfoDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出招投标记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
	 Principal principal = UserUtils.getPrincipal();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BidInfoDetail> list = ei.getDataList(BidInfoDetail.class);
			// PurchaserAccount entity =
			// purchaserAccountService.getByName(principal.getLoginName());
			List<WxIndustry> industryList = null;
			for (BidInfoDetail bidInfoDetail : list) {
				try {
					WxIndustry wxIndustry = new WxIndustry();
					if (bidInfoDetail.getProjectIndustry() != null) {
						wxIndustry.setName(bidInfoDetail.getProjectIndustry());
						industryList = wxIndustryService.findList(wxIndustry);
					}
					if (industryList.size() > 0) {
						WxIndustry wxIndustrys = industryList.get(0);
						bidInfoDetail.setProjectIndustry(wxIndustrys.getId());
					}
					bidInfoDetail.setAddUser(principal.getLoginName());
					bidInfoDetail.setStatus(Constants.BID_CHECK_PASS);
	                bidInfoDetail.setAddTime(new Date());
	                bidInfoDetail.setProjectVisitNum(0);
					bidInfoDetailService.save(bidInfoDetail);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条招投标记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条招投标记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入招投标失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/bid/bidInfoDetail/?repage";
	}

	/**
	 * 下载导入招投标数据模板
	 */
	@RequiresPermissions("bid:bidInfoDetail:list")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "招投标数据导入模板.xlsx";
			List<BidInfoDetail> list = Lists.newArrayList();
			new ExportExcel("招投标数据", BidInfoDetail.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/bid/bidInfoDetail/?repage";
	}

}