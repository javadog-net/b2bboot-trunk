/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.bid.web;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import com.jhmis.common.utils.Constants;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.bid.entity.BidCheckRecord;
import com.jhmis.modules.bid.entity.BidInfo;
import com.jhmis.modules.bid.service.BidCheckRecordService;
import com.jhmis.modules.bid.service.BidInfoService;
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.entity.purchaser.Purchaser;
import com.jhmis.modules.shop.entity.purchaser.PurchaserAccount;
import com.jhmis.modules.shop.service.IndustryService;
import com.jhmis.modules.shop.service.purchaser.PurchaserAccountService;
import com.jhmis.modules.shop.service.purchaser.PurchaserService;
import com.jhmis.modules.sys.security.Principal;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.WxIndustry;
import com.jhmis.modules.wechat.service.WxIndustryService;

/**
 * 招投标Controller
 * 
 * @author hdx
 * @version 2019-04-11
 */
@Controller
@RequestMapping(value = "${adminPath}/bid/bidInfo")
public class BidInfoController extends BaseController {

	@Autowired
	private BidInfoService bidInfoService;

	@Autowired
	private PurchaserService purchaserService;
	@Autowired
	private PurchaserAccountService purchaserAccountService;
	@Autowired
	private WxIndustryService wxIndustryService;
	@Autowired
	private BidCheckRecordService bidCheckRecordService;
	
	@Autowired
	private IndustryService industryService;

