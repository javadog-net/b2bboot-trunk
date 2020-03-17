/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.cms.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jhmis.common.config.Global;
import com.jhmis.common.json.AjaxJson;
import com.jhmis.common.utils.DateUtils;
import com.jhmis.common.utils.StringUtils;
import com.jhmis.common.utils.excel.ExportExcel;
import com.jhmis.common.utils.excel.ImportExcel;
import com.jhmis.core.persistence.Page;
import com.jhmis.core.web.BaseController;
import com.jhmis.modules.cms.entity.CmsConfig;
import com.jhmis.modules.cms.service.CmsConfigService;
import com.jhmis.modules.cms.utils.CmsEnum;
import com.jhmis.modules.cms.utils.CmsUtils;
import com.jhmis.modules.cms.utils.Htmlpath;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * cms配置管理表Controller
 * @author lydia
 * @version 2019-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/cmsConfig")
public class CmsConfigController extends BaseController {
	@Autowired
	private CmsConfigService cmsConfigService;
	@ModelAttribute
	public CmsConfig get(@RequestParam(required=false) String id) {
		CmsConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cmsConfigService.get(id);
		}
		if (entity == null){
			entity = new CmsConfig();
		}
		return entity;
	}
	
	/**
	 * cms配置管理表列表页面
	 */
	@RequiresPermissions("cms:cmsConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/cms/cmsConfigList";
	}
	
	/**
	 * cms配置管理表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfig:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(CmsConfig cmsConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CmsConfig> page = cmsConfigService.findPage(new Page<CmsConfig>(request, response), cmsConfig); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑cms配置管理表表单页面
	 */
	@RequiresPermissions(value={"cms:cmsConfig:view","cms:cmsConfig:add","cms:cmsConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CmsConfig cmsConfig, Model model) {

		if(StringUtils.isBlank(cmsConfig.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		//获取cms 配置管理信息
		cmsConfig = CmsUtils.getCmsConfig();
		//查询模板文件目录下的模板信息
		Map<String,String> map = Maps.newHashMap();
		File templateDir = new File(templatePath);
		if(!templateDir.exists()){
			templateDir.mkdirs();
		}
		//模板文件目录中查找对应风格的文件夹
		File fileName [] = templateDir.listFiles();
		Arrays.asList(fileName).forEach(e->{
			if(CmsEnum.TEMPLATE.getCode().equalsIgnoreCase(e.getName())){
				map.put(e.getName(),"默认模板");
			}else{
				map.put(e.getName(),e.getName());
			}
		});
		model.addAttribute("cmsConfig",cmsConfig);
		model.addAttribute("templateStyleMap",map);
		return "modules/cms/cmsConfigForm";
	}

	/**
	 * 保存cms配置管理表
	 */
	@RequiresPermissions(value={"cms:cmsConfig:add","cms:cmsConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CmsConfig cmsConfig, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, cmsConfig)){
			return form(cmsConfig, model);
		}
		cmsConfig.setHtmlPath(Htmlpath.htmlIndexPath(cmsConfig));
		//源文件目录  yml文件中配置的模板路径+cms配置中设置的模板风格+“resources”
		String templateResourceDir = templatePath+cmsConfig.getTempletStyle()+"/resources";
		//静态文件的目录    (yml文件中配置的静态文件路径+cms配置中的首页目录)
		String staticDir = staticPath+ Htmlpath.htmlIndexPath(cmsConfig)+"resources/";
		//新增或编辑表单保存
		cmsConfigService.save(cmsConfig,templateResourceDir,staticDir);//保存
		addMessage(redirectAttributes, "保存cms配置管理表成功");
		return "redirect:"+Global.getAdminPath()+"/cms/cmsConfig/form";
	}
	
	/**
	 * 删除cms配置管理表
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfig:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(CmsConfig cmsConfig, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		cmsConfigService.delete(cmsConfig);
		j.setMsg("删除cms配置管理表成功");
		return j;
	}
	
	/**
	 * 批量删除cms配置管理表
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfig:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			cmsConfigService.delete(cmsConfigService.get(id));
		}
		j.setMsg("删除cms配置管理表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("cms:cmsConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(CmsConfig cmsConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "cms配置管理表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CmsConfig> page = cmsConfigService.findPage(new Page<CmsConfig>(request, response, -1), cmsConfig);
    		new ExportExcel("cms配置管理表", CmsConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出cms配置管理表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("cms:cmsConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CmsConfig> list = ei.getDataList(CmsConfig.class);
			for (CmsConfig cmsConfig : list){
				try{
					cmsConfigService.save(cmsConfig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条cms配置管理表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条cms配置管理表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入cms配置管理表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsConfig/?repage";
    }
	
	/**
	 * 下载导入cms配置管理表数据模板
	 */
	@RequiresPermissions("cms:cmsConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "cms配置管理表数据导入模板.xlsx";
    		List<CmsConfig> list = Lists.newArrayList(); 
    		new ExportExcel("cms配置管理表数据", CmsConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/cms/cmsConfig/?repage";
    }


}