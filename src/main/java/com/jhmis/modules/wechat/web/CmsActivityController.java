/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

import java.util.Calendar;
import java.util.Collections;
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
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.persistence.UseUrl;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.sys.entity.User;
import com.jhmis.modules.sys.utils.UserUtils;
import com.jhmis.modules.wechat.entity.CmsActivity;
import com.jhmis.modules.wechat.entity.CmsPrize;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;
import com.jhmis.modules.wechat.service.CmsActivityService;
import com.jhmis.modules.wechat.service.CmsPrizeService;
import com.jhmis.modules.wechat.service.CmsSignupDrawService;
import com.jhmis.modules.wechat.service.CmsWinPrizeService;
import com.jhmis.modules.wechat.service.WxMeetingService;

/**
 * 活动表操作Controller
 * 
 * @author tc
 * @version 2019-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/cmsActivity")
public class CmsActivityController extends BaseController {
	@Autowired
	private CmsSignupDrawService cmsSignupDrawService;
	@Autowired
	private CmsPrizeService cmsPrizeService;
	@Autowired
	private CmsActivityService cmsActivityService;
	@Autowired
	private WxMeetingService meetingService;
	@Autowired
	private UseUrl useUrl;

	@ModelAttribute
	public CmsActivity get(@RequestParam(required = false) String id) {
		CmsActivity entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = cmsActivityService.get(id);
		}
		if (entity == null) {
			entity = new CmsActivity();
		}
		return entity;
	}

	/**
	 * 活动表列表页面
	 */
	@RequiresPermissions("wechat:cmsActivity:list")
	@RequestMapping(value = { "list", "" })
	public String list(CmsActivity cmsActivity, Model model) {

		model.addAttribute("cmsActivity", cmsActivity);
		String meetingId = cmsActivity.getMeetingId();
		String meetingName = meetingService.get(meetingId).getName();
		System.out.println(meetingName + "=====mname");
		model.addAttribute("meetingName", meetingName);
		model.addAttribute("meetingId", meetingId);
		return "modules/wechat/cmsActivityList";
	}

	/**
	 * 活动表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsActivity:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsActivity cmsActivity, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String a = cmsActivity.getMeetingId();
		System.out.println("==aaaaaaaa==" + a);
		Page<CmsActivity> page = cmsActivityService.findPage(new Page<CmsActivity>(request, response), cmsActivity);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑活动表表单页面
	 */
	@RequiresPermissions(value = { "wechat:cmsActivity:view", "wechat:cmsActivity:add",
			"wechat:cmsActivity:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsActivity cmsActivity, Model model) {
		System.out.println("meetingName" + cmsActivity.getMeetingName());
		System.out.println("$$$$$$$$$$$&&&&&&&&" + cmsActivity.getListcmsPrize() + "^^^^^^^^^^^^^^^^^^^");
		// cmsActivity=cmsActivityService.get(cmsActivity.getId());
		if (StringUtils.isNotBlank(cmsActivity.getId())) {
			CmsPrize cmsPrize = new CmsPrize();
			cmsPrize.setActvId(cmsActivity.getId());
			System.out.println("[[[[[[[" + cmsPrize + "]]]]]]]");
			List<CmsPrize> cmsprizelist = cmsPrizeService.findList(cmsPrize);
			System.out.println(cmsprizelist.size());
			if (cmsprizelist != null && cmsprizelist.size() > 0) {
				cmsActivity.setListcmsPrize(cmsprizelist);
			}
		}
		model.addAttribute("cmsActivity", cmsActivity);
		if (StringUtils.isBlank(cmsActivity.getId())) {// 如果ID是空为添加
			model.addAttribute("isAdd", true);
		}

		return "modules/wechat/cmsActivityForm";
	}

	/**
	 * 保存活动表
	 */
	@RequiresPermissions(value = { "wechat:cmsActivity:add", "wechat:cmsActivity:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsActivity cmsActivity, Model model, RedirectAttributes redirectAttributes) throws Exception {
		if (!beanValidator(model, cmsActivity)) {
			return form(cmsActivity, model);
		}
		if (cmsActivity.getStartTime() == null || cmsActivity.getStartTime().equals("")) {
			redirectAttributes.addFlashAttribute("error", "error");
			addMessage(redirectAttributes, "创建活动必须设定开始时间！");
			return "redirect:" + Global.getAdminPath() + "/wechat/cmsActivity/?repage&meetingId="
					+ cmsActivity.getMeetingId();
		}
		List<CmsPrize> cpList = cmsActivity.getListcmsPrize();
		if (cpList == null || cpList.size() == 0) {
			redirectAttributes.addFlashAttribute("error", "error");
			addMessage(redirectAttributes, "创建活动必须创建奖项！");
			return "redirect:" + Global.getAdminPath() + "/wechat/cmsActivity/?repage&meetingId="
					+ cmsActivity.getMeetingId();
		}
		String id = cmsActivity.getId();
		if (StringUtils.isNotEmpty(id)) {
			CmsPrize cmsPrize = new CmsPrize();
			cmsPrize.setActvId(id);
			List<CmsPrize> list = cmsPrizeService.findList(cmsPrize);
			for (CmsPrize c : list) {
				if ("1".equals(c.getPrizeTab())) {
					redirectAttributes.addFlashAttribute("error", "error");
					addMessage(redirectAttributes, "抽奖已经进行，请勿进行操作！");
					return "redirect:" + Global.getAdminPath() + "/wechat/cmsActivity/?repage&meetingId="
							+ cmsActivity.getMeetingId();
				}
			}
		}
		System.out.println("==============" + cmsActivity + "============");
		// 新增或编辑表单保存
		if (StringUtils.isBlank(cmsActivity.getCreatUser())) {
			User currentUser = UserUtils.getUser();
			cmsActivity.setCreatUser(currentUser.getName());
		}
		if (null == cmsActivity.getCreatTime()) {
			cmsActivity.setCreatTime(new Date());
		}
		// cmsActivity.setStartTime(new Date());// 暂时没用
		if (StringUtils.isBlank(cmsActivity.getActvTab())) {
			cmsActivity.setActvTab("0");
		}
		cmsActivityService.save(cmsActivity);// 保存活动表
		String actvId = cmsActivity.getId();
		List<CmsPrize> list = cmsActivity.getListcmsPrize();
		CmsPrize cprize = new CmsPrize();
		cprize.setActvId(actvId);
		List<CmsPrize> cprizelist = cmsPrizeService.findList(cprize);
		if (null != cprizelist && cprizelist.size() > 0) {
			for (CmsPrize cmsp : cprizelist) {
				cmsPrizeService.delete(cmsp);
			}
		}

		if (null != list && list.size() > 0) {
			int i = 0;
			for (CmsPrize cmsPrize : list) {
				if (null != cmsPrize.getPrizeType() || null != cmsPrize.getPrizeNum() || "" != cmsPrize.getPrizeNum()
						|| "" != cmsPrize.getPrizeType()) {
					cmsPrize.setActvId(actvId);
					// 因为数据库保存时间差距在毫秒之间，甚至更短，因无法进行正常排列。
					// 以下为每个奖项创建时间，每个奖项在当前时间的秒数下加上循环的次数
					// 用于排列升序查询奖项
					Date date = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(date);
					c.add(Calendar.SECOND, i);
					date = c.getTime();
					cmsPrize.setAddTime(date);
					cmsPrize.setMeetingId(cmsActivity.getMeetingId());
					cmsPrize.setPrizeTab("0");
					cmsPrizeService.save(cmsPrize);// 保存奖项表
				}
				i = i + 1;
			}
		}
		addMessage(redirectAttributes, "保存活动表成功");
		return "redirect:" + Global.getAdminPath() + "/wechat/cmsActivity/?repage&meetingId="
				+ cmsActivity.getMeetingId();
	}

	/**
	 * 删除活动表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsActivity:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsActivity cmsActivity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsActivityService.delete(cmsActivity);
		j.setMsg("删除活动表成功");
		return j;
	}

	/**
	 * 批量删除活动表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsActivity:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			cmsActivityService.delete(cmsActivityService.get(id));
			System.out.println("cmsactivity删除成功");
			CmsPrize cmsprize = new CmsPrize();
			cmsprize.setActvId(id);
			cmsPrizeService.deleteAll(cmsPrizeService.findList(cmsprize));
			System.out.println("cmsprize删除成功");
			CmsSignupDraw cmsDrawSignup = new CmsSignupDraw();
			cmsDrawSignup.setActvId(id);
			cmsSignupDrawService.deleteAll(cmsSignupDrawService.findList(cmsDrawSignup));
			System.out.println("cmssignupdraw删除成功");

		}
		j.setMsg("删除活动表成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsActivity:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(CmsActivity cmsActivity, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "活动表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<CmsActivity> page = cmsActivityService.findPage(new Page<CmsActivity>(request, response, -1),
					cmsActivity);
			new ExportExcel("活动表", CmsActivity.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出活动表记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * Excel数据
	 * 
	 */
	@RequiresPermissions("wechat:cmsActivity:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsActivity> list = ei.getDataList(CmsActivity.class);
			for (CmsActivity cmsActivity : list) {
				try {
					cmsActivityService.save(cmsActivity);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条活动表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条活动表记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入活动表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/cmsActivity/?repage";
	}

	/**
	 * 下载导入活动表数据模板
	 */
	@RequiresPermissions("wechat:cmsActivity:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "活动表数据导入模板.xlsx";
			List<CmsActivity> list = Lists.newArrayList();
			new ExportExcel("活动表数据", CmsActivity.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/cmsActivity/?repage";
	}

}