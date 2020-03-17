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
import com.jhmis.modules.shop.entity.MdmCustomersSource;
import com.jhmis.modules.shop.service.MdmCustomersSourceService;

/**
 * 直采专区mdm主数据源Controller
 * @author hdx
 * @version 2019-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/mdmCustomersSource")
public class MdmCustomersSourceController extends BaseController {

	@Autowired
	private MdmCustomersSourceService mdmCustomersSourceService;
	
	@ModelAttribute
	public MdmCustomersSource get(@RequestParam(required=false) String id) {
		MdmCustomersSource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mdmCustomersSourceService.get(id);
		}
		if (entity == null){
			entity = new MdmCustomersSource();
		}
		return entity;
	}
	
	/**
	 * 直采专区列表页面
	 */
	@RequiresPermissions("shop:mdmCustomersSource:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/mdmCustomersSourceList";
	}
	
	/**
	 * 直采专区列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersSource:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MdmCustomersSource mdmCustomersSource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MdmCustomersSource> page = mdmCustomersSourceService.findPage(new Page<MdmCustomersSource>(request, response), mdmCustomersSource); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑直采专区表单页面
	 */
	@RequiresPermissions(value={"shop:mdmCustomersSource:view","shop:mdmCustomersSource:add","shop:mdmCustomersSource:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MdmCustomersSource mdmCustomersSource, Model model) {
		model.addAttribute("mdmCustomersSource", mdmCustomersSource);
		if(StringUtils.isBlank(mdmCustomersSource.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/mdmCustomersSourceForm";
	}

	/**
	 * 保存直采专区
	 */
	@RequiresPermissions(value={"shop:mdmCustomersSource:add","shop:mdmCustomersSource:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MdmCustomersSource mdmCustomersSource, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, mdmCustomersSource)){
			return form(mdmCustomersSource, model);
		}
		//新增或编辑表单保存
		mdmCustomersSourceService.save(mdmCustomersSource);//保存
		addMessage(redirectAttributes, "保存直采专区成功");
		return "redirect:"+Global.getAdminPath()+"/shop/mdmCustomersSource/?repage";
	}
	
	/**
	 * 删除直采专区
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersSource:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MdmCustomersSource mdmCustomersSource, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		mdmCustomersSourceService.delete(mdmCustomersSource);
		j.setMsg("删除直采专区成功");
		return j;
	}
	
	/**
	 * 批量删除直采专区
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersSource:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			mdmCustomersSourceService.delete(mdmCustomersSourceService.get(id));
		}
		j.setMsg("删除直采专区成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersSource:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MdmCustomersSource mdmCustomersSource, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "直采专区"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MdmCustomersSource> page = mdmCustomersSourceService.findPage(new Page<MdmCustomersSource>(request, response, -1), mdmCustomersSource);
    		new ExportExcel("直采专区", MdmCustomersSource.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出直采专区记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:mdmCustomersSource:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MdmCustomersSource> list = ei.getDataList(MdmCustomersSource.class);
			for (MdmCustomersSource mdmCustomersSource : list){
				try{
					mdmCustomersSourceService.save(mdmCustomersSource);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条直采专区记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条直采专区记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入直采专区失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/mdmCustomersSource/?repage";
    }
	
	/**
	 * 下载导入直采专区数据模板
	 */
	@RequiresPermissions("shop:mdmCustomersSource:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "直采专区数据导入模板.xlsx";
    		List<MdmCustomersSource> list = Lists.newArrayList(); 
    		new ExportExcel("直采专区数据", MdmCustomersSource.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/mdmCustomersSource/?repage";
    }

}