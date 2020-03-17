/**
 * Copyright &copy; 2005-2020 <a href="http://www.jhmis.com/">jhmis</a> All rights reserved.
 */
package com.jhmis.modules.shop.web;

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
import com.jhmis.modules.shop.entity.Industry;
import com.jhmis.modules.shop.service.IndustryService;

/**
 * 行业信息管理Controller
 * @author tity
 * @version 2018-07-22
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/industry")
public class IndustryController extends BaseController {

	@Autowired
	private IndustryService industryService;
	
	@ModelAttribute
	public Industry get(@RequestParam(required=false) String id) {
		Industry entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = industryService.get(id);
		}
		if (entity == null){
			entity = new Industry();
		}
		return entity;
	}
	
	/**
	 * 行业信息列表页面
	 */
	@RequiresPermissions("shop:industry:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/industryList";
	}
	
	/**
	 * 行业信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:industry:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Industry industry, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Industry> page = industryService.findPage(new Page<Industry>(request, response), industry); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑行业信息表单页面
	 */
	@RequiresPermissions(value={"shop:industry:view","shop:industry:add","shop:industry:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Industry industry, Model model) {
		model.addAttribute("industry", industry);
		return "modules/shop/industryForm";
	}

	/**
	 * 保存行业信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"shop:industry:add","shop:industry:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Industry industry, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, industry)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		industryService.save(industry);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存行业信息成功");
		return j;
	}
	
	/**
	 * 删除行业信息
	 */
	@ResponseBody
	@RequiresPermissions("shop:industry:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Industry industry, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		industryService.delete(industry);
		j.setMsg("删除行业信息成功");
		return j;
	}
	
	/**
	 * 批量删除行业信息
	 */
	@ResponseBody
	@RequiresPermissions("shop:industry:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			industryService.delete(industryService.get(id));
		}
		j.setMsg("删除行业信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:industry:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Industry industry, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "行业信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Industry> page = industryService.findPage(new Page<Industry>(request, response, -1), industry);
    		new ExportExcel("行业信息", Industry.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出行业信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:industry:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Industry> list = ei.getDataList(Industry.class);
			for (Industry industry : list){
				try{
					industryService.save(industry);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条行业信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条行业信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入行业信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/industry/?repage";
    }
	
	/**
	 * 下载导入行业信息数据模板
	 */
	@RequiresPermissions("shop:industry:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "行业信息数据导入模板.xlsx";
    		List<Industry> list = Lists.newArrayList(); 
    		new ExportExcel("行业信息数据", Industry.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/industry/?repage";
    }

}