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
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.modules.wechat.entity.WxBannerClassify;
import com.jhmis.modules.wechat.service.WxBannerClassifyService;

/**
 * banner分类表Controller
 * @author lvyangzhuo
 * @version 2018-11-22
 */
@Controller
@RequestMapping(value = "${adminPath}/wechat/wxBannerClassify")
public class WxBannerClassifyController extends BaseController {

	@Autowired
	private WxBannerClassifyService wxBannerClassifyService;
	
	@ModelAttribute
	public WxBannerClassify get(@RequestParam(required=false) String id) {
		WxBannerClassify entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wxBannerClassifyService.get(id);
		}
		if (entity == null){
			entity = new WxBannerClassify();
		}
		return entity;
	}
	
	/**
	 * banner分类表列表页面
	 */
	@RequiresPermissions("wechat:wxBannerClassify:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/wechat/chat/wxBannerClassifyList";
	}
	
	/**
	 * banner分类表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBannerClassify:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(WxBannerClassify wxBannerClassify, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<WxBannerClassify> page = wxBannerClassifyService.findPage(new Page<WxBannerClassify>(request, response), wxBannerClassify); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑banner分类表表单页面
	 */
	@RequiresPermissions(value={"wechat:wxBannerClassify:view","wechat:wxBannerClassify:add","wechat:wxBannerClassify:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(WxBannerClassify wxBannerClassify, Model model) {
		model.addAttribute("wxBannerClassify", wxBannerClassify);
		if(StringUtils.isBlank(wxBannerClassify.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/wechat/chat/wxBannerClassifyForm";
	}

	/**
	 * 保存banner分类表
	 */
	@RequiresPermissions(value={"wechat:wxBannerClassify:add","wechat:wxBannerClassify:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(WxBannerClassify wxBannerClassify, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, wxBannerClassify)){
			return form(wxBannerClassify, model);
		}
		//新增或编辑表单保存
		wxBannerClassifyService.save(wxBannerClassify);//保存
		addMessage(redirectAttributes, "保存banner分类表成功");
		return "redirect:"+Global.getAdminPath()+"/wechat/chat/wxBannerClassify/?repage";
	}
	
	/**
	 * 删除banner分类表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBannerClassify:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(WxBannerClassify wxBannerClassify, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		wxBannerClassifyService.delete(wxBannerClassify);
		j.setMsg("删除banner分类表成功");
		return j;
	}
	
	/**
	 * 批量删除banner分类表
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBannerClassify:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wxBannerClassifyService.delete(wxBannerClassifyService.get(id));
		}
		j.setMsg("删除banner分类表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wechat:wxBannerClassify:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(WxBannerClassify wxBannerClassify, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "banner分类表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<WxBannerClassify> page = wxBannerClassifyService.findPage(new Page<WxBannerClassify>(request, response, -1), wxBannerClassify);
    		new ExportExcel("banner分类表", WxBannerClassify.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出banner分类表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("wechat:wxBannerClassify:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<WxBannerClassify> list = ei.getDataList(WxBannerClassify.class);
			for (WxBannerClassify wxBannerClassify : list){
				try{
					wxBannerClassifyService.save(wxBannerClassify);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条banner分类表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条banner分类表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入banner分类表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/chat/wxBannerClassify/?repage";
    }
	
	/**
	 * 下载导入banner分类表数据模板
	 */
	@RequiresPermissions("wechat:wxBannerClassify:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "banner分类表数据导入模板.xlsx";
    		List<WxBannerClassify> list = Lists.newArrayList(); 
    		new ExportExcel("banner分类表数据", WxBannerClassify.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wechat/chat/wxBannerClassify/?repage";
    }

}