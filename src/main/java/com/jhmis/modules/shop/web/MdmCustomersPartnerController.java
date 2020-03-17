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
import com.jhmis.modules.shop.entity.MdmCustomersPartner;
import com.jhmis.modules.shop.service.MdmCustomersPartnerService;

/**
 * MDM四方关系Controller
 * @author hdx
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/mdmCustomersPartner")
public class MdmCustomersPartnerController extends BaseController {

	@Autowired
	private MdmCustomersPartnerService mdmCustomersPartnerService;
	
	@ModelAttribute
	public MdmCustomersPartner get(@RequestParam(required=false) String id) {
		MdmCustomersPartner entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mdmCustomersPartnerService.get(id);
		}
		if (entity == null){
			entity = new MdmCustomersPartner();
		}
		return entity;
	}
	
	/**
	 * MDM四方关系列表页面
	 */
	@RequiresPermissions("shop:mdmCustomersPartner:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/shop/mdmCustomersPartnerList";
	}
	
	/**
	 * MDM四方关系列表数据
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersPartner:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MdmCustomersPartner mdmCustomersPartner, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MdmCustomersPartner> page = mdmCustomersPartnerService.findPage(new Page<MdmCustomersPartner>(request, response), mdmCustomersPartner); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑MDM四方关系表单页面
	 */
	@RequiresPermissions(value={"shop:mdmCustomersPartner:view","shop:mdmCustomersPartner:add","shop:mdmCustomersPartner:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MdmCustomersPartner mdmCustomersPartner, Model model) {
		model.addAttribute("mdmCustomersPartner", mdmCustomersPartner);
		if(StringUtils.isBlank(mdmCustomersPartner.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/shop/mdmCustomersPartnerForm";
	}

	/**
	 * 保存MDM四方关系
	 */
	@RequiresPermissions(value={"shop:mdmCustomersPartner:add","shop:mdmCustomersPartner:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MdmCustomersPartner mdmCustomersPartner, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, mdmCustomersPartner)){
			return form(mdmCustomersPartner, model);
		}
		//新增或编辑表单保存
		mdmCustomersPartnerService.save(mdmCustomersPartner);//保存
		addMessage(redirectAttributes, "保存MDM四方关系成功");
		return "redirect:"+Global.getAdminPath()+"/shop/mdmCustomersPartner/?repage";
	}
	
	/**
	 * 删除MDM四方关系
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersPartner:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MdmCustomersPartner mdmCustomersPartner, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		mdmCustomersPartnerService.delete(mdmCustomersPartner);
		j.setMsg("删除MDM四方关系成功");
		return j;
	}
	
	/**
	 * 批量删除MDM四方关系
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersPartner:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			mdmCustomersPartnerService.delete(mdmCustomersPartnerService.get(id));
		}
		j.setMsg("删除MDM四方关系成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("shop:mdmCustomersPartner:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MdmCustomersPartner mdmCustomersPartner, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "MDM四方关系"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MdmCustomersPartner> page = mdmCustomersPartnerService.findPage(new Page<MdmCustomersPartner>(request, response, -1), mdmCustomersPartner);
    		new ExportExcel("MDM四方关系", MdmCustomersPartner.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出MDM四方关系记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("shop:mdmCustomersPartner:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MdmCustomersPartner> list = ei.getDataList(MdmCustomersPartner.class);
			for (MdmCustomersPartner mdmCustomersPartner : list){
				try{
					mdmCustomersPartnerService.save(mdmCustomersPartner);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条MDM四方关系记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条MDM四方关系记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入MDM四方关系失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/mdmCustomersPartner/?repage";
    }
	
	/**
	 * 下载导入MDM四方关系数据模板
	 */
	@RequiresPermissions("shop:mdmCustomersPartner:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "MDM四方关系数据导入模板.xlsx";
    		List<MdmCustomersPartner> list = Lists.newArrayList(); 
    		new ExportExcel("MDM四方关系数据", MdmCustomersPartner.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/mdmCustomersPartner/?repage";
    }

}