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
import com.jhmis.modules.shop.entity.AreaCode;
import com.jhmis.modules.shop.service.AreaCodeService;

/**
 * 省市区码表Controller
 * @author hdx
 * @version 2019-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/areaCode")
public class AreaCodeController extends BaseController {

	@Autowired
	private AreaCodeService areaCodeService;
	
	@ModelAttribute
	public AreaCode get(@RequestParam(required=false) String id) {
		AreaCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = areaCodeService.get(id);
		}
		if (entity == null){
			entity = new AreaCode();
		}
		return entity;
	}
	
	/**
	 * 省市区码表列表页面
	 */
	@RequiresPermissions("shop:areaCode:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/areaCodeList";
	}
	
	/**
	 * 省市区码表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:areaCode:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(AreaCode areaCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AreaCode> page = areaCodeService.findPage(new Page<AreaCode>(request, response), areaCode); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑省市区码表表单页面
	 */
	@RequiresPermissions(value={"shop:areaCode:view","shop:areaCode:add","shop:areaCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(AreaCode areaCode, Model model) {
		model.addAttribute("areaCode", areaCode);
		if(StringUtils.isBlank(areaCode.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/areaCodeForm";
	}

	/**
	 * 保存省市区码表
	 */
	@RequiresPermissions(value={"shop:areaCode:add","shop:areaCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(AreaCode areaCode, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, areaCode)){
			return form(areaCode, model);
		}
		//新增或编辑表单保存
		areaCodeService.save(areaCode);//保存
		addMessage(redirectAttributes, "保存省市区码表成功");
		return "redirect:"+Global.getAdminPath()+"/shop/areaCode/?repage";
	}
	
	/**
	 * 删除省市区码表
	 */
	@ResponseBody
	@RequiresPermissions("shop:areaCode:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(AreaCode areaCode, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		areaCodeService.delete(areaCode);
		j.setMsg("删除省市区码表成功");
		return j;
	}
	
	/**
	 * 批量删除省市区码表
	 */
	@ResponseBody
	@RequiresPermissions("shop:areaCode:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			areaCodeService.delete(areaCodeService.get(id));
		}
		j.setMsg("删除省市区码表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:areaCode:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(AreaCode areaCode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "省市区码表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<AreaCode> page = areaCodeService.findPage(new Page<AreaCode>(request, response, -1), areaCode);
    		new ExportExcel("省市区码表", AreaCode.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出省市区码表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:areaCode:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<AreaCode> list = ei.getDataList(AreaCode.class);
			for (AreaCode areaCode : list){
				try{
					areaCodeService.save(areaCode);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条省市区码表记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条省市区码表记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入省市区码表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/areaCode/?repage";
    }
	
	/**
	 * 下载导入省市区码表数据模板
	 */
	@RequiresPermissions("shop:areaCode:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "省市区码表数据导入模板.xlsx";
    		List<AreaCode> list = Lists.newArrayList(); 
    		new ExportExcel("省市区码表数据", AreaCode.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/areaCode/?repage";
    }

}