/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

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
import com.jhmis.modules.cms.entity.InfoImg;
import com.jhmis.modules.cms.service.InfoImgService;

/**
 * 内容图片集Controller
 * @author lydia
 * @version 2019-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/infoImg")
public class InfoImgController extends BaseController {

	@Autowired
	private InfoImgService infoImgService;
	
	@ModelAttribute
	public InfoImg get(@RequestParam(required=false) String id) {
		InfoImg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = infoImgService.get(id);
		}
		if (entity == null){
			entity = new InfoImg();
		}
		return entity;
	}
	
	/**
	 * 内容图片集列表页面
	 */
	@RequiresPermissions("cms:infoImg:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/infoImgList";
	}
	
	/**
	 * 内容图片集列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoImg:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(InfoImg infoImg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InfoImg> page = infoImgService.findPage(new Page<InfoImg>(request, response), infoImg); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑内容图片集表单页面
	 */
	@RequiresPermissions(value={"cms:infoImg:view","cms:infoImg:add","cms:infoImg:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(InfoImg infoImg, Model model) {
		model.addAttribute("infoImg", infoImg);
		if(StringUtils.isBlank(infoImg.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/cms/infoImgForm";
	}

	/**
	 * 保存内容图片集
	 */
	@RequiresPermissions(value={"cms:infoImg:add","cms:infoImg:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(InfoImg infoImg, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, infoImg)){
			return form(infoImg, model);
		}
		//新增或编辑表单保存
		infoImgService.save(infoImg);//保存
		addMessage(redirectAttributes, "保存内容图片集成功");
		return "redirect:"+Global.getAdminPath()+"/cms/infoImg/?repage";
	}
	
	/**
	 * 删除内容图片集
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoImg:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(InfoImg infoImg, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		infoImgService.delete(infoImg);
		j.setMsg("删除内容图片集成功");
		return j;
	}
	
	/**
	 * 批量删除内容图片集
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoImg:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			infoImgService.delete(infoImgService.get(id));
		}
		j.setMsg("删除内容图片集成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:infoImg:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(InfoImg infoImg, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "内容图片集"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<InfoImg> page = infoImgService.findPage(new Page<InfoImg>(request, response, -1), infoImg);
    		new ExportExcel("内容图片集", InfoImg.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出内容图片集记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:infoImg:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<InfoImg> list = ei.getDataList(InfoImg.class);
			for (InfoImg infoImg : list){
				try{
					infoImgService.save(infoImg);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条内容图片集记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条内容图片集记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入内容图片集失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/infoImg/?repage";
    }
	
	/**
	 * 下载导入内容图片集数据模板
	 */
	@RequiresPermissions("cms:infoImg:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "内容图片集数据导入模板.xlsx";
    		List<InfoImg> list = Lists.newArrayList(); 
    		new ExportExcel("内容图片集数据", InfoImg.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/infoImg/?repage";
    }

}