	@ModelAttribute
	public BidInfo get(@RequestParam(required = false) String id) {
		BidInfo entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = bidInfoService.get(id);
		}
		if (entity == null) {
			entity = new BidInfo();
		}
		return entity;
	}

	/**
	 * 招投标列表页面
	 */
	@RequiresPermissions("bid:bidInfo:list")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/bid/bidInfoList";
	}

	/**
	 * 招投标列表数据
	 */
	@ResponseBody
	@RequiresPermissions("bid:bidInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BidInfo bidInfo, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<BidInfo> page = bidInfoService.findPage(new Page<BidInfo>(request, response), bidInfo);
		// 根据行业ID 获取行业
		List<BidInfo> info = page.getList();
		for (BidInfo in : info) {
			WxIndustry stry = wxIndustryService.find(in.getIndustry());
			if (stry != null) {
				in.setIndustry(stry.getName());
			}
		}
		return getBootstrapData(page);
	}

	// @RequiresPermissions(value={"bid:bidInfo:view","bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "particular")
	public String particular(HttpServletRequest request, BidInfo bidInfo, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			String yulan = request.getParameter("preview");
			if (yulan != null && yulan.equals("1")) {
				model.addAttribute("preview", 1);
			} else {
				model.addAttribute("preview", 0);
			}
			// 根据行业ID 获取行业
			WxIndustry stry = wxIndustryService.find(bidInfo.getIndustry());
			if (stry != null) {
				bidInfo.setIndustry(stry.getName());
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BidCheckRecord bidCheckRecord = new BidCheckRecord();
			bidCheckRecord.setBidInfoId(bidInfo.getId());
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
			model.addAttribute("bidInfo", bidInfo);
			if (StringUtils.isBlank(bidInfo.getId())) {// 如果ID是空为添加
				model.addAttribute("isAdd", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/bid/bidInfoParticular";
		// return "purchaser/view/user/login";

	}

	/**
	 * 查看，增加，编辑招投标表单页面
	 */
	// @RequiresPermissions(value={"bid:bidInfo:view","bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, BidInfo bidInfo, Model model,
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
		/*	// 读取行业信息
			List<Industry> industryList = industryService.findList(new Industry());*/
			WxIndustry wxIndustry =new WxIndustry ();
			List<WxIndustry> industryList =wxIndustryService.findList(wxIndustry);
			model.addAttribute("industryList", industryList);
			model.addAttribute("bidInfo", bidInfo);
			if (StringUtils.isBlank(bidInfo.getId())) {// 如果ID是空为添加
				model.addAttribute("isAdd", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/bid/bidInfoForm";
		// return "purchaser/view/user/login";

	}


	/**
	 * 查看，增加，编辑招投标表单页面
	 */
	// @RequiresPermissions(value={"bid:bidInfo:view","bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "edit")
	public String edit(HttpServletRequest request, BidInfo bidInfo, Model model,
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
		/*	// 读取行业信息
			List<Industry> industryList = industryService.findList(new Industry());*/
			WxIndustry wxIndustry =new WxIndustry ();
			List<WxIndustry> industryList =wxIndustryService.findList(wxIndustry);
			model.addAttribute("industryList", industryList);
			model.addAttribute("bidInfo", bidInfo);
			if (StringUtils.isBlank(bidInfo.getId())) {// 如果ID是空为添加
				model.addAttribute("isAdd", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/bid/bidInfoEdit";
		// return "purchaser/view/user/login";

	}

	
	
	/**
	 * 保存招投标
	 */
	// @RequiresPermissions(value={"bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request, BidInfo bidInfo, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		if (!beanValidator(model, bidInfo)) {
			return form(request, bidInfo, model, redirectAttributes);
		}
		// 新增或编辑表单保存
		bidInfoService.save(bidInfo);// 保存
		addMessage(redirectAttributes, "保存招投标成功");
		return "redirect:" + Global.getAdminPath() + "/bid/bidInfo/?repage";
	}

	/**
	 * 更改有效状态
	 */
	// @RequiresPermissions(value={"bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@ResponseBody
	@RequestMapping(value = "upStatus")
	public AjaxJson upStatus(HttpServletRequest request, BidInfo bidInfo, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		// 新增或编辑表单保存
		AjaxJson j = new AjaxJson();
		bidInfoService.save(bidInfo);// 保存
		j.setMsg("操作成功");
		return j;
	}

	/**
	 * 批量更改有效状态
	 */
	// @RequiresPermissions(value={"bid:bidInfo:add","bid:bidInfo:edit"},logical=Logical.OR)
	@ResponseBody
	@RequestMapping(value = "updateValid")
	public AjaxJson updateValid(HttpServletRequest request, String ids, String valid, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		// 新增或编辑表单保存
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			bidInfoService.updateValid(id, valid);// 保存
		}
		j.setMsg("操作成功");
		return j;
	}

	
	
	/**
	 * 删除招投标
	 */
	@ResponseBody
	// @RequiresPermissions("bid:bidInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BidInfo bidInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		bidInfoService.delete(bidInfo);
		j.setMsg("删除招投标成功");
		return j;
	}

	/**
	 * 批量删除招投标
	 */
	@ResponseBody
	// @RequiresPermissions("bid:bidInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			bidInfoService.delete(bidInfoService.get(id));
		}
		j.setMsg("删除招投标成功");
		return j;
	}

	/**
	 * 批量删除招投标
	 */
	@ResponseBody
	// @RequiresPermissions("bid:bidInfo:del")
	@RequestMapping(value = "deleteAllByLogic")
	public AjaxJson deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		BidInfo bidInfo = null;
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			bidInfo = new BidInfo();
			bidInfo.setId(id);
			bidInfo.setIsDel("1");
			bidInfoService.deleteAllByLogic(bidInfo);
		}
		j.setMsg("删除招投标成功");
		return j;
	}


	/**
	 * 导出excel文件
	 */
	@ResponseBody
	// @RequiresPermissions("bid:bidInfo:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(BidInfo bidInfo, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "招投标" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<BidInfo> page = bidInfoService.findPage(new Page<BidInfo>(request, response, -1), bidInfo);
			new ExportExcel("招投标", BidInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	// @RequiresPermissions("bid:bidInfo:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		Principal principal = UserUtils.getPrincipal();
		Purchaser purchaser =null;
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			PurchaserAccount entity = purchaserAccountService.getByName(principal.getLoginName());
			List<BidInfo> list = ei.getDataList(BidInfo.class);
			List<WxIndustry> industryList = null;
			for (BidInfo bidInfo : list) {
				try {
					//暂时模糊匹配行业如果存在取其id 不存在存其文字
					WxIndustry wxIndustry =new WxIndustry ();
					if(bidInfo.getIndustry()!=null){
					wxIndustry.setName(bidInfo.getIndustry());
					industryList =wxIndustryService.findList(wxIndustry);
					}
					if(industryList.size()>0){
						WxIndustry wxIndustrys  = industryList.get(0);
						bidInfo.setIndustry(wxIndustrys.getId());
					}
					bidInfo.setSource("2");
					//客服导入直接为有效且审核通过
					/*bidInfo.setAreaInfo(purchaser.getAreaInfo());
					bidInfo.setProvinceId(purchaser.getProvinceId());
					bidInfo.setCityId(purchaser.getCityId());
					bidInfo.setBidConcat(entity.getLoginName());
					bidInfo.setBidConcatEmail(purchaser.getEmail());
					bidInfo.setBidConcatPhone(purchaser.getMobile());*/
					bidInfo.setAddUser(entity.getPurchaserId());
					bidInfo.setStatus(Constants.BID_CHECK_PASS);
					bidInfo.setIsValid(Constants.BID_VALID_YES);
					bidInfoService.save(bidInfo);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
					System.out.println(ex.getMessage());   
				} catch (Exception ex) {
					failureNum++;
					ex.getStackTrace();
					System.out.println(ex.getMessage());   

				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条招投标记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条招投标记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入招投标失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/bid/bidInfo/?repage";
	}

	/**
	 * 下载导入招投标数据模板
	 */
	// @RequiresPermissions("bid:bidInfo:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "招投标数据导入模板.xlsx";
			List<BidInfo> list = Lists.newArrayList();
			new ExportExcel("招采项目数据", BidInfo.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/bid/bidInfo/?repage";
	}

}