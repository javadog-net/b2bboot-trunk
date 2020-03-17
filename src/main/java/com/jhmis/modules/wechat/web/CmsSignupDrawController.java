/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.wechat.web;

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
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.wechat.entity.CmsPrize;
import com.jhmis.modules.wechat.entity.CmsSignupDraw;
import com.jhmis.modules.wechat.service.CmsPrizeService;
import com.jhmis.modules.wechat.service.CmsSignupDrawService;

/**
 * 参与报名抽奖Controller
 * 
 * @author tc
 * @version 2019-02-27
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/cmsSignupDraw")
public class CmsSignupDrawController extends BaseController {

	@Autowired
	private CmsSignupDrawService cmsSignupDrawService;
	@Autowired
	private CmsPrizeService cmsPrizeService;

	@ModelAttribute
	public CmsSignupDraw get(@RequestParam(required = false) String id) {
		CmsSignupDraw entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = cmsSignupDrawService.get(id);
		}
		if (entity == null) {
			entity = new CmsSignupDraw();
		}
		return entity;
	}

	/**
	 * 参与报名抽奖表列表页面
	 */
	@RequiresPermissions("wechat:cmsSignupDraw:list")
	@RequestMapping(value = { "list", "" })
	public String list(CmsSignupDraw cmsSignupDraw, Model model) {
		System.out.println("这是活动idactvid" + cmsSignupDraw.getActvId());
		model.addAttribute("cmsSignupDraw", cmsSignupDraw);
		return "modules/wechat/cmsSignupDrawList";
	}

	/**
	 * 参与报名抽奖表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsSignupDraw:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsSignupDraw cmsSignupDraw, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		System.out.println("===" + cmsSignupDraw + "===");
		Page<CmsSignupDraw> page = cmsSignupDrawService.findPage(new Page<CmsSignupDraw>(request, response),
				cmsSignupDraw);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑参与报名抽奖表表单页面
	 */
	@RequiresPermissions(value = { "wechat:cmsSignupDraw:view", "wechat:cmsSignupDraw:add",
			"wechat:cmsSignupDraw:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsSignupDraw cmsSignupDraw, Model model) {
	System.out.println("cmsSignupDarw"+cmsSignupDraw.getActvId());
		model.addAttribute("cmsSignupDraw", cmsSignupDraw);
		if (StringUtils.isBlank(cmsSignupDraw.getId())) {// 如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		try {

			String actvid = cmsSignupDraw.getActvId();
			CmsPrize cmsPrize = new CmsPrize();
			cmsPrize.setActvId(actvid);
			List<CmsPrize> list = cmsPrizeService.findList(cmsPrize);
			if (list != null && list.size() > 0) {
				model.addAttribute("prizelist", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/wechat/cmsSignupDrawForm";
	}

	/**
	 * 保存参与报名抽奖表
	 */
	@RequiresPermissions(value = { "wechat:cmsSignupDraw:add", "wechat:cmsSignupDraw:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsSignupDraw cmsSignupDraw, Model model, RedirectAttributes redirectAttributes,
			String cmsPrize,HttpServletRequest request
			)
			throws Exception {
		if (!beanValidator(model, cmsSignupDraw)) {
			return form(cmsSignupDraw, model);
		}
		cmsPrize=request.getParameter("cmsPrize");
		cmsSignupDraw.setPrizeIdTab(cmsPrize);
		// 新增或编辑表单保存
		
		cmsSignupDrawService.save(cmsSignupDraw);// 保存
		//model.addAttribute("cmsSignupDraw", cmsSignupDraw);
		addMessage(redirectAttributes, "保存参与报名抽奖表成功");
		//return "forward:/list.do?actvId="+cmsSignupDraw.getActvId();
		//return "modules/wechat/cmsSignupDrawList";
		return "redirect:"+Global.getAdminPath()+"/wechat/cmsSignupDraw/?repage&actvId="+cmsSignupDraw.getActvId();
	}

	/**
	 * 删除参与报名抽奖表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsSignupDraw:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsSignupDraw cmsSignupDraw, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsSignupDrawService.delete(cmsSignupDraw);
		j.setMsg("删除参与报名抽奖表成功");
		return j;
	}

	/**
	 * 批量删除参与报名抽奖表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsSignupDraw:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			cmsSignupDrawService.delete(cmsSignupDrawService.get(id));
		}
		j.setMsg("删除参与报名抽奖表成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:cmsSignupDraw:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(CmsSignupDraw cmsSignupDraw, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "参与报名抽奖表" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<CmsSignupDraw> page = cmsSignupDrawService.findPage(new Page<CmsSignupDraw>(request, response, -1),
					cmsSignupDraw);
			new ExportExcel("参与报名抽奖表", CmsSignupDraw.class).setDataList(page.getList()).write(response, fileName)
					.dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出参与报名抽奖表记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("wechat:cmsSignupDraw:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsSignupDraw> list = ei.getDataList(CmsSignupDraw.class);
			for (CmsSignupDraw cmsSignupDraw : list) {
				try {
					cmsSignupDrawService.save(cmsSignupDraw);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条参与报名抽奖表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条参与报名抽奖表记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入参与报名抽奖表失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/cmsSignupDraw/?repage";
	}

	/**
	 * 下载导入参与报名抽奖表数据模板
	 */
	@RequiresPermissions("wechat:cmsSignupDraw:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "参与报名抽奖表数据导入模板.xlsx";
			List<CmsSignupDraw> list = Lists.newArrayList();
			new ExportExcel("参与报名抽奖表数据", CmsSignupDraw.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/wechat/cmsSignupDraw/?repage";
	}

}