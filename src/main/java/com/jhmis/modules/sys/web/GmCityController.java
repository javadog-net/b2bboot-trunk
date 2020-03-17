/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.sys.web;

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
import com.jhmis.modules.sys.entity.GmCity;
import com.jhmis.modules.sys.service.GmCityService;

/**
 * 工贸城市Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/gmCity")
public class GmCityController extends BaseController {

	@Autowired
	private GmCityService gmCityService;
	
	@ModelAttribute
	public GmCity get(@RequestParam(required=false) String id) {
		GmCity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gmCityService.get(id);
		}
		if (entity == null){
			entity = new GmCity();
		}
		return entity;
	}
	
	/**
	 * 工贸城市列表页面
	 */
	@RequiresPermissions("sys:gmCity:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/gmCityList";
	}
	
	/**
	 * 工贸城市列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmCity:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(GmCity gmCity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GmCity> page = gmCityService.findPage(new Page<GmCity>(request, response), gmCity); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工贸城市表单页面
	 */
	@RequiresPermissions(value={"sys:gmCity:view","sys:gmCity:add","sys:gmCity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(GmCity gmCity, Model model) {
		model.addAttribute("gmCity", gmCity);
		return "modules/sys/gmCityForm";
	}

	/**
	 * 保存工贸城市
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys:gmCity:add","sys:gmCity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(GmCity gmCity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, gmCity)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		gmCityService.save(gmCity);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存工贸城市成功");
		return j;
	}
	
	/**
	 * 删除工贸城市
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmCity:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(GmCity gmCity, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		gmCityService.delete(gmCity);
		j.setMsg("删除工贸城市成功");
		return j;
	}
	
	/**
	 * 批量删除工贸城市
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmCity:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			gmCityService.delete(gmCityService.get(id));
		}
		j.setMsg("删除工贸城市成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:gmCity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(GmCity gmCity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工贸城市"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<GmCity> page = gmCityService.findPage(new Page<GmCity>(request, response, -1), gmCity);
    		new ExportExcel("工贸城市", GmCity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工贸城市记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:gmCity:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<GmCity> list = ei.getDataList(GmCity.class);
			for (GmCity gmCity : list){
				try{
					gmCityService.save(gmCity);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工贸城市记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工贸城市记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工贸城市失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/gmCity/?repage";
    }
	
	/**
	 * 下载导入工贸城市数据模板
	 */
	@RequiresPermissions("sys:gmCity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工贸城市数据导入模板.xlsx";
    		List<GmCity> list = Lists.newArrayList(); 
    		new ExportExcel("工贸城市数据", GmCity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/gmCity/?repage";
    }

}